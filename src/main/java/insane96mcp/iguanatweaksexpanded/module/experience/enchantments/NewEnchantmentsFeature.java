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
import insane96mcp.insanelib.event.HurtItemStackEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

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
	public static final RegistryObject<Enchantment> SMARTNESS = ITERegistries.ENCHANTMENTS.register("smartness", Smartness::new);
	public static final RegistryObject<Enchantment> MA_JUMP = ITERegistries.ENCHANTMENTS.register("ma_jump", DoubleJump::new);
	public static final RegistryObject<Enchantment> GRAVITY_DEFYING = ITERegistries.ENCHANTMENTS.register("gravity_defying", GravityDefying::new);
	public static final RegistryObject<Enchantment> CRITICAL = ITERegistries.ENCHANTMENTS.register("critical", Critical::new);
	public static final RegistryObject<Enchantment> SWIFT_STRIKE = ITERegistries.ENCHANTMENTS.register("swift_strike", SwiftStrike::new);
	public static final RegistryObject<Enchantment> HEALTHY = ITERegistries.ENCHANTMENTS.register("healthy", Healthy::new);
	public static final RegistryObject<Enchantment> CURSE_OF_MENDING = ITERegistries.ENCHANTMENTS.register("curse_of_mending", CurseOfMending::new);
	public static final RegistryObject<Enchantment> REACH = ITERegistries.ENCHANTMENTS.register("reach", Reach::new);
	public static final RegistryObject<Enchantment> VINDICATION = ITERegistries.ENCHANTMENTS.register("vindication", Vindication::new);
	public static final RegistryObject<Enchantment> LUCKY_HOOK = ITERegistries.ENCHANTMENTS.register("lucky_hook", LuckyHook::new);
	public NewEnchantmentsFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

    @SubscribeEvent
	public void onAttributeModifiers(ItemAttributeModifierEvent event) {
		StepUp.applyAttributeModifier(event);
		Zippy.applyAttributeModifier(event);
		Reach.applyAttributeModifier(event);
		GravityDefying.applyAttributeModifier(event);
		Healthy.applyAttributeModifier(event);
		SwiftStrike.applyAttributeModifier(event);
		MeleeProtection.applyAttributeModifier(event);
	}

	@SubscribeEvent
	public void onHurtItemStack(HurtItemStackEvent event) {
		CurseOfMending.consumePlayerExperience(event);
	}

	@SubscribeEvent
	public void onEntityTick(LivingEvent.LivingTickEvent event) {
		Magnetic.tryPullItems(event.getEntity());
	}

	@SubscribeEvent
	public void onDamaged(LivingDamageEvent event) {
		Vindication.tryStackDamage(event.getEntity(), event.getSource(), event.getAmount());
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		Vindication.tryApplyDamage(event);
	}

	@SubscribeEvent
	public void onEffectAdded(MobEffectEvent.Added event) {
		MagicProtection.reduceBadEffectsDuration(event.getEntity(), event.getEffectInstance());
	}

	@SubscribeEvent
	public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		event.setNewSpeed(event.getNewSpeed() + Blasting.getMiningSpeedBoost(event.getEntity(), event.getState()));
	}

	@SubscribeEvent
	public void onItemFished(ItemFishedEvent event) {
		if (event.isCanceled())
			return;
		Player player = event.getEntity();
		boolean hasFishingRod = player.getMainHandItem().getItem() instanceof FishingRodItem;
		boolean hasFishingRodInOffHand = player.getOffhandItem().getItem() instanceof FishingRodItem;
		if (!hasFishingRod && !hasFishingRodInOffHand)
			return;
		int lvl = hasFishingRod ? player.getMainHandItem().getEnchantmentLevel(LUCKY_HOOK.get()) : player.getOffhandItem().getEnchantmentLevel(LUCKY_HOOK.get());
		if (lvl == 0 || player.getRandom().nextFloat() > LuckyHook.getChanceToDoubleReel(lvl))
			return;
		ItemStack stack = hasFishingRod ? player.getMainHandItem() : player.getOffhandItem();
		FishingHook hookEntity = event.getHookEntity();
		LootParams lootparams = (new LootParams.Builder((ServerLevel) player.level())).withParameter(LootContextParams.ORIGIN, hookEntity.position()).withParameter(LootContextParams.TOOL, stack).withParameter(LootContextParams.THIS_ENTITY, hookEntity).withParameter(LootContextParams.KILLER_ENTITY, player).withParameter(LootContextParams.THIS_ENTITY, hookEntity).withLuck((float) hookEntity.luck + player.getLuck()).create(LootContextParamSets.FISHING);
		LootTable loottable = player.level().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
		List<ItemStack> list = loottable.getRandomItems(lootparams);
		for(ItemStack itemstack : list) {
			ItemEntity itementity = new ItemEntity(hookEntity.level(), hookEntity.getX(), hookEntity.getY(), hookEntity.getZ(), itemstack);
			double xDiff = player.getX() - hookEntity.getX();
			double yDiff = player.getY() - hookEntity.getY();
			double zDiff = player.getZ() - hookEntity.getZ();
			double deltaMovMultiplier = 0.1D;
			itementity.setDeltaMovement(xDiff * deltaMovMultiplier, yDiff * deltaMovMultiplier + Math.sqrt(Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff)) * 0.08D, zDiff * deltaMovMultiplier);
			hookEntity.level().addFreshEntity(itementity);
			player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5D, player.getZ() + 0.5D, event.getEntity().getRandom().nextInt(6) + 1));
			if (itemstack.is(ItemTags.FISHES))
				player.awardStat(Stats.FISH_CAUGHT, 1);
		}
	}

	/*public static boolean doesItemInEitherHandMatches(LivingEntity entity, Predicate<ItemStack> stackPredicate) {
		return stackPredicate.test(entity.getMainHandItem()) || stackPredicate.test(entity.getOffhandItem());
	}*/

	@SubscribeEvent
	public void onExperienceDropped(LivingExperienceDropEvent event) {
		if (event.getAttackingPlayer() == null)
			return;
		int lvl = EnchantmentHelper.getEnchantmentLevel(SMARTNESS.get(), event.getAttackingPlayer());
		if (lvl > 0)
			event.setDroppedExperience(Smartness.getIncreasedExperience(event.getEntity().getRandom(), lvl, event.getDroppedExperience()));
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onBlockExperienceDropped(BlockEvent.BreakEvent event) {
		int lvl = EnchantmentHelper.getEnchantmentLevel(SMARTNESS.get(), event.getPlayer());
		if (lvl > 0)
			event.setExpToDrop(Smartness.getIncreasedExperience(event.getLevel().getRandom(), lvl, event.getExpToDrop()));
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
		if (lvl <= 0)
			return;
		event.setDamageModifier(Critical.getCritAmount(lvl, event.getDamageModifier()));
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