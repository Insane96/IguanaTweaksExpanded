package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class Critical extends BonusDamageEnchantment {
    public Critical() {
        super(Rarity.UNCOMMON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public static float getCritAmount(int lvl, float baseCrit) {
        float critBonus = baseCrit - 1f;
        return lvl * critBonus + baseCrit;
    }

    @Override
    public float getDamageBonusPerLevel() {
        return 0;
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity entity, int lvl) {
        if (lvl > 0 && attacker instanceof ServerPlayer player) {
            boolean isCrit = player.getAttackStrengthScale(0.5F) > 0.9f && player.fallDistance > 0.0F && !player.onGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && entity instanceof LivingEntity;
            if (isCrit)
                player.magicCrit(entity);
        }
    }
}
