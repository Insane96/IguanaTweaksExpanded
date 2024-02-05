package insane96mcp.iguanatweaksexpanded.module.experience.enchantments;

import com.mojang.blaze3d.platform.InputConstants;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.*;
import insane96mcp.iguanatweaksexpanded.network.message.JumpMidAirMessage;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "New Enchantments", description = "Change some enchantments related stuff and adds new enchantments. Please note that Damaging enchantments such as water coolant are enabled only if ITR 'Replace damaging enchantments' is enabled. This also applies for protection enchantments and ITR 'Replace protection enchantments'")
@LoadFeature(module = Modules.Ids.EXPERIENCE)
public class NewEnchantmentsFeature extends Feature {

	public static final RegistryObject<Enchantment> MAGNETIC = ITERegistries.ENCHANTMENTS.register("magnetic", Magnetic::new);
	public static final RegistryObject<Enchantment> MAGIC_PROTECTION = ITERegistries.ENCHANTMENTS.register("magic_protection", MagicProtection::new);
	public static final RegistryObject<Enchantment> MELEE_PROTECTION = ITERegistries.ENCHANTMENTS.register("melee_protection", MeleeProtection::new);
	public static final RegistryObject<Enchantment> BLASTING = ITERegistries.ENCHANTMENTS.register("blasting", Blasting::new);
	public static final RegistryObject<Enchantment> EXPANDED = ITERegistries.ENCHANTMENTS.register("expanded", Expanded::new);
	public static final RegistryObject<Enchantment> VEINING = ITERegistries.ENCHANTMENTS.register("veining", Veining::new);
	public static final RegistryObject<Enchantment> STEP_UP = ITERegistries.ENCHANTMENTS.register("step_up", StepUp::new);
	public static final RegistryObject<Enchantment> WATER_COOLANT = ITERegistries.ENCHANTMENTS.register("water_coolant", WaterCoolant::new);
	public static final RegistryObject<Enchantment> SMARTNESS = ITERegistries.ENCHANTMENTS.register("smartness", Smartness::new);
	public static final RegistryObject<Enchantment> MA_JUMP = ITERegistries.ENCHANTMENTS.register("ma_jump", DoubleJump::new);
	public static final RegistryObject<Enchantment> GRAVITY_DEFYING = ITERegistries.ENCHANTMENTS.register("gravity_defying", GravityDefying::new);
	public static final RegistryObject<Enchantment> CRITICAL = ITERegistries.ENCHANTMENTS.register("critical", Critical::new);
	public static final RegistryObject<Enchantment> SWIFT_STRIKE = ITERegistries.ENCHANTMENTS.register("swift_strike", SwiftStrike::new);
	public static final RegistryObject<Enchantment> HEALTHY = ITERegistries.ENCHANTMENTS.register("healthy", Healthy::new);
	//public static final RegistryObject<Enchantment> CURSE_OF_MENDING = ITERegistries.ENCHANTMENTS.register("curse_of_mending", CurseOfMending::new);
	public static final RegistryObject<Enchantment> REACH = ITERegistries.ENCHANTMENTS.register("reach", Reach::new);

	public NewEnchantmentsFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

    @SubscribeEvent
	public void onAttributeModifiers(ItemAttributeModifierEvent event) {
		if (!this.isEnabled())
			return;

		StepUp.applyAttributeModifier(event);
		Reach.applyAttributeModifier(event);
		GravityDefying.applyAttributeModifier(event);
		Healthy.applyAttributeModifier(event);
		SwiftStrike.applyAttributeModifier(event);
		MeleeProtection.applyAttributeModifier(event);
	}

	@SubscribeEvent
	public void onEntityTick(LivingEvent.LivingTickEvent event) {
		if (!this.isEnabled())
			return;

		Magnetic.tryPullItems(event.getEntity());
		if (event.getEntity() instanceof ServerPlayer player)
			CurseOfMending.consumePlayerExperience(player);
	}

	@SubscribeEvent
	public void onEffectAdded(MobEffectEvent.Added event) {
		if (!this.isEnabled())
			return;

		MagicProtection.reduceBadEffectsDuration(event.getEntity(), event.getEffectInstance());
	}

	@SubscribeEvent
	public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		if (!this.isEnabled())
			return;

		event.setNewSpeed(event.getNewSpeed() + Blasting.getMiningSpeedBoost(event.getEntity(), event.getState()));
	}

	@SubscribeEvent
	public void onExperienceDropped(LivingExperienceDropEvent event) {
		if (!this.isEnabled()
				|| event.getAttackingPlayer() == null)
			return;
		int lvl = EnchantmentHelper.getEnchantmentLevel(SMARTNESS.get(), event.getAttackingPlayer());
		if (lvl > 0)
			event.setDroppedExperience(Smartness.getIncreasedExperience(event.getEntity().getRandom(), lvl, event.getDroppedExperience()));
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onBlockExperienceDropped(BlockEvent.BreakEvent event) {
		if (!this.isEnabled())
			return;
		int lvl = EnchantmentHelper.getEnchantmentLevel(SMARTNESS.get(), event.getPlayer());
		if (lvl > 0)
			event.setExpToDrop(Smartness.getIncreasedExperience(event.getLevel().getRandom(), lvl, event.getExpToDrop()));
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onRenderLevel(RenderLevelStageEvent event) {
		if (!this.isEnabled())
			return;

		Expanded.applyOutlineAndDestroyAnimation(event);
		Veining.applyOutlineAndDestroyAnimation(event);
	}

	//Priority high: run before Timber Trees
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if (!this.isEnabled())
			return;

		HitResult pick = event.getPlayer().pick(event.getPlayer().getEntityReach() + 0.5d, 1f, false);
		if (pick instanceof BlockHitResult blockHitResult) {
			Veining.tryApply(event.getPlayer(), event.getPlayer().level(), event.getPos(), blockHitResult.getDirection(), event.getState());
			Expanded.tryApply(event.getPlayer(), event.getPlayer().level(), event.getPos(), blockHitResult.getDirection(), event.getState());
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onCriticalHit(CriticalHitEvent event) {
		if (!this.isEnabled())
			return;

		int lvl = event.getEntity().getMainHandItem().getEnchantmentLevel(CRITICAL.get());
		if (lvl <= 0)
			return;
		event.setDamageModifier(Critical.getCritAmount(lvl, event.getDamageModifier()));
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onJump(InputEvent.Key event) {
		if (!this.isEnabled()
				|| Minecraft.getInstance().player == null
				|| event.getAction() != InputConstants.PRESS
				|| event.getKey() != Minecraft.getInstance().options.keyJump.getKey().getValue())
			return;

		LocalPlayer player = Minecraft.getInstance().player;
		if (DoubleJump.extraJump(player)) {
			JumpMidAirMessage.jumpMidAir(player);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onLivingFall(LivingFallEvent event) {
		if (!this.isEnabled())
			return;

		if (event.getEntity() instanceof LocalPlayer player)
			player.getPersistentData().putInt("double_jumps", 0);
		GravityDefying.applyFallDamageReduction(event);
	}
}