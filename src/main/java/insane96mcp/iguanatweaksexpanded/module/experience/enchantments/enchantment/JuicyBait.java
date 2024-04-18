package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.LootBonusEnchantment;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.entity.player.ItemFishedEvent;

import java.util.List;

public class JuicyBait extends Enchantment {

    public JuicyBait() {
        super(Rarity.RARE, EnchantmentCategory.FISHING_ROD, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinCost(int pEnchantmentLevel) {
        return 15 + (pEnchantmentLevel - 1) * 9;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof LootBonusEnchantment) && super.checkCompatibility(other);
    }

    public static float getChanceToDoubleReel(int lvl) {
        return lvl * 0.05f;
    }

    public static void apply(ItemFishedEvent event) {
        Player player = event.getEntity();
        boolean hasFishingRod = player.getMainHandItem().getItem() instanceof FishingRodItem;
        boolean hasFishingRodInOffHand = player.getOffhandItem().getItem() instanceof FishingRodItem;
        if (!hasFishingRod && !hasFishingRodInOffHand)
            return;
        int lvl = hasFishingRod ? player.getMainHandItem().getEnchantmentLevel(NewEnchantmentsFeature.JUICY_BAIT.get()) : player.getOffhandItem().getEnchantmentLevel(NewEnchantmentsFeature.JUICY_BAIT.get());
        if (lvl == 0 || player.getRandom().nextFloat() > JuicyBait.getChanceToDoubleReel(lvl))
            return;
        ItemStack stack = hasFishingRod ? player.getMainHandItem() : player.getOffhandItem();
        FishingHook hookEntity = event.getHookEntity();
        LootParams lootparams = (new LootParams.Builder((ServerLevel) player.level())).withParameter(LootContextParams.ORIGIN, hookEntity.position()).withParameter(LootContextParams.TOOL, stack).withParameter(LootContextParams.THIS_ENTITY, hookEntity).withParameter(LootContextParams.KILLER_ENTITY, player).withParameter(LootContextParams.THIS_ENTITY, hookEntity).withLuck((float) hookEntity.luck + player.getLuck()).create(LootContextParamSets.FISHING);
        LootTable loottable = player.level().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
        List<ItemStack> list = loottable.getRandomItems(lootparams);
        for(ItemStack itemstack : list) {
            ItemEntity itementity = new ItemEntity(hookEntity.level(), hookEntity.getX(), hookEntity.getY(), hookEntity.getZ(), itemstack);
            double xDiff = player.getX() - hookEntity.getX();
            double yDiff = player.getY() - hookEntity.getY();
            double zDiff = player.getZ() - hookEntity.getZ();
            double deltaMovMultiplier = 0.1D;
            itementity.setDeltaMovement(xDiff * deltaMovMultiplier, yDiff * deltaMovMultiplier + Math.sqrt(Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff)) * 0.08D, zDiff * deltaMovMultiplier);
            hookEntity.level().addFreshEntity(itementity);
            player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5D, player.getZ() + 0.5D, event.getEntity().getRandom().nextInt(6) + 1));
            if (itemstack.is(ItemTags.FISHES))
                player.awardStat(Stats.FISH_CAUGHT, 1);
        }
    }
}
