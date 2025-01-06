package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.data;

import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.MultiItemSoulBlastingRecipe;
import insane96mcp.iguanatweaksexpanded.setup.client.ISEBookCategory;

public class MultiItemSoulBlastingSerializer extends AbstractMultiItemSmeltingSerializer {

    public MultiItemSoulBlastingSerializer() {
        super(MultiItemSoulBlastingRecipe::new);
    }

    @Override
    protected ISEBookCategory getDefaultBookCategory() {
        return ISEBookCategory.SOUL_BLAST_FURNACE_MISC;
    }

    @Override
    int getIngredientSlotsCount() {
        return 6;
    }
}