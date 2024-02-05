package insane96mcp.iguanatweaksexpanded.module.misc;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedDataPack;
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
public class SRDataPacks extends Feature {

    @Config
    @Label(name = "Disable ALL data packs", description = "If true, no integrated data pack will be loaded")
    public static Boolean disableAllDataPacks = false;

    @Config
    @Label(name = "Advancements", description = "Enables a Data Pack that overhauls advancements to make them work with IguanaTweaks.")
    public static Boolean advancements = true;
    @Config
    @Label(name = "Item Stats Data Pack", description = "Enables a data pack that changes all the item stats")
    public static Boolean itemStatsDataPack = true;

    public SRDataPacks(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
        IntegratedDataPack.INTEGRATED_DATA_PACKS.add(new IntegratedDataPack(PackType.SERVER_DATA, "ite_advancements", Component.literal("IguanaTweaks Expanded Advancements"), () -> this.isEnabled() && !SRDataPacks.disableAllDataPacks && advancements));
        insane96mcp.iguanatweaksreborn.setup.IntegratedDataPack.INTEGRATED_DATA_PACKS.add(new insane96mcp.iguanatweaksreborn.setup.IntegratedDataPack(PackType.SERVER_DATA, "item_stats", Component.literal("IguanaTweaks Reborn Item Stats"), () -> this.isEnabled() && !DataPacks.disableAllDataPacks && itemStatsDataPack));
    }
}
