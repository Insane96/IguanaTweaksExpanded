package insane96mcp.iguanatweaksexpanded.modifier;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksreborn.modifier.Modifiers;
import net.minecraft.resources.ResourceLocation;

public class ITEModifiers {
    public static void init() {
        Modifiers.registerModifier(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "season"), SeasonModifier.class);
    }
}
