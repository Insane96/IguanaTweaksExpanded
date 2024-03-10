package insane96mcp.iguanatweaksexpanded.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyConstant(method = "aiStep", constant = @Constant(doubleValue = 0.003d, ordinal = 1))
    public double yClamping(double ySpeedClamp) {
        return 0.0001d;
    }
}
