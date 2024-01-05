package insane96mcp.iguanatweaksexpanded.module.combat;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.SRDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedDataPack;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;

@Label(name = "Shields+", description = "Changes to Shields+.")
@LoadFeature(module = Modules.Ids.COMBAT)
public class ShieldsPlus extends Feature {
	@Config
	@Label(name = "Shields+ Compat DataPack", description = "Changes the crafting recipes of metal shields to require a Forge and rebalances the shields.")
	public static Boolean shieldsPlusCompatDataPack = true;

	public ShieldsPlus(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedDataPack.INTEGRATED_DATA_PACKS.add(new IntegratedDataPack(PackType.SERVER_DATA, "shields", Component.literal("Survival Reimagined Shields"), () -> super.isEnabled() && !SRDataPacks.disableAllDataPacks && shieldsPlusCompatDataPack));
	}
}