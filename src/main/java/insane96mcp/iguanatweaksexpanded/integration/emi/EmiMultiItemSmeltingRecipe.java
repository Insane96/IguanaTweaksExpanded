package insane96mcp.iguanatweaksexpanded.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.AbstractMultiItemSmeltingRecipe;
import insane96mcp.insanelib.InsaneLib;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class EmiMultiItemSmeltingRecipe implements EmiRecipe {
	private final ResourceLocation id;
	private final NonNullList<EmiIngredient> ingredients;
	final float experience;
	final float outputIncrease;
	private final EmiStack output;
	protected final int cookingTime;
	@Nullable
	final AbstractMultiItemSmeltingRecipe.Recycle recycle;

	public EmiMultiItemSmeltingRecipe(AbstractMultiItemSmeltingRecipe recipe) {
		this.id = recipe.getId();
		this.ingredients = NonNullList.withSize(6, EmiIngredient.of(Ingredient.EMPTY));
		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			this.ingredients.set(i, EmiIngredient.of(recipe.getIngredients().get(i)));
		}
		this.experience = recipe.getExperience();
		this.outputIncrease = recipe.getOutputIncrease();
		this.recycle = recipe.getRecycle();
		int amount = (int) (this.outputIncrease + 1);
		if (this.recycle != null)
			amount = this.recycle.amountAtFullDurability();
		this.output = EmiStack.of(recipe.getResult(), (long) (amount));
		this.cookingTime = recipe.getCookingTime();
	}

	@Override
	public abstract EmiRecipeCategory getCategory();

	@Override
	public @Nullable ResourceLocation getId() {
		return this.id;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return this.ingredients;
	}

	@Override
	public List<EmiStack> getOutputs() {
		return List.of(this.output);
	}

	@Override
	public int getDisplayWidth() {
		return 153;
	}

	@Override
	public int getDisplayHeight() {
		return 45;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addTexture(EmiTexture.EMPTY_FLAME, 3, 13);
		widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 3, 13, 4000, false, true, true);
		widgets.addFillingArrow(94, 16, this.cookingTime * 50).tooltip(
				(mx, my) -> List.of(ClientTooltipComponent.create(Component.translatable("emi.cooking.time", this.cookingTime / 20f).getVisualOrderText())));
		widgets.addText(Component.translatable("emi.cooking.experience", this.experience), 92, 5, -1, true);
		SlotWidget outputSlot = widgets.addSlot(this.output, 125, 12).large(true).recipeContext(this);
		if (this.recycle != null)
			outputSlot.appendTooltip(Component.translatable("emi.tooltip.iguanatweaksexpanded.recycle", InsaneLib.ONE_DECIMAL_FORMATTER.format(this.recycle.ratio() * 100f)).withStyle(ChatFormatting.DARK_GREEN));
		/*widgets.addSlot(this.ingredient, 2, 2);
		widgets.addSlot(this.gear, 2, 38);
		widgets.addText(Component.literal("" + this.smashesRequired), 32, 11, 0xFFFFFF, true);
		widgets.addTooltipText(List.of(Component.translatable(IguanaTweaksExpanded.MOD_ID + ".smashes_required")), 30, 9, 18, 11);
		widgets.addTexture(ForgeScreen.TEXTURE, 0, 0, 86, 58, 53, 14);*/
	}
}
