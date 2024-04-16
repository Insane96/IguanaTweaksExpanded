package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.util.MathHelper;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.LootBonusEnchantment;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;

public class Smartness extends Enchantment {
    public static TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_smartness");
    public static EnchantmentCategory CATEGORY = EnchantmentCategory.create("accepts_smartness", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
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

    public static int applyToBlockDrops(Player player, int expToDrop) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.SMARTNESS.get(), player);
        if (lvl > 0)
            return Smartness.getIncreasedExperience(player.getRandom(), lvl, expToDrop);
        return expToDrop;
    }

    public static int applyToFishing(FishingHook hook, int exp) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.SMARTNESS.get(), hook.getPlayerOwner());
        if (lvl > 0)
            return getIncreasedExperience(hook.level().random, lvl, exp);
        return exp;
    }
}
