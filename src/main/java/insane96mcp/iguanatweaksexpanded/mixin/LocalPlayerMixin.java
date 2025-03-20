package insane96mcp.iguanatweaksexpanded.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.Invulnerability;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    public LocalPlayerMixin(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pGameProfile);
    }

    @ModifyExpressionValue(method = "hurtTo", at = @At(value = "CONSTANT", args = "intValue=10", ordinal = 1))
    public int iguanatweaksreborn$hurtDuration(int original) {
        return Invulnerability.getHurtDuration((LivingEntity) (Object) this, original);
    }
}
