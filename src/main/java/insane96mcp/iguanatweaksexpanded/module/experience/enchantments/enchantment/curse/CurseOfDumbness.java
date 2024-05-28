package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

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
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;

public class CurseOfDumbness extends Enchantment {
    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_dumbness_curse");
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

    public static int applyToBlockDrops(Player player, int expToDrop) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_DUMBNESS.get(), player);
        if (lvl > 0)
            return getDecreasedExperience(player.getRandom(), expToDrop);
        return expToDrop;
    }

    public static int applyToFishing(FishingHook hook, int exp) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_DUMBNESS.get(), hook.getPlayerOwner());
        if (lvl > 0)
            return getDecreasedExperience(hook.level().random, exp);
        return exp;
    }
}
