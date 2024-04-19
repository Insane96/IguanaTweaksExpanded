package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BurstOfArrows extends Enchantment {

    public static final String BURST = IguanaTweaksExpanded.RESOURCE_PREFIX + "burst";

    public BurstOfArrows() {
        super(Rarity.VERY_RARE, EnchantmentCategory.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    public int getMinCost(int pEnchantmentLevel) {
        return pEnchantmentLevel * 25;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return this.getMinCost(pEnchantmentLevel) + 50;
    }


    public static void trySummonArrows(Projectile projectile) {
        if (projectile.level().isClientSide)
            return;
        if (!(projectile instanceof Arrow arrow))
            return;
        if (!arrow.getPersistentData().getBoolean(BURST))
            return;

        summonArrows(arrow);
        arrow.getPersistentData().putBoolean(BURST, false);
    }

    public static void summonArrows(Arrow arrow) {
        for (int i = 0; i < 360; i += 60) {
            Arrow newArrow = new Arrow(arrow.level(), arrow.getX(), arrow.getY(), arrow.getZ());
            newArrow.setOwner(arrow.getOwner());
            newArrow.pickup = Arrow.Pickup.CREATIVE_ONLY;
            newArrow.setPierceLevel(arrow.getPierceLevel());
            newArrow.setBaseDamage(arrow.getBaseDamage());
            newArrow.setKnockback(arrow.getKnockback());
            newArrow.shoot(Math.cos(Math.toRadians(i)), 0.5f, Math.sin(Math.toRadians(i)), 0.7f, 0);
            newArrow.setBaseDamage(arrow.getBaseDamage() * 0.5f);
            newArrow.getPersistentData().putBoolean(BURST, false);
            arrow.level().addFreshEntity(newArrow);
        }
    }
}
