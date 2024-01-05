package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.client;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.inventory.MultiBlockBlastFurnaceMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MultiBlockBlastFurnaceScreen extends AbstractMultiBlockFurnaceScreen<MultiBlockBlastFurnaceMenu>{
    private static final ResourceLocation TEXTURE = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "textures/gui/container/blast_furnace.png");
    public MultiBlockBlastFurnaceScreen(MultiBlockBlastFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new MultiBlockBlastFurnaceRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE);
    }
}
