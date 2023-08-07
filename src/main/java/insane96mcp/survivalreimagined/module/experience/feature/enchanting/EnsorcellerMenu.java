package insane96mcp.survivalreimagined.module.experience.feature.enchanting;

import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;

import java.util.List;

public class EnsorcellerMenu extends AbstractContainerMenu {
    public static final int ITEM_SLOT = 0;
    public static final int SLOT_COUNT = 1;
    public static final int DATA_COUNT = 3;
    private static final int INV_SLOT_START = ITEM_SLOT + 1;
    private static final int INV_SLOT_END = INV_SLOT_START + 27;
    private static final int USE_ROW_SLOT_START = INV_SLOT_END;
    private static final int USE_ROW_SLOT_END = USE_ROW_SLOT_START + 9;
    private final Container container;
    private final ContainerData data;
    private final ContainerLevelAccess access;
    protected final Level level;
    public static final int MAX_STEPS = 12;
    public static final int LVL_ON_JACKPOT = 16;

    public EnsorcellerMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, new SimpleContainer(SLOT_COUNT), new SimpleContainerData(DATA_COUNT), ContainerLevelAccess.NULL);
    }

    protected EnsorcellerMenu(int pContainerId, Inventory pPlayerInventory, Container pContainer, ContainerData pData, ContainerLevelAccess access) {
        super(EnchantingFeature.ENSORCELLER_MENU_TYPE.get(), pContainerId);
        checkContainerSize(pContainer, SLOT_COUNT);
        checkContainerDataCount(pData, DATA_COUNT);
        this.container = pContainer;
        this.data = pData;
        this.access = access;
        this.level = pPlayerInventory.player.level();
        this.addSlot(new Slot(pContainer, ITEM_SLOT, 26, 16) {
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
            public int getMaxStackSize() {
                return 1;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
        }
        this.addDataSlots(pData);
    }

    @Override
    public boolean clickMenuButton(Player player, int pId) {
        //Roll and enchant buttons
        if (pId < 0 || pId > 1) {
            Util.logAndPauseIfInIde(player.getName() + " pressed invalid button id: " + pId);
            return false;
        }

        //Roll
        if (pId == 0) {
            if (player.experienceLevel <= 0 && !player.getAbilities().instabuild)
                return false;
            if (this.getSteps() == MAX_STEPS)
                return false;

            //This is executed only server side
            this.access.execute((level, blockPos) -> {
                this.incrementSteps(this.level.random.nextInt(6) + 1);
                this.incrementLevelsUsed();
                if (!player.getAbilities().instabuild)
                    ((ServerPlayer)player).setExperienceLevels(player.experienceLevel - 1);
                if (this.getSteps() > MAX_STEPS) {
                    level.playSound(null, blockPos, SoundEvents.RESPAWN_ANCHOR_DEPLETE.get(), SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.8F);
                    this.setSteps(0);
                }
                else if (this.getSteps() == MAX_STEPS) {
                    level.playSound(null, blockPos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 1.2F);
                }
                else {
                    level.playSound(null, blockPos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.8F);
                }
            });
        }
        //Enchant
        else if (this.getSteps() > 0) {
            ItemStack enchantableItem = this.container.getItem(ITEM_SLOT);
            if (enchantableItem.isEmpty())
                return false;

            //This is executed only server side
            this.access.execute((level, blockPos) -> {
                ItemStack result = enchantableItem;
                List<EnchantmentInstance> enchantments = this.getEnchantmentList(enchantableItem, this.getSteps() == MAX_STEPS ? LVL_ON_JACKPOT : this.getSteps());
                if (!enchantments.isEmpty()) {
                    player.onEnchantmentPerformed(enchantableItem, 0);
                    boolean isBook = enchantableItem.is(Items.BOOK);
                    if (isBook) {
                        result = new ItemStack(Items.ENCHANTED_BOOK);
                        CompoundTag itemTag = enchantableItem.getTag();
                        if (itemTag != null)
                            result.setTag(itemTag);

                        this.container.setItem(ITEM_SLOT, result);
                    }

                    for (EnchantmentInstance enchantmentInstance : enchantments) {
                        if (isBook)
                            EnchantedBookItem.addEnchantment(result, enchantmentInstance);
                        else
                            result.enchant(enchantmentInstance.enchantment, enchantmentInstance.level);
                    }

                    player.awardStat(Stats.ENCHANT_ITEM);
                    if (player instanceof ServerPlayer)
                        CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer) player, result, this.getLevelsUsed());

                    this.container.setChanged();
                    this.setSteps(0);
                    this.slotsChanged(this.container);
                    level.playSound(null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.25F);
                }
            });
        }
        this.updateCanEnchant();
        this.broadcastChanges();
        return true;
    }

    private List<EnchantmentInstance> getEnchantmentList(ItemStack pStack, int pLevel) {
        List<EnchantmentInstance> list = EnchantmentHelper.selectEnchantment(this.level.random, pStack, pLevel, false);
        if (pStack.is(Items.BOOK) && list.size() > 1) {
            list.remove(this.level.random.nextInt(list.size()));
        }

        return list;
    }

    private void updateCanEnchant() {
        ItemStack stack = this.container.getItem(ITEM_SLOT);
        this.setCanEnchant(!stack.isEmpty() && stack.isEnchantable() && this.getSteps() > 0);
    }

    public int getSteps() {
        return this.data.get(EnsorcellerBlockEntity.DATA_STEPS);
    }

    public void setSteps(int steps) {
        this.data.set(EnsorcellerBlockEntity.DATA_STEPS, steps);
    }

    public void incrementSteps(int steps) {
        setSteps(getSteps() + steps);
    }

    public int getLevelsUsed() {
        return this.data.get(EnsorcellerBlockEntity.DATA_ROLLS_PERFORMED);
    }

    public void incrementLevelsUsed() {
        this.data.set(EnsorcellerBlockEntity.DATA_ROLLS_PERFORMED, this.data.get(EnsorcellerBlockEntity.DATA_ROLLS_PERFORMED) + 1);
    }

    public boolean canEnchant() {
        return this.data.get(EnsorcellerBlockEntity.DATA_CAN_ENCHANT) == 1;
    }

    public void setCanEnchant(boolean canEnchant) {
        this.data.set(EnsorcellerBlockEntity.DATA_CAN_ENCHANT, canEnchant ? 1 : 0);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slotClicked = this.slots.get(pIndex);
        if (!slotClicked.hasItem())
            return itemstack;

        ItemStack itemInSlot = slotClicked.getItem();
        itemstack = itemInSlot.copy();
        if (pIndex == ITEM_SLOT) {
            if (!this.moveItemStackTo(itemInSlot, INV_SLOT_START, USE_ROW_SLOT_END, true))
                return ItemStack.EMPTY;
        }
        else {
            if (this.slots.get(ITEM_SLOT).hasItem() || !this.slots.get(ITEM_SLOT).mayPlace(itemInSlot))
                return ItemStack.EMPTY;

            ItemStack itemstack2 = itemInSlot.copyWithCount(1);
            itemInSlot.shrink(1);
            this.slots.get(ITEM_SLOT).setByPlayer(itemstack2);
        }

        if (itemInSlot.isEmpty())
            slotClicked.setByPlayer(ItemStack.EMPTY);
        else
            slotClicked.setChanged();

        if (itemInSlot.getCount() == itemstack.getCount())
            return ItemStack.EMPTY;

        slotClicked.onTake(pPlayer, itemInSlot);

        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.container.stillValid(pPlayer);
    }
}