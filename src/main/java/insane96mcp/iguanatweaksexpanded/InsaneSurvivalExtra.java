package insane96mcp.iguanatweaksexpanded;

import com.google.common.collect.Lists;
import insane96mcp.iguanatweaksexpanded.data.criterion.ISETriggers;
import insane96mcp.iguanatweaksexpanded.data.generator.*;
import insane96mcp.iguanatweaksexpanded.data.generator.client.ISEBlockModelsProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.client.ISEBlockStatesProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.client.ISEItemModelsProvider;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.dispenser.ISEArrowDispenseBehaviour;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.items.recallpotion.Recall;
import insane96mcp.iguanatweaksexpanded.network.NetworkHandler;
import insane96mcp.iguanatweaksexpanded.setup.ISECommonConfig;
import insane96mcp.iguanatweaksexpanded.setup.ISEPackSource;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.client.ClientSetup;
import insane96mcp.iguanatweaksreborn.InsaneSurvivalOverhaul;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
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

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

    public InsaneSurvivalExtra() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ISECommonConfig.CONFIG_SPEC, InsaneSurvivalOverhaul.NEW_MOD_ID + "/expanded-common.toml");
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (FMLLoader.getDist().isClient()) {
            modEventBus.addListener(EventPriority.LOW, ClientSetup::onBuildCreativeModeTabContents);
            modEventBus.addListener(ClientSetup::registerEntityRenderers);
            modEventBus.addListener(ClientSetup::registerRecipeBookCategories);
            modEventBus.addListener(ClientSetup::registerParticleFactories);
            modEventBus.addListener(ClientSetup::registerTooltips);
            modEventBus.addListener(ClientSetup::registerItemColorHandlers);
        }
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::addPackFinders);
        ISERegistries.REGISTRIES.forEach(register -> register.register(modEventBus));

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

    public void addPackFinders(AddPackFindersEvent event)
    {
        for (IntegratedPack integratedPack : IntegratedPack.INTEGRATED_PACKS) {
            if (event.getPackType() != integratedPack.getPackType())
                continue;

            Path resourcePath = ModList.get().getModFileById(MOD_ID).getFile().findResource("integrated_packs/" + integratedPack.getPath());
            var pack = Pack.readMetaAndCreate(InsaneSurvivalExtra.RESOURCE_PREFIX + integratedPack.getPath(), integratedPack.getDescription(), integratedPack.shouldBeEnabled(),
                    (path) -> new PathPackResources(path, resourcePath, false), PackType.SERVER_DATA, Pack.Position.TOP, integratedPack.shouldBeEnabled() ? PackSource.DEFAULT : ISEPackSource.DISABLED);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
    }

    @SubscribeEvent
    public void onServerStartedEvent(ServerStartedEvent event)
    {
        boolean hasDisabledPack = false;
        PackRepository packRepository = event.getServer().getPackRepository();
        List<Pack> list = Lists.newArrayList(packRepository.getSelectedPacks());
        for (IntegratedPack dataPack : IntegratedPack.INTEGRATED_PACKS) {
            String dataPackId = InsaneSurvivalExtra.RESOURCE_PREFIX + dataPack.getPath();
            Pack pack = packRepository.getPack(dataPackId);
            if (pack != null && !dataPack.shouldBeEnabled()) {
                list.remove(pack);
                hasDisabledPack = true;
            }
        }
        if (hasDisabledPack)
            event.getServer().reloadResources(list.stream().map(Pack::getId).collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onMissingMappings(MissingMappingsEvent event) {
        event.getMappings(ForgeRegistries.Keys.ITEMS, MOD_ID).stream()
                .filter(mapping -> mapping.getKey().getPath().contains("ancient_lapis"))
                .forEach(mapping -> mapping.remap(EnchantingFeature.ENCHANTED_CLEANSED_LAPIS.get()));
    }
}
