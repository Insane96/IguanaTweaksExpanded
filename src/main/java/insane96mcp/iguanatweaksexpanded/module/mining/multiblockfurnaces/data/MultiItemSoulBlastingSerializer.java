package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.data;

import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.MultiItemSoulBlastingRecipe;
import insane96mcp.iguanatweaksexpanded.setup.client.ITEBookCategory;

public class MultiItemSoulBlastingSerializer extends AbstractMultiItemSmeltingSerializer {

    public MultiItemSoulBlastingSerializer() {
        super(MultiItemSoulBlastingRecipe::new);
    }

    @Override
    protected ITEBookCategory getDefaultBookCategory() {
        return ITEBookCategory.SOUL_BLAST_FURNACE_MISC;
    }

    @Override
    int getIngredientSlotsCount() {
        return 6;
    }
}