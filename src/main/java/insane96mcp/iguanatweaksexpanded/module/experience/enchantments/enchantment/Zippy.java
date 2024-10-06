package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.IAttributeEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class Zippy extends Enchantment implements IAttributeEnchantment {

    public static final UUID MODIFIER_UUID = UUID.fromString("74e97c20-6a62-482f-b909-e709087b066a");

    public Zippy() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
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

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        event.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(MODIFIER_UUID, "Zippy Enchantment Modifier", 0.075d * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
