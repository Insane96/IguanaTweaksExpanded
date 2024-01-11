package insane96mcp.iguanatweaksexpanded.module.combat.fletching.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

public class DiamondArrow extends ITEArrow {
    public DiamondArrow(EntityType<? extends Arrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.gravityForce = 0.08f;
    }
}
