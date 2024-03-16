package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.util.MathHelper;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.LootBonusEnchantment;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.level.BlockEvent;

public class Smartness extends Enchantment implements IEnchantmentTooltip {
    public static TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_smartness");
    public static EnchantmentCategory CATEGORY = EnchantmentCategory.create("accepts_smartness", item -> EnchantmentsFeature.WEAPONS_CATEGORY.canEnchant(item) || item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public Smartness() {
        super(Rarity.RARE, CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public int getMinCost(int lvl) {
        return 15 + (lvl - 1) * 9;
    }

    public int getMaxCost(int lvl) {
        return super.getMinCost(lvl) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && !(enchantment instanceof LootBonusEnchantment);
    }

    public static float getBonusExperience(int lvl) {
        return lvl * 0.5f;
    }

    public static int getIncreasedExperience(RandomSource random, int lvl, int experience) {
        return MathHelper.getAmountWithDecimalChance(random, experience * (1f + getBonusExperience(lvl)));
    }

    public static void applyToLivingDrops(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() == null)
            return;
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.SMARTNESS.get(), event.getAttackingPlayer());
        if (lvl > 0)
            event.setDroppedExperience(Smartness.getIncreasedExperience(event.getEntity().getRandom(), lvl, event.getDroppedExperience()));
    }

    public static void applyToBlockDrops(BlockEvent.BreakEvent event) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.SMARTNESS.get(), event.getPlayer());
        if (lvl > 0)
            event.setExpToDrop(Smartness.getIncreasedExperience(event.getLevel().getRandom(), lvl, event.getExpToDrop()));
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", getBonusExperience(lvl) * 100f).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
