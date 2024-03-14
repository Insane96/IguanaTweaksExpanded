package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.level.BlockEvent;

public class CurseOfDumbness extends Enchantment implements IEnchantmentTooltip {
    public CurseOfDumbness() {
        super(Rarity.UNCOMMON, EnchantmentsFeature.WEAPONS_CATEGORY, EquipmentSlot.values());
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

    public static void applyToLivingDrops(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() == null)
            return;
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_DUMBNESS.get(), event.getAttackingPlayer());
        if (lvl > 0)
            event.setDroppedExperience((int) (event.getDroppedExperience() * 0.5f));
    }

    public static void applyToBlockDrops(BlockEvent.BreakEvent event) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_DUMBNESS.get(), event.getPlayer());
        if (lvl > 0)
            event.setExpToDrop((int) (event.getExpToDrop() * 0.5f));
    }

    @Override
    public Component getTooltip(ItemStack itemStack, int i) {
        return Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.RED);
    }
}
