package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingTransformRecipe.class)
public class SmithingTransformRecipeMixin {
    @Inject(at = @At(value = "RETURN"), method = "matches", cancellable = true)
    private void onMatching(Container pContainer, Level pLevel, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = pContainer.getItem(1);
        if (stack.isEnchanted() && !EnchantingFeature.hasOnlyCurses(stack) && Feature.isEnabled(EnchantingFeature.class) && EnchantingFeature.noEnchantedSmithing)
            cir.setReturnValue(false);
    }

    @Inject(at = @At(value = "RETURN"), method = "assemble")
    private void onAssemble(Container pContainer, RegistryAccess pRegistryAccess, CallbackInfoReturnable<ItemStack> cir) {
        if (cir.getReturnValue().getDamageValue() > cir.getReturnValue().getMaxDamage())
            cir.getReturnValue().setDamageValue(cir.getReturnValue().getMaxDamage());
    }
}
