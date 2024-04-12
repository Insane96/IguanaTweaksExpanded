package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

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
    public int getMinCost(int level) {
        return 20 + (level - 1) * 30;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 20;
    }

    public static float getGravityReduction() {
        return 0.5f;
    }

    public static void applyFallDamageReduction(LivingFallEvent event) {
        event.setDistance((event.getDistance() - 0.5f));
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        event.addModifier(ForgeMod.ENTITY_GRAVITY.get(), new AttributeModifier(GRAVITY_MODIFIER_UUID, "Gravity Defying enchantment", -getGravityReduction(), AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
