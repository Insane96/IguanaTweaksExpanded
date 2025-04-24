package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.DiggingEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.state.BlockState;

public class Blasting extends Enchantment {
    static final EnchantmentCategory PICKAXES = EnchantmentCategory.create("pickaxes", item -> item instanceof PickaxeItem);

    public Blasting() {
        super(Rarity.COMMON, PICKAXES, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinCost(int pEnchantmentLevel) {
        return 1 + 10 * (pEnchantmentLevel - 1);
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof DiggingEnchantment) && super.checkCompatibility(other);
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(NewEnchantmentsFeature.class);
    }

    public static float getMiningSpeedBoost(ItemStack stack, LivingEntity entity, BlockState state) {
        if (state == null || entity == null)
            return 0f;
        if (!stack.isCorrectToolForDrops(state))
            return 0f;
        int lvl = stack.getEnchantmentLevel(NewEnchantmentsFeature.BLASTING.get());
        if (lvl == 0)
            return 0f;
        if (!(stack.getItem() instanceof DiggerItem diggerItem))
            return 0f;

        float miningSpeedBoost = lvl * Math.max(0.04f, (7f - state.getBlock().getExplosionResistance()) * 0.2f) * diggerItem.speed;
        return EnchantmentsFeature.applyMiningSpeedModifiers(miningSpeedBoost, state, false, entity);
    }
}
