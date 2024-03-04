package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.util.MathHelper;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LootBonusEnchantment;

public class Smartness extends Enchantment implements IEnchantmentTooltip {
    public Smartness() {
        super(Rarity.RARE, EnchantmentsFeature.WEAPONS_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public int getMinCost(int lvl) {
        return 15 + (lvl - 1) * 9;
    }

    public int getMaxCost(int lvl) {
        return super.getMinCost(lvl) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && !(enchantment instanceof LootBonusEnchantment);
    }

    public static float getBonusExperience(int lvl) {
        return lvl * 0.5f;
    }

    public static int getIncreasedExperience(RandomSource random, int lvl, int experience) {
        return MathHelper.getAmountWithDecimalChance(random, experience * (1f + getBonusExperience(lvl)));
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", getBonusExperience(lvl) * 100f).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
