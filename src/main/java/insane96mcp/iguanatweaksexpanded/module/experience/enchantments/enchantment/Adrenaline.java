package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.IAttributeEnchantment;
import insane96mcp.iguanatweaksreborn.utils.MCUtils;
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
    public static TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_adrenaline");
    public static EnchantmentCategory CATEGORY = EnchantmentCategory.create("accepts_adrenaline", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
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

    public static float getMiningSpeedBoost(ItemStack stack, int lvl) {
        if (!(stack.getItem() instanceof DiggerItem diggerItem))
            return 0f;
        float durConsumed = 1 - MCUtils.getPercentageDurabilityLeft(stack);
        return EnchantmentsFeature.getEfficiencyBonus(diggerItem.speed, lvl) * 2.5f * (durConsumed * durConsumed);
    }

    public static float getMiningSpeedBoost(LivingEntity entity, BlockState state) {
        ItemStack heldStack = entity.getMainHandItem();
        if (!heldStack.isCorrectToolForDrops(state))
            return 0f;
        int lvl = heldStack.getEnchantmentLevel(NewEnchantmentsFeature.ADRENALINE.get());
        if (lvl == 0)
            return 0f;

        float miningSpeedBoost = getMiningSpeedBoost(heldStack, lvl);
        if (miningSpeedBoost == 0f)
            return 0f;
        return EnchantmentsFeature.applyMiningSpeedModifiers(miningSpeedBoost, false, entity);
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND)
            return;
        float durConsumed = 1 - MCUtils.getPercentageDurabilityLeft(event.getItemStack());
        event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(MODIFIER_UUID, "Adrenaline Enchantment Modifier", 0.15f * enchantmentLvl * (durConsumed * durConsumed), AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
