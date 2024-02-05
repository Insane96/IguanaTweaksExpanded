package insane96mcp.iguanatweaksexpanded.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.pipeline.VertexConsumerWrapper;
import org.jetbrains.annotations.NotNull;

public class ClientUtils {
    private static final RandomSource RANDOM = RandomSource.create();
    public static void renderGhostBlock(PoseStack poseStack, BlockState state, BlockPos pos) {
        BakedModel model = mc().getBlockRenderer().getBlockModel(state);
        Vec3 offset = Vec3.atLowerCornerOf(pos).subtract(mc().gameRenderer.getMainCamera().getPosition());
        ModelData modelData = model.getModelData(mc().level, pos, state, mc().level.getModelDataManager().getAt(pos));
        poseStack.pushPose();
        poseStack.translate(offset.x, offset.y, offset.z);
        for (RenderType type : model.getRenderTypes(state, RANDOM, modelData))
        {
            mc().getBlockRenderer().renderBatched(
                    state,
                    pos,
                    mc().level,
                    poseStack,
                    new GhostVertexConsumer(mc().renderBuffers().bufferSource().getBuffer(ForgeRenderTypes.TRANSLUCENT_ON_PARTICLES_TARGET.get()), 0xAA),
                    false,
                    RANDOM,
                    modelData,
                    type);
        }
        poseStack.popPose();
        RenderSystem.disableDepthTest();
        mc().renderBuffers().bufferSource().endBatch();
    }

    @NotNull
    private static Minecraft mc() {
        return Minecraft.getInstance();
    }

    public static final class GhostVertexConsumer extends VertexConsumerWrapper
    {
        private final int alpha;

        public GhostVertexConsumer(VertexConsumer wrapped, int alpha)
        {
            super(wrapped);
            this.alpha = alpha;
        }

        @Override
        public VertexConsumer color(int red, int green, int blue, int alpha)
        {
            return parent.color(red, green, blue, (alpha * this.alpha) / 0xFF);
        }
    }
}
