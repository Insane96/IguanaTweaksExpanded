package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.inventory.GrindstoneMenu$3")
public class GrindstoneMenu3Mixin {
    @Inject(method = "mayPlace", at = @At(value = "RETURN"), cancellable = true)
    public void canPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (Feature.isEnabled(EnchantingFeature.class) && EnchantingFeature.grindstoneEnchantmentExtraction && stack.is(Items.BOOK))
            cir.setReturnValue(true);
    }
}
