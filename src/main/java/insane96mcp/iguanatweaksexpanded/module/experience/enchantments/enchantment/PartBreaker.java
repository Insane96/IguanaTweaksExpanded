package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class PartBreaker extends Enchantment {

    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_part_breaker");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("part_breaker", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public PartBreaker() {
        super(Rarity.RARE, CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public static float getChance(int lvl) {
        return lvl * 0.05f;
    }

    public static void onLootDrop(LivingDropsEvent event) {
        if (!(event.getEntity().level() instanceof ServerLevel level)
                || !(event.getSource().getDirectEntity() instanceof LivingEntity killer))
            return;
        int lvl = killer.getMainHandItem().getEnchantmentLevel(NewEnchantmentsFeature.PART_BREAKER.get());
        if (lvl <= 0)
            return;
        ResourceLocation lootTableLocation = ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).withPrefix("part_breaking/");
        LootParams.Builder lootParamsBuilder = new LootParams.Builder(level);
        if (killer instanceof Player player)
            lootParamsBuilder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player);

        lootParamsBuilder.withParameter(LootContextParams.DAMAGE_SOURCE, event.getSource());
        lootParamsBuilder.withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, killer);
        lootParamsBuilder.withOptionalParameter(LootContextParams.KILLER_ENTITY, killer);
        lootParamsBuilder.withParameter(LootContextParams.THIS_ENTITY, event.getEntity());
        lootParamsBuilder.withParameter(LootContextParams.ORIGIN, event.getEntity().position());
        LootParams lootParams = lootParamsBuilder.create(LootContextParamSets.ENTITY);
        LootTable lootTable = level.getServer().getLootData().getLootTable(lootTableLocation);
        lootTable.getRandomItems(lootParams)
                .forEach(stack -> {
                    for (ItemEntity itemEntity : event.getDrops()) {
                        if (itemEntity.getItem().getItem() == stack.getItem()) {
                            stack.shrink(itemEntity.getItem().getCount());
                        }
                    }
                    if (level.getRandom().nextFloat() < PartBreaker.getChance(lvl)) {
                        event.getDrops().add(new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack));
                    }
                });
    }
}
