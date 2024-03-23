package insane96mcp.iguanatweaksexpanded.module.world.seasons;

import com.google.common.collect.Lists;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksreborn.event.HookTickToHookLureEvent;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonChangedEvent;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.FertilityConfig;
import sereneseasons.config.ServerConfig;
import sereneseasons.handler.season.SeasonHandler;
import sereneseasons.season.SeasonSavedData;
import sereneseasons.season.SeasonTime;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Label(name = "Seasons", description = "Change a few things relative to Serene Seasons")
@LoadFeature(module = Modules.Ids.WORLD)
public class Seasons extends Feature {

	public static final GameRules.Key<GameRules.BooleanValue> RULE_SEASONGRASSGROWDEATH = GameRules.register("iguanatweaks:doSeasonGrassGrowDeath", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));

	@Config
	@Label(name = "No greenhouse glass", description = "Removes greenhouse glass.")
	public static Boolean noGreenHouseGlass = true;

	@Config
	@Label(name = "No Saplings in Winter", description = "Saplings no longer drop in Winter.")
	public static Boolean noSaplingsInWinter = true;

	@Config
	@Label(name = "Grass Decay and Growth", description = "Grass and tall grass decays in Winter and regrows back in Spring. Saplings are also transformed into Dead Bushes.")
	public static Boolean grassDecayAndGrowth = true;

	@Config
	@Label(name = "Serene Seasons changes", description = """
			Makes the following changes to Serene Seasons config:
			Makes the following changes to Serene Seasons config:
			* seasonal_crops is set to false, as it's controlled by Plants Growth
			* Sets the starting season to mid summer
			""")
	public static Boolean changeSereneSeasonsConfig = true;

	@Config(min = 0, max = 3d)
	@Label(name = "Time Control day night shift", description = "How many minutes will day and night duration be shifted based off seasons? E.g. in Mid spring / autumn the duration of day and night is vanilla, when moving off those seasons day and night will last this many minutes more/less. In mid summer / winter the duration of day and night duration will be more / less by 3 times this value.")
	public static Double timeControlDayNightShift = 1d;

	@Config
	@Label(name = "Season based fishing time")
	public static Boolean seasonBasedFishingTime = true;

	@Config
	@Label(name = "Bone meal fail chance", description = "Chance for a bone meal to fail to grow something. Empty this string to disable. Accepts a list of seasons and chances separated by a ;")
	public static String boneMealChance = "AUTUMN,0.4;WINTER,0.8";

	public Seasons(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "serene_seasons_changes", Component.literal("IguanaTweaks Expanded Serene Seasons Changes"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && noGreenHouseGlass));
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "no_saplings_in_winter", Component.literal("IguanaTweaks Expanded No Saplings in Winter"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && noSaplingsInWinter));
	}

	@Override
	public void readConfig(ModConfigEvent event) {
		super.readConfig(event);

		if (changeSereneSeasonsConfig)
			FertilityConfig.seasonalCrops.set(false);
	}

	@SubscribeEvent
	public void onSeasonChanged(SeasonChangedEvent.Standard event) {
		if (!this.isEnabled())
			return;

		if (ModList.get().isLoaded("timecontrol"))
			TimeControlIntegration.updateDayNightLength(event.getNewSeason());
	}

	@SubscribeEvent
	public void onServerStart(ServerStartedEvent event) {
		if (changeSereneSeasonsConfig) {
			ServerConfig.startingSubSeason.set(4);
			ServerConfig.progressSeasonWhileOffline.set(false);
		}
	}

	@SubscribeEvent
	public void onPreLevelTick(TickEvent.LevelTickEvent event) {
		if (!event.level.isClientSide && event.level.getGameTime() == 0 && changeSereneSeasonsConfig) {
			SeasonSavedData seasonData = SeasonHandler.getSeasonSavedData(event.level);
			seasonData.seasonCycleTicks = SeasonTime.ZERO.getSubSeasonDuration() * Season.SubSeason.EARLY_SUMMER.ordinal();
			seasonData.setDirty();
			SeasonHandler.sendSeasonUpdate(event.level);
		}
	}

	static final Map<Season.SubSeason, Float> CHANCE_TO_GROW_OR_DECAY = Map.ofEntries(
			Map.entry(Season.SubSeason.EARLY_SUMMER, 0.075f),
			Map.entry(Season.SubSeason.MID_SUMMER, 0.025f),
			Map.entry(Season.SubSeason.LATE_SUMMER, 0.02f),
			Map.entry(Season.SubSeason.EARLY_AUTUMN, 0f),
			Map.entry(Season.SubSeason.MID_AUTUMN, 0f),
			Map.entry(Season.SubSeason.LATE_AUTUMN, -0.10f),
			Map.entry(Season.SubSeason.EARLY_WINTER, -0.30f),
			Map.entry(Season.SubSeason.MID_WINTER, -0.50f),
			Map.entry(Season.SubSeason.LATE_WINTER, -0.30f),
			Map.entry(Season.SubSeason.EARLY_SPRING, 0f),
			Map.entry(Season.SubSeason.MID_SPRING, 0.10f),
			Map.entry(Season.SubSeason.LATE_SPRING, 0.125f)
	);

	@SubscribeEvent
	public void onLevelTick(TickEvent.LevelTickEvent event) {
        if (!this.isEnabled()
				|| !grassDecayAndGrowth
				|| event.phase != TickEvent.Phase.END
				|| event.side != LogicalSide.SERVER
				|| !event.level.getGameRules().getBoolean(RULE_SEASONGRASSGROWDEATH))
            return;

        Season.SubSeason subSeason = SeasonHelper.getSeasonState(event.level).getSubSeason();
		float chance = CHANCE_TO_GROW_OR_DECAY.get(subSeason);
		if (chance == 0f)
			return;

        ServerLevel level = (ServerLevel)event.level;
		level.getProfiler().push("tallGrassRandomTick");
        ChunkMap chunkMap = level.getChunkSource().chunkMap;
        DistanceManager distanceManager = chunkMap.getDistanceManager();
        int naturalSpawnChunkCount = distanceManager.getNaturalSpawnChunkCount();
        List<ChunkAndHolder> list = Lists.newArrayListWithCapacity(naturalSpawnChunkCount);
        chunkMap.getChunks().forEach(chunkHolder -> {
            LevelChunk levelChunk = chunkHolder.getTickingChunk();
            if (levelChunk != null)
				list.add(new ChunkAndHolder(levelChunk, chunkHolder));
        });

        Collections.shuffle(list);
		for (ChunkAndHolder chunkAndHolder : list) {
			ChunkPos chunkPos = chunkAndHolder.chunk.getPos();
			if (level.shouldTickBlocksAt(chunkPos.toLong()) && (chunkMap.anyPlayerCloseEnoughForSpawning(chunkPos) || distanceManager.shouldForceTicks(chunkPos.toLong()))) {
				tickPlantsLifeDeath(chunkMap, chunkAndHolder.chunk, subSeason, chance / 10f);
			}
		}
		level.getProfiler().pop();
    }

	private static void tickPlantsLifeDeath(ChunkMap chunkMap, LevelChunk levelChunk, Season.SubSeason subSeason, float chance) {
		ServerLevel level = chunkMap.level;
		ChunkPos chunkpos = levelChunk.getPos();
		int x = chunkpos.getMinBlockX();
		int z = chunkpos.getMinBlockZ();

		LevelChunkSection[] levelChunkSections = levelChunk.getSections();
		for (int s = 0; s < levelChunkSections.length; s++) {
			LevelChunkSection levelchunksection = levelChunkSections[s];
			if (levelchunksection.isRandomlyTicking()) {
				int sectionY = levelChunk.getSectionYFromSectionIndex(s);
				int y = SectionPos.sectionToBlockCoord(sectionY);

				for (int t = 0; t < level.getGameRules().getInt(GameRules.RULE_RANDOMTICKING); t++) {
					BlockPos pos = level.getBlockRandomPos(x, y, z, 15);
					BlockState state = levelchunksection.getBlockState(pos.getX() - x, pos.getY() - y, pos.getZ() - z);
					BlockPos abovePos = pos.above();
					if (state.is(BlockTags.DIRT) && level.getBrightness(LightLayer.SKY, abovePos) >= 10) {
						BlockState stateUp = level.getBlockState(abovePos);
						if (chance < 0f && level.getRandom().nextFloat() < -chance) {
							if (stateUp.is(Blocks.FERN) || stateUp.is(Blocks.GRASS) || stateUp.is(Blocks.TALL_GRASS) || stateUp.is(Blocks.LARGE_FERN))
								level.setBlock(abovePos, Blocks.AIR.defaultBlockState(), 3);
							else if (stateUp.is(BlockTags.SAPLINGS))
								level.setBlock(abovePos, Blocks.DEAD_BUSH.defaultBlockState(), 3);
						}
						else if (level.isDay() && state.is(Blocks.GRASS_BLOCK) && level.getRandom().nextFloat() < chance && stateUp.isAir()) {
							Optional<Holder.Reference<PlacedFeature>> oPlacedFeature = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(VegetationPlacements.GRASS_BONEMEAL);
							oPlacedFeature.ifPresent(placedFeatureReference ->
									placedFeatureReference.value().place(level, level.getChunkSource().getGenerator(), level.random, abovePos));
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void shouldSlowdownFishing(HookTickToHookLureEvent event) {
		if (!Feature.isEnabled(Seasons.class)
				|| !seasonBasedFishingTime)
			return;

		Level level = event.getHookEntity().level();
		Season season = SeasonHelper.getSeasonState(level).getSeason();
		//Chance to slowdown fishing
		float rng = switch (season) {
			case SPRING -> 0.1F;
			case SUMMER -> 0.0F;
			case AUTUMN -> 0.2F;
			case WINTER -> 0.5F;
		};
		if (level.getRandom().nextFloat() < rng)
			event.setTick(event.getTick() - 1);
	}

	record ChunkAndHolder(LevelChunk chunk, ChunkHolder holder) {}

	//Run before ITR BoneMeal
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBonemeal(BonemealEvent event) {
		if (event.isCanceled()
				|| event.getResult() != Event.Result.DENY
				|| !this.isEnabled()
				|| event.getLevel().isClientSide
				|| boneMealChance.isBlank())
			return;
        if ((event.getBlock().hasProperty(BlockStateProperties.AGE_1) && event.getBlock().getValue(BlockStateProperties.AGE_1) == 1)
				|| (event.getBlock().hasProperty(BlockStateProperties.AGE_2) && event.getBlock().getValue(BlockStateProperties.AGE_2) == 2)
				|| (event.getBlock().hasProperty(BlockStateProperties.AGE_3) && event.getBlock().getValue(BlockStateProperties.AGE_3) == 3)
				|| (event.getBlock().hasProperty(BlockStateProperties.AGE_4) && event.getBlock().getValue(BlockStateProperties.AGE_4) == 4)
				|| (event.getBlock().hasProperty(BlockStateProperties.AGE_5) && event.getBlock().getValue(BlockStateProperties.AGE_5) == 5)
				|| (event.getBlock().hasProperty(BlockStateProperties.AGE_7) && event.getBlock().getValue(BlockStateProperties.AGE_7) == 7)
				|| (event.getBlock().hasProperty(BlockStateProperties.AGE_15) && event.getBlock().getValue(BlockStateProperties.AGE_15) == 15)
				|| (event.getBlock().hasProperty(BlockStateProperties.AGE_25) && event.getBlock().getValue(BlockStateProperties.AGE_25) == 25))
            return;
        String[] seasonSplit = boneMealChance.split(";");
		for (String seasonChance : seasonSplit) {
			String[] chanceSplit = seasonChance.split(",");
			if (chanceSplit.length != 2)
				continue;
			Season season = Season.valueOf(chanceSplit[0]);
			float chance = Float.parseFloat(chanceSplit[1]);
			if (SeasonHelper.getSeasonState(event.getLevel()).getSeason().equals(season) && event.getLevel().random.nextFloat() < chance) {
				event.setResult(Event.Result.ALLOW);
				break;
			}
		}
	}
}