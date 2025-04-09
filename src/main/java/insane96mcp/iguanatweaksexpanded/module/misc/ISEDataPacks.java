package insane96mcp.iguanatweaksexpanded.module.misc;

import insane96mcp.iguanatweaksexpanded.InsaneSurvivalExtra;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksreborn.module.misc.DataPacks;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import net.minecraftforge.fml.ModList;

@Label(name = "Data Packs", description = "Various data packs that can be enabled/disabled")
@LoadFeature(module = Modules.Ids.MISC)
public class ISEDataPacks extends Feature {

    @Config
    @Label(name = "Disable ALL data packs", description = "If true, no integrated data pack will be loaded")
    public static Boolean disableAllDataPacks = false;

    @Config
    @Label(name = "Advancements", description = "Enables a Data Pack that overhauls advancements to make them work with IguanaTweaks.")
    public static Boolean advancements = true;
    @Config
    @Label(name = "Item Stats", description = "Enables a data pack that changes all the item stats")
    public static Boolean itemStatsDataPack = true;
    @Config
    @Label(name = "Better Structure Loot", description = "If true a data pack will be enabled that overhauls structure loot. This overrides some loot tables from ITR")
    public static Boolean betterStructureLoot = true;
    @Config
    @Label(name = "Block Data Data Pack")
    public static Boolean blockData = true;
    @Config
    @Label(name = "Tinkers Construct Integration")
    public static Boolean tconstructIntegration = true;

    public ISEDataPacks(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
        InsaneSurvivalExtra.addServerPack("advancements", "IguanaTweaks Expanded Advancements", () -> this.isEnabled() && !disableAllDataPacks && advancements);
        InsaneSurvivalExtra.addServerPack("item_stats", "IguanaTweaks Expanded Item Stats", () -> this.isEnabled() && !disableAllDataPacks && itemStatsDataPack);
        InsaneSurvivalExtra.addServerPack("better_loot", "IguanaTweaks Expanded Better Loot", () -> this.isEnabled() && !disableAllDataPacks && betterStructureLoot);
        InsaneSurvivalExtra.addServerPack("block_data", "IguanaTweaks Expanded Block Data", () -> this.isEnabled() && !disableAllDataPacks && blockData);
        InsaneSurvivalExtra.addServerPack("supplementaries_integration", "IguanaTweaks Expanded Supplementaries Integration", () -> this.isEnabled() && !DataPacks.disableAllDataPacks && DataPacks.supplementaries && ModList.get().isLoaded("supplementaries"));
        InsaneSurvivalExtra.addServerPack("farmers_delight_integration", "IguanaTweaks Expanded Farmer's Delight integration", () -> this.isEnabled() && !disableAllDataPacks && ModList.get().isLoaded("farmersdelight") && DataPacks.farmersDelight);
        InsaneSurvivalExtra.addServerPack("tconstruct_integration", "IguanaTweaks Expanded Tinkers' Construct integration", () -> this.isEnabled() && !disableAllDataPacks && ModList.get().isLoaded("tconstruct") && tconstructIntegration);
    }
}
