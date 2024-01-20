package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileWeaponItem.class)
public class BowItemMixin {
    @Inject(method = "getEnchantmentValue", at = @At("RETURN"), cancellable = true)
    public void onGetEnchantmentValue(CallbackInfoReturnable<Integer> cir) {
        if (!Feature.isEnabled(EnchantingFeature.class) || !EnchantingFeature.toolsEnchantabilityFix)
            return;
        if ((Object)this == Items.BOW)
            cir.setReturnValue(15);
        else if ((Object)this == Items.CROSSBOW)
            cir.setReturnValue(11);

    }
}
