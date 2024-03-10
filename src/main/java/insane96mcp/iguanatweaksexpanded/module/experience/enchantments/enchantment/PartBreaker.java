package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.IguanaTweaksReborn;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class PartBreaker extends Enchantment implements IEnchantmentTooltip {

    public PartBreaker() {
        super(Rarity.RARE, EnchantmentsFeature.WEAPONS_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return 22 * level;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 22;
    }

    public static float getChance(int lvl) {
        return lvl * 0.05f;
    }

    @Override
    public Component getTooltip(ItemStack itemStack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", IguanaTweaksReborn.ONE_DECIMAL_FORMATTER.format(getChance(lvl) * 100f)).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
