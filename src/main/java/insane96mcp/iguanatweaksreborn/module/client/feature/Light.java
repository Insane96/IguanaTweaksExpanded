package insane96mcp.iguanatweaksreborn.module.client.feature;

import insane96mcp.iguanatweaksreborn.module.Modules;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;

@Label(name = "Light", description = "Changes to light")
@LoadFeature(module = Modules.Ids.CLIENT)
public class Light extends Feature {

    @Config
    @Label(name = "No Night Vision Flashing", description = "If true night vision will no longer flash 10 seconds before expiring, instead will slowly fade out 4 seconds before expiring.")
    public static Boolean noNightVisionFlashing = true;

    public Light(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
    }

    //TODO
    public static boolean shouldDisableNightVisionFlashing() {
        return false;/*isEnabled(Light.class) && noNightVisionFlashing;*/
    }
}
