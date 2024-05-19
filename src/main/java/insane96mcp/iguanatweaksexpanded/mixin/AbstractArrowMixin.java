package insane96mcp.iguanatweaksexpanded.mixin;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.GravityDefying;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {

    protected AbstractArrowMixin(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", shift = At.Shift.AFTER))
    public void onSetDeltaMovement(CallbackInfo ci) {
        if (this.isNoGravity() && this.getPersistentData().getBoolean(GravityDefying.NBT_TAG)) {
            if (this.getDeltaMovement().horizontalDistance() < 0.2)
                this.setNoGravity(false);
            else
                this.setDeltaMovement(this.getDeltaMovement().scale(0.985f));
        }
    }
}
