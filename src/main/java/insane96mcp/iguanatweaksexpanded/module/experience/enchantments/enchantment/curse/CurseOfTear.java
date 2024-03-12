package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.items.itemstats.ItemStats;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerXpEvent;

import java.util.Map;

public class CurseOfTear extends Enchantment implements IEnchantmentTooltip {
    public CurseOfTear() {
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

    public static void tearPlayerItems(PlayerXpEvent.PickupXp event) {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(NewEnchantmentsFeature.CURSE_OF_TEAR.get(), event.getEntity(), stack -> !ItemStats.isUnbreakable(stack) || stack.getDamageValue() < stack.getMaxDamage());
        if (entry == null)
            return;

        ItemStack stack = entry.getValue();
        int durabilityLeft = stack.getMaxDamage() - stack.getDamageValue();
        if (ItemStats.isUnbreakable(stack))
            durabilityLeft--;
        int tearAmount = Math.min(event.getOrb().value, durabilityLeft);
        if (tearAmount == 0)
            return;
        stack.hurt(tearAmount, event.getEntity().getRandom(), event.getEntity() instanceof ServerPlayer serverPlayer ? serverPlayer : null);
        event.getOrb().value -= tearAmount;
        if (event.getOrb().value > 0)
            tearPlayerItems(event);
    }

    @Override
    public Component getTooltip(ItemStack itemStack, int i) {
        return Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.RED);
    }
}
