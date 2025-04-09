package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class Hoppy extends Enchantment {

    public Hoppy() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int level) {
        return 22 * level;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 22;
    }

    public static void onFall(LivingFallEvent event) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.HOPPY.get(), event.getEntity());
        if (lvl <= 0)
            return;
        event.setDistance(event.getDistance() - lvl);
    }

    public static float getJumpBoost(LivingEntity entity, float original) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.HOPPY.get(), entity);
        if (lvl > 0)
            return original + 0.1f * lvl;
        return original;
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(EnchantmentsFeature.class);
    }
}
