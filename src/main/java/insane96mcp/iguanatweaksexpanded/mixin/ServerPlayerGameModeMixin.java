package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.event.ITEEventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {
    @Shadow protected ServerLevel level;

    @Shadow @Final protected ServerPlayer player;

    @Unique private BlockState iguanaTweaksExpanded$oldState;

    @Inject(method = "destroyBlock", at = @At("HEAD"))
    public void onDestroyBlock(BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        this.iguanaTweaksExpanded$oldState = this.level.getBlockState(pPos);
    }

    @Inject(method = "destroyBlock", at = @At("RETURN"))
    public void onDestroyBlockReturn(BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue())
            return;
        ITEEventFactory.onBlockDestroyPosts(this.level, pPos, this.iguanaTweaksExpanded$oldState, this.player);
    }
}
