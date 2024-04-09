package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.IAttributeEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class CurseOfUnhurried extends Enchantment implements IAttributeEnchantment {
    public static final UUID[] UUIDS = new UUID[] {
            UUID.fromString("532b9dee-e3c6-4c1d-9eb5-0e15010e5a58"),
            UUID.fromString("c8b9ab58-c2ab-4a6e-ab67-5d76b86cc1f5"),
            UUID.fromString("a48d2f59-7b0d-4539-b4bb-c7271f1a7bd9"),
            UUID.fromString("13225ef2-359e-4efa-9b64-122f26da388a")
    };
    public CurseOfUnhurried() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR, EquipmentSlot.values());
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 25;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return 50;
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        event.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUIDS[event.getSlotType().getIndex()], "Unhurried Curse Modifier", -0.1d * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
