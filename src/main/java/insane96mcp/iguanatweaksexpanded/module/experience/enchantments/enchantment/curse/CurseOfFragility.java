
package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.event.HurtItemStackEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CurseOfFragility extends Enchantment {
    public CurseOfFragility() {
        super(Rarity.UNCOMMON, EnchantmentCategory.VANISHABLE, EquipmentSlot.values());
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

    public static void increaseItemHurt(HurtItemStackEvent event) {
        if (event.getStack().getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_FRAGILITY.get()) <= 0)
            return;

        event.setAmount(event.getAmount() + 1);
    }
}
