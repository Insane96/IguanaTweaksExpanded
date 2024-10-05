package insane96mcp.iguanatweaksexpanded.module.items.solarium.integration;

import com.teamabnormals.buzzier_bees.core.registry.BBMobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BuzzierBeesIntegration {
    public static boolean hasSunny(Entity entity) {
        return entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(BBMobEffects.SUNNY.get());
    }
}
