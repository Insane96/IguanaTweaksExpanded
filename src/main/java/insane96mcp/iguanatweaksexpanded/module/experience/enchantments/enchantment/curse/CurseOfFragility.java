
package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.event.HurtItemStackEvent;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CurseOfFragility extends Enchantment implements IEnchantmentTooltip {
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

        if (event.getAmount() <= 0)
            event.setAmount(1);
        else
            event.setAmount(event.getAmount() * 2);
    }

    @Override
    public Component getTooltip(ItemStack itemStack, int i) {
        return Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.RED);
    }
}
