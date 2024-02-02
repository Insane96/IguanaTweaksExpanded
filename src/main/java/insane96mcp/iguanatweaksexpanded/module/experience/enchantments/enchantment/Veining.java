package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.IEnchantmentTooltip;
import insane96mcp.iguanatweaksreborn.module.items.itemstats.ItemStats;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.List;

public class Veining extends Enchantment implements IEnchantmentTooltip {
    public Veining() {
        super(Rarity.RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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
        return !(other instanceof DiggingEnchantment) && !(other instanceof Blasting) && !(other instanceof Expanded) && super.checkCompatibility(other);
    }

    public static void tryApply(LivingEntity entity, Level level, BlockPos pos, Direction face, BlockState state) {
        ItemStack heldStack = entity.getMainHandItem();
        if (!heldStack.isCorrectToolForDrops(state))
            return;
        int enchLevel = heldStack.getEnchantmentLevel(NewEnchantmentsFeature.VEINING.get());
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
    public static void applyDestroyAnimation(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)
            return;
        // validate required variables are set
        MultiPlayerGameMode controller = Minecraft.getInstance().gameMode;
        if (controller == null || !controller.isDestroying()) {
            return;
        }
        Level level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;
        if (level == null || player == null || Minecraft.getInstance().getCameraEntity() == null) {
            return;
        }
        // must have the enchantment
        int enchLevel = player.getMainHandItem().getEnchantmentLevel(NewEnchantmentsFeature.VEINING.get());
        if (enchLevel == 0)
            return;
        // must be targeting a block
        HitResult result = Minecraft.getInstance().hitResult;
        if (result == null || result.getType() != HitResult.Type.BLOCK) {
            return;
        }
        // find breaking progress
        BlockHitResult blockTrace = (BlockHitResult)result;
        BlockPos targetPos = blockTrace.getBlockPos();
        BlockState targetState = level.getBlockState(targetPos);
        ItemStack heldStack = player.getMainHandItem();
        if (!heldStack.isCorrectToolForDrops(targetState))
            return;
        BlockDestructionProgress progress = null;
        for (Int2ObjectMap.Entry<BlockDestructionProgress> entry : Minecraft.getInstance().levelRenderer.destroyingBlocks.int2ObjectEntrySet()) {
            if (entry.getValue().getPos().equals(targetPos)) {
                progress = entry.getValue();
                break;
            }
        }
        if (progress == null) {
            return;
        }
        // determine extra blocks to highlight
        List<BlockPos> minedBlocks = getMinedBlocks(heldStack, enchLevel, level, player, targetPos, blockTrace.getDirection());
        if (minedBlocks.isEmpty()) {
            return;
        }

        // set up buffers
        PoseStack matrices = event.getPoseStack();
        matrices.pushPose();
        MultiBufferSource.BufferSource vertices = event.getLevelRenderer().renderBuffers.crumblingBufferSource();
        VertexConsumer vertexBuilder = vertices.getBuffer(ModelBakery.DESTROY_TYPES.get(progress.getProgress()));

        // finally, render the blocks
        Camera renderInfo = Minecraft.getInstance().gameRenderer.getMainCamera();
        double x = renderInfo.getPosition().x;
        double y = renderInfo.getPosition().y;
        double z = renderInfo.getPosition().z;
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        for (BlockPos minedPos : minedBlocks) {
            matrices.pushPose();
            matrices.translate(minedPos.getX() - x, minedPos.getY() - y, minedPos.getZ() - z);
            PoseStack.Pose entry = matrices.last();
            VertexConsumer blockBuilder = new SheetedDecalTextureGenerator(vertexBuilder, entry.pose(), entry.normal(), 1f);
            dispatcher.renderBreakingTexture(level.getBlockState(minedPos), minedPos, level, matrices, blockBuilder, ModelData.EMPTY);
            matrices.popPose();
        }
        // finish rendering
        matrices.popPose();
        vertices.endBatch();
    }

    public static int getAmountMined(int lvl) {
        return (lvl + 1) * (lvl + 1);
    }

    public static List<BlockPos> getMinedBlocks(ItemStack heldStack, int lvl, Level level, LivingEntity entity, BlockPos targetPos, Direction face) {
        List<BlockPos> minedBlocks = new ArrayList<>();

        //Clamp level to 3
        if (lvl > 3)
            lvl = 3;

        int blocksMined = getAmountMined(lvl);
        List<BlockPos> posToCheck = new ArrayList<>();
        posToCheck.add(targetPos);
        List<BlockPos> explored = new ArrayList<>();
        int toMine = 0;
        while (!posToCheck.isEmpty() && toMine < blocksMined) {
            List<BlockPos> posToCheckTmp = new ArrayList<>();
            for (BlockPos pos : posToCheck) {
                for (Direction direction : Direction.values()) {
                    BlockPos relativePos = pos.relative(direction);
                    if (explored.contains(relativePos))
                        continue;
                    if (addIfCanBeMined(heldStack, minedBlocks, level, targetPos, relativePos)) {
                        posToCheckTmp.add(relativePos);
                        if (++toMine >= blocksMined) {
                            posToCheckTmp.clear();
                            break;
                        }
                    }
                    explored.add(relativePos);
                }
                if (toMine >= blocksMined) {
                    posToCheckTmp.clear();
                    break;
                }
            }
            posToCheck.clear();
            posToCheck.addAll(posToCheckTmp);
        }

        return minedBlocks;
    }

    private static boolean addIfCanBeMined(ItemStack stack, List<BlockPos> blockPos, Level level, BlockPos targetPos, BlockPos minedPos) {
        BlockState targetState = level.getBlockState(targetPos);
        BlockState minedState = level.getBlockState(minedPos);
        if (targetState.is(minedState.getBlock())) {
            blockPos.add(minedPos);
            return true;
        }
        return false;
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", getAmountMined(lvl)).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
