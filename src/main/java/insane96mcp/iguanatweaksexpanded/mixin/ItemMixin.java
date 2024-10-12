package insane96mcp.iguanatweaksexpanded.mixin;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin {
    /*@Inject(method = "isFoil", at = @At("RETURN"), cancellable = true)
    public void isFoil(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() && (!EnchantingFeature.hasOnlyCurses(stack) || EnchantingFeature.hasEnchantGlintOnly(stack)));
    }*/
}
