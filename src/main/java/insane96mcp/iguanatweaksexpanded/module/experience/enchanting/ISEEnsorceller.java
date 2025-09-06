package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ISEEnsorceller extends BaseEntityBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public ISEEnsorceller(Properties props) {
        super(props);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState s) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof ISEEnsorcellerBlockEntity blockEntity))
            return InteractionResult.PASS;

        if (held.is(Items.ENCHANTED_BOOK)) {
            int gain = 0;
            ListTag list = EnchantedBookItem.getEnchantments(held);
            List<CompoundTag> toRemove = new ArrayList<>();
            for (Tag tag : list) {
                if (tag instanceof CompoundTag compound) {
                    Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(EnchantmentHelper.getEnchantmentId(compound));
                    if (enchantment == null
                            || enchantment.isCurse())
                        continue;
                    int lvl = EnchantmentHelper.getEnchantmentLevel(compound);
                    gain += EnchantingFeature.getCost(enchantment, lvl);
                    toRemove.add(compound);
                }
            }
            for (CompoundTag tag : toRemove) {
                list.remove(tag);
            }
            if (gain > 0) {
                if (!level.isClientSide) {
                    blockEntity.addCharges(gain);
                    if (list.isEmpty()) {
                        player.getItemInHand(hand).shrink(1);
                        player.setItemSlot(hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, new ItemStack(Items.BOOK));
                    }
                    level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, 0.5F);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        if (held.is(Items.BOOK) && blockEntity.hasPendingEnchantment()) {
            if (!level.isClientSide) {
                ItemStack out = blockEntity.createPendingBook(level.random);
                if (!out.isEmpty()) {
                    if (held.getCount() == 1) {
                        player.setItemSlot(hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, out);
                    }
                    else {
                        held.shrink(1);
                        if (!player.addItem(out)) {
                            ItemEntity drop = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), out);
                            level.addFreshEntity(drop);
                        }
                    }
                    level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, 2.5F);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof ISEEnsorcellerBlockEntity blockEntity) {
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ISEEnsorcellerBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, EnchantingFeature.ENSORCELLER_BLOCK_ENTITY.get(), ISEEnsorcellerBlockEntity::tick);
    }
}