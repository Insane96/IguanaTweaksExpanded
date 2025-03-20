package insane96mcp.iguanatweaksexpanded.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public abstract class EntityMixin {
    /*@WrapOperation(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWater()Z"))
    public boolean iguanatweaksreborn$swimAnywhere(Entity instance, Operation<Boolean> original) {
        return original.call(instance) || true;
    }
    @WrapOperation(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;canStartSwimming()Z"))
    public boolean iguanatweaksreborn$swimAnywhere2(Entity instance, Operation<Boolean> original) {
        return original.call(instance) || true;
    }*/
}
