package insane96mcp.survivalreimagined.module.farming.block;

import insane96mcp.survivalreimagined.module.farming.feature.BoneMeal;
import insane96mcp.survivalreimagined.module.farming.feature.Crops;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;

public class CompostedFarmlandBlock extends FarmBlock {
    public CompostedFarmlandBlock(Properties properties) {
        super(properties);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnToFarmland(null, state, level, pos);
        }
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int moisture = state.getValue(MOISTURE);
        boolean isUnderCrops = isUnderCrops(level, pos);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (moisture > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
            }
            else {
                turnToFarmland(null, state, level, pos);
            }
        }
        else if (moisture < MAX_MOISTURE) {
            level.setBlock(pos, state.setValue(MOISTURE, MAX_MOISTURE), 2);
        }

        if (moisture == MAX_MOISTURE && isUnderCrops) {
            for (int e = 0; e < BoneMeal.compostedFarmlandExtraTicks; e++) {
                level.getBlockState(pos.above()).randomTick(level, pos.above(), random);
            }
            //Bonemeal particles
            level.levelEvent(2005, pos.above(), 0);
        }
        if (random.nextDouble() < BoneMeal.compostedFarmlandChanceToDecay) {
            turnToFarmland(null, state, level, pos);
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        PlantType type = plantable.getPlantType(world, pos.relative(facing));
        return type == PlantType.CROP;
    }

    public static void turnToFarmland(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        BlockState farmland = Blocks.FARMLAND.defaultBlockState().setValue(MOISTURE, state.getValue(MOISTURE));
        level.setBlockAndUpdate(pos, farmland);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, farmland));
    }

    private static boolean isUnderCrops(BlockGetter p_53251_, BlockPos p_53252_) {
        BlockState plant = p_53251_.getBlockState(p_53252_.above());
        BlockState state = p_53251_.getBlockState(p_53252_);
        return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(p_53251_, p_53252_, Direction.UP, (net.minecraftforge.common.IPlantable)plant.getBlock());
    }

    private static boolean isNearWater(LevelReader p_53259_, BlockPos p_53260_) {
        BlockState state = p_53259_.getBlockState(p_53260_);
        for(BlockPos blockpos : BlockPos.betweenClosed(p_53260_.offset(-Crops.getWaterHydrationRadius(), 0, -Crops.getWaterHydrationRadius()), p_53260_.offset(Crops.getWaterHydrationRadius(), 1, Crops.getWaterHydrationRadius()))) {
            if (state.canBeHydrated(p_53259_, p_53260_, p_53259_.getFluidState(blockpos), blockpos)) {
                return true;
            }
        }

        return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(p_53259_, p_53260_);
    }
}
