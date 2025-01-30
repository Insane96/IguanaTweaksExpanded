package insane96mcp.iguanatweaksexpanded.module.experience.enchantments;

import com.mojang.blaze3d.platform.InputConstants;
import insane96mcp.iguanatweaksexpanded.event.DestroyBlockPostEvent;
import insane96mcp.iguanatweaksexpanded.event.EnchantmentBlockBreakEvent;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.*;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse.*;
import insane96mcp.iguanatweaksexpanded.network.message.JumpMidAirMessage;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksreborn.data.lootmodifier.DropMultiplierModifier;
import insane96mcp.iguanatweaksreborn.event.EnchantmentBonusEfficiencyEvent;
import insane96mcp.iguanatweaksreborn.event.StackMaxDamageEvent;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.event.HurtItemStackEvent;
import insane96mcp.insanelib.event.PlayerSprintEvent;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;
import java.util.function.Predicate;

@Label(name = "New Enchantments", description = "Adds new enchantments. Please note that Damaging enchantments such as water coolant are enabled only if ITR 'Replace damaging enchantments' is enabled. This also applies for protection enchantments and ITR 'Replace protection enchantments'")
@LoadFeature(module = Modules.Ids.EXPERIENCE)
public class NewEnchantmentsFeature extends Feature {

	//Tools
	public static final RegistryObject<Enchantment> BLASTING = ISERegistries.ENCHANTMENTS.register("blasting", Blasting::new);
	public static final RegistryObject<Enchantment> AIR_BORN = ISERegistries.ENCHANTMENTS.register("air_born", AirBorn::new);
	public static final RegistryObject<Enchantment> EXPANDED = ISERegistries.ENCHANTMENTS.register("expanded", Expanded::new);
	public static final RegistryObject<Enchantment> VEINING = ISERegistries.ENCHANTMENTS.register("veining", Veining::new);
	public static final RegistryObject<Enchantment> EXCHANGE = ISERegistries.ENCHANTMENTS.register("exchange", Exchange::new);
	public static final RegistryObject<Enchantment> HASTE = ISERegistries.ENCHANTMENTS.register("haste", Haste::new);

	//Armor
	public static final RegistryObject<Enchantment> MAGIC_PROTECTION = ISERegistries.ENCHANTMENTS.register("magic_protection", MagicProtection::new);
	public static final RegistryObject<Enchantment> MELEE_PROTECTION = ISERegistries.ENCHANTMENTS.register("melee_protection", MeleeProtection::new);
	public static final RegistryObject<Enchantment> HEALTHY = ISERegistries.ENCHANTMENTS.register("healthy", Healthy::new);
	public static final RegistryObject<Enchantment> VINDICATION = ISERegistries.ENCHANTMENTS.register("vindication", Vindication::new);
	public static final RegistryObject<Enchantment> RECOVERY = ISERegistries.ENCHANTMENTS.register("recovery", Recovery::new);
	public static final RegistryObject<Enchantment> STEP_UP = ISERegistries.ENCHANTMENTS.register("step_up", StepUp::new);
	public static final RegistryObject<Enchantment> ZIPPY = ISERegistries.ENCHANTMENTS.register("zippy", Zippy::new);
	public static final RegistryObject<Enchantment> HOPPY = ISERegistries.ENCHANTMENTS.register("hoppy", Hoppy::new);
	public static final RegistryObject<Enchantment> SPRINT_PACT = ISERegistries.ENCHANTMENTS.register("sprint_pact", SprintPact::new);
	public static final RegistryObject<Enchantment> MAGNETIC = ISERegistries.ENCHANTMENTS.register("magnetic", Magnetic::new);
	public static final RegistryObject<Enchantment> RETREAT = ISERegistries.ENCHANTMENTS.register("retreat", Retreat::new);
	public static final RegistryObject<Enchantment> DOUBLE_JUMP = ISERegistries.ENCHANTMENTS.register("double_jump", DoubleJump::new);
	public static final RegistryObject<Enchantment> GRAVITY_DEFYING = ISERegistries.ENCHANTMENTS.register("gravity_defying", GravityDefying::new);
	public static final RegistryObject<Enchantment> STEADY_FALL = ISERegistries.ENCHANTMENTS.register("steady_fall", SteadyFall::new);

	//Weapons
	public static final RegistryObject<Enchantment> WATER_COOLANT = ISERegistries.ENCHANTMENTS.register("water_coolant", WaterCoolant::new);
	public static final RegistryObject<Enchantment> RAGE = ISERegistries.ENCHANTMENTS.register("rage", Rage::new);
	public static final RegistryObject<Enchantment> SWIFT_STRIKE = ISERegistries.ENCHANTMENTS.register("swift_strike", SwiftStrike::new);
	public static final RegistryObject<Enchantment> PADDING = ISERegistries.ENCHANTMENTS.register("padding", Padding::new);
	public static final RegistryObject<Enchantment> PART_BREAKER = ISERegistries.ENCHANTMENTS.register("part_breaker", PartBreaker::new);
	public static final RegistryObject<Enchantment> AIR_STEALER = ISERegistries.ENCHANTMENTS.register("air_stealer", AirStealer::new);
	public static final RegistryObject<Enchantment> ARMOR_PIERCER = ISERegistries.ENCHANTMENTS.register("armor_piercer", ArmorPiercer::new);
	public static final RegistryObject<Enchantment> CRYO_ASPECT = ISERegistries.ENCHANTMENTS.register("cryo_aspect", CryoAspect::new);
	public static final RegistryObject<Enchantment> EXPLOSIVE = ISERegistries.ENCHANTMENTS.register("explosive", Explosive::new);

	//Tools and weapons
	public static final RegistryObject<Enchantment> SMARTNESS = ISERegistries.ENCHANTMENTS.register("smartness", Smartness::new);
	public static final RegistryObject<Enchantment> REACH = ISERegistries.ENCHANTMENTS.register("reach", Reach::new);
	public static final RegistryObject<Enchantment> ADRENALINE = ISERegistries.ENCHANTMENTS.register("adrenaline", Adrenaline::new);
	public static final RegistryObject<Enchantment> KNOWLEDGEABLE = ISERegistries.ENCHANTMENTS.register("knowledgeable", Knowledgeable::new);

	//Fishing rods
	public static final RegistryObject<Enchantment> JUICY_BAIT = ISERegistries.ENCHANTMENTS.register("lucky_hook", JuicyBait::new);

	//Crossbows
	public static final RegistryObject<Enchantment> BURST_OF_ARROWS = ISERegistries.ENCHANTMENTS.register("burst_of_arrows", BurstOfArrows::new);

	//General
	public static final RegistryObject<Enchantment> SOULBOUND = ISERegistries.ENCHANTMENTS.register("soulbound", Soulbound::new);
	public static final RegistryObject<Enchantment> ENDURING = ISERegistries.ENCHANTMENTS.register("enduring", Enduring::new);

	//Curses
	public static final RegistryObject<Enchantment> CURSE_OF_EXPERIENCE = ISERegistries.ENCHANTMENTS.register("experience_curse", CurseOfExperience::new);
	public static final RegistryObject<Enchantment> CURSE_OF_TEAR = ISERegistries.ENCHANTMENTS.register("tear_curse", CurseOfTear::new);
	public static final RegistryObject<Enchantment> BLOOD_PACT = ISERegistries.ENCHANTMENTS.register("blood_pact_curse", CurseOfBloodPact::new);
	public static final RegistryObject<Enchantment> CURSE_OF_UNHURRIED = ISERegistries.ENCHANTMENTS.register("unhurried_curse", CurseOfUnhurried::new);
	public static final RegistryObject<Enchantment> CURSE_OF_SLOW_STRIKE = ISERegistries.ENCHANTMENTS.register("slow_strike_curse", CurseOfSlowStrike::new);
	public static final RegistryObject<Enchantment> CURSE_OF_INEFFICIENCY = ISERegistries.ENCHANTMENTS.register("inefficiency_curse", CurseOfInefficiency::new);
	public static final RegistryObject<Enchantment> CURSE_OF_SHORT_ARM = ISERegistries.ENCHANTMENTS.register("short_arm_curse", CurseOfShortArm::new);
	public static final RegistryObject<Enchantment> CURSE_OF_FRAGILITY = ISERegistries.ENCHANTMENTS.register("fragility_curse", CurseOfFragility::new);
	public static final RegistryObject<Enchantment> CURSE_OF_ENDER = ISERegistries.ENCHANTMENTS.register("ender_curse", CurseOfEnder::new);
	public static final RegistryObject<Enchantment> CURSE_OF_WALKING = ISERegistries.ENCHANTMENTS.register("walking_curse", CurseOfWalking::new);
	public static final RegistryObject<Enchantment> CURSE_OF_STEEL_FALL = ISERegistries.ENCHANTMENTS.register("steel_fall_curse", CurseOfSteelFall::new);
	public static final RegistryObject<Enchantment> CURSE_OF_THE_VOID = ISERegistries.ENCHANTMENTS.register("void_curse", CurseOfTheVoid::new);
	public static final RegistryObject<Enchantment> CURSE_OF_SLOW_CHARGE = ISERegistries.ENCHANTMENTS.register("slow_charge_curse", CurseOfSlowCharge::new);
	public static final RegistryObject<Enchantment> CURSE_OF_DUMBNESS = ISERegistries.ENCHANTMENTS.register("dumbness_curse", CurseOfDumbness::new);
	public static final RegistryObject<Enchantment> CURSE_OF_HOP = ISERegistries.ENCHANTMENTS.register("hop_curse", CurseOfHop::new);
	public static final RegistryObject<Enchantment> CURSE_OF_OBSCURITY = ISERegistries.ENCHANTMENTS.register("obscurity_curse", CurseOfObscurity::new);
	public static final RegistryObject<Enchantment> CURSE_OF_FRENZY = ISERegistries.ENCHANTMENTS.register("frenzy_curse", CurseOfFrenzy::new);
	public static final RegistryObject<Enchantment> CURSE_OF_UNSTABLE_MOTION = ISERegistries.ENCHANTMENTS.register("unstable_motion_curse", CurseOfUnstableMotion::new);
	public static final RegistryObject<Enchantment> CURSE_OF_STATIC_CHARGE = ISERegistries.ENCHANTMENTS.register("static_charge_curse", CurseOfStaticCharge::new);
	//public static final RegistryObject<Enchantment> CURSE_OF_EXPLOSION = ITERegistries.ENCHANTMENTS.register("explosion_curse", CurseOfExplosion::new);
	//public static final RegistryObject<Enchantment> CURSE_OF_ANCHOR = ITERegistries.ENCHANTMENTS.register("anchor_curse", CurseOfAnchor::new);
	//public static final RegistryObject<Enchantment> CURSE_OF_SINKING = ITERegistries.ENCHANTMENTS.register("sinking_curse", CurseOfSinking::new);
	public NewEnchantmentsFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	@SubscribeEvent
	public void onHurtItemStack(HurtItemStackEvent event) {
		CurseOfExperience.consumePlayerExperience(event);
		CurseOfFragility.increaseItemHurt(event);
		CurseOfBloodPact.trySuckingAndRepairing(event);
	}

	@SubscribeEvent
	public void onStackMaxDamage(StackMaxDamageEvent event) {
		int lvl = event.getStack().getEnchantmentLevel(ENDURING.get());
		if (lvl > 0)
			event.setNewMaxDamage(event.getNewMaxDamage() + Enduring.getBonusDurability() * lvl);
	}

	@SubscribeEvent
	public void onEntityTick(LivingEvent.LivingTickEvent event) {
		Magnetic.tryPullItems(event.getEntity());
		CurseOfAnchor.apply(event);
		CurseOfSinking.sink(event);
		CurseOfHop.tick(event);
		Recovery.regen(event);
		CurseOfUnstableMotion.tick(event);
		CurseOfStaticCharge.tick(event);
		Retreat.applyMovementSpeedModifier(event);
	}

	@SubscribeEvent
	public void onDamaged(LivingDamageEvent event) {
		Vindication.tryStackDamage(event.getEntity(), event.getSource(), event.getAmount());
		Recovery.storeDamageToRegen(event.getEntity(), event.getSource(), event.getAmount());
		CurseOfEnder.onHurt(event);
		SteadyFall.onFall(event);
		CurseOfObscurity.apply(event);
		CurseOfFrenzy.onDamage(event);
	}

	//Run after hoes, shovels and Knockback feature
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onKnockback(LivingKnockBackEvent event) {
		Padding.reduceKnockback(event);
	}

	@SubscribeEvent
	public void onPickUpExperience(PlayerXpEvent.PickupXp event) {
		CurseOfTear.tearPlayerItems(event);
	}

	@SubscribeEvent
	public void onFall(LivingFallEvent event) {
		Hoppy.onFall(event);
		CurseOfSteelFall.onFall(event);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onLivingFall(LivingFallEvent event) {
		if (event.getEntity() instanceof LocalPlayer player)
			player.getPersistentData().putInt("double_jumps", 0);
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		Vindication.tryApplyDamage(event);
		if (event.getSource().getDirectEntity() instanceof LivingEntity attacker) {
			AirStealer.onAttack(attacker, event.getEntity());
		}
		Padding.shouldApply(event);
	}

	@SubscribeEvent
	public void onEffectAdded(MobEffectEvent.Added event) {
		MagicProtection.reduceBadEffectsDuration(event.getEntity(), event.getEffectInstance());
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		event.setNewSpeed(event.getNewSpeed() * AirBorn.getMiningSpeedMultiplier(event.getEntity(), event.getState()));
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBreakSpeedEnchantment(EnchantmentBonusEfficiencyEvent event) {
		if (event.getStack().getEnchantmentLevel(HASTE.get()) > 0)
			event.setNewEfficiency(event.getNewEfficiency() + Haste.getBonusEfficiency());
		event.setNewEfficiency(event.getNewEfficiency() + Blasting.getMiningSpeedBoost(event.getStack(), event.getEntity(), event.getState()));
		event.setNewEfficiency(event.getNewEfficiency() + Adrenaline.getMiningSpeedBoost(event.getStack(), event.getEntity(), event.getState()));
		if (event.getStack().getEnchantmentLevel(CURSE_OF_INEFFICIENCY.get()) > 0)
			event.setNewEfficiency(event.getNewEfficiency() * 0.65f);
	}

	@SubscribeEvent
	public void onItemFished(ItemFishedEvent event) {
		if (event.isCanceled())
			return;
		JuicyBait.apply(event);
		CurseOfTheVoid.onFishing(event);
	}

	/*public static boolean doesItemInEitherHandMatches(LivingEntity entity, Predicate<ItemStack> stackPredicate) {
		return stackPredicate.test(entity.getMainHandItem()) || stackPredicate.test(entity.getOffhandItem());
	}*/

	@SubscribeEvent
	public void onExperienceDropped(LivingExperienceDropEvent event) {
		Smartness.applyToLivingDrops(event);
		CurseOfDumbness.applyToLivingDrops(event);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onBlockBreakLowest(BlockEvent.BreakEvent event) {
		event.setExpToDrop(Knowledgeable.applyToBlockDrops(event.getPlayer(), event.getExpToDrop()));
		event.setExpToDrop(Smartness.applyToBlockDrops(event.getPlayer(), event.getExpToDrop()));
		event.setExpToDrop(CurseOfDumbness.applyToBlockDrops(event.getPlayer(), event.getExpToDrop()));
	}

	static BlockHitResult blockHitResult;

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEnchantmentBlockBreak(EnchantmentBlockBreakEvent event) {
		event.setExpToDrop(Knowledgeable.applyToBlockDrops(event.getPlayer(), event.getExpToDrop()));
		event.setExpToDrop(Smartness.applyToBlockDrops(event.getPlayer(), event.getExpToDrop()));
		event.setExpToDrop(CurseOfDumbness.applyToBlockDrops(event.getPlayer(), event.getExpToDrop()));
		HitResult pick = event.getPlayer().pick(event.getPlayer().getEntityReach() + 0.5d, 0f, false);
		if (pick instanceof BlockHitResult) {
			blockHitResult = (BlockHitResult) pick;
		}
	}

	//Priority high: run before Timber Trees
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		HitResult pick = event.getPlayer().pick(event.getPlayer().getEntityReach() + 0.5d, 0f, false);
		if (pick instanceof BlockHitResult) {
			blockHitResult = (BlockHitResult) pick;
			Veining.tryApply(event.getPlayer(), event.getPlayer().level(), event.getPos(), blockHitResult.getDirection(), event.getState());
			Expanded.tryApply(event.getPlayer(), event.getPlayer().level(), event.getPos(), blockHitResult.getDirection(), event.getState());
		}
	}

	@SubscribeEvent
	public void onPostBlockBreak(DestroyBlockPostEvent event) {
		if (blockHitResult == null)
			return;
		Exchange.tryApply(event.getPlayer(), event.getPlayer().level(), event.getPos(), blockHitResult, event.getState());
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		PartBreaker.onLootDrop(event);
		CurseOfTheVoid.onLootDrop(event);
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		Soulbound.onPlayerDeath(event);
		CurseOfExplosion.onDeath(event);
		Explosive.onKill(event);
	}

	@SubscribeEvent
	public void onLeaveEvent(EntityLeaveLevelEvent event) {
		CurseOfExplosion.onEntityRemoved(event);
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		Soulbound.onPlayerRespawn(event);
	}

	@SubscribeEvent
	public void onProjectileShoot(EntityJoinLevelEvent event) {
		onCrossbowProjectileShot(event.getEntity());
		onBowProjectileShot(event.getEntity());
	}

	private static void onCrossbowProjectileShot(Entity entity) {
		if (!(entity instanceof Projectile projectile)
				|| !(projectile.getOwner() instanceof LivingEntity owner)
				|| (projectile.getPersistentData().contains(BurstOfArrows.BURST) && !projectile.getPersistentData().getBoolean(BurstOfArrows.BURST)))
			return;

		Optional<InteractionHand> activeHand = getActiveHand(owner, (stack) -> stack.getItem() instanceof CrossbowItem);
		if (activeHand.isEmpty())
			return;
		int lvl = activeHand.get() == InteractionHand.MAIN_HAND ? owner.getMainHandItem().getEnchantmentLevel(BURST_OF_ARROWS.get()) : owner.getOffhandItem().getEnchantmentLevel(BURST_OF_ARROWS.get());
		if (lvl == 0)
			return;
		projectile.getPersistentData().putBoolean(BurstOfArrows.BURST, true);
	}

	private static void onBowProjectileShot(Entity entity) {
		if (!(entity instanceof Projectile projectile)
				|| !(projectile.getOwner() instanceof LivingEntity owner))
			return;

		Optional<InteractionHand> activeHand = getActiveHand(owner, (stack) -> stack.getItem() instanceof BowItem);
		if (activeHand.isEmpty())
			return;
		int lvl = activeHand.get() == InteractionHand.MAIN_HAND ? owner.getMainHandItem().getEnchantmentLevel(GRAVITY_DEFYING.get()) : owner.getOffhandItem().getEnchantmentLevel(GRAVITY_DEFYING.get());
		if (lvl == 0)
			return;
		projectile.setNoGravity(true);
		projectile.getPersistentData().putBoolean(GravityDefying.NBT_TAG, true);
	}

	public static Optional<InteractionHand> getActiveHand(LivingEntity entity, Predicate<ItemStack> stackPredicate) {
		boolean hasBow = stackPredicate.test(entity.getMainHandItem());
		boolean hasBowInOffHand = stackPredicate.test(entity.getOffhandItem());
		if (!hasBow && !hasBowInOffHand)
			return Optional.empty();
		return Optional.of(hasBow ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onRenderLevel(RenderLevelStageEvent event) {
		Expanded.applyOutlineAndDestroyAnimation(event);
		Veining.applyOutlineAndDestroyAnimation(event);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onSprint(PlayerSprintEvent event) {
		if (event.getPlayer().getAbilities().instabuild)
			return;
		if (EnchantmentHelper.getEnchantmentLevel(SPRINT_PACT.get(), event.getPlayer()) <= 0
				&& EnchantmentHelper.getEnchantmentLevel(CURSE_OF_WALKING.get(), event.getPlayer()) <= 0)
			return;

		event.setCanceled(true);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onJump(InputEvent.Key event) {
		if (Minecraft.getInstance().player == null
				|| Minecraft.getInstance().screen != null
				|| event.getAction() != InputConstants.PRESS
				|| event.getKey() != Minecraft.getInstance().options.keyJump.getKey().getValue())
			return;

		LocalPlayer player = Minecraft.getInstance().player;
		if (DoubleJump.extraJump(player))
			JumpMidAirMessage.jumpMidAir(player);
	}

	private static final String path = "new_enchantments_feature/";

	public static void addGlobalLoot(GlobalLootModifierProvider provider) {
		provider.add(path + "void_curse", new DropMultiplierModifier(new LootItemCondition[] {
					LootItemRandomChanceCondition.randomChance(0.35f).build(),
					MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(NewEnchantmentsFeature.CURSE_OF_THE_VOID.get(), MinMaxBounds.Ints.ANY))).build()
				}, Optional.empty(), Optional.empty(), 0, 0, false)
		);
	}
}