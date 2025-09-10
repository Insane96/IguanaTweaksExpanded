package insane96mcp.iguanatweaksexpanded;

import insane96mcp.iguanatweaksexpanded.data.criterion.ISETriggers;
import insane96mcp.iguanatweaksexpanded.data.generator.*;
import insane96mcp.iguanatweaksexpanded.data.generator.client.ISEBlockModelsProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.client.ISEBlockStatesProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.client.ISEItemModelsProvider;
import insane96mcp.iguanatweaksexpanded.module.items.recallpotion.Recall;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.Solarium;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.durium.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.quaron.Quaron;
import insane96mcp.iguanatweaksexpanded.network.NetworkHandler;
import insane96mcp.iguanatweaksexpanded.setup.ISECommonConfig;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksexpanded.setup.client.ClientSetup;
import insane96mcp.iguanatweaksreborn.InsaneSO;
import insane96mcp.iguanatweaksreborn.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksreborn.module.items.copper.CopperEquipment;
import insane96mcp.iguanatweaksreborn.module.items.repairkit.RepairKits;
import insane96mcp.insanelib.InsaneLib;
import insane96mcp.insanelib.util.IntegratedPack;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.MissingMappingsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;

@Mod("iguanatweaksexpanded")
public class InsaneSE
{
	public static final String MOD_ID = "iguanatweaksexpanded";
	public static final String RESOURCE_PREFIX = MOD_ID + ":";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final RecipeBookType MULTI_ITEM_BLASTING_RECIPE_BOOK_TYPE = RecipeBookType.create(InsaneSE.RESOURCE_PREFIX + "multi_item_blasting");
    public static final RecipeBookType MULTI_ITEM_SOUL_BLASTING_RECIPE_BOOK_TYPE = RecipeBookType.create(InsaneSE.RESOURCE_PREFIX + "multi_item_soul_blasting");
    public static final RecipeBookType FORGING_RECIPE_BOOK_TYPE = RecipeBookType.create(InsaneSE.RESOURCE_PREFIX + "forging");

    public InsaneSE(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, ISECommonConfig.CONFIG_SPEC, InsaneSO.NEW_MOD_ID + "/expanded-common.toml");
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = context.getModEventBus();
        if (FMLLoader.getDist().isClient()) {
            modEventBus.addListener(EventPriority.LOW, ClientSetup::onBuildCreativeModeTabContents);
            modEventBus.addListener(ClientSetup::registerEntityRenderers);
            modEventBus.addListener(ClientSetup::registerRecipeBookCategories);
            modEventBus.addListener(ClientSetup::registerTooltips);
        }
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::gatherData);
        ISERegistries.REGISTRIES.forEach(register -> register.register(modEventBus));

        if (ModList.get().isLoaded("shieldsplus")) {
            Durium.ShieldsPlusIntegration.init();
            SoulSteel.ShieldsPlusIntegration.init();
            Quaron.ShieldsPlusIntegration.init();
            Keego.ShieldsPlusIntegration.init();
            Solarium.ShieldsPlusIntegration.init();
        }

        ISETriggers.init();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NetworkHandler.init();
        Recall.onLoadComplete();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ClientSetup.init(event);
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new ISERecipeProvider(generator.getPackOutput()));
        generator.addProvider(event.includeServer(), new ISEGlobalLootModifierProvider(generator.getPackOutput(), InsaneSE.MOD_ID));
        ISEBlockTagsProvider blockTags = new ISEBlockTagsProvider(generator.getPackOutput(), lookupProvider, InsaneSE.MOD_ID, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ISEItemTagsProvider(generator.getPackOutput(), lookupProvider, blockTags.contentsGetter(), InsaneSE.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeServer(), new ISEDamageTypeTagsProvider(generator.getPackOutput(), lookupProvider, InsaneSE.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ISEBlockStatesProvider(generator.getPackOutput(), InsaneSE.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ISEBlockModelsProvider(generator.getPackOutput(), InsaneSE.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ISEItemModelsProvider(generator.getPackOutput(), InsaneSE.MOD_ID, existingFileHelper));
    }

    @SubscribeEvent
    public void onMissingMappings(MissingMappingsEvent event) {
        InsaneLib.handleMissingMappings(event, MOD_ID, Registries.ITEM, name -> switch (name) {
            case "copper_pickaxe", "coated_copper_pickaxe" -> CopperEquipment.PICKAXE.get();
            case "copper_axe", "coated_copper_axe" -> CopperEquipment.AXE.get();
            case "copper_shovel", "coated_copper_shovel" -> CopperEquipment.SHOVEL.get();
            case "copper_hoe", "coated_copper_hoe" -> CopperEquipment.HOE.get();
            case "copper_sword", "coated_copper_sword" -> CopperEquipment.SWORD.get();
            case "chained_copper_helmet" -> CopperEquipment.HELMET.get();
            case "chained_copper_chestplate" -> CopperEquipment.CHESTPLATE.get();
            case "chained_copper_leggings" -> CopperEquipment.LEGGINGS.get();
            case "chained_copper_boots" -> CopperEquipment.BOOTS.get();
            case "cleansed_lapis" -> Items.LAPIS_LAZULI;
            case "enchanted_cleansed_lapis" -> Items.EXPERIENCE_BOTTLE;
            case "repair_kit" -> RepairKits.ITEM.get();
            case "wooden_hammer" -> Items.OAK_LOG;
            case "stone_hammer" -> Items.COBBLESTONE;
            case "copper_hammer" -> Items.COPPER_BLOCK;
            case "iron_hammer", "golden_hammer", "diamond_hammer", "netherite_hammer", "keego_hammer", "soul_steel_hammer", "durium_hammer", "quaron_hammer", "solarium_hammer" -> Forging.HAMMER.get();
            case "quartz_arrow" -> Fletching.QUARTZ_ARROW_ITEM.get();
            case "diamond_arrow" -> Fletching.DIAMOND_ARROW_ITEM.get();
            case "torch_arrow" -> Fletching.TORCH_ARROW_ITEM.get();
            case "explosive_arrow" -> Fletching.EXPLOSIVE_ARROW_ITEM.get();
            case "ice_arrow" -> Fletching.ICE_ARROW_ITEM.get();
            case "fletching_table" -> Fletching.FLETCHING_TABLE.item().get();
            default -> null;
        });
        InsaneLib.handleMissingMappings(event, MOD_ID, Registries.BLOCK, name -> switch (name) {
            case "fletching_table" -> Fletching.FLETCHING_TABLE.block().get();
            default -> null;
        });
        InsaneLib.handleMissingMappings(event, MOD_ID, Registries.ENTITY_TYPE, name -> switch (name) {
            case "quartz_arrow" -> Fletching.QUARTZ_ARROW.get();
            case "diamond_arrow" -> Fletching.DIAMOND_ARROW.get();
            case "torch_arrow" -> Fletching.TORCH_ARROW.get();
            case "explosive_arrow" -> Fletching.EXPLOSIVE_ARROW.get();
            case "ice_arrow" -> Fletching.ICE_ARROW.get();
            default -> null;
        });
        if (ModList.get().isLoaded("shieldsplus")) {
            ShieldsPlusMissingMappings.missingMappings(event);
        }
    }

    private static class ShieldsPlusMissingMappings {
        public static void missingMappings(MissingMappingsEvent event) {
            InsaneLib.handleMissingMappings(event, MOD_ID, Registries.ITEM, name -> switch (name) {
                case "copper_shield", "coated_copper_shield" -> CopperEquipment.ShieldsPlusIntegration.SHIELD.get();
                default -> null;
            });
        }
    }

    public static void addServerPack(String path, String description, BooleanSupplier enabled) {
        IntegratedPack.addServerPack(MOD_ID, path, description, enabled);
    }

    public static void addServerPack(int priority, String path, String description, BooleanSupplier enabled) {
        IntegratedPack.addServerPack(priority, MOD_ID, path, description, enabled);
    }

    public static void addClientPack(String path, String description, BooleanSupplier enabled) {
        IntegratedPack.addClientPack(MOD_ID, path, description, enabled);
    }

    public static void addClientPack(int priority, String path, String description, BooleanSupplier enabled) {
        IntegratedPack.addClientPack(priority, MOD_ID, path, description, enabled);
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static String lang(String path) {
        return MOD_ID  + "." + path;
    }
}
