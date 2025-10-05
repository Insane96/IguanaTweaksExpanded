package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DiggingEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Dwarfing extends Enchantment {

    public Dwarfing() {
        super(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    public int getMinCost(int lvl) {
        return 4 + (lvl - 1) * 8;
    }

    public int getMaxCost(int lvl) {
        return this.getMinCost(lvl) + 20;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof DiggingEnchantment) && super.checkCompatibility(other);
    }

    public static float getMiningSpeedBoost(ItemStack stack) {
        if (!(stack.getItem() instanceof DiggerItem diggerItem))
            return 0f;
        int lvl = stack.getEnchantmentLevel(NewEnchantmentsFeature.DWARFING.get());
        if (lvl == 0)
            return 0f;
        return EnchantmentsFeature.getEfficiencyBonus(diggerItem.speed, lvl) * 3f;
    }

    public static float getMiningSpeedBoost(ItemStack stack, LivingEntity entity, BlockState state, boolean tooltip) {
        float miningSpeedBoost = getMiningSpeedBoost(stack);
        if (miningSpeedBoost == 0f)
            return 0f;
        int y = getNormalizedY(entity.getBlockY(), entity.level());
        if (y < 0)
            return 0f;
        int maxY = entity.level().getSeaLevel() - entity.level().getMinBuildHeight();
        float ratio = (float) y / maxY;
        miningSpeedBoost *= ratio;
        return EnchantmentsFeature.applyMiningSpeedModifiers(miningSpeedBoost, state,false, entity, !tooltip);
    }

    public static int getNormalizedY(int y, Level level) {
        int startingY = level.getSeaLevel();
        if (y > startingY)
            return -1;
        //Normalize y to go from 0 at sea level
        return (y - startingY) * -1;
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(NewEnchantmentsFeature.class);
    }
}
