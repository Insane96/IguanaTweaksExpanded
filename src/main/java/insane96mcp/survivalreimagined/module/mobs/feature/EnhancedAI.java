package insane96mcp.survivalreimagined.module.mobs.feature;

import insane96mcp.enhancedai.modules.animal.feature.Animals;
import insane96mcp.enhancedai.modules.base.feature.Movement;
import insane96mcp.enhancedai.modules.base.feature.Targeting;
import insane96mcp.enhancedai.modules.creeper.feature.CreeperSwell;
import insane96mcp.enhancedai.modules.skeleton.feature.SkeletonFleeTarget;
import insane96mcp.enhancedai.modules.skeleton.feature.SkeletonShoot;
import insane96mcp.enhancedai.modules.spider.feature.ThrowingWeb;
import insane96mcp.enhancedai.modules.witch.feature.WitchFleeTarget;
import insane96mcp.enhancedai.modules.zombie.feature.DiggerZombie;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.survivalreimagined.base.SRFeature;
import insane96mcp.survivalreimagined.module.Modules;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Label(name = "EnhancedAI", description = "Changes to EnhancedAI config")
@LoadFeature(module = Modules.Ids.MOBS)
public class EnhancedAI extends SRFeature {

    public EnhancedAI(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
    }

    @Override
    public void readConfig(ModConfigEvent event) {
        super.readConfig(event);

        Module.getFeature(CreeperSwell.class).setConfigOption("Cena.Chance", 0.01d);
        Module.getFeature(CreeperSwell.class).setConfigOption("Cena.Particles", false);
        Module.getFeature(CreeperSwell.class).setConfigOption("Cena.Explosion power", 3.0d);
        Module.getFeature(CreeperSwell.class).setConfigOption("Ignore Walls Chance", 0.65d);
        Module.getFeature(CreeperSwell.class).setConfigOption("Walking Fuse Speed Modifier", -0.5d);
        Module.getFeature(CreeperSwell.class).readConfig(event);

        Module.getFeature(SkeletonFleeTarget.class).setConfigOption("Flee speed Multiplier Near", 1.1d);
        Module.getFeature(SkeletonFleeTarget.class).setConfigOption("Flee speed Multiplier Far", 1d);
        Module.getFeature(SkeletonFleeTarget.class).setConfigOption("Flee Distance Near", 6d);
        Module.getFeature(SkeletonFleeTarget.class).setConfigOption("Flee Distance Far", 13d);
        Module.getFeature(SkeletonFleeTarget.class).setConfigOption("Avoid Player chance", 0.25d);
        Module.getFeature(SkeletonFleeTarget.class).readConfig(event);

        //Controlled via MPR
        Module.getFeature(SkeletonShoot.class).setConfigOption("Spammer chance", 0d);
        Module.getFeature(SkeletonShoot.class).readConfig(event);

        Module.getFeature(ThrowingWeb.class).setConfigOption("Slowness.Amplifier", 1);
        Module.getFeature(ThrowingWeb.class).setConfigOption("Slowness.Stacking Amplifier", false);
        Module.getFeature(ThrowingWeb.class).readConfig(event);

        Module.getFeature(WitchFleeTarget.class).setConfigOption("Flee speed Multiplier Near", 1.1d);
        Module.getFeature(WitchFleeTarget.class).setConfigOption("Flee speed Multiplier Far", 1d);
        Module.getFeature(WitchFleeTarget.class).readConfig(event);

        Module.getFeature(Movement.class).setConfigOption("Swim Speed Multiplier", 1d);
        Module.getFeature(Movement.class).readConfig(event);

        Module.getFeature(Targeting.class).setConfigOption("Instant Target", false);
        Module.getFeature(Targeting.class).readConfig(event);

        Module.getFeature(Animals.class).setConfigOption("Movement Speed Multiplier", 1.3d);
        Module.getFeature(Animals.class).setConfigOption("Fight back chance", 0.3d);
        Module.getFeature(Animals.class).readConfig(event);

        Module.getFeature(DiggerZombie.class).setConfigOption("Digger Speed Multiplier", 2d);
        Module.getFeature(DiggerZombie.class).readConfig(event);
    }
}
