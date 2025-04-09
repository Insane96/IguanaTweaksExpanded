package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Rage extends BonusDamageEnchantment {
    public Rage() {
        super(Rarity.UNCOMMON, new EquipmentSlot[]{ EquipmentSlot.MAINHAND });
    }

    @Override
    public float getDamageBonusPerLevel() {
        return 2f;
    }

    @Override
    public float getDamageBonus(LivingEntity attacker, Entity target, ItemStack stack, int lvl) {
        if (!this.isAffectedByEnchantment(target))
            return 0f;
        float ratio = Math.min(1f, 1f - ((attacker.getHealth() - 1) / attacker.getMaxHealth()));
        return this.getDamageBonus(stack, lvl) * (ratio * ratio * 0.8f + 0.2f);
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(EnchantmentsFeature.class);
    }
}
