package insane96mcp.iguanatweaksexpanded.module.farming.plantsgrowth;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.farming.plantsgrowth.modifier.SeasonModifier;
import insane96mcp.iguanatweaksreborn.module.farming.plantsgrowth.PlantGrowthModifiers;
import net.minecraft.resources.ResourceLocation;

public class ITEPlantGrowthModifiers {
    public static void init() {
        PlantGrowthModifiers.registerModifier(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "season"), SeasonModifier.class);
    }
}
