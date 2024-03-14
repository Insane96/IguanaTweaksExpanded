package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Rage extends BonusDamageEnchantment implements IEnchantmentTooltip {
    public Rage() {
        super(Rarity.UNCOMMON, EnchantmentsFeature.WEAPONS_CATEGORY, new EquipmentSlot[]{ EquipmentSlot.MAINHAND });
    }

    @Override
    public float getDamageBonusPerLevel() {
        return 2f;
    }

    @Override
    public float getDamageBonus(LivingEntity attacker, LivingEntity target, ItemStack stack, int lvl) {
        if (!this.isAffectedByEnchantment(target))
            return 0f;
        float ratio = 1f - attacker.getHealth() / attacker.getMaxHealth();
        return this.getDamageBonus(stack, lvl) * (ratio * ratio);
    }
}
