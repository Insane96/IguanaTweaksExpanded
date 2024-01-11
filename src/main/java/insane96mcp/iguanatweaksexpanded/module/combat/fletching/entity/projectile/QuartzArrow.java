package insane96mcp.iguanatweaksexpanded.module.combat.fletching.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

public class QuartzArrow extends ITEArrow {
    public QuartzArrow(EntityType<? extends Arrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.friction = 0.995f;
        this.velocityMultiplier = 1.25f;
    }
}
