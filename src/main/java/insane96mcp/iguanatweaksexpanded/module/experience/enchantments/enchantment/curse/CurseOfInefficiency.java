package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CurseOfInefficiency extends Enchantment {
    public CurseOfInefficiency() {
        super(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, EquipmentSlot.values());
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 25;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return 50;
    }
}
