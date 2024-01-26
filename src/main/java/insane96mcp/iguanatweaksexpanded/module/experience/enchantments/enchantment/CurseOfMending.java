package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class CurseOfMending extends Enchantment {
    public CurseOfMending() {
        super(Rarity.RARE, EnchantmentCategory.VANISHABLE, EquipmentSlot.values());
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

    public static void consumePlayerExperience(Player player) {
        if (player.tickCount % 20 != 0)
            return;

        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(NewEnchantmentsFeature.CURSE_OF_MENDING.get(), player, ItemStack::isDamaged);
        if (entry != null) {
            ItemStack stack = entry.getValue();
            int lvl = stack.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_MENDING.get());
            player.giveExperiencePoints(-lvl);
            stack.setDamageValue(Math.max(0, stack.getDamageValue() - lvl));
        }
    }
}
