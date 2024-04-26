package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.insanelib.base.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.passablefoliage.enchantment.LeafWalkerEnchantment;

@Mixin(LeafWalkerEnchantment.class)
public class LeafWalkerEnchantmentMixin {
    @Inject(method = "isCurse", at = @At("RETURN"), remap = false, cancellable = true)
    public void isCurse(CallbackInfoReturnable<Boolean> cir) {
        if (Feature.isEnabled(EnchantingFeature.class)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isDiscoverable", at = @At("RETURN"), remap = false, cancellable = true)
    public void isDiscoverable(CallbackInfoReturnable<Boolean> cir) {
        if (Feature.isEnabled(EnchantingFeature.class)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isTradeable", at = @At("RETURN"), remap = false, cancellable = true)
    public void isTradeable(CallbackInfoReturnable<Boolean> cir) {
        if (Feature.isEnabled(EnchantingFeature.class)) {
            cir.setReturnValue(true);
        }
    }
}
