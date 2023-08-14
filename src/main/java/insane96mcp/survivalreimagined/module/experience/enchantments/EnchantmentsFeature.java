package insane96mcp.survivalreimagined.module.experience.enchantments;

import com.mojang.blaze3d.platform.InputConstants;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.util.IdTagMatcher;
import insane96mcp.survivalreimagined.SurvivalReimagined;
import insane96mcp.survivalreimagined.base.SRFeature;
import insane96mcp.survivalreimagined.module.Modules;
import insane96mcp.survivalreimagined.module.experience.enchantments.enchantment.*;
import insane96mcp.survivalreimagined.network.message.JumpMidAirMessage;
import insane96mcp.survivalreimagined.setup.SREnchantments;
import insane96mcp.survivalreimagined.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Label(name = "Enchantments", description = "Change some enchantments related stuff and adds new enchantments.")
@LoadFeature(module = Modules.Ids.EXPERIENCE)
public class EnchantmentsFeature extends SRFeature {

	public static final TagKey<EntityType<?>> WATER_COOLANT_AFFECTED = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(SurvivalReimagined.MOD_ID, "water_coolant_affected"));

	@Config
	@Label(name = "Infinity overhaul", description = "Infinity can go up to level 4. Each level makes an arrow have 1 in level+1 chance to not consume.")
	public static Boolean infinityOverhaul = true;
	@Config
	@Label(name = "Less unbreakable unbreaking", description = "Unbreaking chance to not consume durability is changed from 50%/66.7%/75%/80%/... to 25%/45%/60%/70%/... (at levels I/II/III/IV)")
	public static Boolean unbreakingOverhaul = true;
	@Config
	@Label(name = "Small Thorns Overhaul", description = "Thorns is no longer compatible with other protections, but deals damage every time (higher levels deal more damage) and no longer damages items.")
	public static Boolean thornsOverhaul = true;
	@Config
	@Label(name = "Bane of SSSSS", description = "Enable Bane of SSSSS, similar to Bane of Arthropods deals more damage to Spiders but also creepers. Bane of arthropods is disabled via disabled_enchantments.json")
	public static Boolean enableBaneOfSSSSS = true;

	@Config
	@Label(name = "Better Efficiency Formula", description = "Change the efficiency formula from tool_efficiency+(lvl*lvl+1) to tool_efficiency * (0.15 * (lvl * lvl + 1))")
	public static Boolean changeEfficiencyFormula = true;

	@Config(min = 0d, max = 10d)
	@Label(name = "Power Enchantment Damage", description = "Set arrow's damage increase with the Power enchantment (vanilla is 0.5). Set to 0.5 to disable.")
	public static Double powerEnchantmentDamage = 0.4d;

	@Config
	@Label(name = "Prevent farmland trampling with Feather Falling")
	public static Boolean preventFarmlandTramplingWithFeatherFalling = true;
	@Config
	@Label(name = "Buff Feather Falling", description = "Increases the damage protection from Feather Falling. From 12% per level to 16% per level")
	public static Boolean buffFeatherFalling = true;

	public static final ArrayList<IdTagMatcher> DISABLED_ENCHANTMENTS_DEFAULT = new ArrayList<>(List.of(
			new IdTagMatcher(IdTagMatcher.Type.ID, "minecraft:protection"),
			new IdTagMatcher(IdTagMatcher.Type.ID, "minecraft:mending"),
			new IdTagMatcher(IdTagMatcher.Type.ID, "minecraft:bane_of_arthropods"),
			new IdTagMatcher(IdTagMatcher.Type.ID, "allurement:reforming"),
			new IdTagMatcher(IdTagMatcher.Type.ID, "allurement:alleviating")

	));
	public static final ArrayList<IdTagMatcher> disabledEnchantments = new ArrayList<>();

	public EnchantmentsFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);

		JSON_CONFIGS.add(new SRFeature.JsonConfig<>("disabled_enchantments.json", disabledEnchantments, DISABLED_ENCHANTMENTS_DEFAULT, Utils.ID_TAG_MATCHER_LIST_TYPE));
	}

	public static boolean isEnchantmentDisabled(Enchantment enchantment) {
		if (!Feature.isEnabled(EnchantmentsFeature.class))
			return false;

		for (IdTagMatcher idTagMatcher : disabledEnchantments) {
			if (idTagMatcher.matchesEnchantment(enchantment))
				return true;
		}
		return false;
	}

	public static boolean isUnbreakingOverhaul() {
		return Feature.isEnabled(EnchantmentsFeature.class) && unbreakingOverhaul;
	}

	//Shamelessly stolen from TinkersConstruct/src/main/java/slimeknights/tconstruct/tools/modifiers/upgrades/general/ReinforcedModifier.java
	public static float unbreakingBonus(int lvl) {
		if (isUnbreakingOverhaul()) {
			float chance = 0.625f;
			if (lvl < 5) {
				// formula gives 22.5%, 40%, 52.5%, 60%, 62.5% for first 5 levels
				// In terms of durability, the tool lasts for x1.29, x1.67, x2.11, x2.5, x2.67 times more
				chance = 0.025f * lvl * (10 - lvl);
			}
			return chance;
		}
		else {
			return 1f / (lvl + 1);
		}
	}

	public static boolean isBetterEfficiencyFormula() {
		return Feature.isEnabled(EnchantmentsFeature.class) && changeEfficiencyFormula;
	}

	public static float getEfficiencyBonus(float toolEfficiency, int lvl) {
		if (isBetterEfficiencyFormula()) {
			float baseEfficiency = 0.15f;
			return toolEfficiency * (baseEfficiency * (lvl * lvl + 1));
		}
		else {
			return toolEfficiency + (lvl * lvl + 1);
		}
	}

	public static boolean isThornsOverhaul() {
		return Feature.isEnabled(EnchantmentsFeature.class) && thornsOverhaul;
	}

    @SubscribeEvent
	public void onAttributeModifiers(ItemAttributeModifierEvent event) {
		if (!this.isEnabled())
			return;

		StepUp.applyAttributeModifier(event);
		GravityDefying.applyAttributeModifier(event);
	}

	@SubscribeEvent
	public void onEntityTick(LivingEvent.LivingTickEvent event) {
		if (!this.isEnabled())
			return;

		Magnetic.tryPullItems(event.getEntity());
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
		int lvl = EnchantmentHelper.getEnchantmentLevel(SREnchantments.SMARTNESS.get(), event.getAttackingPlayer());
		if (lvl > 0)
			event.setDroppedExperience(Smartness.getIncreasedExperience(lvl, event.getDroppedExperience()));
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onRenderLevel(RenderLevelStageEvent event) {
		if (!this.isEnabled())
			return;

		Expanded.applyDestroyAnimation(event);
	}

	//Priority high: run before Timber Trees
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if (!this.isEnabled())
			return;

		HitResult pick = event.getPlayer().pick(event.getPlayer().getEntityReach() + 0.5d, 1f, false);
		if (pick instanceof BlockHitResult blockHitResult)
			Expanded.apply(event.getPlayer(), event.getPlayer().level(), event.getPos(), blockHitResult.getDirection(), event.getState());
	}

	@SubscribeEvent
	public void onLivingAttack(LivingHurtEvent event) {
		if (!this.isEnabled()
				|| !(event.getSource().getEntity() instanceof LivingEntity attacker))
			return;

		baneOfSssssOnAttack(attacker, event.getEntity(), event);
		waterCoolantOnAttack(attacker, event.getEntity(), event);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onCriticalHit(CriticalHitEvent event) {
		if (!this.isEnabled())
			return;

		int lvl = event.getEntity().getMainHandItem().getEnchantmentLevel(SREnchantments.CRITICAL.get());
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

	public void baneOfSssssOnAttack(LivingEntity attacker, LivingEntity entity, LivingHurtEvent event) {
		if (!(entity instanceof Creeper))
			return;
		int lvl = attacker.getMainHandItem().getEnchantmentLevel(SREnchantments.BANE_OF_SSSSS.get());
		if (lvl == 0)
			return;

		event.setAmount((event.getAmount() + 2.5f * lvl));
	}

	public void waterCoolantOnAttack(LivingEntity attacker, LivingEntity entity, LivingHurtEvent event) {
		if (!Utils.isEntityInTag(entity, WATER_COOLANT_AFFECTED))
			return;

		int lvl = attacker.getMainHandItem().getEnchantmentLevel(SREnchantments.WATER_COOLANT.get());
		if (lvl == 0)
			return;

		float bonusDamage = 2.5f * lvl;
		event.setAmount((event.getAmount() + bonusDamage));
	}

	@SubscribeEvent
	public void onArrowSpawn(EntityJoinLevelEvent event) {
		if (!this.isEnabled()
				|| !(event.getEntity() instanceof AbstractArrow arrow))
			return;
		if (!arrow.shotFromCrossbow())
			processBow(arrow);
	}

	@SubscribeEvent
	public void onFarmlandTrample(BlockEvent.FarmlandTrampleEvent event) {
		if (!this.isEnabled()
				|| !preventFarmlandTramplingWithFeatherFalling
				|| !(event.getEntity() instanceof LivingEntity entity)
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, entity) <= 0)
			return;

		event.setCanceled(true);
	}

	private void processBow(AbstractArrow arrow) {
		if (powerEnchantmentDamage != 0.5d && arrow.getOwner() instanceof LivingEntity) {
			int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, (LivingEntity) arrow.getOwner());
			if (powerLevel == 0)
				return;
			double powerReduction = 0.5d - powerEnchantmentDamage;
			arrow.setBaseDamage(arrow.getBaseDamage() - (powerLevel * powerReduction + 0.5d));
		}
	}

	public static boolean isInfinityOverhaulEnabled() {
		return Feature.isEnabled(EnchantmentsFeature.class) && infinityOverhaul;
	}

	public static boolean isFeatherFallingBuffed() {
		return Feature.isEnabled(EnchantmentsFeature.class) && buffFeatherFalling;
	}

	public static boolean isBaneOfSSSSSEnabled() {
		return Feature.isEnabled(EnchantmentsFeature.class) && enableBaneOfSSSSS;
	}
}