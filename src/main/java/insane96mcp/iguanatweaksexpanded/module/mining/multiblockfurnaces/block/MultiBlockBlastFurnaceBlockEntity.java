package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block;

import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.inventory.MultiBlockBlastFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class MultiBlockBlastFurnaceBlockEntity extends AbstractMultiBlockFurnaceBlockEntity {
    public MultiBlockBlastFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MultiBlockFurnaces.BLAST_FURNACE_BLOCK_ENTITY_TYPE.get(), pPos, pBlockState, MultiBlockFurnaces.BLASTING_RECIPE_TYPE.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.blast_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new MultiBlockBlastFurnaceMenu(pContainerId, pInventory, this, this.dataAccess);
    }

    @Override
    public int[] getIngredientSlots() {
        return MultiBlockBlastFurnaceMenu.getIngredientSlots();
    }

    @Override
    protected boolean canOverflowFuel() {
        return false;
    }

    @Override
    protected int maxOverflow() {
        return 0;
    }

    @Override
    protected boolean shouldBurnTimeTick() {
        return true;
    }

    protected int getBurnDuration(ItemStack pFuel) {
        return super.getBurnDuration(pFuel) / 2;
    }
}
