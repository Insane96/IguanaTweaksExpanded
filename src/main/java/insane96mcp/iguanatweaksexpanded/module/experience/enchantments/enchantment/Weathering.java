package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.integration.BuzzierBeesIntegration;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.event.HurtItemStackEvent;
import insane96mcp.insanelib.util.MCUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;

import java.util.UUID;

public class Weathering extends Enchantment {
    public static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("435317e9-0146-4f1b-bc21-67f466ee5f9c");

    public Weathering() {
        super(Rarity.RARE, EnchantmentCategory.VANISHABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    public int getMinCost(int level) {
        return 20 + (level - 1) * 30;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 20;
    }

    public static float getMiningSpeedBoost(ItemStack stack) {
        if (!(stack.getItem() instanceof DiggerItem diggerItem))
            return 0f;
        int lvl = stack.getEnchantmentLevel(NewEnchantmentsFeature.WEATHERING.get());
        if (lvl == 0)
            return 0f;
        return diggerItem.speed * 0.5f;
    }

    public static float getMiningSpeedBoost(ItemStack stack, LivingEntity entity, BlockState state, boolean tooltip) {
        float miningSpeedBoost = getMiningSpeedBoost(stack);
        if (miningSpeedBoost == 0f)
            return 0f;

        float skyLightRatio = getSunLightRatio(entity);
        if (skyLightRatio <= 0f)
            return 0f;
        miningSpeedBoost *= skyLightRatio;
        return EnchantmentsFeature.applyMiningSpeedModifiers(miningSpeedBoost, state,false, entity, !tooltip);
    }

    public static float getSunLightRatio(Entity entity) {
        if (!entity.level().isDay()
                || entity.level().isThundering())
            return 0f;
        float sunLight = getSunLight(entity);
        if (entity.level().isRaining())
            sunLight *= 0.35f;
        return Math.min(sunLight, 12f) / 12f;
    }

    public static float getSunLight(Entity entity) {
        float calculatedSkyLight = entity.level().getBrightness(LightLayer.SKY, entity.blockPosition()) - entity.level().getSkyDarken();
        if (ModList.get().isLoaded("buzzier_bees") && BuzzierBeesIntegration.hasSunny(entity))
            calculatedSkyLight = 15f;
        return calculatedSkyLight;
    }

    public static float getAttackSpeedBoost(ItemStack stack) {
        int lvl = stack.getEnchantmentLevel(NewEnchantmentsFeature.WEATHERING.get());
        if (lvl == 0)
            return 0f;
        return 0.25f;
    }

    public static void applyAttackSpeedBoost(LivingEntity entity) {
        if (entity.tickCount % 5 != 3)
            return;
        float attackSpeedBoost = getAttackSpeedBoost(entity.getMainHandItem());
        if (attackSpeedBoost == 0f)
            return;
        AttributeInstance attributeInstance = entity.getAttribute(Attributes.ATTACK_SPEED);
        if (attributeInstance == null)
            return;

        float skyLightRatio = getMoonLightRatio(entity);
        attackSpeedBoost *= skyLightRatio;
        AttributeModifier modifier = attributeInstance.getModifier(ATTACK_SPEED_MODIFIER_UUID);
        if (modifier != null && modifier.getAmount() != attackSpeedBoost)
            attributeInstance.removeModifier(ATTACK_SPEED_MODIFIER_UUID);
        if (attackSpeedBoost > 0f)
            MCUtils.applyModifier(entity, Attributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER_UUID, "Weathering boost", attackSpeedBoost, AttributeModifier.Operation.MULTIPLY_BASE, false);
    }

    public static float getMoonLightRatio(Entity entity) {
        if (entity.level().isDay()
                || entity.level().isThundering())
            return 0f;
        float moonLight = getMoonLight(entity);
        if (entity.level().isRaining())
            moonLight *= 0.35f;
        return Math.min(moonLight, 12f) / 12f;
    }

    public static float getMoonLight(Entity entity) {
        float calculatedSkyLight = entity.level().getBrightness(LightLayer.SKY, entity.blockPosition());
        if (ModList.get().isLoaded("buzzier_bees") && BuzzierBeesIntegration.hasSunny(entity))
            calculatedSkyLight = 0f;
        return calculatedSkyLight;
    }

    public static void applyUnbreaking(HurtItemStackEvent event) {
        int lvl = event.getStack().getEnchantmentLevel(NewEnchantmentsFeature.WEATHERING.get());
        if (lvl == 0)
            return;
        float skyLightRatio = getRainRatio(event.getEntity());
        if (skyLightRatio <= 0f)
            return;
        int newAmount = event.getAmount();
        for (int i = 0; i < event.getAmount(); i++) {
            if (event.getEntity().getRandom().nextFloat() <= 0.667f)
                newAmount--;
        }
        event.setAmount(newAmount);
    }

    public static float getRainRatio(Entity entity) {
        return entity.level().isRainingAt(entity.blockPosition()) ? 1f : 0f;
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(NewEnchantmentsFeature.class);
    }
}
