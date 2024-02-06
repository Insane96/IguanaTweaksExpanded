package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.data;

import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.MultiItemBlastingRecipe;
import insane96mcp.iguanatweaksexpanded.setup.client.ITEBookCategory;

public class MultiItemBlastingSerializer extends AbstractMultiItemSmeltingSerializer {

    public MultiItemBlastingSerializer() {
        super(MultiItemBlastingRecipe::new);
    }

    @Override
    protected ITEBookCategory getDefaultBookCategory() {
        return ITEBookCategory.BLAST_FURNACE_MISC;
    }

    @Override
    int getIngredientSlotsCount() {
        return 4;
    }
}