package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.data.generator.ITRItemTagsProvider;
import insane96mcp.insanelib.InsaneLib;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class Reach extends Enchantment implements IAttributeEnchantment, IEnchantmentTooltip {

    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITRItemTagsProvider.create("enchanting/accepts_reach");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("reach", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));

    public static final UUID REACH_MODIFIER_UUID = UUID.fromString("75f2bcd4-4f76-43b8-b75c-0c2a825cec8a");

    public Reach() {
        super(Rarity.VERY_RARE, CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 20;
    }

    @Override
    public int getMaxCost(int level) {
        return 40;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND)
            return;
        event.addModifier(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(REACH_MODIFIER_UUID, "Reach Enchantment Modifier", 0.2f * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
        event.addModifier(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(REACH_MODIFIER_UUID, "Reach Enchantment Modifier", 0.2f * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", InsaneLib.ONE_DECIMAL_FORMATTER.format(0.2f * lvl * 100f)).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
