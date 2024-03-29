package insane96mcp.iguanatweaksexpanded.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.ForgeRecipe;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.ForgeScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiForgeRecipe implements EmiRecipe {
	private final ResourceLocation id;
	private final EmiIngredient ingredient;
	private final EmiIngredient gear;
	private final EmiStack output;
	private final int smashesRequired;

	public EmiForgeRecipe(ForgeRecipe recipe) {
		this.id = recipe.getId();
		this.ingredient = EmiIngredient.of(recipe.getIngredient(), recipe.getIngredientAmount());
		this.gear = EmiIngredient.of(recipe.getGear());
		this.output = EmiStack.of(recipe.getResult());
		this.smashesRequired = recipe.getSmashesRequired();
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return ITEEmiPlugin.FORGE_RECIPE_CATEGORY;
	}

	@Override
	public @Nullable ResourceLocation getId() {
		return this.id;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return List.of(this.ingredient, this.gear);
	}

	@Override
	public List<EmiStack> getOutputs() {
		return List.of(this.output);
	}

	@Override
	public int getDisplayWidth() {
		return 86;
	}

	@Override
	public int getDisplayHeight() {
		return 58;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addSlot(this.ingredient, 2, 2);
		widgets.addSlot(this.gear, 2, 38);
		widgets.addSlot(this.output, 62, 20).recipeContext(this);
		widgets.addText(Component.literal("" + this.smashesRequired), 32, 11, 0xFFFFFF, true);
		widgets.addTooltipText(List.of(Component.translatable(IguanaTweaksExpanded.MOD_ID + ".smash_required")), 30, 9, 18, 11);
		widgets.addTexture(ForgeScreen.TEXTURE, 0, 0, 86, 58, 53, 14);
	}
}
