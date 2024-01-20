package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.item.FishingRodItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {
    @Inject(method = "getEnchantmentValue", at = @At("RETURN"), cancellable = true)
    public void onGetEnchantmentValue(CallbackInfoReturnable<Integer> cir) {
        if (!Feature.isEnabled(EnchantingFeature.class) || !EnchantingFeature.toolsEnchantabilityFix)
            return;
        cir.setReturnValue(15);
    }
}
