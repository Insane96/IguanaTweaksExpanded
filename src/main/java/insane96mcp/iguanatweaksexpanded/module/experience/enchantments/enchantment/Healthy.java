package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.IAttributeEnchantment;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.protection.IProtectionEnchantment;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.protection.ITRProtectionEnchantment;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class Healthy extends Enchantment implements IAttributeEnchantment {
    public static final UUID[] MAX_HEALTH_MODIFIER_UUIDS = new UUID[] {
            UUID.fromString("532b9dee-e3c6-4c1d-9eb5-0e15010e5a58"),
            UUID.fromString("c8b9ab58-c2ab-4a6e-ab67-5d76b86cc1f5"),
            UUID.fromString("a48d2f59-7b0d-4539-b4bb-c7271f1a7bd9"),
            UUID.fromString("13225ef2-359e-4efa-9b64-122f26da388a")
    };
    public Healthy() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, ITRProtectionEnchantment.ARMOR_SLOTS);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinCost(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 8;
    }

    public boolean checkCompatibility(Enchantment other) {
        if (other instanceof ProtectionEnchantment otherProtection) {
            return otherProtection.type == ProtectionEnchantment.Type.FALL;
        } else {
            return !(other instanceof IProtectionEnchantment) && super.checkCompatibility(other);
        }
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        event.addModifier(Attributes.MAX_HEALTH, new AttributeModifier(MAX_HEALTH_MODIFIER_UUIDS[event.getSlotType().getIndex()], "Healthy enchantment", 1d * enchantmentLvl, AttributeModifier.Operation.ADDITION));
    }
}
