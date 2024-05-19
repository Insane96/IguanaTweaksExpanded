package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends ProjectileWeaponItem {
    public CrossbowItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "getChargeDuration", at = @At(value = "RETURN"), cancellable = true)
    private static void onGetChargeDuration(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem() instanceof CrossbowItem crossbowItem && stack.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_SLOW_CHARGE.get()) > 0) {
            cir.setReturnValue(cir.getReturnValue() + 10);
        }
    }
}
