package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.IAttributeEnchantment;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.protection.ISOProtectionEnchantment;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.DiggingEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class FlatProtection extends Enchantment implements IAttributeEnchantment {
    public static final UUID[] MODIFIER_UUIDS = new UUID[] {
            UUID.fromString("2f42e9bd-0537-403b-96b1-2a1d67029729"),
            UUID.fromString("5455c8d5-2e83-4da2-a698-2aa4333e8347"),
            UUID.fromString("f6adf83d-cc4e-48db-83b1-b5b942214353"),
            UUID.fromString("ab9acb05-1838-473a-bc87-c0832503edaa")
    };
    public FlatProtection() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, ISOProtectionEnchantment.ARMOR_SLOTS);
    }

    @Override
    public int getMinCost(int level) {
        return 22 * level;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 22;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof DiggingEnchantment) && super.checkCompatibility(other);
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(NewEnchantmentsFeature.class);
    }

    public static float getArmor() {
        return 1;
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        event.addModifier(Attributes.ARMOR, new AttributeModifier(MODIFIER_UUIDS[event.getSlotType().getIndex()], "Flat Protection enchantment", getArmor() * enchantmentLvl, AttributeModifier.Operation.ADDITION));
    }
}
