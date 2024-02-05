package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("deprecation")
public abstract class AbstractMultiBlockFurnace extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public static final String MULTI_BLOCK_TOOLTIP = IguanaTweaksExpanded.MOD_ID + ".multi_block_structure";

    public AbstractMultiBlockFurnace(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.FALSE));
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        else {
            if (this.isValidMultiBlock(level, pos))
                this.openContainer(level, pos, player);
            else {
                player.sendSystemMessage(Component.translatable(getInvalidStructureLang()));
                if (MultiBlockFurnaces.GHOST_BLOCKS_DATA.stream().anyMatch(ghostBlocksData -> ghostBlocksData.furnacePos.equals(pos))) {
                    return InteractionResult.CONSUME;
                }
                MultiBlockFurnaces.GhostBlocksData ghostBlocksData = new MultiBlockFurnaces.GhostBlocksData(pos);
                Direction direction = state.getValue(FACING);
                BlockPos midBlock = pos.relative(direction.getOpposite()).above();
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                for (Map.Entry<Vec3i, TagKey<Block>> entry : getRelativePosBlockTags().entrySet()) {
                    mutableBlockPos.set(midBlock.offset(entry.getKey()));
                    BlockState stateRelative = level.getBlockState(mutableBlockPos);
                    Block block = stateRelative.getBlock();
                    if (block.equals(this) || stateRelative.is(entry.getValue()))
                        continue;
                    Optional<HolderSet.Named<Block>> optionalBlocks = BuiltInRegistries.BLOCK.getTag(entry.getValue());
                    List<Block> blocks = optionalBlocks
                            .map(holderSet -> holderSet
                                    .stream()
                                    .filter(blockHolder -> blockHolder.value().isCollisionShapeFullBlock(blockHolder.value().defaultBlockState(), level, pos))
                                    .map(Holder::value)
                                    .toList()
                            )
                            .orElse(new ArrayList<>());
                    if (blocks.isEmpty()) {
                        player.sendSystemMessage(Component.literal("%s has no blocks".formatted(entry.getValue().toString())));
                        return InteractionResult.CONSUME;
                    }
                    BlockState stateNeeded = blocks.get(level.random.nextInt(blocks.size())).defaultBlockState;
                    ghostBlocksData.posAndStates.put(mutableBlockPos.immutable(), stateNeeded);
                }
                MultiBlockFurnaces.GHOST_BLOCKS_DATA.add(ghostBlocksData);
            }
            return InteractionResult.CONSUME;
        }
    }

    protected abstract String getInvalidStructureLang();

    protected abstract void openContainer(Level pLevel, BlockPos pPos, Player pPlayer);

    /**
     * Returns true if the multi-block structure is valid
     */
    public boolean isValidMultiBlock(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Direction direction = state.getValue(FACING);
        BlockPos midBlock = pos.relative(direction.getOpposite()).above();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        boolean hasFoundThisBlock = false;
        for (Map.Entry<Vec3i, TagKey<Block>> entry : getRelativePosBlockTags().entrySet()) {
            mutableBlockPos.set(midBlock.offset(entry.getKey()));
            BlockState stateRelative = level.getBlockState(mutableBlockPos);
            Block block = stateRelative.getBlock();
            if (block.equals(this) && !hasFoundThisBlock) {
                hasFoundThisBlock = true;
                continue;
            }
            if (!stateRelative.is(entry.getValue()))
                return false;
        }
        return true;
    }

    public abstract Map<Vec3i, TagKey<Block>> getRelativePosBlockTags();

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    /**
     * Called by BlockItem after this block has been placed.
     */
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        if (pStack.hasCustomHoverName()) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof AbstractMultiBlockFurnaceBlockEntity) {
                ((AbstractMultiBlockFurnaceBlockEntity)blockentity).setCustomName(pStack.getHoverName());
            }
        }

    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof AbstractMultiBlockFurnaceBlockEntity) {
                if (pLevel instanceof ServerLevel) {
                    Containers.dropContents(pLevel, pPos, (AbstractMultiBlockFurnaceBlockEntity)blockentity);
                    ((AbstractMultiBlockFurnaceBlockEntity)blockentity).getRecipesToAwardAndPopExperience((ServerLevel)pLevel, Vec3.atCenterOf(pPos));
                }

                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    /**
     * @deprecated call via {@link
     * net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#hasAnalogOutputSignal} whenever possible.
     * Implementing/overriding is fine.
     */
    @Deprecated
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    /**
     * @deprecated call via {@link
     * net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getAnalogOutputSignal} whenever possible.
     * Implementing/overriding is fine.
     */
    @Deprecated
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getRenderShape}
     * whenever possible. Implementing/overriding is fine.
     */
    @Deprecated
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#rotate} whenever
     * possible. Implementing/overriding is fine.
     */
    @Deprecated
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#mirror} whenever
     * possible. Implementing/overriding is fine.
     */
    @Deprecated
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    @javax.annotation.Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level pLevel, BlockEntityType<T> pServerType, BlockEntityType<? extends AbstractMultiBlockFurnaceBlockEntity> pClientType) {
        return pLevel.isClientSide ? null : createTickerHelper(pServerType, pClientType, AbstractMultiBlockFurnaceBlockEntity::serverTick);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable(MULTI_BLOCK_TOOLTIP));
    }
}
