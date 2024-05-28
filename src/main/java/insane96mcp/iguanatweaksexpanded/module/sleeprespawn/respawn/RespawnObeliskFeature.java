package insane96mcp.iguanatweaksexpanded.module.sleeprespawn.respawn;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.IguanaTweaksReborn;
import insane96mcp.iguanatweaksreborn.data.ITRMobEffectInstance;
import insane96mcp.insanelib.base.JsonFeature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.data.IdTagValue;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Label(name = "Respawn Obelisk", description = "Generate Respawn Obelisks around the world where a precise respawn can be set")
@LoadFeature(module = Modules.Ids.SLEEP_RESPAWN)
public class RespawnObeliskFeature extends JsonFeature {
	public static final String FAIL_RESPAWN_OBELISK_LANG = IguanaTweaksExpanded.MOD_ID + ".fail_respawn_obelisk";

	public static final SimpleBlockWithItem RESPAWN_OBELISK = SimpleBlockWithItem.register("respawn_obelisk", () -> new RespawnObeliskBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(50.0F, 1200.0F).lightLevel(RespawnObeliskBlock::lightLevel)));

	public static final List<IdTagValue> RESPAWN_OBELISK_CATALYSTS_DEFAULT = List.of(
			IdTagValue.newId("minecraft:iron_block", 0.75d),
			IdTagValue.newId("minecraft:gold_block", 0.3d),
			IdTagValue.newId("iguanatweaksexpanded:durium_block", 0.075d),
			IdTagValue.newId("minecraft:diamond_block", 0.05d),
			IdTagValue.newId("iguanatweaksexpanded:keego_block", 0.05d),
			IdTagValue.newId("iguanatweaksexpanded:quaron_block", 0.25d),
			IdTagValue.newId("iguanatweaksexpanded:soul_steel_block", 0.05d),
			IdTagValue.newId("minecraft:emerald_block", 0.35d),
			IdTagValue.newId("minecraft:netherite_block", 0d)
	);

	public static final ArrayList<IdTagValue> respawnObeliskCatalysts = new ArrayList<>();

	public static final List<ITRMobEffectInstance> RESPAWN_OBELISK_EFFECTS_DEFAULT = List.of(
			new ITRMobEffectInstance.Builder(MobEffects.REGENERATION, 45 * 20)
					.ambientParticles()
					.build(),
			new ITRMobEffectInstance.Builder(MobEffects.ABSORPTION, 60 * 20)
					.setAmplifier(1)
					.ambientParticles()
					.build(),
			new ITRMobEffectInstance.Builder(MobEffects.MOVEMENT_SPEED, 60 * 20)
					.ambientParticles()
					.build()
	);

	public static final ArrayList<ITRMobEffectInstance> respawnObeliskEffects = new ArrayList<>();

	public RespawnObeliskFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		JSON_CONFIGS.add(new JsonConfig<>("respawn_obelisk_catalysts.json", respawnObeliskCatalysts, RESPAWN_OBELISK_CATALYSTS_DEFAULT, IdTagValue.LIST_TYPE));
		JSON_CONFIGS.add(new JsonConfig<>("respawn_obelisk_effects.json", respawnObeliskEffects, RESPAWN_OBELISK_EFFECTS_DEFAULT, ITRMobEffectInstance.LIST_TYPE));
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "respawn_obelisk", Component.literal("IguanaTweaks Reborn Respawn Obelisk"), this::isEnabled));
	}

	@Override
	public String getModConfigFolder() {
		return IguanaTweaksReborn.CONFIG_FOLDER;
	}

	@Override
	public void loadJsonConfigs() {
		if (!this.isEnabled())
			return;
		super.loadJsonConfigs();
	}

	@SubscribeEvent
	public void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
		if (!this.isEnabled()
				|| event.isEndConquered())
			return;

		tryRespawnObelisk(event);
	}

	private void tryRespawnObelisk(PlayerEvent.PlayerRespawnEvent event) {
		ServerPlayer player = (ServerPlayer) event.getEntity();
		BlockPos pos = player.getRespawnPosition();
		if (pos == null
				|| !player.level().getBlockState(pos).is(RESPAWN_OBELISK.block().get()))
			return;

		if (!player.level().getBlockState(pos).getValue(RespawnObeliskBlock.ENABLED)) {
			player.sendSystemMessage(Component.translatable(FAIL_RESPAWN_OBELISK_LANG));
			RespawnObeliskBlock.trySetOldSpawn(player);
			return;
		}
		RespawnObeliskBlock.onObeliskRespawn(player, player.level(), pos);
	}

	@SubscribeEvent
	public void onSetSpawnPreventObeliskOverwrite(PlayerSetSpawnEvent event) {
		if (!this.isEnabled()
				|| event.isForced()
				|| !(event.getEntity() instanceof ServerPlayer player))
			return;

		if (player.getRespawnPosition() != null
				&& player.level().dimension().equals(player.getRespawnDimension())
				&& player.level().getBlockState(player.getRespawnPosition()).is(RESPAWN_OBELISK.block().get())
				&& player.level().getBlockState(player.getRespawnPosition()).getValue(RespawnObeliskBlock.ENABLED)
				&& event.getNewSpawn() != null
				&& !player.level().getBlockState(event.getNewSpawn()).is(RESPAWN_OBELISK.block().get())) {
			if (RespawnObeliskBlock.saveOldSpawn(player, event.getNewSpawn(), event.isForced(), 0f, event.getSpawnLevel()))
				player.sendSystemMessage(Component.translatable("iguanatweaksexpanded.set_old_respawn"));
			event.setCanceled(true);
		}
	}
}