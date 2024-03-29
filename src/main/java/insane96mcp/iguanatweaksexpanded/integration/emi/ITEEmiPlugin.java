package insane96mcp.iguanatweaksexpanded.integration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.ForgeRecipe;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

@EmiEntrypoint
public class ITEEmiPlugin implements EmiPlugin {
	public static final ResourceLocation FORGE_CATEGORY_ID = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "forge");
	public static final EmiStack FORGE_WORKSTATION = EmiStack.of(Forging.FORGE.item().get());
	public static final EmiRecipeCategory FORGE_RECIPE_CATEGORY = new EmiRecipeCategory(FORGE_CATEGORY_ID, FORGE_WORKSTATION);

	@Override
	public void register(EmiRegistry registry) {
		registry.addCategory(FORGE_RECIPE_CATEGORY);

		registry.addWorkstation(FORGE_RECIPE_CATEGORY, FORGE_WORKSTATION);
		RecipeManager manager = registry.getRecipeManager();
		for (ForgeRecipe forgeRecipe : manager.getAllRecipesFor(Forging.FORGE_RECIPE_TYPE.get())) {
			registry.addRecipe(new EmiForgeRecipe(forgeRecipe));
		}
	}
}
