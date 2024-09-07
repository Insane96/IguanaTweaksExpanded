package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksreborn.module.combat.PiercingDamage;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class ArmorPiercer extends Enchantment implements IAttributeEnchantment {
    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_armor_piercer");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("armor_piercer_enchantment", (item) -> {
        return item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT);
    });

    public static final UUID MODIFIER_UUID = UUID.fromString("74e97c20-6a62-482f-b909-e709087b066a");

    public ArmorPiercer() {
        super(Rarity.UNCOMMON, CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND)
            return;
        event.addModifier(PiercingDamage.PIERCING_DAMAGE.get(), new AttributeModifier(MODIFIER_UUID, "Armor Piercier Enchantment Modifier", 1d * enchantmentLvl, AttributeModifier.Operation.ADDITION));
        event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(MODIFIER_UUID, "Armor Piercier Enchantment Modifier", -0.10d * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
