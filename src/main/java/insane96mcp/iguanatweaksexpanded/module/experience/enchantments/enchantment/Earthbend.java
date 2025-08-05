package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class Earthbend extends Enchantment {
    public Earthbend() {
        super(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 1;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return 50;
    }

    public static float getMiningSpeedBonus(LivingEntity entity, @Nullable BlockState state) {
        if (state == null
                || state.requiresCorrectToolForDrops())
            return 0f;
        ItemStack mainHandItem = entity.getMainHandItem();
        if (mainHandItem.isCorrectToolForDrops(state))
            return 0f;
        int lvl = mainHandItem.getEnchantmentLevel(NewEnchantmentsFeature.EARTHBEND.get());
        if (lvl == 0)
            return 0f;

        return state.destroySpeed;
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(NewEnchantmentsFeature.class);
    }
}
