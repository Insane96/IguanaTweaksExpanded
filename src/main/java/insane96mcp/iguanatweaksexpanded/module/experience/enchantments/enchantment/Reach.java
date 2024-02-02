package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class Reach extends Enchantment/* implements IEnchantmentTooltip */{

    public static final UUID REACH_MODIFIER_UUID = UUID.fromString("75f2bcd4-4f76-43b8-b75c-0c2a825cec8a");

    public Reach() {
        super(Rarity.VERY_RARE, EnchantmentsFeature.WEAPONS_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 20;
    }

    @Override
    public int getMaxCost(int level) {
        return 40;
    }

    public static void applyAttributeModifier(ItemAttributeModifierEvent event) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND)
            return;
        int lvl = event.getItemStack().getEnchantmentLevel(NewEnchantmentsFeature.REACH.get());
        if (lvl == 0)
            return;

        event.addModifier(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(REACH_MODIFIER_UUID, "Reach Enchantment Modifier", 0.2f * lvl, AttributeModifier.Operation.MULTIPLY_BASE));
        event.addModifier(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(REACH_MODIFIER_UUID, "Reach Enchantment Modifier", 0.2f * lvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    /*@Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", IguanaTweaksReborn.ONE_DECIMAL_FORMATTER.format(0.2f * lvl * 100f)).withStyle(ChatFormatting.DARK_PURPLE);
    }*/
}
