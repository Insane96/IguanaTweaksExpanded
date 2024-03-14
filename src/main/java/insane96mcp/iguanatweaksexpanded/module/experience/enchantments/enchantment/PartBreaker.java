package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.InsaneLib;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class PartBreaker extends Enchantment implements IEnchantmentTooltip {

    public PartBreaker() {
        super(Rarity.RARE, EnchantmentsFeature.WEAPONS_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return 22 * level;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 22;
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
                    if (level.getRandom().nextFloat() < PartBreaker.getChance(lvl))
                        event.getDrops().add(new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack));
                });
    }

    @Override
    public Component getTooltip(ItemStack itemStack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", InsaneLib.ONE_DECIMAL_FORMATTER.format(getChance(lvl) * 100f)).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
