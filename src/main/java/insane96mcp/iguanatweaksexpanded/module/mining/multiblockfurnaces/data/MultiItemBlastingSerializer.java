package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.data;

import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.MultiItemBlastingRecipe;
import insane96mcp.iguanatweaksexpanded.setup.client.ISEBookCategory;

public class MultiItemBlastingSerializer extends AbstractMultiItemSmeltingSerializer {

    public MultiItemBlastingSerializer() {
        super(MultiItemBlastingRecipe::new);
    }

    @Override
    protected ISEBookCategory getDefaultBookCategory() {
        return ISEBookCategory.BLAST_FURNACE_MISC;
    }

    @Override
    int getIngredientSlotsCount() {
        return 4;
    }
}