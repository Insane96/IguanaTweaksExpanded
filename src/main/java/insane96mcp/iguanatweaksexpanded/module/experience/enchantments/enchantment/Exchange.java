package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.mixin.BlockItemInvoker;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
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
        if (place(new BlockPlaceContext(level, player, InteractionHand.OFF_HAND, offhandItem, new BlockHitResult(blockHitResult.getLocation().add(direction.getStepX(), direction.getStepY(), direction.getStepZ()), direction, pos.relative(blockHitResult.getDirection()), blockHitResult.isInside())), blockItem))
            entity.swing(InteractionHand.OFF_HAND);
    }

    public static boolean place(BlockPlaceContext context, BlockItem blockItem) {
        BlockPlaceContext updatedContext = blockItem.updatePlacementContext(context);
        if (updatedContext == null)
            return false;

        BlockState blockstate = ((BlockItemInvoker)blockItem).invokeGetPlacementState(updatedContext);
        if (blockstate == null
                || !updatedContext.getLevel().setBlock(updatedContext.getClickedPos(), blockstate, 11))
            return false;
        BlockPos blockpos = updatedContext.getClickedPos();
        Level level = updatedContext.getLevel();
        Player player = updatedContext.getPlayer();
        ItemStack itemstack = updatedContext.getItemInHand();
        BlockState blockstate1 = level.getBlockState(blockpos);
        if (blockstate1.is(blockstate.getBlock())) {
            BlockItem.updateCustomBlockEntityTag(level, player, blockpos, itemstack);
            blockstate1 = ((BlockItemInvoker)blockItem).invokeUpdateBlockStateFromTag(blockpos, level, itemstack, blockstate1);
            blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
            }
        }

        SoundType soundtype = blockstate1.getSoundType(level, blockpos, context.getPlayer());
        level.playSound(player, blockpos, ((BlockItemInvoker)blockItem).invokeGetPlaceSound(blockstate1, level, blockpos, context.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        level.gameEvent(GameEvent.BLOCK_PLACE, blockpos, GameEvent.Context.of(player, blockstate1));
        if (player == null || !player.getAbilities().instabuild)
            itemstack.shrink(1);

        return true;
    }
}
