package insane96mcp.survivalreimagined.mixin;

import insane96mcp.survivalreimagined.module.misc.feature.BeaconConduit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

	@Inject(at = @At("HEAD"), method = "applyEffects", cancellable = true)
	private static void applyEffects(Level level, BlockPos blockPos, int layers, MobEffect effectPrimary, MobEffect effectSecondary, CallbackInfo ci) {
		if (BeaconConduit.beaconApplyEffects(level, blockPos, layers, effectPrimary, effectSecondary))
			ci.cancel();
	}
}