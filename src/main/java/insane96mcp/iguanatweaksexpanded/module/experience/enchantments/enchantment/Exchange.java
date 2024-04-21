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
import net.minecraft.world.InteractionResult;
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
        if (state.getDestroySpeed(level, pos) == 0
                || !(entity instanceof Player player))
            return;
        ItemStack heldStack = entity.getMainHandItem();
        int enchLevel = heldStack.getEnchantmentLevel(NewEnchantmentsFeature.EXCHANGE.get());
        if (enchLevel == 0)
            return;
        ItemStack offhandItem = entity.getOffhandItem();
        if (!(offhandItem.getItem() instanceof BlockItem))
            return;
        offhandItem = tryGetAnotherBlockItem(player, offhandItem);
        Direction direction = blockHitResult.getDirection().getOpposite();
        ItemStack finalOffhandItem = offhandItem;
        ScheduledTasks.schedule(new ScheduledTickTask(0) {
            @Override
            public void run() {
                place(((BlockItem)finalOffhandItem.getItem()), new BlockPlaceContext(level, player, InteractionHand.OFF_HAND, finalOffhandItem, new BlockHitResult(blockHitResult.getLocation().add(direction.getStepX(), direction.getStepY(), direction.getStepZ()), direction, pos, blockHitResult.isInside())));
            }
        });
    }

    public static InteractionResult place(BlockItem blockItem, BlockPlaceContext pContext) {
        if (!blockItem.getBlock().isEnabled(pContext.getLevel().enabledFeatures())) {
            return InteractionResult.FAIL;
        } else if (!pContext.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockplacecontext = blockItem.updatePlacementContext(pContext);
            if (blockplacecontext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockstate = ((BlockItemInvoker)blockItem).invokeGetPlacementState(blockplacecontext);
                if (blockstate == null) {
                    return InteractionResult.FAIL;
                } else if (!((BlockItemInvoker)blockItem).invokePlaceBlock(blockplacecontext, blockstate)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockpos = blockplacecontext.getClickedPos();
                    Level level = blockplacecontext.getLevel();
                    Player player = blockplacecontext.getPlayer();
                    ItemStack itemstack = blockplacecontext.getItemInHand();
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    if (blockstate1.is(blockstate.getBlock())) {
                        blockstate1 = ((BlockItemInvoker)blockItem).invokeUpdateBlockStateFromTag(blockpos, level, itemstack, blockstate1);
                        ((BlockItemInvoker)blockItem).invokeUpdateCustomBlockEntityTag(blockpos, level, player, itemstack, blockstate1);
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(level, blockpos, pContext.getPlayer());
                    level.playSound(null, blockpos, ((BlockItemInvoker)blockItem).invokeGetPlaceSound(blockstate1, level, blockpos, pContext.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    level.gameEvent(GameEvent.BLOCK_PLACE, blockpos, GameEvent.Context.of(player, blockstate1));
                    if (player == null || !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    public static ItemStack tryGetAnotherBlockItem(Player player, ItemStack stack) {
        int slot = player.getInventory().findSlotMatchingItem(stack);
        return slot == -1 ? stack : player.getInventory().getItem(slot);
    }
}
