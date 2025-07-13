package insane96mcp.iguanatweaksexpanded.module.combat;

import insane96mcp.iguanatweaksexpanded.InsaneSE;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ISEDataPacks;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import net.minecraftforge.fml.ModList;

@Label(name = "Shields+", description = "Changes to Shields+.")
@LoadFeature(module = Modules.Ids.COMBAT)
public class ShieldsPlus extends Feature {
	@Config
	@Label(name = "Shields+ Compat DataPack", description = "Changes the crafting recipes of metal shields to require a Forge and rebalances the shields.")
	public static Boolean shieldsPlusCompatDataPack = true;

	public ShieldsPlus(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		InsaneSE.addServerPack("shields", "IguanaTweaks Expanded Shields", () -> super.isEnabled() && !ISEDataPacks.disableAllDataPacks && shieldsPlusCompatDataPack && ModList.get().isLoaded("shieldsplus"));
	}
}