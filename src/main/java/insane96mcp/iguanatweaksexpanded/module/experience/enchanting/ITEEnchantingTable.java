package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import insane96mcp.iguanatweaksexpanded.network.message.SyncITEEnchantingTableUnlockedEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ITEEnchantingTable extends BaseEntityBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    protected ITEEnchantingTable(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ITEEnchantingTableBlockEntity(pPos, pState);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof ITEEnchantingTableBlockEntity) {
            player.openMenu((MenuProvider)blockentity);
            SyncITEEnchantingTableUnlockedEnchantments.sync((ServerLevel) level, (ITEEnchantingTableBlockEntity) level.getBlockEntity(pos));
        }
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player player, InteractionHand hand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        else {
            if (player.getItemInHand(hand).is(Items.ENCHANTED_BOOK)) {
                ITEEnchantingTableBlockEntity enchantingTableBE = (ITEEnchantingTableBlockEntity) pLevel.getBlockEntity(pPos);
                CompoundTag compoundtag = player.getItemInHand(hand).getTag();
                if (compoundtag != null) {
                    ListTag list = compoundtag.getList("StoredEnchantments", 10);
                    boolean hasTreasure = false;
                    for (int i = 0; i < list.size(); i++) {
                        Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(EnchantmentHelper.getEnchantmentId(list.getCompound(i)));
                        if (enchantment == null || !enchantment.isTreasureOnly() || enchantment.isCurse())
                            continue;
                        hasTreasure = true;
                        if (enchantingTableBE.knowsEnchantment(enchantment)) {
                            player.sendSystemMessage(Component.translatable("iguanatweaksexpanded.enchanting_table.already_knows").append(Component.translatable(enchantment.getDescriptionId())));
                            continue;
                        }
                        enchantingTableBE.learnEnchantment(enchantment);
                        player.sendSystemMessage(Component.translatable("iguanatweaksexpanded.enchanting_table.learned_enchantment").append(Component.translatable(enchantment.getDescriptionId())));
                    }
                    if (hasTreasure)
                        player.getItemInHand(hand).shrink(1);
                    else
                        player.sendSystemMessage(Component.translatable("iguanatweaksexpanded.enchanting_table.no_valid_enchantments"));
                }
            }
            else {
                this.openContainer(pLevel, pPos, player);
            }
            return InteractionResult.CONSUME;
        }
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof ITEEnchantingTableBlockEntity ITEEnchantingTableBlockEntity) {
                if (pLevel instanceof ServerLevel) {
                    Containers.dropContents(pLevel, pPos, ITEEnchantingTableBlockEntity);
                }

                pLevel.updateNeighbourForOutputSignal(pPos, this);
                //ensorcellerBlockEntity.dropExperience(pLevel);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
     */
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);

        for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
            if (pRandom.nextInt(16) == 0 && EnchantmentTableBlock.isValidBookShelf(pLevel, pPos, blockpos)) {
                pLevel.addParticle(ParticleTypes.ENCHANT, (double)pPos.getX() + 0.5D, (double)pPos.getY() + 2.0D, (double)pPos.getZ() + 0.5D, (double)((float)blockpos.getX() + pRandom.nextFloat()) - 0.5D, (double)((float)blockpos.getY() - pRandom.nextFloat() - 1.0F), (double)((float)blockpos.getZ() + pRandom.nextFloat()) - 0.5D);
            }
        }

    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        /*BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof SREnchantingTableBlockEntity SREnchantingTableBlockEntity) {
            if (!level.isClientSide) {
                ItemStack itemstack = new ItemStack(this.asItem());
                //Clear items before dropping otherwise the item will stay in the dropped ensorceller
                SREnchantingTableBlockEntity.items.clear();
                SREnchantingTableBlockEntity.saveToItem(itemstack);
                if (SREnchantingTableBlockEntity.hasCustomName()) {
                    itemstack.setHoverName(SREnchantingTableBlockEntity.getCustomName());
                }

                ItemEntity itementity = new ItemEntity(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
        }*/
        super.playerWillDestroy(level, pos, state, player);
    }

    /**
     * Called by BlockItem after this block has been placed.
     */
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        if (pStack.hasCustomHoverName()) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof ITEEnchantingTableBlockEntity) {
                ((ITEEnchantingTableBlockEntity)blockentity).setCustomName(pStack.getHoverName());
            }
        }

    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? createTickerHelper(pBlockEntityType, EnchantingFeature.ENCHANTING_TABLE_BLOCK_ENTITY.get(), ITEEnchantingTableBlockEntity::clientTick) : null;
    }
}
