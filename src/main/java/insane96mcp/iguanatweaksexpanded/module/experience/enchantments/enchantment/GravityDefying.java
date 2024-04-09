package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import java.util.UUID;

public class GravityDefying extends Enchantment implements IAttributeEnchantment {
    public static final UUID GRAVITY_MODIFIER_UUID = UUID.fromString("7567b3ad-a4c6-4700-8bf0-cd8c4356c155");
    public GravityDefying() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int level) {
        return 20 + (level - 1) * 30;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 20;
    }

    public static final float[] GRAVITY_REDUCTION = new float[] { 0.5f, 0.7f, 0.8f };

    public static float getGravityReduction(int lvl) {
        if (lvl <= 0 || lvl > GRAVITY_REDUCTION.length)
            return 0f;
        return GRAVITY_REDUCTION[lvl - 1];
    }

    public static void applyFallDamageReduction(LivingFallEvent event) {
        int lvl = event.getEntity().getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(NewEnchantmentsFeature.GRAVITY_DEFYING.get());
        if (lvl <= 0)
            return;

        event.setDistance((event.getDistance() - lvl * 1.5f));
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        event.addModifier(ForgeMod.ENTITY_GRAVITY.get(), new AttributeModifier(GRAVITY_MODIFIER_UUID, "Gravity Defying enchantment", -getGravityReduction(enchantmentLvl), AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
