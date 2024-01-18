package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingTransformRecipe.class)
public class SmithingTransformRecipeMixin {

    @Inject(at = @At(value = "RETURN"), method = "isBaseIngredient", cancellable = true)
    private void onCheckBaseIngredient(ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        if (pStack.isEnchanted() && Feature.isEnabled(EnchantingFeature.class) && EnchantingFeature.noEnchantedSmithing)
            cir.setReturnValue(false);
    }
}
