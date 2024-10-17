package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import insane96mcp.iguanatweaksexpanded.network.message.SyncITEEnchantingTableStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ITEEnchantingTableBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    public float rot;
    public float oRot;
    public float tRot;
    private static final RandomSource RANDOM = RandomSource.create();
    protected NonNullList<ItemStack> items = NonNullList.withSize(ITEEnchantingTableMenu.SLOT_COUNT, ItemStack.EMPTY);
    public Map<Enchantment, Integer> learnedEnchantments = new HashMap<>();
    protected ITEEnchantingTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EnchantingFeature.ENCHANTING_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
        if (tag.contains("treasure_enchantments")) {
            ListTag listtag = tag.getList("treasure_enchantments", CompoundTag.TAG_STRING);
            for (int i = 0; i < listtag.size(); i++) {
                String enchantment = listtag.getString(i);
                Enchantment enchantment1 = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(enchantment));
                if (enchantment1 == null)
                    continue;
                this.learnedEnchantments.put(enchantment1, enchantment1.getMaxLevel() / 2);
            }
            tag.remove("treasure_enchantments");
        }
        ListTag listTag = tag.getList("learned_enchantments", CompoundTag.TAG_COMPOUND);
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag compoundTag = listTag.getCompound(i);
            String enchantmentId = compoundTag.getString("id");
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(enchantmentId));
            if (enchantment == null)
                continue;
            int lvl = compoundTag.getInt("lvl");
            this.learnedEnchantments.put(enchantment, lvl);
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        ListTag listTag = new ListTag();
        for (Map.Entry<Enchantment, Integer> learnedEnchantment : this.learnedEnchantments.entrySet()) {
            ResourceLocation enchantmentId = ForgeRegistries.ENCHANTMENTS.getKey(learnedEnchantment.getKey());
            if (enchantmentId == null)
                continue;
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("id", enchantmentId.toString());
            compoundTag.putInt("lvl", learnedEnchantment.getValue());
            listTag.add(compoundTag);
        }
        tag.put("learned_enchantments", listTag);
    }

    public boolean knowsEnchantment(Enchantment enchantment, int lvl) {
        return this.learnedEnchantments.containsKey(enchantment) && this.learnedEnchantments.get(enchantment) >= lvl;
    }

    public void learnEnchantment(Enchantment enchantment, int lvl) {
        if (this.knowsEnchantment(enchantment, lvl))
            return;
        this.learnedEnchantments.put(enchantment, lvl);
        this.setChanged();
    }

    public void forgetEnchantment(Enchantment enchantment) {
        this.learnedEnchantments.remove(enchantment);
        this.setChanged();
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }


    public static void clientTick(Level pLevel, BlockPos pos, BlockState pState, ITEEnchantingTableBlockEntity blockEntity) {
        bookAnimationTick(pLevel, pos, pState, blockEntity);
    }

    public static void bookAnimationTick(Level pLevel, BlockPos pPos, BlockState pState, ITEEnchantingTableBlockEntity pBlockEntity) {
        pBlockEntity.oOpen = pBlockEntity.open;
        pBlockEntity.oRot = pBlockEntity.rot;
        Player player = pLevel.getNearestPlayer((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, 3.0D, false);
        if (player != null) {
            double d0 = player.getX() - ((double)pPos.getX() + 0.5D);
            double d1 = player.getZ() - ((double)pPos.getZ() + 0.5D);
            pBlockEntity.tRot = (float) Mth.atan2(d1, d0);
            pBlockEntity.open += 0.1F;
            if (pBlockEntity.open < 0.5F || RANDOM.nextInt(40) == 0) {
                float f1 = pBlockEntity.flipT;

                do {
                    pBlockEntity.flipT += (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
                } while(f1 == pBlockEntity.flipT);
            }
        }
        else {
            pBlockEntity.tRot += 0.02F;
            pBlockEntity.open -= 0.1F;
        }

        while (pBlockEntity.rot >= (float)Math.PI) {
            pBlockEntity.rot -= ((float)Math.PI * 2F);
        }

        while (pBlockEntity.rot < -(float)Math.PI) {
            pBlockEntity.rot += ((float)Math.PI * 2F);
        }

        while (pBlockEntity.tRot >= (float)Math.PI) {
            pBlockEntity.tRot -= ((float)Math.PI * 2F);
        }

        while (pBlockEntity.tRot < -(float)Math.PI) {
            pBlockEntity.tRot += ((float)Math.PI * 2F);
        }

        float f2;
        for (f2 = pBlockEntity.tRot - pBlockEntity.rot; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) {
        }

        while (f2 < -(float)Math.PI) {
            f2 += ((float)Math.PI * 2F);
        }

        pBlockEntity.rot += f2 * 0.4F;
        pBlockEntity.open = Mth.clamp(pBlockEntity.open, 0.0F, 1.0F);
        ++pBlockEntity.time;
        pBlockEntity.oFlip = pBlockEntity.flip;
        float f = (pBlockEntity.flipT - pBlockEntity.flip) * 0.4F;
        float f3 = 0.2F;
        f = Mth.clamp(f, -f3, f3);
        pBlockEntity.flipA += (f - pBlockEntity.flipA) * 0.9F;
        pBlockEntity.flip += pBlockEntity.flipA;
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.enchant");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new ITEEnchantingTableMenu(pContainerId, pInventory, this, ContainerLevelAccess.create(this.level, this.worldPosition));
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return this.items.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return ContainerHelper.removeItem(this.items, pSlot, pAmount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(this.items, pSlot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.items.set(slot, stack);
        if (this.level instanceof ServerLevel serverLevel)
            SyncITEEnchantingTableStatus.sync(serverLevel, this.getBlockPos(), this);
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    public @Nullable ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler> handler : handlers)
            handler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }
}
