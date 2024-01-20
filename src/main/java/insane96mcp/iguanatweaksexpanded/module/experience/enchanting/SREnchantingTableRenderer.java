package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SREnchantingTableRenderer implements BlockEntityRenderer<SREnchantingTableBlockEntity> {
    /** The texture for the book above the enchantment table. */
    public static final Material BOOK_LOCATION = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/enchanting_table_book"));
    private final BookModel bookModel;
    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    public SREnchantingTableRenderer(BlockEntityRendererProvider.Context context) {
        this.bookModel = new BookModel(context.bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void render(SREnchantingTableBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        this.renderHoveringItem(blockEntity, ((Container) blockEntity).getItem(SREnchantingTableMenu.ITEM_SLOT), partialTicks, poseStack, bufferSource, packedLight);
        this.renderBook(blockEntity, ((Container) blockEntity).getItem(SREnchantingTableMenu.ITEM_SLOT), partialTicks, poseStack, bufferSource, packedLight, packedOverlay);
    }

    private void renderHoveringItem(SREnchantingTableBlockEntity blockEntity, ItemStack itemToEnchant, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float time = (float)blockEntity.time + partialTick;
        float open = 1 - Mth.lerp(partialTick, blockEntity.oOpen, blockEntity.open);
        poseStack.translate(0.5F, open * 0.2f + 1.1f, 0.5F);
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(itemToEnchant, blockEntity.getLevel(), null, 0);
        //float hoverOffset = Mth.sin(((float)blockEntity.getLevel().dayTime() + partialTick) / 10.0F) * 0.1F + 0.1F;
        float modelYScale = model.getTransforms().getTransform(ItemDisplayContext.FIXED).scale.y();
        poseStack.translate(0.0, 0.1F + Mth.sin(time * 0.1F) * 0.05F + 0.2F * modelYScale, 0.0);
        float f1 = blockEntity.rot - blockEntity.oRot;
        while (f1 >= (float)Math.PI)
            f1 -= ((float) Math.PI * 2F);
        while (f1 < -(float)Math.PI)
            f1 += ((float)Math.PI * 2F);
        //f1 +=
        poseStack.mulPose(Axis.YP.rotation(-(blockEntity.oRot + f1 * partialTick) + (float) (Math.PI / 2f)));
        Minecraft.getInstance().getItemRenderer().render(itemToEnchant, ItemDisplayContext.GROUND, false, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
    }

    private void renderBook(SREnchantingTableBlockEntity blockEntity, ItemStack itemToEnchant, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.75F, 0.5F);
        float f = (float)blockEntity.time + partialTick;
        poseStack.translate(0.0F, 0.1F + Mth.sin(f * 0.1F) * 0.01F, 0.0F);

        float f1;
        for(f1 = blockEntity.rot - blockEntity.oRot; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F)) {
        }

        while(f1 < -(float)Math.PI) {
            f1 += ((float)Math.PI * 2F);
        }

        float f2 = blockEntity.oRot + f1 * partialTick;
        poseStack.mulPose(Axis.YP.rotation(-f2));
        poseStack.mulPose(Axis.ZP.rotationDegrees(80.0F));
        float f3 = Mth.lerp(partialTick, blockEntity.oFlip, blockEntity.flip);
        float f4 = Mth.frac(f3 + 0.25F) * 1.6F - 0.3F;
        float f5 = Mth.frac(f3 + 0.75F) * 1.6F - 0.3F;
        float f6 = Mth.lerp(partialTick, blockEntity.oOpen, blockEntity.open);
        this.bookModel.setupAnim(f, Mth.clamp(f4, 0.0F, 1.0F), Mth.clamp(f5, 0.0F, 1.0F), f6);
        VertexConsumer vertexconsumer = BOOK_LOCATION.buffer(buffer, RenderType::entitySolid);
        this.bookModel.render(poseStack, vertexconsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
