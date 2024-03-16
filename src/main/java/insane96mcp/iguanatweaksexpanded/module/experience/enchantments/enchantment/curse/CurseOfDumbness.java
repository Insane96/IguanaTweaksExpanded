package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.data.generator.ITRItemTagsProvider;
import insane96mcp.insanelib.util.MathHelper;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.level.BlockEvent;

public class CurseOfDumbness extends Enchantment implements IEnchantmentTooltip {
    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITRItemTagsProvider.create("enchanting/accepts_dumbness_curse");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("dumbness_curse", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public CurseOfDumbness() {
        super(Rarity.UNCOMMON, CATEGORY, EquipmentSlot.values());
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

    public static int getDecreasedExperience(RandomSource random, int experience) {
        return MathHelper.getAmountWithDecimalChance(random, experience * 0.5f);
    }

    public static void applyToLivingDrops(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() == null)
            return;
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_DUMBNESS.get(), event.getAttackingPlayer());
        if (lvl > 0)
            event.setDroppedExperience(getDecreasedExperience(event.getEntity().getRandom(), event.getDroppedExperience()));
    }

    public static void applyToBlockDrops(BlockEvent.BreakEvent event) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_DUMBNESS.get(), event.getPlayer());
        if (lvl > 0)
            event.setExpToDrop(getDecreasedExperience(event.getPlayer().getRandom(), event.getExpToDrop()));
    }

    public static int applyToFishing(FishingHook hook, int exp) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_DUMBNESS.get(), hook.getPlayerOwner());
        if (lvl > 0)
            return getDecreasedExperience(hook.level().random, exp);
        return exp;
    }

    @Override
    public Component getTooltip(ItemStack itemStack, int i) {
        return Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.RED);
    }
}
