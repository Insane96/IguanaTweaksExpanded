package insane96mcp.iguanatweaksexpanded.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.Enlightened;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightTexture.class)
public abstract class LightTextureMixin {
    @SuppressWarnings("ConstantConditions")
    @ModifyExpressionValue(method = "updateLightTexture", at = @At(value = "CONSTANT", args = "floatValue=0.0", ordinal = 1))
    private float iguanatweaksreborn$getBrightness(float original) {
        return Enlightened.getBrightness(Minecraft.getInstance().player, original);
    }
}
