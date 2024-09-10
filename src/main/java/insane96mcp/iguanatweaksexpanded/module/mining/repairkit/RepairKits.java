package insane96mcp.iguanatweaksexpanded.module.mining.repairkit;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Repair Kits", description = "Add repair kits, making you able to repair items in the crafting grid")
@LoadFeature(module = Modules.Ids.MINING)
public class RepairKits extends Feature {

	public static final RegistryObject<SimpleCraftingRecipeSerializer<RepairKitRepairRecipe>> RECIPE_SERIALIZER = ITERegistries.RECIPE_SERIALIZERS.register("crafting_special_repairingkit", () -> new SimpleCraftingRecipeSerializer<>(RepairKitRepairRecipe::new));
	public static final RegistryObject<Item> REPAIR_KIT = ITERegistries.ITEMS.register("repair_kit", () -> new RepairKitItem(new Item.Properties().stacksTo(16)));

	@Config(min = 1)
	@Label(name = "Repair Kit Ingot Ratio", description = "How many ingots does a repair kit repair")
	public static Integer repairKitIngotRatio = 2;

	public RepairKits(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}
}