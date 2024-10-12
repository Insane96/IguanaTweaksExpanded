package insane96mcp.iguanatweaksexpanded.module.mining.durium;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class FallingDuriumOreBlock extends FallingBlock {
    private final IntProvider xpRange;
    private final Block baseBlock;

    public FallingDuriumOreBlock(BlockBehaviour.Properties pProperties, IntProvider pXpRange, Block baseBlock) {
        super(pProperties);
        this.xpRange = pXpRange;
        this.baseBlock = baseBlock;
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader level, net.minecraft.util.RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return silkTouchLevel == 0 ? this.xpRange.sample(randomSource) : 0;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (isFree(pLevel.getBlockState(pPos.below())) && pPos.getY() >= pLevel.getMinBuildHeight()) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(pLevel, pPos, pState);
            fallingblockentity.disableDrop();
        }
    }

    public void onBrokenAfterFall(Level pLevel, BlockPos pPos, FallingBlockEntity pFallingBlock) {
        Vec3 vec3 = pFallingBlock.getBoundingBox().getCenter();
        pLevel.levelEvent(2001, BlockPos.containing(vec3), Block.getId(pFallingBlock.getBlockState()));
        pFallingBlock.spawnAtLocation(this.baseBlock);
        pLevel.gameEvent(pFallingBlock, GameEvent.BLOCK_DESTROY, vec3);
    }
}
