package insane96mcp.iguanatweaksexpanded.module.items.recallpotion;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.world.effect.ILMobEffect;
import insane96mcp.insanelib.world.scheduled.ScheduledTasks;
import insane96mcp.insanelib.world.scheduled.ScheduledTickTask;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

@Label(name = "Recall Potion", description = "Back to base")
@LoadFeature(module = Modules.Ids.ITEMS)
public class Recall extends Feature {
	public static final RegistryObject<MobEffect> BACK_TO_SPAWN = ITERegistries.MOB_EFFECTS.register("back_to_spawn", () -> new ILMobEffect(MobEffectCategory.BENEFICIAL, 0x6600ff));
	public static final RegistryObject<Potion> REGEN_WITHER = ITERegistries.POTION.register("regen_wither", () -> new Potion("regen_wither", new MobEffectInstance(MobEffects.REGENERATION, 450), new MobEffectInstance(MobEffects.WITHER, 450)));
	public static final RegistryObject<Potion> RECALL = ITERegistries.POTION.register("recall", () -> new Potion("recall", new MobEffectInstance(BACK_TO_SPAWN.get(), 300)));
	//public static final RegistryObject<Item> ITEM = ITERegistries.ITEMS.register("recall_idol", () -> new RecallIdolItem(new Item.Properties().stacksTo(1)));

	public Recall(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	public static void onLoadComplete() {
		PotionBrewing.addMix(Potions.STRONG_REGENERATION, Items.WITHER_ROSE, REGEN_WITHER.get());
		PotionBrewing.addMix(REGEN_WITHER.get(), Items.ECHO_SHARD, RECALL.get());
	}

	@SubscribeEvent
	public void onEffectExpire(MobEffectEvent.Expired event) {
        if (!event.getEffectInstance().getEffect().equals(BACK_TO_SPAWN.get())
				|| event.getEntity().level().isClientSide
				|| event.getEntity().isDeadOrDying())
            return;

		ServerPlayer player;
		if (event.getEntity() instanceof ServerPlayer)
			player = (ServerPlayer) event.getEntity();
		else if (event.getEntity() instanceof OwnableEntity tamableAnimal && tamableAnimal.getOwner() instanceof ServerPlayer)
			player = (ServerPlayer) tamableAnimal.getOwner();
		else
			return;
        BlockPos respawnPos = player.getRespawnPosition();
		float respawnAngle = player.getRespawnAngle();
		boolean forcedRespawn = player.isRespawnForced();
		ServerLevel serverLevel = player.server.getLevel(player.getRespawnDimension());
		Optional<Vec3> optional;
		if (serverLevel != null && respawnPos != null)
			optional = Player.findRespawnPositionAndUseSpawnBlock(serverLevel, respawnPos, respawnAngle, forcedRespawn, true);
		else
			optional = Optional.empty();
		optional.ifPresent(vec3 -> {
			if (player.level() != serverLevel)
				event.getEntity().changeDimension(serverLevel);
			event.getEntity().teleportTo(vec3.x, vec3.y, vec3.z);
			ScheduledTasks.schedule(new ScheduledTickTask(2) {
				@Override
				public void run() {
					serverLevel.playSound(null, event.getEntity(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.6f, 1f);
					//serverLevel.broadcastEntityEvent(entity, (byte)35);
				}
			});
		});
	}

	/*private static final String path = "item/recall_idol/";

	public static void addGlobalLoot(ITEGlobalLootModifierProvider provider) {
		provider.add(path + "recall_idol", new InjectLootTableModifier(new ResourceLocation("minecraft:chests/end_city_treasure"), new ResourceLocation("iguanatweaksexpanded:chests/injection/recall_idol")));
	}*/
}