package insane96mcp.iguanatweaksexpanded;

import com.google.common.collect.Lists;
import insane96mcp.iguanatweaksexpanded.data.criterion.ITETriggers;
import insane96mcp.iguanatweaksexpanded.data.generator.*;
import insane96mcp.iguanatweaksexpanded.data.generator.client.SRBlockModelsProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.client.SRBlockStatesProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.client.SRItemModelsProvider;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.dispenser.SRArrowDispenseBehaviour;
import insane96mcp.iguanatweaksexpanded.network.NetworkHandler;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedDataPack;
import insane96mcp.iguanatweaksexpanded.setup.SRCommonConfig;
import insane96mcp.iguanatweaksexpanded.setup.SRPackSource;
import insane96mcp.iguanatweaksexpanded.setup.SRRegistries;
import insane96mcp.iguanatweaksexpanded.setup.client.ClientSetup;
import insane96mcp.iguanatweaksreborn.IguanaTweaksReborn;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Mod("iguanatweaksexpanded")
public class IguanaTweaksExpanded
{
	public static final String MOD_ID = "iguanatweaksexpanded";
	public static final String RESOURCE_PREFIX = MOD_ID + ":";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String CONFIG_FOLDER = IguanaTweaksReborn.CONFIG_FOLDER;

    public static DecimalFormat ONE_DECIMAL_FORMATTER;
    public static final RecipeBookType MULTI_ITEM_BLASTING_RECIPE_BOOK_TYPE = RecipeBookType.create(IguanaTweaksExpanded.RESOURCE_PREFIX + "multi_item_blasting");
    public static final RecipeBookType MULTI_ITEM_SOUL_BLASTING_RECIPE_BOOK_TYPE = RecipeBookType.create(IguanaTweaksExpanded.RESOURCE_PREFIX + "multi_item_soul_blasting");
    public static final RecipeBookType FORGING_RECIPE_BOOK_TYPE = RecipeBookType.create(IguanaTweaksExpanded.RESOURCE_PREFIX + "forging");
    public static final RecipeBookType FLETCHING_RECIPE_BOOK_TYPE = RecipeBookType.create(IguanaTweaksExpanded.RESOURCE_PREFIX + "fletching");

    public IguanaTweaksExpanded() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SRCommonConfig.CONFIG_SPEC, IguanaTweaksReborn.MOD_ID + "/expanded-common.toml");
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (FMLLoader.getDist().isClient()) {
            modEventBus.addListener(ClientSetup::onBuildCreativeModeTabContents);
            modEventBus.addListener(ClientSetup::registerEntityRenderers);
            modEventBus.addListener(ClientSetup::registerRecipeBookCategories);
            modEventBus.addListener(ClientSetup::registerParticleFactories);
        }
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::addPackFinders);
        SRRegistries.REGISTRIES.forEach(register -> register.register(modEventBus));

        ITETriggers.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NetworkHandler.init();

        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(Fletching.QUARTZ_ARROW_ITEM.get(), new SRArrowDispenseBehaviour());
            DispenserBlock.registerBehavior(Fletching.DIAMOND_ARROW_ITEM.get(), new SRArrowDispenseBehaviour());
            DispenserBlock.registerBehavior(Fletching.EXPLOSIVE_ARROW_ITEM.get(), new SRArrowDispenseBehaviour());
            DispenserBlock.registerBehavior(Fletching.TORCH_ARROW_ITEM.get(), new SRArrowDispenseBehaviour());
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ClientSetup.init(event);
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new SRRecipeProvider(generator.getPackOutput()));
        generator.addProvider(event.includeServer(), new SRGlobalLootModifierProvider(generator.getPackOutput(), IguanaTweaksExpanded.MOD_ID));
        SRBlockTagsProvider blockTags = new SRBlockTagsProvider(generator.getPackOutput(), lookupProvider, IguanaTweaksExpanded.MOD_ID, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new SRItemTagsProvider(generator.getPackOutput(), lookupProvider, blockTags.contentsGetter(), IguanaTweaksExpanded.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeServer(), new SRDamageTypeTagsProvider(generator.getPackOutput(), lookupProvider, IguanaTweaksExpanded.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new SRBlockStatesProvider(generator.getPackOutput(), IguanaTweaksExpanded.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new SRBlockModelsProvider(generator.getPackOutput(), IguanaTweaksExpanded.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new SRItemModelsProvider(generator.getPackOutput(), IguanaTweaksExpanded.MOD_ID, existingFileHelper));
    }

    public void addPackFinders(AddPackFindersEvent event)
    {
        for (IntegratedDataPack dataPack : IntegratedDataPack.INTEGRATED_DATA_PACKS) {
            if (event.getPackType() != dataPack.getPackType())
                continue;

            Path resourcePath = ModList.get().getModFileById(MOD_ID).getFile().findResource("integrated_packs/" + dataPack.getPath());
            var pack = Pack.readMetaAndCreate(IguanaTweaksExpanded.RESOURCE_PREFIX + dataPack.getPath(), dataPack.getDescription(), dataPack.shouldBeEnabled(),
                    (path) -> new PathPackResources(path, resourcePath, false), PackType.SERVER_DATA, Pack.Position.TOP, dataPack.shouldBeEnabled() ? PackSource.DEFAULT : SRPackSource.DISABLED);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
    }

    @SubscribeEvent
    public void onServerStartedEvent(ServerStartedEvent event)
    {
        boolean hasDisabledPack = false;
        PackRepository packRepository = event.getServer().getPackRepository();
        List<Pack> list = Lists.newArrayList(packRepository.getSelectedPacks());
        for (IntegratedDataPack dataPack : IntegratedDataPack.INTEGRATED_DATA_PACKS) {
            String dataPackId = IguanaTweaksExpanded.RESOURCE_PREFIX + dataPack.getPath();
            Pack pack = packRepository.getPack(dataPackId);
            if (pack != null && !dataPack.shouldBeEnabled()) {
                list.remove(pack);
                hasDisabledPack = true;
            }
        }
        if (hasDisabledPack)
            event.getServer().reloadResources(list.stream().map(Pack::getId).collect(Collectors.toList()));
    }

}
