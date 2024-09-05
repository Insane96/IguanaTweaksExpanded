package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Explosive extends BonusDamageEnchantment {
    public Explosive() {
        super(Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public float getDamageBonus(LivingEntity attacker, Entity target, ItemStack stack, int lvl) {
        return 0f;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public static float getExplosionPower(int lvl, ItemStack stack) {
        return Math.max(1, (1 + (lvl - 1) * 0.5f) * getDamageBonusRatio(stack));
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity entity, int lvl) {
        if (!(entity instanceof LivingEntity livingEntity))
            return;

        if (lvl > 0)
            livingEntity.level().explode(null, livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() / 2, livingEntity.getZ(), getExplosionPower(lvl, attacker.getMainHandItem()), Level.ExplosionInteraction.TNT);
    }
}
