package insane96mcp.iguanatweaksreborn.module.sleeprespawn.feature;

import insane96mcp.iguanatweaksreborn.setup.Config;
import insane96mcp.iguanatweaksreborn.setup.ITMobEffects;
import insane96mcp.iguanatweaksreborn.setup.Strings;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Label(name = "Tiredness", description = "Prevents sleeping if the player is not tired. Tiredness is gained by gaining exhaustion")
public class Tiredness extends Feature {

	private final ForgeConfigSpec.DoubleValue tirednessGainMultiplierConfig;
	private final ForgeConfigSpec.BooleanValue shouldPreventSpawnPointConfig;

	public double tirednessGainMultiplier = 1d;
	public boolean shouldPreventSpawnPoint = false;

	public Tiredness(Module module) {
		super(Config.builder, module);
		this.pushConfig(Config.builder);
		tirednessGainMultiplierConfig = Config.builder
				.comment("Multiply the tiredness gained by this value. Normally you gain tiredness equal to the exhaustion gained. 'Effective Hunger' doesn't affect the exhaustion gained.")
				.defineInRange("Tiredness gained multiplier", this.tirednessGainMultiplier, 0d, 128d);
		shouldPreventSpawnPointConfig = Config.builder
				.comment("If true the player will not set the spawn point if he/she can't sleep.")
				.define("Prevent Spawn Point", this.shouldPreventSpawnPoint);
		Config.builder.pop();
	}

	@Override
	public void loadConfig() {
		super.loadConfig();
		this.tirednessGainMultiplier = this.tirednessGainMultiplierConfig.get();
		this.shouldPreventSpawnPoint = this.shouldPreventSpawnPointConfig.get();
	}

	public void onFoodExhaustion(Player player, float amount) {
		if (!this.isEnabled())
			return;

		if (player.level.isClientSide)
			return;

		ServerPlayer serverPlayer = (ServerPlayer) player;

		CompoundTag persistentData = serverPlayer.getPersistentData();
		float tiredness = persistentData.getFloat(Strings.Tags.TIREDNESS);
		persistentData.putFloat(Strings.Tags.TIREDNESS, tiredness + amount);
		if (tiredness < 320 && tiredness + amount >= 320) {
			serverPlayer.displayClientMessage(new TranslatableComponent(Strings.Translatable.TIRED_ENOUGH), false);
		}
		else if (tiredness >= 400 && player.tickCount % 20 == 0) {
			serverPlayer.addEffect(new MobEffectInstance(ITMobEffects.TIRED.get(), 25, Math.min((int) ((tiredness - 400) / 20), 4), true, false, true));
		}
	}

	@SubscribeEvent
	public void notTiredToSleep(PlayerSleepInBedEvent event) {
		if (!this.isEnabled()
				|| event.getResultStatus() != null
				|| event.getPlayer().level.isClientSide)
			return;

		ServerPlayer player = (ServerPlayer) event.getPlayer();

		if (player.getPersistentData().getFloat(Strings.Tags.TIREDNESS) < 320f) {
			event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
			player.displayClientMessage(new TranslatableComponent(Strings.Translatable.NOT_TIRED), true);
			if (!this.shouldPreventSpawnPoint)
				player.setRespawnPosition(player.level.dimension(), event.getPos(), player.getYRot(), false, false);
		}
		else {
			event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
			player.startSleeping(event.getPos());
			((ServerLevel)player.level).updateSleepingPlayerList();
		}
	}

	@SubscribeEvent
	public void resetTirednessOnWakeUp(SleepFinishedTimeEvent event) {
		if (!this.isEnabled())
			return;
		event.getWorld().players().stream().filter(LivingEntity::isSleeping).toList().forEach((player) -> player.getPersistentData().putFloat(Strings.Tags.TIREDNESS, 0f));
	}

	@SubscribeEvent
	public void resetTirednessOnWakeUp(SleepingTimeCheckEvent event) {
		if (!this.isEnabled()
				|| event.getPlayer().getPersistentData().getFloat(Strings.Tags.TIREDNESS) < 320f)
			return;
		event.setResult(Event.Result.ALLOW);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onFog(EntityViewRenderEvent.RenderFogEvent event) {
		if (!this.isEnabled()
				|| event.getCamera().getEntity().isSpectator()
				|| !(event.getCamera().getEntity() instanceof LivingEntity livingEntity)
				|| !livingEntity.hasEffect(ITMobEffects.TIRED.get()))
			return;

		int amplifier = livingEntity.getEffect(ITMobEffects.TIRED.get()).getAmplifier();
		if (amplifier < 1)
			return;
		float renderDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
		float near = -8;
		float far = Math.min(48f, renderDistance) - ((amplifier - 1) * 10);
		event.setNearPlaneDistance(near);
		event.setFarPlaneDistance(far);
		event.setCanceled(true);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onFog(EntityViewRenderEvent.FogColors event) {
		if (!this.isEnabled()
				|| event.getCamera().getEntity().isSpectator()
				|| !(event.getCamera().getEntity() instanceof LivingEntity livingEntity)
				|| !livingEntity.hasEffect(ITMobEffects.TIRED.get()))
			return;

		int amplifier = livingEntity.getEffect(ITMobEffects.TIRED.get()).getAmplifier();
		if (amplifier < 1)
			return;
		float color = 0f;
		event.setRed(color);
		event.setGreen(color);
		event.setBlue(color);
	}
}