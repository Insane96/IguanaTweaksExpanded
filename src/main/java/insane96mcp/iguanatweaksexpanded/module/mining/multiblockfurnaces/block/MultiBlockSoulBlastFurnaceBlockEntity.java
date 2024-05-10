package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.inventory.MultiBlockSoulBlastFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public class MultiBlockSoulBlastFurnaceBlockEntity extends AbstractMultiBlockFurnaceBlockEntity {
    public MultiBlockSoulBlastFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MultiBlockFurnaces.SOUL_BLAST_FURNACE_BLOCK_ENTITY_TYPE.get(), pPos, pBlockState, MultiBlockFurnaces.SOUL_BLASTING_RECIPE_TYPE.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(IguanaTweaksExpanded.MOD_ID + ".container.soul_blast_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new MultiBlockSoulBlastFurnaceMenu(pContainerId, pInventory, this, this.dataAccess);
    }

    @Override
    public int[] getIngredientSlots() {
        return  MultiBlockSoulBlastFurnaceMenu.getIngredientSlots();
    }

    @Override
    protected boolean canOverflowFuel() {
        return true;
    }

    @Override
    protected int maxOverflow() {
        return (super.getBurnDuration(new ItemStack(Items.LAVA_BUCKET)) / 2) * 4;
    }

    protected int getBurnDuration(ItemStack pFuel) {
        return super.getBurnDuration(pFuel) / 2;
    }
}
