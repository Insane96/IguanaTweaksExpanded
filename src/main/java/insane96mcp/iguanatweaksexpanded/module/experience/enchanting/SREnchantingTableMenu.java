package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class SREnchantingTableMenu extends AbstractContainerMenu {
    public static final int ITEM_SLOT = 0;
    public static final int CATALYST_SLOT = 1;
    public static final int SLOT_COUNT = 2;
    private static final int INV_SLOT_START = CATALYST_SLOT + 1;
    private static final int INV_SLOT_END = INV_SLOT_START + 27;
    private static final int USE_ROW_SLOT_START = INV_SLOT_END;
    private static final int USE_ROW_SLOT_END = USE_ROW_SLOT_START + 9;
    private final Container container;
    private final ContainerLevelAccess access;
    protected final Level level;
    public DataSlot maxCost = DataSlot.standalone();

    public SREnchantingTableMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, new SimpleContainer(SLOT_COUNT), ContainerLevelAccess.NULL);
    }

    protected SREnchantingTableMenu(int pContainerId, Inventory pPlayerInventory, Container pContainer, ContainerLevelAccess access) {
        super(EnchantingFeature.ENCHANTING_TABLE_MENU_TYPE.get(), pContainerId);
        checkContainerSize(pContainer, SLOT_COUNT);
        this.container = pContainer;
        this.access = access;
        this.level = pPlayerInventory.player.level();
        this.addSlot(new Slot(pContainer, ITEM_SLOT, 19, 18) {
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void setChanged() {
                super.setChanged();
                SREnchantingTableMenu.this.slotsChanged(this.container);
            }

            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.isEnchantable();
            }
        });
        /*this.addSlot(new Slot(pContainer, CATALYST_SLOT, 28, 18) {
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Tags.Items.ENCHANTING_FUELS);
            }
        });*/

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
        }
        this.addDataSlot(this.maxCost);

        this.access.execute((level, blockPos) -> this.updateMaxCost(this.container.getItem(0), level, blockPos));
    }

    @Override
    public void slotsChanged(Container pContainer) {
        this.access.execute((level, blockPos) -> this.updateMaxCost(this.container.getItem(0), level, blockPos));
    }

    private void updateMaxCost(ItemStack stack, Level level, BlockPos blockPos) {
        if (stack.isEmpty() || !stack.isEnchantable() || stack.isEnchanted()) {
            this.maxCost.set(0);
        }
        else {
            float enchantingPower = 0;

            for (BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
                if (EnchantmentTableBlock.isValidBookShelf(level, blockPos, blockpos)) {
                    enchantingPower += level.getBlockState(blockPos.offset(blockpos)).getEnchantPowerBonus(level, blockPos.offset(blockpos));
                }
            }
            //float leftoverPower = 0;
            if (enchantingPower > 15f) {
                //leftoverPower = Math.min(enchantingPower - 20f, 5f);
                enchantingPower = 15f;
            }
            float p = 0.5f;
            //float maxCost = 3;
            if (stack.getTag() != null && stack.getTag().contains(EnchantingFeature.INFUSED_ITEM))
                p = 1f;
            float maxCost = stack.getEnchantmentValue() * p * (enchantingPower / 15f) + 3;
            //maxCost *= (enchantingPower / 15f);
            this.maxCost.set((int) maxCost);
        }
        this.broadcastChanges();
    }

    public void updateEnchantmentsChosen(List<EnchantmentInstance> enchantments) {
        this.access.execute((level, blockPos) -> {
            CompoundTag tag = this.container.getItem(0).getOrCreateTag();
            if (enchantments.isEmpty()) {
                if (tag.contains("PendingEnchantments", CompoundTag.TAG_LIST))
                    tag.remove("PendingEnchantments");
                return;
            }
            if (!tag.contains("PendingEnchantments", CompoundTag.TAG_LIST)) {
                tag.put("PendingEnchantments", new ListTag());
            }
            ListTag pendingEnchantments = tag.getList("PendingEnchantments", CompoundTag.TAG_COMPOUND);
            pendingEnchantments.clear();
            for (EnchantmentInstance enchantmentInstance : enchantments) {
                pendingEnchantments.add(EnchantmentHelper.storeEnchantment(ForgeRegistries.ENCHANTMENTS.getKey(enchantmentInstance.enchantment), (byte)enchantmentInstance.level));
            }
        });
    }

    @Override
    public boolean clickMenuButton(Player player, int pId) {
        if (pId != 0) {
            Util.logAndPauseIfInIde(player.getName() + " pressed invalid button id: " + pId);
            return false;
        }
        this.access.execute((level, blockPos) -> {
            ItemStack stack = this.container.getItem(0);
            if (stack.getTag() == null || !stack.getTag().contains("PendingEnchantments"))
                return;
            ListTag enchantmentsListTag = stack.getTag().getList("PendingEnchantments", CompoundTag.TAG_COMPOUND);
            float cost = 0;
            for (int i = 0; i < enchantmentsListTag.size(); ++i) {
                CompoundTag compoundtag = enchantmentsListTag.getCompound(i);
                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(compoundtag.getString("id")));
                if (enchantment != null) {
                    stack.enchant(enchantment, compoundtag.getShort("lvl"));
                }
                cost += EnchantingFeature.getCost(enchantment, compoundtag.getShort("lvl"));
            }
            player.onEnchantmentPerformed(stack, (int) cost);
            //ItemStack lapis = this.container.getItem(1);
            //lapis.shrink((int)(cost / 5));
            level.playSound(null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1f, 1f);
            //this.container.getItem(0).enchant(Enchantments.SHARPNESS, 1);

        });
        this.broadcastChanges();
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slotClicked = this.slots.get(pIndex);
        if (!slotClicked.hasItem())
            return itemstack;

        ItemStack itemClicked = slotClicked.getItem();
        itemstack = itemClicked.copy();
        if (pIndex == ITEM_SLOT) {
            if (!this.moveItemStackTo(itemClicked, INV_SLOT_START, USE_ROW_SLOT_END, true))
                return ItemStack.EMPTY;
        }
        /*else if (pIndex == CATALYST_SLOT) {
            if (!this.moveItemStackTo(itemClicked, INV_SLOT_START, USE_ROW_SLOT_END, true))
                return ItemStack.EMPTY;
        }*/
        else {
            if (!this.slots.get(ITEM_SLOT).hasItem() && this.slots.get(ITEM_SLOT).mayPlace(itemClicked)) {
                ItemStack itemstack2 = itemClicked.copyWithCount(1);
                itemClicked.shrink(1);
                this.slots.get(ITEM_SLOT).setByPlayer(itemstack2);
            }
            else if (this.slots.get(CATALYST_SLOT).mayPlace(itemClicked)) {
                if (!this.moveItemStackTo(itemClicked, CATALYST_SLOT, CATALYST_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

        }

        if (itemClicked.isEmpty())
            slotClicked.setByPlayer(ItemStack.EMPTY);
        else
            slotClicked.setChanged();

        if (itemClicked.getCount() == itemstack.getCount())
            return ItemStack.EMPTY;

        slotClicked.onTake(pPlayer, itemClicked);

        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.container.stillValid(pPlayer);
    }
}
