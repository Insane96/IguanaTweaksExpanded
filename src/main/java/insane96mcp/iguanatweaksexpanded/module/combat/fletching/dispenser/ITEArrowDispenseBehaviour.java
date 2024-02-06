package insane96mcp.iguanatweaksexpanded.module.combat.fletching.dispenser;

import insane96mcp.iguanatweaksexpanded.module.combat.fletching.item.ITEArrowItem;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ITEArrowDispenseBehaviour extends AbstractProjectileDispenseBehavior {
	@Override
	protected Projectile getProjectile(Level pLevel, Position pPosition, ItemStack pStack) {
		return ((ITEArrowItem)pStack.getItem()).createDispenserArrow(pLevel, pPosition, pStack);
	}
}
