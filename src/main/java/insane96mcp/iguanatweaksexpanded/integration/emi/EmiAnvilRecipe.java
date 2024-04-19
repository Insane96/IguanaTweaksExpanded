package insane96mcp.iguanatweaksexpanded.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiAnvilRecipe implements EmiRecipe {
	private final ResourceLocation id;
	private final EmiIngredient item;
	private final EmiIngredient resource;
	private final EmiStack output;

	public EmiAnvilRecipe(ResourceLocation id, Item item, Item resource, ItemStack output) {
		this.id = id;
		this.item = EmiIngredient.of(Ingredient.of(item));
		this.resource = EmiIngredient.of(Ingredient.of(resource));
		this.output = EmiStack.of(output);
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return VanillaEmiRecipeCategories.ANVIL_REPAIRING;
	}

	@Override
	public @Nullable ResourceLocation getId() {
		return this.id;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return List.of(this.item, this.resource);
	}

	@Override
	public List<EmiStack> getOutputs() {
		return List.of(this.output);
	}

	@Override
	public boolean supportsRecipeTree() {
		return false;
	}

	@Override
	public int getDisplayWidth() {
		return 125;
	}

	@Override
	public int getDisplayHeight() {
		return 18;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addTexture(EmiTexture.PLUS, 27, 3);
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 75, 1);
		widgets.addSlot(this.item,0, 0);
		widgets.addSlot(this.resource, 49, 0);
		widgets.addSlot(this.output, 107, 0).recipeContext(this);
	}
}
