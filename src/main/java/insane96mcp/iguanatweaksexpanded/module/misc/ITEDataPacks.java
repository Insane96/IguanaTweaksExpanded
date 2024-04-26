package insane96mcp.iguanatweaksexpanded.module.misc;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksreborn.module.misc.DataPacks;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;

@Label(name = "Data Packs", description = "Various data packs that can be enabled/disabled")
@LoadFeature(module = Modules.Ids.MISC)
public class ITEDataPacks extends Feature {

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
    @Label(name = "Plant growth multipliers", description = "If true, a data pack is enabled that changes the growth of plants based off seasons")
    public static Boolean plantGrowthMultipliersDataPack = true;
    @Config
    @Label(name = "Livestock Data", description = "Enables a data pack that slows down growing, breeding, egging etc based off seasons")
    public static Boolean livestockDataPack = true;
    @Config
    @Label(name = "Assets override", description = "Change some vanilla textures such as arrows to flint, spawners to echo and also some lang entries")
    public static Boolean assetsOverride = true;
    @Config
    @Label(name = "Block Data Data Pack")
    public static Boolean blockData = true;

    public ITEDataPacks(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "ite_advancements", Component.literal("IguanaTweaks Expanded Advancements"), () -> this.isEnabled() && !disableAllDataPacks && advancements));
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "item_stats", Component.literal("IguanaTweaks Expanded Item Stats"), () -> this.isEnabled() && !disableAllDataPacks && itemStatsDataPack));
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "better_loot", Component.literal("IguanaTweaks Expanded Better Loot"), () -> this.isEnabled() && !disableAllDataPacks && betterStructureLoot));
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "plant_growth_modifiers", Component.literal("IguanaTweaks Expanded Plant Growth modifiers"), () -> this.isEnabled() && !disableAllDataPacks && plantGrowthMultipliersDataPack));
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "livestock_changes", Component.literal("IguanaTweaks Expanded Livestock Changes"), () -> this.isEnabled() && !disableAllDataPacks && livestockDataPack));
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "block_data", Component.literal("IguanaTweaks Expanded Block Data"), () -> this.isEnabled() && !disableAllDataPacks && blockData));
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "farmers_delight_integration", Component.literal("IguanaTweaks Expanded Farmer's Delight integration"), () -> this.isEnabled() && !disableAllDataPacks && DataPacks.farmersDelight));
        IntegratedPack.addPack(new IntegratedPack(PackType.CLIENT_RESOURCES, "assets_override", Component.literal("IguanaTweaks Expanded Assets Override"), () -> assetsOverride));
    }
}
