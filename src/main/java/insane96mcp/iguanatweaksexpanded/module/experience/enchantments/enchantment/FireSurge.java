package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class FireSurge extends BonusDamageEnchantment {
    public FireSurge() {
        super(Rarity.UNCOMMON, new EquipmentSlot[]{ EquipmentSlot.MAINHAND });
    }

    @Override
    public float getDamageBonusPerLevel() {
        return 2.5f;
    }

    @Override
    public float getDamageBonus(LivingEntity attacker, Entity target, ItemStack stack, int lvl) {
        if (!this.isAffectedByEnchantment(target)
                || !attacker.isOnFire())
            return 0f;
        float ratio = 1;
        if (attacker.hasEffect(MobEffects.FIRE_RESISTANCE))
            ratio = 0.2f;
        return this.getDamageBonus(stack, lvl) * ratio;
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(NewEnchantmentsFeature.class);
    }
}
