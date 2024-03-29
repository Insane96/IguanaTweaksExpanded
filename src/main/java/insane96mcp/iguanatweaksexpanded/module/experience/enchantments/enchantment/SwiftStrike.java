package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.insanelib.InsaneLib;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
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
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", InsaneLib.ONE_DECIMAL_FORMATTER.format(getAttackSpeedBonusPerLevel() * lvl * 100f)).withStyle(ChatFormatting.DARK_PURPLE);
    }

    @Override
    public void applyAttributeModifier(ItemAttributeModifierEvent event, int enchantmentLvl) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND)
            return;
        event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BONUS_ATTACK_SPEED_UUID, "Rhythmic Swing enchantment", getAttackSpeedBonusPerLevel() * enchantmentLvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
