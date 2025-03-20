package insane96mcp.iguanatweaksexpanded.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.Invulnerability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @ModifyConstant(method = "aiStep", constant = @Constant(doubleValue = 0.003d, ordinal = 1))
    public double yClamping(double ySpeedClamp) {
        return 0.0001d;
    }

    @Inject(method = "getJumpBoostPower", at = @At("RETURN"), cancellable = true)
    public void onGetJumpBoostPower(CallbackInfoReturnable<Float> cir) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.HOPPY.get(), (LivingEntity) (Object) this);
        if (lvl > 0)
            cir.setReturnValue(cir.getReturnValue() + 0.1f * lvl);
    }

    @ModifyExpressionValue(method = "hurt", at = @At(value = "CONSTANT", args = "floatValue=10.0", ordinal = 0))
    public float iguanatweaksreborn$invulnerabilityTime(float original) {
        return Invulnerability.getInvulnerableTime((LivingEntity) (Object) this, original);
    }

    @ModifyExpressionValue(method = "hurt", at = @At(value = "CONSTANT", args = "intValue=10", ordinal = 0))
    public int iguanatweaksreborn$hurtDuration(int original) {
        return Invulnerability.getHurtDuration((LivingEntity) (Object) this, original);
    }

    @ModifyExpressionValue(method = "handleDamageEvent", at = @At(value = "CONSTANT", args = "intValue=10", ordinal = 0))
    public int iguanatweaksreborn$hurtDuration2(int original) {
        return Invulnerability.getHurtDuration((LivingEntity) (Object) this, original);
    }

    @ModifyExpressionValue(method = "animateHurt", at = @At(value = "CONSTANT", args = "intValue=10", ordinal = 0))
    public int iguanatweaksreborn$hurtDuration3(int original) {
        return Invulnerability.getHurtDuration((LivingEntity) (Object) this, original);
    }
}
