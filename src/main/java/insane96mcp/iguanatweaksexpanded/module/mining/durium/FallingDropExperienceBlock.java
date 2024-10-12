package insane96mcp.iguanatweaksexpanded.module.mining.durium;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FallingDropExperienceBlock extends FallingBlock {
    private final IntProvider xpRange;

    public FallingDropExperienceBlock(Properties pProperties) {
        this(pProperties, ConstantInt.of(0));
    }

    public FallingDropExperienceBlock(BlockBehaviour.Properties pProperties, IntProvider pXpRange) {
        super(pProperties);
        this.xpRange = pXpRange;
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader level, net.minecraft.util.RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return silkTouchLevel == 0 ? this.xpRange.sample(randomSource) : 0;
    }
}
