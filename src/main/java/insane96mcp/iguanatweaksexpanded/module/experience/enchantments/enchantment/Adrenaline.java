package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ISEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.IAttributeEnchantment;
import insane96mcp.iguanatweaksreborn.utils.MCUtils;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DiggingEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class Adrenaline extends Enchantment implements IAttributeEnchantment {
    public static TagKey<Item> ACCEPTS_ENCHANTMENT = ISEItemTagsProvider.create("enchanting/accepts_adrenaline");
    public static EnchantmentCategory CATEGORY = EnchantmentCategory.create("accepts_adrenaline", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT) || item instanceof DiggerItem);
    public static final UUID MODIFIER_UUID = UUID.fromString("656cda69-88a6-4925-a1ce-8ec8e475efdd");
    public Adrenaline() {
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

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof DiggingEnchantment) && super.checkCompatibility(other);
    }

    public static float getMiningSpeedBoost(ItemStack stack) {
        if (!(stack.getItem() instanceof DiggerItem diggerItem))
            return 0f;
        int lvl = stack.getEnchantmentLevel(NewEnchantmentsFeature.ADRENALINE.get());
        if (lvl == 0)
            return 0f;
        float durConsumed = 1f - MCUtils.getPercentageDurabilityLeft(stack);
        float ratio = Math.min(1f, durConsumed / 0.65f);
        return EnchantmentsFeature.getEfficiencyBonus(diggerItem.speed, lvl) * 2f * ratio;
    }

    public static float getMiningSpeedBoost(ItemStack stack, LivingEntity entity, BlockState state, boolean tooltip) {
        float miningSpeedBoost = getMiningSpeedBoost(stack);
        if (miningSpeedBoost == 0f)
            return 0f;
        return EnchantmentsFeature.applyMiningSpeedModifiers(miningSpeedBoost, state,false, entity, !tooltip);
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND)
            return;
        float durConsumed = 1 - MCUtils.getPercentageDurabilityLeft(event.getItemStack());
        event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(MODIFIER_UUID, "Adrenaline Enchantment Modifier", 0.15f * enchantmentLvl * durConsumed, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(NewEnchantmentsFeature.class);
    }
}
