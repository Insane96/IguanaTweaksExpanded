package insane96mcp.iguanatweaksexpanded.module.items.toolbelt;

import dev.gigaherz.toolbelt.ToolBelt;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

@Label(name = "Tool Belt Integration")
@LoadFeature(module = Modules.Ids.ITEMS)
public class ToolBeltIntegration extends Feature {

	@Config
	@Label(name = "Bigger ToolBelt", description = "Enables a data pack that changes the crafting of the Tool Belt to give more slots (2 -> 4). Also makes the cost to upgrade start from 4 instead of 2")
	public static Boolean biggerToolbelt = true;

	public ToolBeltIntegration(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "toolbelt_integration", Component.literal("IguanaTweaks Expanded Tool Belt"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && biggerToolbelt));
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled() && ModList.get().isLoaded("toolbelt");
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onAnvilUpdate(AnvilUpdateEvent event) {
		if (!this.isEnabled()
				|| !biggerToolbelt
				|| !event.getLeft().is(ToolBelt.BELT.get())
				|| !event.getRight().is(ToolBelt.POUCH.get()))
			return;

		int slots = ToolBeltItem.getSlotsCount(event.getLeft()) - 4;
		if (slots < 0)
			event.setCost(0);
		else
			event.setCost(ToolBeltItem.xpCost[slots]);
	}
}