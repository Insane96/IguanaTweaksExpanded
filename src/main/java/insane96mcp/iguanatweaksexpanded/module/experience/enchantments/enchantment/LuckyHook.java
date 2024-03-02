package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.IguanaTweaksReborn;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.LootBonusEnchantment;

public class LuckyHook extends Enchantment implements IEnchantmentTooltip {

    public LuckyHook() {
        super(Rarity.RARE, EnchantmentCategory.FISHING_ROD, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 15 + (pEnchantmentLevel - 1) * 9;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof LootBonusEnchantment) && super.checkCompatibility(other);
    }

    public static float getChanceToDoubleReel(int lvl) {
        return lvl * 0.05f;
    }



    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        if (!(stack.getItem() instanceof DiggerItem diggerItem))
            return Component.empty();
        return Component.translatable(this.getDescriptionId() + ".tooltip", IguanaTweaksReborn.ONE_DECIMAL_FORMATTER.format(getChanceToDoubleReel(lvl))).withStyle(ChatFormatting.DARK_PURPLE);
    }
}