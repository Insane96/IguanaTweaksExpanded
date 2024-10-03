package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.IAttributeEnchantment;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class CurseOfShortArm extends Enchantment implements IAttributeEnchantment {
    public static final UUID MODIFIER_UUID = UUID.fromString("7567b3ad-a4c6-4700-8bf0-cd8c4356c155");
    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_short_arm_curse");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("short_arm_curse", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public CurseOfShortArm() {
        super(Rarity.UNCOMMON, CATEGORY, EquipmentSlot.values());
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
        if (event.getSlotType() != EquipmentSlot.MAINHAND)
            return;
        event.addModifier(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(MODIFIER_UUID, "Short Arm Curse Modifier", -0.25d * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
        event.addModifier(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(MODIFIER_UUID, "Short Arm Curse Modifier", -0.25d * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
