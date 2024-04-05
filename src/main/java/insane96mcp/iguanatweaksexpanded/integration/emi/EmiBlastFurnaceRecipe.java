package insane96mcp.iguanatweaksexpanded.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.widget.WidgetHolder;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.AbstractMultiItemSmeltingRecipe;

public class EmiBlastFurnaceRecipe extends EmiMultiItemSmeltingRecipe {
    public EmiBlastFurnaceRecipe(AbstractMultiItemSmeltingRecipe recipe) {
        super(recipe);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ITEEmiPlugin.BLAST_FURNACE_CATEGORY;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(this.getInputs().get(0), 32, 6);
        widgets.addSlot(this.getInputs().get(1), 50, 6);
        widgets.addSlot(this.getInputs().get(2), 42, 24);
        widgets.addSlot(this.getInputs().get(3), 60, 24);
        super.addWidgets(widgets);
    }
}
