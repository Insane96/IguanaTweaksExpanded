package insane96mcp.iguanatweaksexpanded.module.combat.fletching.client;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Arrow;

public class ITEArrowRenderer extends ArrowRenderer<Arrow> {
    public static final ResourceLocation QUARTZ_ARROW_LOCATION = new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "textures/entity/projectiles/quartz_arrow.png");
    public static final ResourceLocation DIAMOND_ARROW_LOCATION = new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "textures/entity/projectiles/diamond_arrow.png");
    public static final ResourceLocation EXPLOSIVE_ARROW_LOCATION = new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "textures/entity/projectiles/explosive_arrow.png");
    public static final ResourceLocation TORCH_ARROW_LOCATION = new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "textures/entity/projectiles/torch_arrow.png");
    public static final ResourceLocation ICE_ARROW_LOCATION = new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "textures/entity/projectiles/ice_arrow.png");
    public ITEArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Arrow pEntity) {
        if (pEntity.getType().equals(Fletching.QUARTZ_ARROW.get()))
            return QUARTZ_ARROW_LOCATION;
        else if (pEntity.getType().equals(Fletching.DIAMOND_ARROW.get()))
            return DIAMOND_ARROW_LOCATION;
        else if (pEntity.getType().equals(Fletching.EXPLOSIVE_ARROW.get()))
            return EXPLOSIVE_ARROW_LOCATION;
        else if (pEntity.getType().equals(Fletching.TORCH_ARROW.get()))
            return TORCH_ARROW_LOCATION;
        else if (pEntity.getType().equals(Fletching.ICE_ARROW.get()))
            return ICE_ARROW_LOCATION;
        return null;
    }
}
