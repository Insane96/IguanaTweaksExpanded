package insane96mcp.iguanatweaksexpanded.module.items.altimeter;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Altimeter", description = "Check your altitude")
@LoadFeature(module = Modules.Ids.ITEMS, canBeDisabled = false)
public class Altimeter extends Feature {
	public static final RegistryObject<Item> ITEM = ISERegistries.ITEMS.register("altimeter", () -> new AltimeterItem(new Item.Properties()));

	@Config
	@Label(name = "Show approx altitude in tooltip")
	public static Boolean tooltip = true;

	public Altimeter(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent event) {
		if (!isEnabled(Altimeter.class)
				|| !tooltip
				|| !event.getItemStack().is(ITEM.get())
				|| event.getEntity() == null)
			return;

		event.getToolTip().add(Component.literal("%d".formatted(event.getEntity().getBlockY())).withStyle(ChatFormatting.GRAY));
	}
}