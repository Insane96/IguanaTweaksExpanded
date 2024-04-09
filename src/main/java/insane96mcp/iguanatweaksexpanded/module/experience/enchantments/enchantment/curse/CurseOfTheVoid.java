package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.JuicyBait;
import insane96mcp.iguanatweaksreborn.data.generator.ITRItemTagsProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;

public class CurseOfTheVoid extends Enchantment {
    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITRItemTagsProvider.create("enchanting/accepts_void_curse");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("void_curse", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public CurseOfTheVoid() {
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

    public static void onLootDrop(LivingDropsEvent event) {
        if (!(event.getEntity().level() instanceof ServerLevel level)
                || !(event.getSource().getDirectEntity() instanceof LivingEntity killer))
            return;
        int lvl = killer.getMainHandItem().getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_THE_VOID.get());
        if (lvl <= 0 || level.getRandom().nextFloat() >= 0.35f)
            return;
        event.getDrops().clear();
    }

    public static void onFishing(ItemFishedEvent event) {
        Player player = event.getEntity();
        boolean hasFishingRod = player.getMainHandItem().getItem() instanceof FishingRodItem;
        boolean hasFishingRodInOffHand = player.getOffhandItem().getItem() instanceof FishingRodItem;
        if (!hasFishingRod && !hasFishingRodInOffHand)
            return;
        int lvl = hasFishingRod ? player.getMainHandItem().getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_THE_VOID.get()) : player.getOffhandItem().getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_THE_VOID.get());
        if (lvl == 0 || player.getRandom().nextFloat() > JuicyBait.getChanceToDoubleReel(lvl))
            return;
        if (event.getHookEntity().level().getRandom().nextFloat() >= 0.35f)
            return;

        event.getDrops().clear();
    }
}
