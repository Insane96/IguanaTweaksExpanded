package insane96mcp.iguanatweaksexpanded.module.experience.enchantments;

import com.mojang.blaze3d.platform.InputConstants;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.*;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse.*;
import insane96mcp.iguanatweaksexpanded.network.message.JumpMidAirMessage;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksreborn.data.lootmodifier.DropMultiplierModifier;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.event.HurtItemStackEvent;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.enchantment.Enchantment;
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
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

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
	public static final RegistryObject<Enchantment> ZIPPY = ITERegistries.ENCHANTMENTS.register("zippy", Zippy::new);
	public static final RegistryObject<Enchantment> WATER_COOLANT = ITERegistries.ENCHANTMENTS.register("water_coolant", WaterCoolant::new);
	public static final RegistryObject<Enchantment> RAGE = ITERegistries.ENCHANTMENTS.register("rage", Rage::new);
	public static final RegistryObject<Enchantment> CRITICAL = ITERegistries.ENCHANTMENTS.register("critical", Critical::new);
	public static final RegistryObject<Enchantment> SMARTNESS = ITERegistries.ENCHANTMENTS.register("smartness", Smartness::new);
	public static final RegistryObject<Enchantment> PART_BREAKER = ITERegistries.ENCHANTMENTS.register("part_breaker", PartBreaker::new);
	public static final RegistryObject<Enchantment> MA_JUMP = ITERegistries.ENCHANTMENTS.register("ma_jump", DoubleJump::new);
	public static final RegistryObject<Enchantment> GRAVITY_DEFYING = ITERegistries.ENCHANTMENTS.register("gravity_defying", GravityDefying::new);
	public static final RegistryObject<Enchantment> SWIFT_STRIKE = ITERegistries.ENCHANTMENTS.register("swift_strike", SwiftStrike::new);
	public static final RegistryObject<Enchantment> HEALTHY = ITERegistries.ENCHANTMENTS.register("healthy", Healthy::new);
	public static final RegistryObject<Enchantment> REACH = ITERegistries.ENCHANTMENTS.register("reach", Reach::new);
	public static final RegistryObject<Enchantment> PADDING = ITERegistries.ENCHANTMENTS.register("padding", Padding::new);
	public static final RegistryObject<Enchantment> VINDICATION = ITERegistries.ENCHANTMENTS.register("vindication", Vindication::new);
	public static final RegistryObject<Enchantment> JUICY_BAIT = ITERegistries.ENCHANTMENTS.register("lucky_hook", JuicyBait::new);
	public static final RegistryObject<Enchantment> BURST_OF_ARROWS = ITERegistries.ENCHANTMENTS.register("burst_of_arrows", BurstOfArrows::new);
	public static final RegistryObject<Enchantment> CURSE_OF_EXPERIENCE = ITERegistries.ENCHANTMENTS.register("experience_curse", CurseOfExperience::new);
	public static final RegistryObject<Enchantment> CURSE_OF_TEAR = ITERegistries.ENCHANTMENTS.register("tear_curse", CurseOfTear::new);
	public static final RegistryObject<Enchantment> CURSE_OF_UNHURRIED = ITERegistries.ENCHANTMENTS.register("unhurried_curse", CurseOfUnhurried::new);
	public static final RegistryObject<Enchantment> CURSE_OF_SLOW_STRIKE = ITERegistries.ENCHANTMENTS.register("slow_strike_curse", CurseOfSlowStrike::new);
	public static final RegistryObject<Enchantment> CURSE_OF_INEFFICIENCY = ITERegistries.ENCHANTMENTS.register("inefficiency_curse", CurseOfInefficiency::new);
	public static final RegistryObject<Enchantment> CURSE_OF_SHORT_ARM = ITERegistries.ENCHANTMENTS.register("short_arm_curse", CurseOfShortArm::new);
	public static final RegistryObject<Enchantment> CURSE_OF_FRAGILITY = ITERegistries.ENCHANTMENTS.register("fragility_curse", CurseOfFragility::new);
	public static final RegistryObject<Enchantment> CURSE_OF_ENDER = ITERegistries.ENCHANTMENTS.register("ender_curse", CurseOfEnder::new);
	public static final RegistryObject<Enchantment> CURSE_OF_STEEL_FALL = ITERegistries.ENCHANTMENTS.register("steel_fall_curse", CurseOfSteelFall::new);
	public static final RegistryObject<Enchantment> CURSE_OF_THE_VOID = ITERegistries.ENCHANTMENTS.register("void_curse", CurseOfTheVoid::new);
	public static final RegistryObject<Enchantment> CURSE_OF_DUMBNESS = ITERegistries.ENCHANTMENTS.register("dumbness_curse", CurseOfDumbness::new);
	//public static final RegistryObject<Enchantment> CURSE_OF_ANCHOR = ITERegistries.ENCHANTMENTS.register("anchor_curse", CurseOfAnchor::new);
	//public static final RegistryObject<Enchantment> CURSE_OF_SINKING = ITERegistries.ENCHANTMENTS.register("sinking_curse", CurseOfSinking::new);
	public NewEnchantmentsFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

    @SubscribeEvent
	public void onAttributeModifiers(ItemAttributeModifierEvent event) {
		event.getItemStack().getAllEnchantments().forEach((enchantment, lvl) -> {
			if (event.getItemStack().getItem() instanceof ArmorItem armorItem
				&& armorItem.getEquipmentSlot() != event.getSlotType())
				return;
			if (enchantment instanceof IAttributeEnchantment attributeEnchantment)
				attributeEnchantment.applyAttributeModifier(event, lvl);
		});
	}

	@SubscribeEvent
	public void onHurtItemStack(HurtItemStackEvent event) {
		CurseOfExperience.consumePlayerExperience(event);
		CurseOfFragility.increaseItemHurt(event);
	}

	@SubscribeEvent
	public void onEntityTick(LivingEvent.LivingTickEvent event) {
		Magnetic.tryPullItems(event.getEntity());
		CurseOfAnchor.apply(event);
	}

	@SubscribeEvent
	public void onDamaged(LivingDamageEvent event) {
		Vindication.tryStackDamage(event.getEntity(), event.getSource(), event.getAmount());
		CurseOfEnder.onHurt(event);
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
		CurseOfSteelFall.onFall(event);
		GravityDefying.applyFallDamageReduction(event);
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
		Padding.shouldApply(event);
	}

	@SubscribeEvent
	public void onEffectAdded(MobEffectEvent.Added event) {
		MagicProtection.reduceBadEffectsDuration(event.getEntity(), event.getEffectInstance());
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		event.setNewSpeed(event.getNewSpeed() + Blasting.getMiningSpeedBoost(event.getEntity(), event.getState()));
		if (event.getEntity().getMainHandItem().getEnchantmentLevel(CURSE_OF_INEFFICIENCY.get()) > 0)
			event.setNewSpeed(event.getNewSpeed() * 0.65f);
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
	public void onBlockExperienceDropped(BlockEvent.BreakEvent event) {
		Smartness.applyToBlockDrops(event);
		CurseOfDumbness.applyToBlockDrops(event);
	}

	//Priority high: run before Timber Trees
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		HitResult pick = event.getPlayer().pick(event.getPlayer().getEntityReach() + 0.5d, 1f, false);
		if (pick instanceof BlockHitResult blockHitResult) {
			Veining.tryApply(event.getPlayer(), event.getPlayer().level(), event.getPos(), blockHitResult.getDirection(), event.getState());
			Expanded.tryApply(event.getPlayer(), event.getPlayer().level(), event.getPos(), blockHitResult.getDirection(), event.getState());
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onCriticalHit(CriticalHitEvent event) {
		int lvl = event.getEntity().getMainHandItem().getEnchantmentLevel(CRITICAL.get());
		if (lvl > 0)
			event.setDamageModifier(Critical.getCritAmount(lvl, event.getDamageModifier()));
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		PartBreaker.onLootDrop(event);
		CurseOfTheVoid.onLootDrop(event);
	}

	@SubscribeEvent
	public void onLivingTick(LivingEvent.LivingTickEvent event) {
		CurseOfSinking.sink(event);
	}

	@SubscribeEvent
	public void onProjectileShoot(EntityJoinLevelEvent event) {
		if (!(event.getEntity() instanceof Projectile projectile)
				|| !(projectile.getOwner() instanceof LivingEntity owner)
				|| (projectile.getPersistentData().contains(BurstOfArrows.BURST) && !projectile.getPersistentData().getBoolean(BurstOfArrows.BURST)))
			return;

		boolean hasCrossbow = owner.getMainHandItem().getItem() instanceof CrossbowItem;
		boolean hasCrossbowInOffHand = owner.getOffhandItem().getItem() instanceof CrossbowItem;
		if (!hasCrossbow && !hasCrossbowInOffHand)
			return;
		int lvl = hasCrossbow ? owner.getMainHandItem().getEnchantmentLevel(BURST_OF_ARROWS.get()) : owner.getOffhandItem().getEnchantmentLevel(BURST_OF_ARROWS.get());
		if (lvl == 0)
			return;
		projectile.getPersistentData().putBoolean(BurstOfArrows.BURST, true);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onRenderLevel(RenderLevelStageEvent event) {
		Expanded.applyOutlineAndDestroyAnimation(event);
		Veining.applyOutlineAndDestroyAnimation(event);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onJump(InputEvent.Key event) {
		if (Minecraft.getInstance().player == null
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