package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class ForgePlaceRecipe extends ServerPlaceRecipe<Container> {
    public ForgePlaceRecipe(RecipeBookMenu<Container> pMenu) {
        super(pMenu);
    }

    @Override
    public void placeRecipe(int pWidth, int pHeight, int pOutputSlot, Recipe<?> pRecipe, Iterator<Integer> pIngredients, int pMaxAmount) {
        this.addItemToSlot(pIngredients, 0, ((ForgeRecipe)pRecipe).ingredientAmount, 0, 0);
        this.addItemToSlot(pIngredients, 1, 1, 0, 0);
    }

    @Override
    public void recipeClicked(ServerPlayer pPlayer, @Nullable Recipe<Container> pRecipe, boolean pPlaceAll) {
        if (pRecipe != null && pPlayer.getRecipeBook().contains(pRecipe)) {
            this.inventory = pPlayer.getInventory();
            if (this.testClearGrid() || pPlayer.isCreative()) {
                this.stackedContents.clear();
                for (ItemStack stack : pPlayer.getInventory().items) {
                    this.stackedContents.accountStack(stack);
                }
                this.menu.fillCraftSlotsStackedContents(this.stackedContents);
                if (this.stackedContents.canCraft(pRecipe, null)) {
                    this.handleRecipeClicked(pRecipe, pPlaceAll);
                } else {
                    this.clearGrid();
                    pPlayer.connection.send(new ClientboundPlaceGhostRecipePacket(pPlayer.containerMenu.containerId, pRecipe));
                }

                pPlayer.getInventory().setChanged();
            }
        }
    }
}
