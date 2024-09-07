package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class SprintPact extends Enchantment implements IAttributeEnchantment {
    public static final UUID MODIFIER_UUID = UUID.fromString("532b9dee-e3c6-4c1d-9eb5-0e15010e5a58");
    public SprintPact() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    @Override
    public int getMinCost(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 8;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        if (event.getSlotType() != EquipmentSlot.LEGS)
            return;
        event.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(MODIFIER_UUID, "Healthy enchantment", 0.15d, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}
