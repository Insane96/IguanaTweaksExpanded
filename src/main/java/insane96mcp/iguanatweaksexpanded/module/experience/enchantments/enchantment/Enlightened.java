package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class Enlightened extends Enchantment {
    public Enlightened() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[] {EquipmentSlot.HEAD});
    }

    @Override
    public int getMinCost(int level) {
        return 20 + (level - 1) * 30;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 20;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public static float getBrightness(Player entity, float original) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.ENLIGHTENED.get(), entity);
        return lvl > 0 ? 0.08f : original;
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(NewEnchantmentsFeature.class);
    }
}
