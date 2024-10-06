package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.IAttributeEnchantment;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class SwiftStrike extends BonusDamageEnchantment implements IAttributeEnchantment {
    public static final UUID BONUS_ATTACK_SPEED_UUID = UUID.fromString("7b0cb3a4-7a7c-4908-be8d-aadd523690d7");
    public SwiftStrike() {
        super(Rarity.UNCOMMON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public float getDamageBonusPerLevel() {
        return 0f;
    }

    public static float getAttackSpeedBonusPerLevel() {
        return 0.1f;
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND)
            return;
        event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BONUS_ATTACK_SPEED_UUID, "Rhythmic Swing enchantment", getAttackSpeedBonusPerLevel() * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
