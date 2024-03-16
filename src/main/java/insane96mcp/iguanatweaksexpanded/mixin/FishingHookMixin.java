package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.Smartness;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import javax.annotation.Nullable;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin extends Projectile {
    @Shadow @Nullable public abstract Player getPlayerOwner();

    protected FishingHookMixin(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @ModifyArg(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;<init>(Lnet/minecraft/world/level/Level;DDDI)V"))
    private int onExperienceFromFishing(int exp) {
        if (this.getPlayerOwner() == null)
            return exp;
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.SMARTNESS.get(), this.getPlayerOwner());
        if (lvl > 0)
            return Smartness.getIncreasedExperience(this.random, lvl, exp);
        return exp;
    }

}
