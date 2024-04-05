package insane96mcp.iguanatweaksexpanded.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.widget.WidgetHolder;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.AbstractMultiItemSmeltingRecipe;

public class EmiSoulBlastFurnaceRecipe extends EmiMultiItemSmeltingRecipe {
    public EmiSoulBlastFurnaceRecipe(AbstractMultiItemSmeltingRecipe recipe) {
        super(recipe);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ITEEmiPlugin.SOUL_BLAST_FURNACE_CATEGORY;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(this.getInputs().get(0), 32, 6);
        widgets.addSlot(this.getInputs().get(1), 50, 6);
        widgets.addSlot(this.getInputs().get(2), 68, 6);
        widgets.addSlot(this.getInputs().get(3), 32, 24);
        widgets.addSlot(this.getInputs().get(4), 50, 24);
        widgets.addSlot(this.getInputs().get(5), 68, 24);
        super.addWidgets(widgets);
    }
}
