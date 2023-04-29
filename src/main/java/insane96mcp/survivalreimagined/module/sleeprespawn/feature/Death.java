package insane96mcp.survivalreimagined.module.sleeprespawn.feature;

import insane96mcp.insanelib.ai.ILNearestAttackableTargetGoal;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.survivalreimagined.SurvivalReimagined;
import insane96mcp.survivalreimagined.base.BlockWithItem;
import insane96mcp.survivalreimagined.module.Modules;
import insane96mcp.survivalreimagined.module.experience.feature.GlobalExperience;
import insane96mcp.survivalreimagined.module.experience.feature.PlayerExperience;
import insane96mcp.survivalreimagined.module.sleeprespawn.block.GraveBlock;
import insane96mcp.survivalreimagined.module.sleeprespawn.block.GraveBlockEntity;
import insane96mcp.survivalreimagined.setup.SRBlockEntityTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Label(name = "Death", description = "Changes to death")
@LoadFeature(module = Modules.Ids.SLEEP_RESPAWN)
public class Death extends Feature {

	public static final BlockWithItem GRAVE = BlockWithItem.register("grave", () -> new GraveBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(1.5F, 6.0F)));
	public static final RegistryObject<BlockEntityType<?>> GRAVE_BLOCK_ENTITY_TYPE = SRBlockEntityTypes.REGISTRY.register("grave", () -> BlockEntityType.Builder.of(GraveBlockEntity::new, GRAVE.block().get()).build(null));

	public static final String PLAYER_GHOST = SurvivalReimagined.RESOURCE_PREFIX + "player_ghost";
	public static final String PLAYER_GHOST_LANG = SurvivalReimagined.MOD_ID + ".player_ghost";
	public static final String XP_TO_DROP = SurvivalReimagined.RESOURCE_PREFIX + "xp_to_drop";


	public static final UUID MOVEMENT_SPEED_BONUS = UUID.fromString("1905c271-160b-4560-9b76-c97b007657a5");
	public static final UUID ATTACK_DAMAGE_BONUS = UUID.fromString("bce0ee20-1358-4c8c-89ee-9446548a284b");
	public static final UUID ATTACK_DAMAGE_XP_BONUS = UUID.fromString("4b0d7d72-30cb-4200-9cc7-0944308b8bae");
	public static final UUID HEALTH_XP_BONUS = UUID.fromString("db05e364-0189-47bb-a6cb-487791c8dcd2");

	public Death(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerDeath(LivingDeathEvent event) {
		if (!this.isEnabled()
				|| !(event.getEntity() instanceof ServerPlayer player)
				|| player.getLevel().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)
				|| player.level.isOutsideBuildHeight(player.blockPosition().getY())
				|| (player.getInventory().isEmpty() && player.experienceLevel == 0))
			return;

		player.getLevel().setBlock(player.blockPosition(), GRAVE.block().get().defaultBlockState(), 3);
		if (player.getLevel().getBlockState(player.blockPosition().below()).canBeReplaced())
			player.getLevel().setBlock(player.blockPosition().below(), Blocks.COARSE_DIRT.defaultBlockState(), 3);
		GraveBlockEntity graveBlockEntity = (GraveBlockEntity) player.getLevel().getBlockEntity(player.blockPosition());
		List<ItemStack> items = new ArrayList<>();
		player.getInventory().items.forEach(itemStack -> {
			if (!itemStack.isEmpty())
				items.add(itemStack);
		});
		player.getInventory().armor.forEach(itemStack -> {
			if (!itemStack.isEmpty())
				items.add(itemStack);
		});
		player.getInventory().offhand.forEach(itemStack -> {
			if (!itemStack.isEmpty())
				items.add(itemStack);
		});
		graveBlockEntity.setItems(items);
		int xpDropped = PlayerExperience.getExperienceOnDeath(player, true);
		graveBlockEntity.setXpStored(xpDropped);
		graveBlockEntity.setOwner(player.getUUID());
		graveBlockEntity.setDeathNumber(player.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)) + 1);
		player.setExperienceLevels(0);
		player.setExperiencePoints(0);
		player.getInventory().clearContent();
	}

	//Remove targeting goal. Only attack players that attack the ghost
	@SubscribeEvent
	public void onGhostJoinWorld(EntityJoinLevelEvent event) {
		if (!event.getEntity().getPersistentData().contains(PLAYER_GHOST))
			return;

		Zombie zombie = (Zombie) event.getEntity();
		zombie.targetSelector.getAvailableGoals().removeIf(wrappedGoal -> wrappedGoal.getGoal() instanceof NearestAttackableTargetGoal<?> || wrappedGoal.getGoal() instanceof ILNearestAttackableTargetGoal<?>);
		zombie.goalSelector.addGoal(0, new FloatGoal(zombie));
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if (event.getEntity().level.isClientSide
				|| !event.getEntity().getPersistentData().contains(PLAYER_GHOST))
			return;

		int experienceToDrop = event.getEntity().getPersistentData().getInt(XP_TO_DROP);
		if (experienceToDrop > 0) {
			ExperienceOrb xpOrb = new ExperienceOrb(event.getEntity().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), experienceToDrop);
			xpOrb.getPersistentData().putBoolean(GlobalExperience.XP_PROCESSED, true);
			event.getEntity().level.addFreshEntity(xpOrb);
		}
	}

	@SubscribeEvent
	public void onEntityTick(LivingEvent.LivingTickEvent event) {
		if (!this.isEnabled()
				|| event.getEntity().tickCount % 20 != 2
				|| event.getEntity().level.isClientSide
				|| !event.getEntity().getPersistentData().contains(PLAYER_GHOST))
			return;

		if (event.getEntity().level.hasNearbyAlivePlayer(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), 80))
			event.getEntity().setGlowingTag(true);
		event.getEntity().setTicksFrozen(0);
	}

	@SubscribeEvent
	public void canConvert(LivingConversionEvent.Pre event) {
		if (event.getEntity().getPersistentData().contains(PLAYER_GHOST))
			event.setCanceled(true);
	}
}