package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.items.itemstats.ItemStats;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.enchantment.DiggingEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.List;

public class Expanded extends Enchantment {
    static EnchantmentCategory PICKAXE_AND_SHOVELS = EnchantmentCategory.create("pickaxe", item -> item instanceof PickaxeItem || item instanceof ShovelItem);
    public Expanded() {
        super(Rarity.RARE, PICKAXE_AND_SHOVELS, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int level) {
        return 22 * level;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 22;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof DiggingEnchantment) && !(other instanceof Blasting) && super.checkCompatibility(other);
    }

    public static void tryApply(LivingEntity entity, Level level, BlockPos pos, Direction face, BlockState state) {
        ItemStack heldStack = entity.getMainHandItem();
        if (!heldStack.isCorrectToolForDrops(state))
            return;
        int enchLevel = heldStack.getEnchantmentLevel(NewEnchantmentsFeature.EXPANDED.get());
        if (enchLevel == 0)
            return;
        List<BlockPos> minedBlocks = getMinedBlocks(heldStack, enchLevel, level, entity, pos, face);
        for (BlockPos minedBlock : minedBlocks) {
            if (level instanceof ServerLevel serverLevel && (entity instanceof ServerPlayer player && !player.getAbilities().flying)) {
                BlockState minedBlockState = level.getBlockState(minedBlock);
                BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(minedBlock) : null;
                boolean blockRemoved = removeBlock(serverLevel, minedBlock, player, true);
                if (blockRemoved) {
                    serverLevel.destroyBlock(minedBlock, false, entity);
                    minedBlockState.getBlock().playerDestroy(serverLevel, player, minedBlock, minedBlockState, blockEntity, heldStack);
                    int exp;
                    if (!ForgeHooks.isCorrectToolForDrops(state, player)) // Handle empty block or player unable to break block scenario
                        exp = 0;
                    else {
                        int fortuneLevel = player.getMainHandItem().getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
                        int silkTouchLevel = player.getMainHandItem().getEnchantmentLevel(Enchantments.SILK_TOUCH);
                        exp = state.getExpDrop(level, level.random, pos, fortuneLevel, silkTouchLevel);
                    }
                    minedBlockState.getBlock().popExperience(serverLevel, minedBlock, exp);
                    level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, minedBlock, Block.getId(minedBlockState));
                }
                heldStack.hurtAndBreak(1, entity, livingEntity -> livingEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            }
            if (ItemStats.isBroken(heldStack) || heldStack.isEmpty())
                break;
        }
    }

    private static boolean removeBlock(Level level, BlockPos pos, ServerPlayer player, boolean canHarvest) {
        BlockState state = level.getBlockState(pos);
        boolean removed = state.onDestroyedByPlayer(level, pos, player, canHarvest, level.getFluidState(pos));
        if (removed)
            state.getBlock().destroy(level, pos, state);
        return removed;
    }

    @OnlyIn(Dist.CLIENT)
    public static void applyOutlineAndDestroyAnimation(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)
            return;

        // validate required variables are set
        MultiPlayerGameMode controller = Minecraft.getInstance().gameMode;
        if (controller == null)
            return;
        Level level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;
        if (level == null || player == null || Minecraft.getInstance().getCameraEntity() == null) {
            return;
        }
        // must have the enchantment
        int enchLevel = player.getMainHandItem().getEnchantmentLevel(NewEnchantmentsFeature.EXPANDED.get());
        if (enchLevel == 0)
            return;
        // must be targeting a block
        HitResult result = Minecraft.getInstance().hitResult;
        if (result == null || result.getType() != HitResult.Type.BLOCK)
            return;
        // find breaking progress
        BlockHitResult blockTrace = (BlockHitResult)result;
        BlockPos targetPos = blockTrace.getBlockPos();
        BlockState targetState = level.getBlockState(targetPos);
        ItemStack heldStack = player.getMainHandItem();
        if (!heldStack.isCorrectToolForDrops(targetState))
            return;


        // determine extra blocks to highlight
        List<BlockPos> minedBlocks = getMinedBlocks(heldStack, enchLevel, level, player, targetPos, blockTrace.getDirection());
        if (minedBlocks.isEmpty())
            return;

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        renderMineProgress(event.getPoseStack(), event.getLevelRenderer().renderBuffers.crumblingBufferSource(), targetPos, level, camera.getPosition().x, camera.getPosition().y, camera.getPosition().z, minedBlocks);
        renderBlockHighlight(event.getPoseStack(), level, camera.getEntity(), camera.getPosition().x, camera.getPosition().y, camera.getPosition().z, minedBlocks);
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderMineProgress(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, BlockPos targetPos, Level level, double xOffset, double yOffset, double zOffset, List<BlockPos> minedBlocks) {
        BlockDestructionProgress progress = null;
        for (Int2ObjectMap.Entry<BlockDestructionProgress> entry : Minecraft.getInstance().levelRenderer.destroyingBlocks.int2ObjectEntrySet()) {
            if (entry.getValue().getPos().equals(targetPos)) {
                progress = entry.getValue();
                break;
            }
        }
        if (progress == null)
            return;

        poseStack.pushPose();
        VertexConsumer vertexBuilder = bufferSource.getBuffer(ModelBakery.DESTROY_TYPES.get(progress.getProgress()));

        // finally, render the blocks
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        for (BlockPos minedPos : minedBlocks) {
            if (minedPos.equals(targetPos))
                continue;
            poseStack.pushPose();
            poseStack.translate(minedPos.getX() - xOffset, minedPos.getY() - yOffset, minedPos.getZ() - zOffset);
            PoseStack.Pose entry = poseStack.last();
            VertexConsumer blockBuilder = new SheetedDecalTextureGenerator(vertexBuilder, entry.pose(), entry.normal(), 1f);
            dispatcher.renderBreakingTexture(level.getBlockState(minedPos), minedPos, level, poseStack, blockBuilder, ModelData.EMPTY);
            poseStack.popPose();
        }
        // finish rendering
        poseStack.popPose();
        bufferSource.endBatch();
    }

    public static void renderBlockHighlight(PoseStack poseStack, Level level, Entity entity, double xOffset, double yOffset, double zOffset, List<BlockPos> minedBlocks) {
        VertexConsumer vertexConsumer = Minecraft.getInstance().levelRenderer.renderBuffers.bufferSource().getBuffer(RenderType.lines());
        for (BlockPos minedPos : minedBlocks) {
            poseStack.pushPose();
            BlockState state = level.getBlockState(minedPos);
            LevelRenderer.renderShape(poseStack, vertexConsumer, state.getShape(level, minedPos, CollisionContext.of(entity)), (double)minedPos.getX() - xOffset, (double)minedPos.getY() - yOffset, (double)minedPos.getZ() - zOffset, 0.0F, 0.0F, 0.0F, 0.4F);
            poseStack.popPose();
        }
    }

    /**
     *
     * @param forward If true, its relative to direction, else opposite
     */
    public static BlockPos getRelative(BlockPos targetPos, boolean playerRelative, boolean forward, Direction direction) {
        if (!playerRelative) {
            if (forward)
                return targetPos.above();
            else
                return targetPos.below();
        }
        else {
            if (forward)
                return targetPos.relative(direction);
            else
                return targetPos.relative(direction.getOpposite());
        }
    }

    public static final int[] MAX_BLOCKS_MINED = new int[] { 0, 3, 6, 9 };

    public static List<BlockPos> getMinedBlocks(ItemStack heldStack, int lvl, Level level, LivingEntity entity, BlockPos targetPos, Direction face) {
        List<BlockPos> minedBlocks = new ArrayList<>();
        boolean playerRelative = false;
        if (face == Direction.UP || face == Direction.DOWN) {
            face = entity.getDirection();
            playerRelative = true;
        }

        //Clamp level to 3
        if (lvl > 3)
            lvl = 3;

        if (lvl >= 1) {
            addIfCanBeMined(heldStack, minedBlocks, level, targetPos, getRelative(targetPos, playerRelative, true, face));
            addIfCanBeMined(heldStack, minedBlocks, level, targetPos, getRelative(targetPos, playerRelative, false, face));
        }
        if (lvl >= 2) {
            for (int i = lvl - 1; i > 0; i--) {
                addIfCanBeMined(heldStack, minedBlocks, level, targetPos, targetPos.relative(face.getClockWise(), i));
                addIfCanBeMined(heldStack, minedBlocks, level, targetPos, targetPos.relative(face.getCounterClockWise(), i));
                addIfCanBeMined(heldStack, minedBlocks, level, targetPos, getRelative(targetPos.relative(face.getClockWise(), i), playerRelative, true, face));
                addIfCanBeMined(heldStack, minedBlocks, level, targetPos, getRelative(targetPos.relative(face.getCounterClockWise(), i), playerRelative, true, face));
                addIfCanBeMined(heldStack, minedBlocks, level, targetPos, getRelative(targetPos.relative(face.getClockWise(), i), playerRelative, false, face));
                addIfCanBeMined(heldStack, minedBlocks, level, targetPos, getRelative(targetPos.relative(face.getCounterClockWise(), i), playerRelative, false, face));
            }
        }

        return minedBlocks;
    }

    private static boolean addIfCanBeMined(ItemStack stack, List<BlockPos> blockPos, Level level, BlockPos targetPos, BlockPos minedPos) {
        BlockState targetState = level.getBlockState(targetPos);
        BlockState minedState = level.getBlockState(minedPos);
        if (minedState.getDestroySpeed(level, minedPos) > 0 && stack.isCorrectToolForDrops(minedState) && targetState.getDestroySpeed(level, targetPos) >= minedState.getDestroySpeed(level, minedPos) - 0.5d) {
            blockPos.add(minedPos);
            return true;
        }
        return false;
    }
}
