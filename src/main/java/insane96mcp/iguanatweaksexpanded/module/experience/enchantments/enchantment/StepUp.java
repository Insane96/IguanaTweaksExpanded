package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class StepUp extends Enchantment {

    public static final UUID STEP_UP_MODIFIER_UUID = UUID.fromString("74e97c20-6a62-482f-b909-e709087b066a");

    public StepUp() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
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
        if (event.getSlotType() != EquipmentSlot.LEGS)
            return;
        int lvl = event.getItemStack().getEnchantmentLevel(NewEnchantmentsFeature.STEP_UP.get());
        if (lvl == 0)
            return;

        event.addModifier(ForgeMod.STEP_HEIGHT.get(), new AttributeModifier(STEP_UP_MODIFIER_UUID, "Step Up Enchantment Modifier", 0.5d * lvl, AttributeModifier.Operation.ADDITION));
    }

}