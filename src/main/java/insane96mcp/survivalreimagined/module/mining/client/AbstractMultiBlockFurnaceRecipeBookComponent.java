package insane96mcp.survivalreimagined.module.mining.client;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class AbstractMultiBlockFurnaceRecipeBookComponent extends RecipeBookComponent {
    @Nullable
    private Ingredient fuels;

    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(152, 182, 28, 18, RECIPE_BOOK_LOCATION);
    }

    public void setupGhostRecipe(Recipe<?> pRecipe, List<Slot> pSlots) {
        ItemStack itemstack = pRecipe.getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(pRecipe);
        this.ghostRecipe.addIngredient(Ingredient.of(itemstack), (pSlots.get(2)).x, (pSlots.get(2)).y);
        NonNullList<Ingredient> nonnulllist = pRecipe.getIngredients();
        Slot slot = pSlots.get(1);
        if (slot.getItem().isEmpty()) {
            if (this.fuels == null) {
                this.fuels = Ingredient.of(this.getFuelItems().stream().filter((p_274685_) -> {
                    return p_274685_.isEnabled(this.minecraft.level.enabledFeatures());
                }).map(ItemStack::new));
            }

            this.ghostRecipe.addIngredient(this.fuels, slot.x, slot.y);
        }

        Iterator<Ingredient> iterator = nonnulllist.iterator();

        for(int i = 0; i < 2; ++i) {
            if (!iterator.hasNext()) {
                return;
            }

            Ingredient ingredient = iterator.next();
            if (!ingredient.isEmpty()) {
                Slot slot1 = pSlots.get(i);
                this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
            }
        }

    }

    protected abstract Set<Item> getFuelItems();
}