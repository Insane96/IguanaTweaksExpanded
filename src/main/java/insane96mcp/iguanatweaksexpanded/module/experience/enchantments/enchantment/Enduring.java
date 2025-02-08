package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Enduring extends Enchantment {
    public Enduring() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 22 * level;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 22;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof DigDurabilityEnchantment) && super.checkCompatibility(other);
    }

    public static int getBonusDurability(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem ? 100 : 400;
    }
}
