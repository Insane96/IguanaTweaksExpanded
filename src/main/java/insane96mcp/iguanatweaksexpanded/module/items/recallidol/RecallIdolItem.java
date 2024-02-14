package insane96mcp.iguanatweaksexpanded.module.items.recallidol;

import insane96mcp.insanelib.world.scheduled.ScheduledTasks;
import insane96mcp.insanelib.world.scheduled.ScheduledTickTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class RecallIdolItem extends Item {
	public RecallIdolItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public int getUseDuration(ItemStack p_41454_) {
		return 110;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack p_40678_) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemstack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
		if (remainingUseDuration % 20 == 0) {
			level.playSound(null, livingEntity, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1f - remainingUseDuration / 100f, 1.5f - remainingUseDuration / 100f);
			for (int i = 0; i < (getUseDuration(stack) - remainingUseDuration) * 20; i++) {
				level.addParticle(ParticleTypes.PORTAL, livingEntity.getX() + level.random.nextFloat() - 0.5f, livingEntity.getY() + level.random.nextFloat() * livingEntity.getBbHeight(), livingEntity.getZ() + level.random.nextFloat() - 0.5f, level.random.nextFloat() - 0.5f, level.random.nextFloat() - 0.5f, level.random.nextFloat() - 0.5f);
			}
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (!(entity instanceof ServerPlayer player) || player.isDeadOrDying())
			return stack;
		BlockPos respawnPos = player.getRespawnPosition();
		float respawnAngle = player.getRespawnAngle();
		boolean forcedRespawn = player.isRespawnForced();
		ServerLevel serverLevel = player.server.getLevel(player.getRespawnDimension());
		Optional<Vec3> optional;
		if (serverLevel != null && respawnPos != null) {
			optional = Player.findRespawnPositionAndUseSpawnBlock(serverLevel, respawnPos, respawnAngle, forcedRespawn, true);
		} else {
			optional = Optional.empty();
		}
		optional.ifPresent(vec3 -> {
			if (player.level() != serverLevel)
				player.changeDimension(serverLevel);
			player.teleportTo(vec3.x, vec3.y, vec3.z);
			ScheduledTasks.schedule(new ScheduledTickTask(2) {
				@Override
				public void run() {
					serverLevel.playSound(null, player, SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 4f, 1f);
					//serverLevel.broadcastEntityEvent(entity, (byte)35);
				}
			});
			stack.shrink(1);
		});
		return stack;
	}
}
