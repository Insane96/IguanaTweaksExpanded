package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "isFoil", at = @At("RETURN"), cancellable = true)
    public void isFoil(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() && (!EnchantingFeature.hasOnlyCurses(stack) || EnchantingFeature.hasEnchantGlintOnly(stack)));
    }
}
