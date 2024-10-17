package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.util.MCUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.DiggingEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.UUID;

public class Retreat extends Enchantment {
    public static final UUID MODIFIER_UUID = UUID.fromString("656cda69-88a6-4925-a1ce-8ec8e475efdd");
    public Retreat() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    public int getMinCost(int lvl) {
        return 25;
    }

    public int getMaxCost(int lvl) {
        return 50;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof DiggingEnchantment) && super.checkCompatibility(other);
    }

    public static void applyMovementSpeedModifier(LivingEvent.LivingTickEvent event) {
        if (event.getEntity().level().isClientSide
                || event.getEntity().tickCount % 5 != 4)
            return;

        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.RETREAT.get(), event.getEntity());
        float healthLeft = 1 - (event.getEntity().getHealth() / event.getEntity().getMaxHealth());
        float amount = 0.30f * lvl * (healthLeft * healthLeft);
        AttributeInstance movementSpeed = event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier modifier = movementSpeed.getModifier(MODIFIER_UUID);
        if (modifier != null && modifier.getAmount() == amount)
            return;

        if (modifier != null)
            movementSpeed.removeModifier(MODIFIER_UUID);
        MCUtils.applyModifier(event.getEntity(), Attributes.MOVEMENT_SPEED, MODIFIER_UUID, "Retreat Enchantment Modifier", amount, AttributeModifier.Operation.MULTIPLY_BASE);
    }
}
