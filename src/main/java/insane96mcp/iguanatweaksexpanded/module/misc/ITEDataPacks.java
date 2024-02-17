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
    @Label(name = "Item Stats Data Pack", description = "Enables a data pack that changes all the item stats")
    public static Boolean itemStatsDataPack = true;
    @Config
    @Label(name = "Better Structure Loot", description = "If true a data pack will be enabled that overhauls structure loot. This overrides some loot tables from ITR")
    public static Boolean betterStructureLoot = true;
    @Config
    @Label(name = "Plant growth multipliers data pack", description = "If true, a data pack is enabled that changes the growth of plants based off seasons")
    public static Boolean plantGrowthMultipliersDataPack = true;

    public ITEDataPacks(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "ite_advancements", Component.literal("IguanaTweaks Expanded Advancements"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && advancements));
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "item_stats", Component.literal("IguanaTweaks Expanded Item Stats"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && itemStatsDataPack));
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "better_loot", Component.literal("IguanaTweaks Expanded Better Loot"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && betterStructureLoot));
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "plant_growth_modifiers", Component.literal("IguanaTweaks Expanded Plant Growth modifiers"), () -> super.isEnabled() && !DataPacks.disableAllDataPacks && plantGrowthMultipliersDataPack));
    }
}
