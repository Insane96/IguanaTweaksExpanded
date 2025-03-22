package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.mining.forging.DurabilityModifier;
import insane96mcp.iguanatweaksreborn.module.items.misc.ItemDefinition;
import insane96mcp.iguanatweaksreborn.module.items.misc.ItemDefinitionsReloadListener;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Enduring extends Enchantment {
    public Enduring() {
        super(Rarity.RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return 5 + (pEnchantmentLevel - 1) * 8;
    }

    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof DigDurabilityEnchantment) && super.checkCompatibility(other);
    }

    public static int getBonusDurabilityPerLevel(ItemStack stack) {
        float durabilityModifier = stack.getItem() instanceof DurabilityModifier durabilityModifier1
                ? durabilityModifier1.getDurabilityMultiplier(stack)
                : 1f;
        for (ItemDefinition definition : ItemDefinitionsReloadListener.getDefinitions()) {
            if (!definition.item().matchesItem(stack))
                continue;

            if (definition.durability() != null && definition.durability().durabilityMultiplier != null)
                durabilityModifier *= definition.durability().durabilityMultiplier;
        }
        if (stack.getItem() instanceof ShieldItem)
            return (int) (50 * durabilityModifier);
        return (int) ((stack.getItem() instanceof ArmorItem ? 30 : 100) * durabilityModifier);
    }
}
