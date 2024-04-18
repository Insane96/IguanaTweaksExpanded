package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.state.BlockState;

public class AirBorn extends Enchantment {
    public AirBorn() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 1;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return this.getMinCost(pEnchantmentLevel) + 40;
    }

    public static float getMiningSpeedMultiplier(LivingEntity entity, BlockState state) {
        if (entity.onGround())
            return 1f;
        ItemStack chestStack = entity.getItemBySlot(EquipmentSlot.CHEST);
        int lvl = chestStack.getEnchantmentLevel(NewEnchantmentsFeature.AIR_BORN.get());
        if (lvl == 0)
            return 1f;

        return 5f;
    }
}
