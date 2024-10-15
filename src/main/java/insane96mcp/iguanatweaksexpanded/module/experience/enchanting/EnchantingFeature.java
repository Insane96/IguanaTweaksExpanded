package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import com.teamabnormals.allurement.core.AllurementConfig;
import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.item.ITEItem;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksexpanded.utils.LogHelper;
import insane96mcp.iguanatweaksreborn.IguanaTweaksReborn;
import insane96mcp.iguanatweaksreborn.module.experience.Experience;
import insane96mcp.iguanatweaksreborn.module.experience.PlayerExperience;
import insane96mcp.iguanatweaksreborn.module.experience.anvils.Anvils;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.base.JsonFeature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.data.IdTagMatcher;
import insane96mcp.insanelib.data.lootmodifier.InjectLootTableModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Label(name = "Enchanting", description = "Adds a brand new enchanting table. If this feature is enabled, a data pack is also enabled that changes the enchanting table recipe to give the new one and replaces xp bottles with enchanted cleansed lapis. Items in iguanatweaksexpanded:not_enchantable tag cannot be enchanted.")
@LoadFeature(module = Modules.Ids.EXPERIENCE)
public class EnchantingFeature extends JsonFeature {
    public static final TagKey<Item> NOT_ENCHANTABLE = ITEItemTagsProvider.create("not_enchantable");
	public static final SimpleBlockWithItem ENCHANTING_TABLE = SimpleBlockWithItem.register("enchanting_table", () -> new ITEEnchantingTable(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE)));
	public static final RegistryObject<BlockEntityType<ITEEnchantingTableBlockEntity>> ENCHANTING_TABLE_BLOCK_ENTITY = ITERegistries.BLOCK_ENTITY_TYPES.register("enchanting_table", () -> BlockEntityType.Builder.of(ITEEnchantingTableBlockEntity::new, ENCHANTING_TABLE.block().get()).build(null));
    public static final RegistryObject<MenuType<ITEEnchantingTableMenu>> ENCHANTING_TABLE_MENU_TYPE = ITERegistries.MENU_TYPES.register("enchanting_table", () -> new MenuType<>(ITEEnchantingTableMenu::new, FeatureFlags.VANILLA_SET));

    public static final String INFUSED_ITEM = IguanaTweaksExpanded.RESOURCE_PREFIX + "infused";
    public static final String EMPOWERED_ITEM = IguanaTweaksExpanded.RESOURCE_PREFIX + "empowered";
    @Config
    @Label(name = "No enchantment merge", description = "Enchanted items can no longer be merged with other enchanted items (also applies to enchanted books).")
    public static Boolean noEnchantmentMerge = true;
    @Config
    @Label(name = "No enchanted smithing", description = "Enchanted items can no longer be upgraded (e.g. netherite)")
    public static Boolean noEnchantedSmithing = true;
    @Config
    @Label(name = "Grindstone.Better XP", description = "If true, grindstone will give XP based off the new enchanting table. This is based off the ITR levelScalingFormula set to a fixed value")
    public static Boolean grindstoneBetterXp = true;
    @Config
    @Label(name = "Grindstone.Treasure enchantment extraction", description = "If true, grindstone will be able to extract treasure enchantments (and curses) from items onto books. Please note this feature is incompatible with Forgery, so you should ban the \"Grindstone disenchantment\" feature from it.")
    public static Boolean grindstoneTreasureEnchantmentExtraction = true;
    @Config
    @Label(name = "Grindstone.Enchantment extraction", description = "If true, grindstone will be able to extract all enchantments, not only treasure enchantments.")
    public static Boolean grindstoneEnchantmentExtraction = true;
    @Config
    @Label(name = "Grindstone.Curse removal", description = "If true, grindstone will also remove curses when disenchanting.")
    public static Boolean grindstoneCurseRemoval = true;
    @Config
    @Label(name = "Allurement integration", description = """
            If true, some mixins are used on Allurement to make the enchantments work on more things and configs are changed to not overlap with ITE.
            Requires Minecraft Restart.
            PLEASE NOTE that due to config limitation, some things cannot be disabled, so better use item tags. E.g. Launch enchantment uses a new iguanatweaksexpanded:enchanting/allurement/accepts_launch_enchantments item tag to decide which item accepts the enchantment.
            """)
    public static Boolean allurementIntegration = true;
    @Config
    @Label(name = "Enchanting Table requires learning enchantments", description = "If true, the new enchanting table must learn all the enchantments and not only treasure.")
    public static Boolean enchantingTableRequiresLearning = true;
    @Config
    @Label(name = "Allow learning Curses", description = "If true, the new enchanting table can learn curses.")
    public static Boolean allowLearningCurses = true;

    public static final RegistryObject<Item> CLEANSED_LAPIS = ITERegistries.ITEMS.register("cleansed_lapis", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENCHANTED_CLEANSED_LAPIS = ITERegistries.ITEMS.register("enchanted_cleansed_lapis", () -> new ITEItem(new Item.Properties(), true));

    public static final List<EnchantmentData> DEFAULT_ENCHANTMENTS_DATA = List.of(
            new EnchantmentData("allurement:alleviating", 5),
            new EnchantmentData("allurement:ascension_curse", 4),
            new EnchantmentData("allurement:fleeting_curse", 4),
            new EnchantmentData("allurement:launch", 2, 4, 6),
            new EnchantmentData("allurement:obedience", 3),
            new EnchantmentData("allurement:reeling", 3, 6, 9),
            new EnchantmentData("allurement:reforming", 3),
            new EnchantmentData("allurement:shockwave", 2, 4, 6, 8, 10),
            new EnchantmentData("allurement:spread_of_ailments", 3, 6, 9, 12),
            new EnchantmentData("allurement:vengeance", 3, 6, 9, 12),
            new EnchantmentData("farmersdelight:backstabbing", 2, 4, 6, 8),
            new EnchantmentData("iguanatweaksexpanded:adrenaline", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksexpanded:air_born", 3),
            new EnchantmentData("iguanatweaksexpanded:air_stealer", 2, 4, 6),
            new EnchantmentData("iguanatweaksexpanded:armor_piercer", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksexpanded:blasting", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksexpanded:blood_pact_curse", 4),
            new EnchantmentData("iguanatweaksexpanded:burst_of_arrows", 6),
            new EnchantmentData("iguanatweaksexpanded:double_jump", 3),
            new EnchantmentData("iguanatweaksexpanded:dumbness_curse", 2),
            new EnchantmentData("iguanatweaksexpanded:ender_curse", 3),
            new EnchantmentData("iguanatweaksexpanded:exchange", 3),
            new EnchantmentData("iguanatweaksexpanded:expanded", 4, 8, 12),
            new EnchantmentData("iguanatweaksexpanded:experience_curse", 4),
            new EnchantmentData("iguanatweaksexpanded:fragility_curse", 2),
            new EnchantmentData("iguanatweaksexpanded:gravity_defying", 3),
            new EnchantmentData("iguanatweaksexpanded:healthy", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksexpanded:hop_curse", 3),
            new EnchantmentData("iguanatweaksexpanded:hoppy", 2, 4, 6),
            new EnchantmentData("iguanatweaksexpanded:inefficiency_curse", 2),
            new EnchantmentData("iguanatweaksexpanded:knowledgeable", 6),
            new EnchantmentData("iguanatweaksexpanded:lucky_hook", 3, 6, 9, 12),
            new EnchantmentData("iguanatweaksexpanded:magic_protection", 2, 4, 6, 8, 10),
            new EnchantmentData("iguanatweaksexpanded:magnetic", 3, 6, 9, 12),
            new EnchantmentData("iguanatweaksexpanded:melee_protection", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksexpanded:padding", 3),
            new EnchantmentData("iguanatweaksexpanded:part_breaker", 3, 6, 9, 12),
            new EnchantmentData("iguanatweaksexpanded:rage", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksexpanded:reach", 6),
            new EnchantmentData("iguanatweaksexpanded:recovery", 3),
            new EnchantmentData("iguanatweaksexpanded:short_arm_curse", 2),
            new EnchantmentData("iguanatweaksexpanded:slow_charge_curse", 3),
            new EnchantmentData("iguanatweaksexpanded:slow_strike_curse", 2),
            new EnchantmentData("iguanatweaksexpanded:smartness", 3, 6, 9, 12),
            new EnchantmentData("iguanatweaksexpanded:soulbound", 3),
            new EnchantmentData("iguanatweaksexpanded:sprint_pact", 6),
            new EnchantmentData("iguanatweaksexpanded:steady_fall", 6),
            new EnchantmentData("iguanatweaksexpanded:steel_fall_curse", 3),
            new EnchantmentData("iguanatweaksexpanded:step_up", 3),
            new EnchantmentData("iguanatweaksexpanded:swift_strike", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksexpanded:tear_curse", 3),
            new EnchantmentData("iguanatweaksexpanded:unhurried_curse", 3),
            new EnchantmentData("iguanatweaksexpanded:veining", 4, 8, 12),
            new EnchantmentData("iguanatweaksexpanded:vindication", 3, 6, 9, 12),
            new EnchantmentData("iguanatweaksexpanded:void_curse", 2),
            new EnchantmentData("iguanatweaksexpanded:walking_curse", 4),
            new EnchantmentData("iguanatweaksexpanded:water_coolant", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksexpanded:zippy", 2, 4, 6),
            new EnchantmentData("iguanatweaksreborn:bane_of_sssss", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksreborn:blast_protection", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksreborn:critical", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksreborn:feather_falling", 2, 4, 6, 8, 10),
            new EnchantmentData("iguanatweaksreborn:fire_aspect", 3, 6, 9),
            new EnchantmentData("iguanatweaksreborn:fire_protection", 2, 4, 6, 8, 10),
            new EnchantmentData("iguanatweaksreborn:knockback", 2, 4, 6),
            new EnchantmentData("iguanatweaksreborn:luck", 3, 6, 9, 12),
            new EnchantmentData("iguanatweaksreborn:projectile_protection", 2, 4, 6, 8, 10),
            new EnchantmentData("iguanatweaksreborn:protection", 6),
            new EnchantmentData("iguanatweaksreborn:sharpness", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("iguanatweaksreborn:smite", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("minecraft:aqua_affinity", 3),
            new EnchantmentData("minecraft:bane_of_arthropods", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("minecraft:binding_curse", 4),
            new EnchantmentData("minecraft:blast_protection", 2, 4, 6, 8, 10),
            new EnchantmentData("minecraft:channeling", 6),
            new EnchantmentData("minecraft:depth_strider", 3, 6, 9),
            new EnchantmentData("minecraft:efficiency", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("minecraft:feather_falling", 2, 4, 6, 8, 10),
            new EnchantmentData("minecraft:fire_aspect", 3, 6, 9),
            new EnchantmentData("minecraft:fire_protection", 2, 4, 6, 8, 10),
            new EnchantmentData("minecraft:flame", 3),
            new EnchantmentData("minecraft:fortune", 3, 6, 9, 12),
            new EnchantmentData("minecraft:frost_walker", 3, 6, 9),
            new EnchantmentData("minecraft:impaling", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("minecraft:infinity", 3, 6, 9, 12, 15),
            new EnchantmentData("minecraft:knockback", 2, 4, 6),
            new EnchantmentData("minecraft:looting", 3, 6, 9, 12),
            new EnchantmentData("minecraft:loyalty", 2, 4, 6, 8),
            new EnchantmentData("minecraft:luck_of_the_sea", 3, 6, 9, 12),
            new EnchantmentData("minecraft:lure", 3, 6, 9, 12),
            new EnchantmentData("minecraft:mending", 3),
            new EnchantmentData("minecraft:multishot", 3),
            new EnchantmentData("minecraft:piercing", 2, 4, 6, 8, 10),
            new EnchantmentData("minecraft:power", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("minecraft:projectile_protection", 2, 4, 6, 8, 10),
            new EnchantmentData("minecraft:protection", 3, 6, 9, 12, 15),
            new EnchantmentData("minecraft:punch", 3, 6, 9),
            new EnchantmentData("minecraft:quick_charge", 2, 4, 6, 8),
            new EnchantmentData("minecraft:respiration", 3, 6, 9, 12),
            new EnchantmentData("minecraft:riptide", 3, 6, 9, 12),
            new EnchantmentData("minecraft:sharpness", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("minecraft:silk_touch", 6),
            new EnchantmentData("minecraft:smite", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("minecraft:soul_speed", 4, 8, 12, 16),
            new EnchantmentData("minecraft:sweeping", 3, 6, 9, 12),
            new EnchantmentData("minecraft:swift_sneak", 3, 6, 9, 12),
            new EnchantmentData("minecraft:thorns", 3, 6, 9, 12),
            new EnchantmentData("minecraft:unbreaking", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("minecraft:vanishing_curse", 4),
            new EnchantmentData("passablefoliage:leaf_walker", 2),
            new EnchantmentData("shieldsplus:ablaze", 2, 4, 6),
            new EnchantmentData("shieldsplus:aegis", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("shieldsplus:celestial_guardian", 6),
            new EnchantmentData("shieldsplus:fast_recovery", 3),
            new EnchantmentData("shieldsplus:lightweight", 3),
            new EnchantmentData("shieldsplus:perfect_parry", 6),
            new EnchantmentData("shieldsplus:recoil", 2, 4, 6),
            new EnchantmentData("shieldsplus:reflection", 3, 6, 9, 12, 15),
            new EnchantmentData("shieldsplus:reinforced", 2, 4, 6, 8, 10, 12),
            new EnchantmentData("shieldsplus:shield_bash", 3, 6, 9, 12),
            new EnchantmentData("supplementaries:stasis", 5)
    );
    public static final ArrayList<EnchantmentData> enchantmentsData = new ArrayList<>();

    public static final List<IdTagMatcher> DEFAULT_OVER_LEVEL_ENCHANTMENT_BLACKLIST = List.of(
            IdTagMatcher.newId("minecraft:depth_strider")
    );
    public static final ArrayList<IdTagMatcher> overLevelEnchantmentBlacklist = new ArrayList<>();

	public EnchantingFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);

        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "new_enchanting_table", Component.literal("IguanaTweaks Expanded New Enchanting Table"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks));

        addSyncType(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "enchantments_data"), new SyncType(json -> loadAndReadJson(json, enchantmentsData, DEFAULT_ENCHANTMENTS_DATA, EnchantmentData.LIST_TYPE)));
        JSON_CONFIGS.add(new JsonConfig<>("enchantments_data.json", enchantmentsData, DEFAULT_ENCHANTMENTS_DATA, EnchantmentData.LIST_TYPE, true, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "enchantments_data")));

        addSyncType(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "over_level_enchantment_blacklist"), new SyncType(json -> loadAndReadJson(json, overLevelEnchantmentBlacklist, DEFAULT_OVER_LEVEL_ENCHANTMENT_BLACKLIST, IdTagMatcher.LIST_TYPE)));
        JSON_CONFIGS.add(new JsonConfig<>("over_level_enchantment_blacklist.json", overLevelEnchantmentBlacklist, DEFAULT_OVER_LEVEL_ENCHANTMENT_BLACKLIST, IdTagMatcher.LIST_TYPE, true, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "over_level_enchantment_blacklist")));
	}

    @Override
    public String getModConfigFolder() {
        return IguanaTweaksReborn.CONFIG_FOLDER;
    }

    @Override
    public void loadJsonConfigs() {
        super.loadJsonConfigs();
        if (this.isEnabled() && ModList.get().isLoaded("allurement") && allurementIntegration) {
            AllurementConfig.COMMON.cheapItemRenaming.set(false);
            AllurementConfig.COMMON.removeTooExpensive.set(false);
            AllurementConfig.COMMON.anvilIngotRepairing.set(false);
            AllurementConfig.COMMON.capAnvilCosts.set(false);
            AllurementConfig.COMMON.enchantableHorseArmor.set(false);
            AllurementConfig.COMMON.enchantedHorseArmorGenerates.set(false);
            AllurementConfig.COMMON.enableVengeance.set(false);
            AllurementConfig.COMMON.reformingTickRate.set(2400);
            AllurementConfig.COMMON.alleviatingHealingFactor.set(0.2d);
        }
        for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
            if (EnchantmentsFeature.isEnchantmentDisabled(enchantment))
                continue;
            int maxLvl = enchantment.getMaxLevel();
            if (maxLvl > 1 && canOverLevel(enchantment))
                maxLvl++;
            StringBuilder costs = new StringBuilder();
            for (int i = 1; i <= maxLvl; i++) {
                costs.append(getCost(enchantment, i, true)).append(" ");
            }
            LogHelper.debug("%s %d (%s)", ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString(), maxLvl, costs);
        }
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!this.isEnabled())
            return;
        preventMergingEnchantedItems(event);
        enchantedCleansedLapisCrafting(event);
        cleansedLapis(event);
        enchantedCleansedLapis(event);
    }

    public void preventMergingEnchantedItems(AnvilUpdateEvent event) {
        if (!noEnchantmentMerge)
            return;

        boolean isValidEnchantedBook = event.getRight().is(Items.ENCHANTED_BOOK) && !hasOnlyCurses(event.getRight());
        if ((event.getRight().isEnchanted() && !event.getRight().is(CLEANSED_LAPIS.get())) || (event.getLeft().isEnchanted() && isValidEnchantedBook))
            event.setCanceled(true);
    }

    public void cleansedLapis(final AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        if (!left.getItem().isEnchantable(left) || left.getTag() == null || left.getTag().contains(EnchantingFeature.INFUSED_ITEM))
            return;

        ItemStack right = event.getRight().copy();
        if (!right.is(CLEANSED_LAPIS.get())
                || right.isEnchanted())
            return;
        event.setCost(0);
        event.setMaterialCost(1);
        ItemStack result = left.copy();
        result.getOrCreateTag().putBoolean(EnchantingFeature.INFUSED_ITEM, true);
        event.setOutput(result);
    }

    public void enchantedCleansedLapisCrafting(final AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        if (!left.is(CLEANSED_LAPIS.get())
                || left.isEnchanted()
                || left.getCount() > 1)
            return;

        ItemStack right = event.getRight().copy();
        if (!right.is(Items.EXPERIENCE_BOTTLE))
            return;
        event.setMaterialCost(1);
        ItemStack result = new ItemStack(ENCHANTED_CLEANSED_LAPIS.get());
        event.setOutput(result);
    }

    public void enchantedCleansedLapis(final AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        if (!left.getItem().isEnchantable(left) || left.getTag() == null || left.getTag().contains(EnchantingFeature.EMPOWERED_ITEM))
            return;

        ItemStack right = event.getRight().copy();
        if (!right.is(ENCHANTED_CLEANSED_LAPIS.get()))
            return;
        event.setCost(0);
        event.setMaterialCost(1);
        ItemStack result = left.copy();
        result.getOrCreateTag().putBoolean(EnchantingFeature.EMPOWERED_ITEM, true);
        event.setOutput(result);
    }

    @SubscribeEvent
    public void onGrindstoneTake(GrindstoneEvent.OnTakeItem event) {
        if (!this.isEnabled()
                || !grindstoneBetterXp
                || (grindstoneTreasureEnchantmentExtraction && event.getTopItem().isEnchanted() && event.getBottomItem().is(Items.BOOK)))
            return;

        float lvl = 0;
        for (Map.Entry<Enchantment, Integer> enchantment : EnchantmentHelper.getEnchantments(event.getTopItem()).entrySet()) {
            /*if (enchantment.getKey().isCurse())
                continue;*/
            lvl += getCost(enchantment.getKey(), enchantment.getValue());
        }
        for (Map.Entry<Enchantment, Integer> enchantment : EnchantmentHelper.getEnchantments(event.getBottomItem()).entrySet()) {
            /*if (enchantment.getKey().isCurse())
                continue;*/
            lvl += getCost(enchantment.getKey(), enchantment.getValue());
        }
        lvl = (int)Math.floor(lvl);

        event.setXp((int) (lvl * PlayerExperience.getBetterScalingLevel(30) * getGrindstonePercentageXpGiven()));
    }

    public static float getGrindstonePercentageXpGiven() {
        return 0.75f;
    }

    @SubscribeEvent
    public void onGrindstoneUpdate(GrindstoneEvent.OnPlaceItem event) {
        if (!this.isEnabled())
            return;

        extractTreasureEnchantments(event);
        resetLodestoneCompass(event);
        removeAllEnchantments(event);
    }

    public static void extractTreasureEnchantments(GrindstoneEvent.OnPlaceItem event) {
        if (!grindstoneTreasureEnchantmentExtraction
                || !event.getTopItem().isEnchanted()
                || !event.getBottomItem().is(Items.BOOK)
                || event.getBottomItem().getCount() > 1)
            return;

        ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
        for (Map.Entry<Enchantment, Integer> enchantmentInstance : EnchantmentHelper.getEnchantments(event.getTopItem()).entrySet()) {
            if (!enchantmentInstance.getKey().isTreasureOnly() && !grindstoneEnchantmentExtraction)
                continue;
            EnchantedBookItem.addEnchantment(output, new EnchantmentInstance(enchantmentInstance.getKey(), enchantmentInstance.getValue()));
        }
        event.setOutput(output);
        event.setXp(0);
    }

    public static void resetLodestoneCompass(GrindstoneEvent.OnPlaceItem event) {
        if (!event.getTopItem().is(Items.COMPASS)
                || !CompassItem.isLodestoneCompass(event.getTopItem())
                || !event.getBottomItem().isEmpty())
            return;

        ItemStack output = new ItemStack(Items.COMPASS);
        event.setOutput(output);
        event.setXp(0);
    }

    public static void removeAllEnchantments(GrindstoneEvent.OnPlaceItem event) {
        if (!grindstoneCurseRemoval)
            return;

        if (event.getBottomItem().isEmpty() && event.getTopItem().isEmpty())
            return;
        if (!event.getBottomItem().isEmpty() && !event.getTopItem().isEmpty())
            return;
        ItemStack output = event.getBottomItem().isEmpty() ? event.getTopItem().copy() : event.getBottomItem().copy();
        if (output.isEmpty()
                || !output.isEnchanted())
            return;

        output.getTag().remove("Enchantments");
        event.setOutput(output);
    }

    public static int getCost(Enchantment enchantment, int lvl, boolean checkCurses) {
        if (lvl <= 0 || (enchantment.isCurse() && !checkCurses))
            return 0;
        float vanillaCost = Anvils.getRarityCost(enchantment);
        EnchantmentData enchantmentData = enchantmentsData.stream()
                .filter(data -> data.enchantment.matchesEnchantment(enchantment))
                .findFirst()
                .orElse(null);

        if (enchantmentData != null) {
            if (enchantmentData.cost.length < lvl)
                LogHelper.warn("Enchantment data for %s is missing cost for level %d. Using default formula", enchantmentData.enchantment.location, lvl);
            else
                return enchantmentData.cost[lvl - 1];
        }
        return (int) Math.round(vanillaCost * lvl);
    }

    public static int getCost(Enchantment enchantment, int lvl) {
        return getCost(enchantment, lvl, false);
    }

    public static boolean canBeEnchanted(ItemStack stack) {
        return stack.getItem().isEnchantable(stack) && (!stack.isEnchanted() || hasOnlyCurses(stack)) && !stack.is(EnchantingFeature.NOT_ENCHANTABLE);
    }

    public static boolean hasOnlyCurses(ItemStack stack) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        if (enchantments.isEmpty())
            return false;
        for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
            if (!enchantment.getKey().isCurse())
                return false;
        }
        return true;
    }

    public static boolean hasCurses(ItemStack stack) {
        if (!stack.isEnchanted())
            return false;
        for (Map.Entry<Enchantment, Integer> enchantment : stack.getAllEnchantments().entrySet()) {
            if (enchantment.getKey().isCurse())
                return true;
        }
        return false;
    }

    public static float getCurseCost(ItemStack stack) {
        float cost = 0f;
        for (Map.Entry<Enchantment, Integer> enchantment : stack.getAllEnchantments().entrySet()) {
            if (enchantment.getKey().isCurse())
                cost += getCost(enchantment.getKey(), 1, true);
        }
        return cost;
    }

    public static boolean hasEnchantGlintOnly(ItemStack stack) {
        ListTag enchantments = stack.getEnchantmentTags();
        if (enchantments.isEmpty())
            return false;
        return enchantments.size() == 1 && enchantments.getCompound(0).isEmpty();
    }

    public static boolean canOverLevel(Enchantment enchantment) {
        for (IdTagMatcher idTagMatcher : overLevelEnchantmentBlacklist) {
            if (idTagMatcher.matchesEnchantment(enchantment))
                return false;
        }
        return true;
    }

    public static List<EnchantmentInstance> getPendingEnchantments(ItemStack stack) {
        if (stack.getTag() == null)
            return Collections.emptyList();
        List<EnchantmentInstance> pendingEnchantments = new ArrayList<>();
        ListTag enchantmentsListTag = stack.getTag().getList("PendingEnchantments", CompoundTag.TAG_COMPOUND);
        for (int i = 0; i < enchantmentsListTag.size(); ++i) {
            CompoundTag compoundtag = enchantmentsListTag.getCompound(i);
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(compoundtag.getString("id")));
            short lvl = compoundtag.getShort("lvl");
            if (enchantment != null) {
                pendingEnchantments.add(new EnchantmentInstance(enchantment, lvl));
            }
        }
        return pendingEnchantments;
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!isEnabled(EnchantingFeature.class)
                || !(event.getEntity() instanceof Player)
                || Experience.disableExperience)
            return;

        treasureEnchantmentsEnchantedBooksTooltip(stack, event.getToolTip());
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof AnvilScreen) && !(mc.screen instanceof ITEEnchantingTableScreen) && !Screen.hasShiftDown())
            return;

        enchantabilityTooltip(stack, event.getToolTip());

        CompoundTag tag = stack.getTag();
        if (tag == null)
            return;
        infusedEmpoweredTooltip(stack, tag, event.getToolTip());
        pendingEnchantmentsTooltip(stack, tag, event.getToolTip());
    }

    @OnlyIn(Dist.CLIENT)
    private static void treasureEnchantmentsEnchantedBooksTooltip(ItemStack stack, List<Component> tooltip) {
        if (stack.is(Items.ENCHANTED_BOOK)) {
            for (Map.Entry<Enchantment, Integer> enchantment : EnchantmentHelper.getEnchantments(stack).entrySet()) {
                if ((enchantment.getKey().isTreasureOnly() || enchantingTableRequiresLearning) && (!enchantment.getKey().isCurse() || allowLearningCurses)) {
                    tooltip.add(Component.empty());
                    tooltip.add(Component.translatable("iguanatweaksexpanded.apply_to_enchanting_table").withStyle(ChatFormatting.GREEN));
                    break;
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void enchantabilityTooltip(ItemStack stack, List<Component> tooltip) {
        int enchantmentValue = EnchantmentsFeature.getEnchantmentValue(stack);
        if (enchantmentValue <= 0 || stack.is(EnchantingFeature.NOT_ENCHANTABLE))
            return;
        tooltip.add(Component.translatable("iguanatweaksexpanded.base_enchantability", enchantmentValue).withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    @OnlyIn(Dist.CLIENT)
    private static void infusedEmpoweredTooltip(ItemStack stack, CompoundTag tag, List<Component> tooltip) {
        if (tag.contains(INFUSED_ITEM)) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("iguanatweaksexpanded.infused_item").withStyle(ChatFormatting.DARK_PURPLE));
        }
        if (tag.contains(EMPOWERED_ITEM)) {
            tooltip.add(Component.translatable("iguanatweaksexpanded.empowered_item").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void pendingEnchantmentsTooltip(ItemStack stack, CompoundTag tag, List<Component> tooltip) {
        if (tag.contains("PendingEnchantments") && canBeEnchanted(stack)) {
            tooltip.add(Component.translatable("iguanatweaksexpanded.has_pending_enchantments").withStyle(ChatFormatting.DARK_GRAY));
            if (Minecraft.getInstance().screen instanceof ITEEnchantingTableScreen) {
                ListTag enchantmentsListTag = tag.getList("PendingEnchantments", CompoundTag.TAG_COMPOUND);
                for (int i = 0; i < enchantmentsListTag.size(); ++i) {
                    CompoundTag compoundtag = enchantmentsListTag.getCompound(i);
                    Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(compoundtag.getString("id")));
                    if (enchantment == null)
                        continue;
                    short lvl = compoundtag.getShort("lvl");
                    MutableComponent mutablecomponent = CommonComponents.space().append(Component.translatable(enchantment.getDescriptionId())).withStyle(ChatFormatting.DARK_GRAY);
                    if (lvl != 1 || enchantment.getMaxLevel() != 1) {
                        mutablecomponent.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + lvl));
                    }
                    tooltip.add(mutablecomponent);
                    if (Screen.hasShiftDown())
                        tooltip.add(Component.literal("  ").append(Component.translatable(enchantment.getDescriptionId() + ".info").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC)));
                }
            }
        }
    }

    private static final String path = "experience/enchanting/";
    public static void addGlobalLoot(GlobalLootModifierProvider provider) {
        provider.add(path + "blocks/lapis_ore", new InjectLootTableModifier(new ResourceLocation("minecraft:blocks/lapis_ore"), new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "blocks/injection/cleansed_lapis")));
        provider.add(path + "blocks/deepslate_lapis_ore", new InjectLootTableModifier(new ResourceLocation("minecraft:blocks/deepslate_lapis_ore"), new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "blocks/injection/cleansed_lapis")));
        provider.add(path + "curses_from_spawners", new InjectLootTableModifier(new ResourceLocation(IguanaTweaksReborn.RESOURCE_PREFIX + "empowered_spawner"), new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "curses_from_spawners")));
    }
}