package insane96mcp.iguanatweaksexpanded.module.hungerhealth.fooddrinks;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;

@Label(name = "New Foods & Drinks", description = "Adds new foods and drinks.")
@LoadFeature(module = Modules.Ids.HUNGER_HEALTH)
public class FoodDrinks extends Feature {

	public FoodDrinks(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}
}
