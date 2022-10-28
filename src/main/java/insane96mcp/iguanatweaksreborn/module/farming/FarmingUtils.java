package insane96mcp.iguanatweaksreborn.module.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FarmingUtils {

    /**
     * @return true if the block is affected by the block below
     */
    public static boolean isAffectedByFarmland(LevelAccessor levelAccessor, BlockPos cropPos) {
        BlockState state = levelAccessor.getBlockState(cropPos);
        Block block = state.getBlock();
        return block instanceof CropBlock || block instanceof StemBlock;
    }

    /**
     * @return true if the block is on wet farmland
     */
    public static boolean isCropOnWetFarmland(LevelAccessor levelAccessor, BlockPos cropPos) {
        BlockState sustainState = levelAccessor.getBlockState(cropPos.below());
        if (!(sustainState.getBlock() instanceof FarmBlock))
            return false;
        int moisture = sustainState.getValue(FarmBlock.MOISTURE);
        return moisture >= 7;
    }


    /**
     * @return true if the block is on farmland
     */
    public static boolean isCropOnFarmland(LevelAccessor levelAccessor, BlockPos cropPos) {
        BlockState sustainState = levelAccessor.getBlockState(cropPos.below());
        return sustainState.getBlock() instanceof FarmBlock;
    }
}
