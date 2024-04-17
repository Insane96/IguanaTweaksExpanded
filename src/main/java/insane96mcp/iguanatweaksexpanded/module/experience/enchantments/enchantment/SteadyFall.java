package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.data.generator.ITRItemTagsProvider;
import insane96mcp.iguanatweaksreborn.module.combat.PiercingDamage;
import insane96mcp.iguanatweaksreborn.utils.MCUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import java.util.UUID;

public class SteadyFall extends Enchantment {
    public SteadyFall() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return pEnchantmentLevel * 25;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return this.getMinCost(pEnchantmentLevel) + 50;
    }

    public static float ratio() {
        return 2f;
    }

    public static void onFall(LivingDamageEvent event) {
        if (!event.getSource().is(DamageTypeTags.IS_FALL))
            return;
        ItemStack feetStack = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
        int lvl = feetStack.getEnchantmentLevel(NewEnchantmentsFeature.STEADY_FALL.get());
        if (lvl == 0)
            return;

        float reducedDamage = Math.min(MCUtils.getDurabilityLeft(feetStack) / ratio(), event.getAmount());
        event.setAmount(event.getAmount() - reducedDamage);
        feetStack.hurtAndBreak((int) (reducedDamage * ratio()), event.getEntity(), (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlot.FEET);
        });
    }
}
