package insane96mcp.iguanatweaksexpanded.module.hungerhealth.fooddrinks;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "New Foods & Drinks", description = "Adds new foods and drinks.")
@LoadFeature(module = Modules.Ids.HUNGER_HEALTH)
public class FoodDrinks extends Feature {
	public static final RegistryObject<Item> BROWN_MUSHROOM_STEW = ITERegistries.ITEMS.register("brown_mushroom_stew", () -> new BowlFoodItem(new Item.Properties()
			.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6F).build())
	));
	public static final RegistryObject<Item> RED_MUSHROOM_STEW = ITERegistries.ITEMS.register("red_mushroom_stew", () -> new BowlFoodItem(new Item.Properties()
			.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6F).build())
	));

	public static final RegistryObject<Item> OVER_EASY_EGG = ITERegistries.ITEMS.register("over_easy_egg", () -> new Item(new Item.Properties()
			.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6F).build())
	));

	public static final RegistryObject<Item> PUMPKIN_PULP = ITERegistries.ITEMS.register("pumpkin_pulp", () -> new Item(new Item.Properties()
			.food(new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build())
	));


	public FoodDrinks(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}
}
