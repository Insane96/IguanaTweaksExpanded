package insane96mcp.iguanatweaksexpanded.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(IceBlock.class)
public interface IceBlockInvoker {
    @Invoker("melt")
    void invokeMelt(BlockState pState, Level pLevel, BlockPos pPos);
}
