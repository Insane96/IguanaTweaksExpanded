package insane96mcp.survivalreimagined.module.client.feature;

import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.survivalreimagined.module.ClientModules;

@Label(name = "Sound", description = "Changes to sounds")
@LoadFeature(module = ClientModules.Ids.CLIENT)
public class Sound extends Feature {

    @Config
    @Label(name = "Fix shield blocking sound", description = "If true the entity hit will no longer play the hurt sound if blocking with a shield.")
    public static Boolean fixShieldBlockingSound = true;

    public Sound(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
    }

    public static boolean shouldPreventShieldSoundPlay() {
        return isEnabled(Sound.class) && fixShieldBlockingSound;
    }
}
