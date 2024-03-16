package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.IAttributeEnchantment;
import insane96mcp.iguanatweaksreborn.data.generator.ITRItemTagsProvider;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class CurseOfSlowStrike extends Enchantment implements IAttributeEnchantment, IEnchantmentTooltip {
    public static final UUID GRAVITY_MODIFIER_UUID = UUID.fromString("7567b3ad-a4c6-4700-8bf0-cd8c4356c155");
    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITRItemTagsProvider.create("enchanting/accepts_slow_strike_curse");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("slow_strike_curse", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public CurseOfSlowStrike() {
        super(Rarity.RARE, CATEGORY, EquipmentSlot.values());
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
        event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(GRAVITY_MODIFIER_UUID, "Slow Strike Curse Modifier", -0.2d * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Override
    public Component getTooltip(ItemStack itemStack, int i) {
        return Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.RED);
    }
}
