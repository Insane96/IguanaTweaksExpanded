package insane96mcp.iguanatweaksexpanded;

import insane96mcp.iguanatweaksexpanded.data.criterion.ISETriggers;
import insane96mcp.iguanatweaksexpanded.data.generator.*;
import insane96mcp.iguanatweaksexpanded.data.generator.client.ISEBlockModelsProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.client.ISEBlockStatesProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.client.ISEItemModelsProvider;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.dispenser.ISEArrowDispenseBehaviour;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.items.recallpotion.Recall;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.Solarium;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.durium.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.quaron.Quaron;
import insane96mcp.iguanatweaksexpanded.network.NetworkHandler;
import insane96mcp.iguanatweaksexpanded.setup.ISECommonConfig;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksexpanded.setup.client.ClientSetup;
import insane96mcp.iguanatweaksreborn.InsaneSO;
import insane96mcp.insanelib.util.IntegratedPack;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.level.block.DispenserBlock;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;

@Mod("iguanatweaksexpanded")
public class InsaneSurvivalExtra
{
	public static final String MOD_ID = "iguanatweaksexpanded";
	public static final String RESOURCE_PREFIX = MOD_ID + ":";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final RecipeBookType MULTI_ITEM_BLASTING_RECIPE_BOOK_TYPE = RecipeBookType.create(InsaneSurvivalExtra.RESOURCE_PREFIX + "multi_item_blasting");
    public static final RecipeBookType MULTI_ITEM_SOUL_BLASTING_RECIPE_BOOK_TYPE = RecipeBookType.create(InsaneSurvivalExtra.RESOURCE_PREFIX + "multi_item_soul_blasting");
    public static final RecipeBookType FORGING_RECIPE_BOOK_TYPE = RecipeBookType.create(InsaneSurvivalExtra.RESOURCE_PREFIX + "forging");
    public static final RecipeBookType FLETCHING_RECIPE_BOOK_TYPE = RecipeBookType.create(InsaneSurvivalExtra.RESOURCE_PREFIX + "fletching");

    public InsaneSurvivalExtra(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, ISECommonConfig.CONFIG_SPEC, InsaneSO.NEW_MOD_ID + "/expanded-common.toml");
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = context.getModEventBus();
        if (FMLLoader.getDist().isClient()) {
            modEventBus.addListener(EventPriority.LOW, ClientSetup::onBuildCreativeModeTabContents);
            modEventBus.addListener(ClientSetup::registerEntityRenderers);
            modEventBus.addListener(ClientSetup::registerRecipeBookCategories);
            modEventBus.addListener(ClientSetup::registerTooltips);
            modEventBus.addListener(ClientSetup::registerItemColorHandlers);
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

        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(Fletching.QUARTZ_ARROW_ITEM.get(), new ISEArrowDispenseBehaviour());
            DispenserBlock.registerBehavior(Fletching.DIAMOND_ARROW_ITEM.get(), new ISEArrowDispenseBehaviour());
            DispenserBlock.registerBehavior(Fletching.EXPLOSIVE_ARROW_ITEM.get(), new ISEArrowDispenseBehaviour());
            DispenserBlock.registerBehavior(Fletching.TORCH_ARROW_ITEM.get(), new ISEArrowDispenseBehaviour());
            DispenserBlock.registerBehavior(Fletching.ICE_ARROW_ITEM.get(), new ISEArrowDispenseBehaviour());
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ClientSetup.init(event);
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new ISERecipeProvider(generator.getPackOutput()));
        generator.addProvider(event.includeServer(), new ISEGlobalLootModifierProvider(generator.getPackOutput(), InsaneSurvivalExtra.MOD_ID));
        ISEBlockTagsProvider blockTags = new ISEBlockTagsProvider(generator.getPackOutput(), lookupProvider, InsaneSurvivalExtra.MOD_ID, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ISEItemTagsProvider(generator.getPackOutput(), lookupProvider, blockTags.contentsGetter(), InsaneSurvivalExtra.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeServer(), new ISEDamageTypeTagsProvider(generator.getPackOutput(), lookupProvider, InsaneSurvivalExtra.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ISEBlockStatesProvider(generator.getPackOutput(), InsaneSurvivalExtra.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ISEBlockModelsProvider(generator.getPackOutput(), InsaneSurvivalExtra.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ISEItemModelsProvider(generator.getPackOutput(), InsaneSurvivalExtra.MOD_ID, existingFileHelper));
    }

    @SubscribeEvent
    public void onMissingMappings(MissingMappingsEvent event) {
        event.getMappings(ForgeRegistries.Keys.ITEMS, MOD_ID).stream()
                .filter(mapping -> mapping.getKey().getPath().contains("ancient_lapis"))
                .forEach(mapping -> mapping.remap(EnchantingFeature.ENCHANTED_CLEANSED_LAPIS.get()));
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
}
