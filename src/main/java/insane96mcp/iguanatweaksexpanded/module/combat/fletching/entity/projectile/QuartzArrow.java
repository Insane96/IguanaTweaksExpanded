package insane96mcp.iguanatweaksexpanded.module.combat.fletching.entity.projectile;

import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class QuartzArrow extends ITEArrow {
    public QuartzArrow(EntityType<? extends Arrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.friction = 0.995f;
        this.velocityMultiplier = 1.25f;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Fletching.QUARTZ_ARROW_ITEM.get());
    }
}
