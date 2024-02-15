package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.IguanaTweaksReborn;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class SwiftStrike extends BonusDamageEnchantment {
    public static final UUID BONUS_ATTACK_SPEED_UUID = UUID.fromString("7b0cb3a4-7a7c-4908-be8d-aadd523690d7");
    public SwiftStrike() {
        super(Rarity.UNCOMMON, EnchantmentsFeature.WEAPONS_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public float getDamageBonusPerLevel() {
        return 0f;
    }

    public static float getAttackSpeedBonusPerLevel() {
        return 0.15f;
    }

    public static void applyAttributeModifier(ItemAttributeModifierEvent event) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND)
            return;
        int lvl = event.getItemStack().getEnchantmentLevel(NewEnchantmentsFeature.SWIFT_STRIKE.get());
        if (lvl == 0)
            return;

        event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BONUS_ATTACK_SPEED_UUID, "Rhythmic Swing enchantment", getAttackSpeedBonusPerLevel() * lvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", IguanaTweaksReborn.ONE_DECIMAL_FORMATTER.format(getAttackSpeedBonusPerLevel() * lvl * 100f)).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
