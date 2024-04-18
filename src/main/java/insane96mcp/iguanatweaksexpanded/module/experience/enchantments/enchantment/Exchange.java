package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.mixin.BlockItemInvoker;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.world.scheduled.ScheduledTasks;
import insane96mcp.insanelib.world.scheduled.ScheduledTickTask;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class Exchange extends Enchantment {
    public Exchange() {
        super(Rarity.RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 22 * level;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 22;
    }

    public static void tryApply(LivingEntity entity, Level level, BlockPos pos, BlockHitResult blockHitResult, BlockState state) {
        if (state.getDestroySpeed(level, pos) == 0)
            return;
        ItemStack heldStack = entity.getMainHandItem();
        int enchLevel = heldStack.getEnchantmentLevel(NewEnchantmentsFeature.EXCHANGE.get());
        if (enchLevel == 0)
            return;
        ItemStack offhandItem = entity.getOffhandItem();
        if (!(offhandItem.getItem() instanceof BlockItem blockItem))
            return;
        Player player = null;
        if (entity instanceof Player)
            player = (Player) entity;
        Direction direction = blockHitResult.getDirection().getOpposite();
        Player finalPlayer = player;
        ScheduledTasks.schedule(new ScheduledTickTask(0) {
            @Override
            public void run() {
                blockItem.place(new BlockPlaceContext(level, finalPlayer, InteractionHand.OFF_HAND, offhandItem, new BlockHitResult(blockHitResult.getLocation().add(direction.getStepX(), direction.getStepY(), direction.getStepZ()), direction, pos, blockHitResult.isInside())));
            }
        });
    }
}
