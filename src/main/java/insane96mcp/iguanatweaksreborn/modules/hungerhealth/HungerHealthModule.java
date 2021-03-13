package insane96mcp.iguanatweaksreborn.modules.hungerhealth;

import insane96mcp.iguanatweaksreborn.base.ITModule;
import insane96mcp.iguanatweaksreborn.base.Label;
import insane96mcp.iguanatweaksreborn.modules.hungerhealth.feature.ExaustionIncreaseFeature;
import insane96mcp.iguanatweaksreborn.modules.hungerhealth.feature.FoodFeature;
import insane96mcp.iguanatweaksreborn.setup.Config;

@Label(name = "Hunger")
public class HungerHealthModule extends ITModule {

    public FoodFeature foodFeature;
    public ExaustionIncreaseFeature exaustionIncreaseFeature;

    public HungerHealthModule() {
        super();
        pushConfig();
        foodFeature = new FoodFeature(this);
        exaustionIncreaseFeature = new ExaustionIncreaseFeature(this);
        Config.builder.pop();
    }

    @Override
    public void loadConfig() {
        super.loadConfig();
        foodFeature.loadConfig();
        exaustionIncreaseFeature.loadConfig();
    }

}
