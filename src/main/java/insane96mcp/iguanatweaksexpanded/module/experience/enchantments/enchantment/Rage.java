package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.insanelib.InsaneLib;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
        float ratio = Math.min(1f, 1f - ((attacker.getHealth() - 1) / attacker.getMaxHealth()));
        return this.getDamageBonus(stack, lvl) * (ratio * ratio * 0.8f + 0.2f);
    }

    public float getMinDamageBonus(ItemStack stack, int lvl) {
        return this.getDamageBonusPerLevel() * lvl * getDamageBonusRatio(stack) * 0.2f;
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", InsaneLib.ONE_DECIMAL_FORMATTER.format(this.getDamageBonus(stack, lvl)), InsaneLib.ONE_DECIMAL_FORMATTER.format(this.getMinDamageBonus(stack, lvl))).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
