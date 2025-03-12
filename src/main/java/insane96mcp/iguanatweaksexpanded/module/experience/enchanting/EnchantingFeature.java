package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import com.teamabnormals.allurement.core.AllurementConfig;
import insane96mcp.iguanatweaksexpanded.InsaneSurvivalExtra;
import insane96mcp.iguanatweaksexpanded.data.generator.ISEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.item.ISEItem;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ISEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksexpanded.utils.LogHelper;
import insane96mcp.iguanatweaksreborn.InsaneSurvivalOverhaul;
import insane96mcp.iguanatweaksreborn.module.experience.DroppedExperience;
import insane96mcp.iguanatweaksreborn.module.experience.PlayerExperience;
import insane96mcp.iguanatweaksreborn.module.experience.anvils.Anvils;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.base.JsonFeature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.data.IdTagMatcher;
import insane96mcp.insanelib.data.IdTagValue;
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
    public static final TagKey<Item> NOT_ENCHANTABLE = ISEItemTagsProvider.create("not_enchantable");
	public static final SimpleBlockWithItem ENCHANTING_TABLE = SimpleBlockWithItem.register("enchanting_table", () -> new ISEEnchantingTable(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE)));
	public static final RegistryObject<BlockEntityType<ISEEnchantingTableBlockEntity>> ENCHANTING_TABLE_BLOCK_ENTITY = ISERegistries.BLOCK_ENTITY_TYPES.register("enchanting_table", () -> BlockEntityType.Builder.of(ISEEnchantingTableBlockEntity::new, ENCHANTING_TABLE.block().get()).build(null));
    public static final RegistryObject<MenuType<ISEEnchantingTableMenu>> ENCHANTING_TABLE_MENU_TYPE = ISERegistries.MENU_TYPES.register("enchanting_table", () -> new MenuType<>(ISEEnchantingTableMenu::new, FeatureFlags.VANILLA_SET));

    public static final RegistryObject<Item> CLEANSED_LAPIS = ISERegistries.ITEMS.register("cleansed_lapis", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENCHANTED_CLEANSED_LAPIS = ISERegistries.ITEMS.register("enchanted_cleansed_lapis", () -> new ISEItem(new Item.Properties(), true));
    public static final String PURIFIED_ITEM = InsaneSurvivalExtra.RESOURCE_PREFIX + "infused";
    public static final String INFUSED_ITEM = InsaneSurvivalExtra.RESOURCE_PREFIX + "empowered";

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
    public static Boolean grindstoneCurseRemoval = false;
    @Config
    @Label(name = "Allurement integration", description = """
            If true, some mixins are used on Allurement to make the enchantments work on more things and configs are changed to not overlap with ITE.
            Requires Minecraft Restart.
            PLEASE NOTE that due to config limitation, some things cannot be disabled, so use item tags. E.g. Launch enchantment uses a new iguanatweaksexpanded:enchanting/allurement/accepts_launch_enchantments item tag to decide which item accepts the enchantment.
            """)
    public static Boolean allurementIntegration = true;
    @Config
    @Label(name = "Enchanting Table.Requires learning enchantments", description = "If true, the new enchanting table must learn all the enchantments and not only treasure.")
    public static Boolean enchantingTableRequiresLearning = true;
    @Config
    @Label(name = "Enchanting Table.One time use enchantments", description = "If true, all the enchantments in the enchanting table (so, not only curses) are one time use. If enabled, you can no longer disenchant items in grindstone to prevent accidental loss.")
    public static Boolean enchantingTableOneTimeUseEnchantments = false;
    @Config(min = 0)
    @Label(name = "Enchanting Table.Max enchanting power", description = "Increasing this increases bookshelves required. Vanilla is 15")
    public static Integer enchantingTableMaxEnchantingPower = 20;
    @Config(min = 0)
    @Label(name = "Enchanting Table.Enchantability multiplier", description = "Tool enchantability multiplier if not purified or infused")
    public static Double enchantingTableEnchantabilityMultiplier = 0.5d;
    @Config(min = 0)
    @Label(name = "Enchanting Table.Purified Enchantability multiplier", description = "Tool enchantability multiplier when purified (sums with Enchantability multiplier and Infused bonus Enchantability multiplier)")
    public static Double enchantingTablePurifiedEnchantabilityMultiplier = 0.5d;
    @Config(min = 0)
    @Label(name = "Enchanting Table.Purified Enchantability flat", description = "Tool enchantability bonus when purified")
    public static Integer enchantingTablePurifiedEnchantabilityFlat = 0;
    @Config(min = 0)
    @Label(name = "Enchanting Table.Infused bonus Enchantability multiplier", description = "Tool enchantability bonus percentage when infused (sums with Enchantability multiplier and Purified bonus Enchantability multiplier)")
    public static Double enchantingTableInfusedBonusEnchantability = 0.2d;
    @Config(min = 0)
    @Label(name = "Enchanting Table.Infused bonus Enchantability flat", description = "Tool enchantability bonus when infused")
    public static Integer enchantingTableInfusedBonusEnchantabilityFlat = 1;
    @Config(min = 0)
    @Label(name = "Enchanting Table.Base enchantability")
    public static Integer enchantingTableBaseEnchantability = 1;

    public static final List<EnchantmentData> DEFAULT_ENCHANTMENTS_DATA = List.of(
            new EnchantmentData("minecraft:unbreaking").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:enduring").costPerLevel(1),
            new EnchantmentData("minecraft:mending").costPerLevel(2),
            new EnchantmentData("allurement:reforming").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:soulbound").costPerLevel(2),

            new EnchantmentData("iguanatweaksreborn:luck").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:smartness").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:reach").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:knowledgeable").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:adrenaline").costPerLevel(1),

            new EnchantmentData("minecraft:lure").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:lucky_hook").costPerLevel(4),

            new EnchantmentData("minecraft:power").costPerLevel(1),
            new EnchantmentData("minecraft:punch").costPerLevel(2),
            new EnchantmentData("minecraft:flame").costPerLevel(2),
            new EnchantmentData("minecraft:infinity").costPerLevel(1),

            new EnchantmentData("minecraft:quick_charge").cost(1, 1, 2, 3),
            new EnchantmentData("minecraft:multishot").costPerLevel(3),
            new EnchantmentData("minecraft:piercing").costPerLevel(1),
            new EnchantmentData("allurement:reeling").costPerLevel(2),
            new EnchantmentData("allurement:spread_of_ailments").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:gravity_defying").costPerLevel(2),

            new EnchantmentData("minecraft:loyalty").costPerLevel(2),
            new EnchantmentData("minecraft:channeling").costPerLevel(4),
            new EnchantmentData("minecraft:riptide").costPerLevel(1),
            new EnchantmentData("minecraft:impaling").costPerLevel(1),

            new EnchantmentData("minecraft:efficiency").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:haste").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:blasting").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:expanded").costPerLevel(3),
            new EnchantmentData("minecraft:silk_touch").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:exchange").costPerLevel(2),

            new EnchantmentData("iguanatweaksreborn:sharpness").costPerLevel(1),
            new EnchantmentData("iguanatweaksreborn:bane_of_sssss").costPerLevel(1),
            new EnchantmentData("iguanatweaksreborn:smite").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:water_coolant").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:bane_of_noses").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:rage").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:swift_strike").costPerLevel(1),
            new EnchantmentData("iguanatweaksreborn:critical").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:armor_piercer").costPerLevel(1),
            new EnchantmentData("iguanatweaksreborn:fire_aspect").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:cryo_aspect").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:explosive").costPerLevel(2),
            new EnchantmentData("iguanatweaksreborn:knockback").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:padding").costPerLevel(2),
            new EnchantmentData("allurement:launch").costPerLevel(2),
            new EnchantmentData("iguanatweaksreborn:sweeping_edge").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:part_breaker").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:air_stealer").costPerLevel(2),

            new EnchantmentData("iguanatweaksreborn:protection").costPerLevel(4),
            new EnchantmentData("iguanatweaksreborn:blast_protection").costPerLevel(1),
            new EnchantmentData("iguanatweaksreborn:fire_protection").costPerLevel(1),
            new EnchantmentData("iguanatweaksreborn:projectile_protection").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:magic_protection").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:melee_protection").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:healthy").costPerLevel(1),
            new EnchantmentData("allurement:alleviating").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:flat_protection").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:absorption").costPerLevel(4),

            new EnchantmentData("minecraft:aqua_affinity").costPerLevel(3),
            new EnchantmentData("minecraft:respiration").costPerLevel(2),

            new EnchantmentData("minecraft:thorns").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:air_born").costPerLevel(3),
            new EnchantmentData("iguanatweaksexpanded:recovery").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:vindication").costPerLevel(2),

            new EnchantmentData("minecraft:swift_sneak").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:magnetic").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:retreat").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:sprint_pact").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:step_up").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:zippy").costPerLevel(2),

            new EnchantmentData("iguanatweaksreborn:feather_falling").costPerLevel(1),
            new EnchantmentData("minecraft:soul_speed").costPerLevel(2),
            new EnchantmentData("minecraft:depth_strider").costPerLevel(2),
            new EnchantmentData("minecraft:frost_walker").costPerLevel(2),
            new EnchantmentData("allurement:shockwave").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:double_jump").costPerLevel(4),
            new EnchantmentData("iguanatweaksexpanded:hoppy").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:steady_fall").costPerLevel(4),
            new EnchantmentData("passablefoliage:leaf_walker").costPerLevel(2),

            new EnchantmentData("shieldsplus:ablaze").costPerLevel(1),
            new EnchantmentData("shieldsplus:aegis").costPerLevel(1),
            new EnchantmentData("shieldsplus:celestial_guardian").costPerLevel(4),
            new EnchantmentData("shieldsplus:fast_recovery").costPerLevel(2),
            new EnchantmentData("shieldsplus:lightweight").costPerLevel(2),
            new EnchantmentData("shieldsplus:perfect_parry").costPerLevel(4),
            new EnchantmentData("shieldsplus:recoil").costPerLevel(2),
            new EnchantmentData("shieldsplus:reflection").costPerLevel(1),
            new EnchantmentData("shieldsplus:reinforced").costPerLevel(1),

            new EnchantmentData("allurement:ascension_curse").costPerLevel(3),
            new EnchantmentData("allurement:fleeting_curse").costPerLevel(3),
            new EnchantmentData("allurement:obedience").costPerLevel(2),
            new EnchantmentData("farmersdelight:backstabbing").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:blood_pact_curse").costPerLevel(3),
            new EnchantmentData("iguanatweaksexpanded:dumbness_curse").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:ender_curse").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:experience_curse").costPerLevel(3),
            new EnchantmentData("iguanatweaksexpanded:fragility_curse").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:frenzy_curse").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:hop_curse").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:inefficiency_curse").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:obscurity_curse").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:short_arm_curse").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:slow_charge_curse").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:slow_strike_curse").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:static_charge_curse").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:steel_fall_curse").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:tear_curse").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:unhurried_curse").costPerLevel(2),
            new EnchantmentData("iguanatweaksexpanded:unstable_motion_curse").costPerLevel(3),
            new EnchantmentData("iguanatweaksexpanded:void_curse").costPerLevel(1),
            new EnchantmentData("iguanatweaksexpanded:walking_curse").costPerLevel(3),
            new EnchantmentData("minecraft:binding_curse").costPerLevel(3),
            new EnchantmentData("minecraft:vanishing_curse").costPerLevel(3),
            new EnchantmentData("supplementaries:stasis").costPerLevel(4)
    );
    public static final ArrayList<EnchantmentData> enchantmentsData = new ArrayList<>();

    public static final List<IdTagMatcher> DEFAULT_OVER_LEVEL_ENCHANTMENT_BLACKLIST = List.of(
            IdTagMatcher.newId("minecraft:depth_strider")
    );
    public static final ArrayList<IdTagMatcher> overLevelEnchantmentBlacklist = new ArrayList<>();

    public static final List<IdTagValue> DEFAULT_STARTING_ENCHANTMENTS = List.of(

    );
    public static final ArrayList<IdTagValue> startingEnchantments = new ArrayList<>();

	public EnchantingFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);

        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "new_enchanting_table", Component.literal("IguanaTweaks Expanded New Enchanting Table"), () -> this.isEnabled() && !ISEDataPacks.disableAllDataPacks));

        addSyncType(new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "enchantments_data"), new SyncType(json -> loadAndReadJson(json, enchantmentsData, DEFAULT_ENCHANTMENTS_DATA, EnchantmentData.LIST_TYPE)));
        JSON_CONFIGS.add(new JsonConfig<>("enchantments_data.json", enchantmentsData, DEFAULT_ENCHANTMENTS_DATA, EnchantmentData.LIST_TYPE, true, new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "enchantments_data")));

        addSyncType(new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "over_level_enchantment_blacklist"), new SyncType(json -> loadAndReadJson(json, overLevelEnchantmentBlacklist, DEFAULT_OVER_LEVEL_ENCHANTMENT_BLACKLIST, IdTagMatcher.LIST_TYPE)));
        JSON_CONFIGS.add(new JsonConfig<>("over_level_enchantment_blacklist.json", overLevelEnchantmentBlacklist, DEFAULT_OVER_LEVEL_ENCHANTMENT_BLACKLIST, IdTagMatcher.LIST_TYPE, true, new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "over_level_enchantment_blacklist")));

        addSyncType(new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "starting_enchantments"), new SyncType(json -> loadAndReadJson(json, startingEnchantments, DEFAULT_STARTING_ENCHANTMENTS, IdTagValue.LIST_TYPE)));
        JSON_CONFIGS.add(new JsonConfig<>("starting_enchantments.json", startingEnchantments, DEFAULT_STARTING_ENCHANTMENTS, IdTagValue.LIST_TYPE, true, new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "over_level_enchantment_blacklist")));
	}

    @Override
    public String getModConfigFolder() {
        return InsaneSurvivalOverhaul.CONFIG_FOLDER;
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
            AllurementConfig.COMMON.reformingTickRate.set(1200);
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
            LogHelper.debug("%s %d (%s) %s", ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString(), maxLvl, costs, enchantment.isTreasureOnly());
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

        boolean isEnchantedBook = event.getRight().is(Items.ENCHANTED_BOOK);
        if (event.getRight().isEnchanted() || (isEnchantedBook && !event.getLeft().is(Items.ENCHANTED_BOOK)))
            event.setCanceled(true);
    }

    public void cleansedLapis(final AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        if (!left.getItem().isEnchantable(left) || left.getTag() == null || left.getTag().contains(EnchantingFeature.PURIFIED_ITEM))
            return;

        ItemStack right = event.getRight().copy();
        if (!right.is(CLEANSED_LAPIS.get())
                || right.isEnchanted())
            return;
        event.setCost(0);
        event.setMaterialCost(1);
        ItemStack result = left.copy();
        result.getOrCreateTag().putBoolean(EnchantingFeature.PURIFIED_ITEM, true);
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
        if (!left.getItem().isEnchantable(left) || left.getTag() == null || left.getTag().contains(EnchantingFeature.INFUSED_ITEM))
            return;

        ItemStack right = event.getRight().copy();
        if (!right.is(ENCHANTED_CLEANSED_LAPIS.get()))
            return;
        event.setCost(0);
        event.setMaterialCost(1);
        ItemStack result = left.copy();
        result.getOrCreateTag().putBoolean(EnchantingFeature.INFUSED_ITEM, true);
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
        extractEnchantments(event);
        resetLodestoneCompass(event);
        removeAllEnchantments(event);
        if (enchantingTableOneTimeUseEnchantments && (!event.getTopItem().isEmpty() && event.getBottomItem().isEmpty()) || (event.getTopItem().isEmpty() && !event.getBottomItem().isEmpty()))
            event.setCanceled(true);
    }

    public static void extractEnchantments(GrindstoneEvent.OnPlaceItem event) {
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
            int cost = enchantmentData.getCost(lvl);
            if (cost > 0)
                return cost;
            LogHelper.warn("Enchantment data for %s lvl %s is missing. Using default formula", ForgeRegistries.ENCHANTMENTS.getKey(enchantment), lvl);
        }
        else
            LogHelper.warn("Enchantment data for %s is missing. Using default formula", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
        return Math.round(vanillaCost * lvl);
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

    public static void removePendingEnchantment(ItemStack stack, Enchantment enchantment) {
        if (stack.getTag() == null)
            return;
        CompoundTag toRemove = null;
        ListTag enchantmentsListTag = stack.getTag().getList("PendingEnchantments", CompoundTag.TAG_COMPOUND);
        for (int i = 0; i < enchantmentsListTag.size(); ++i) {
            CompoundTag compoundtag = enchantmentsListTag.getCompound(i);
            Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(compoundtag.getString("id")));
            if (ench != null && ench == enchantment) {
                toRemove = compoundtag;
                break;
            }
        }
        if (toRemove != null)
            enchantmentsListTag.remove(toRemove);
    }

    public static void clearPendingEnchantments(ItemStack stack) {
        if (stack.getTag() == null)
            return;
        stack.getTag().remove("PendingEnchantments");
    }

    public static boolean isStartingEnchantment(Enchantment enchantment) {
        for (IdTagValue idTagValue : startingEnchantments) {
            if (idTagValue.id.matchesEnchantment(enchantment))
                return true;
        }
        return false;
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!isEnabled(EnchantingFeature.class)
                || !(event.getEntity() instanceof Player)
                || DroppedExperience.disableExperience)
            return;

        treasureEnchantmentsEnchantedBooksTooltip(stack, event.getToolTip());
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof AnvilScreen) && !(mc.screen instanceof ISEEnchantingTableScreen) && !Screen.hasShiftDown())
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
                if ((enchantment.getKey().isTreasureOnly() || enchantingTableRequiresLearning)) {
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
        if (tag.contains(PURIFIED_ITEM)) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("iguanatweaksexpanded.infused_item").withStyle(ChatFormatting.DARK_PURPLE));
        }
        if (tag.contains(INFUSED_ITEM)) {
            tooltip.add(Component.translatable("iguanatweaksexpanded.empowered_item").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void pendingEnchantmentsTooltip(ItemStack stack, CompoundTag tag, List<Component> tooltip) {
        if (tag.contains("PendingEnchantments") && canBeEnchanted(stack)) {
            tooltip.add(Component.translatable("iguanatweaksexpanded.has_pending_enchantments").withStyle(ChatFormatting.DARK_GRAY));
            if (Minecraft.getInstance().screen instanceof ISEEnchantingTableScreen) {
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
                    //if (Screen.hasShiftDown())
                        //tooltip.add(Component.literal("  ").append(Component.translatable(enchantment.getDescriptionId() + ".desc").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC)));
                }
            }
        }
    }

    private static final String path = "experience/enchanting/";
    public static void addGlobalLoot(GlobalLootModifierProvider provider) {
        provider.add(path + "blocks/lapis_ore", new InjectLootTableModifier(new ResourceLocation("minecraft:blocks/lapis_ore"), new ResourceLocation(InsaneSurvivalExtra.RESOURCE_PREFIX + "blocks/injection/cleansed_lapis")));
        provider.add(path + "blocks/deepslate_lapis_ore", new InjectLootTableModifier(new ResourceLocation("minecraft:blocks/deepslate_lapis_ore"), new ResourceLocation(InsaneSurvivalExtra.RESOURCE_PREFIX + "blocks/injection/cleansed_lapis")));
    }
}