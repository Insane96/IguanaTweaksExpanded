package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.client;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.inventory.MultiBlockSoulBlastFurnaceMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MultiBlockSoulBlastFurnaceScreen extends AbstractMultiBlockFurnaceScreen<MultiBlockSoulBlastFurnaceMenu>{
    public static final ResourceLocation TEXTURE = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "textures/gui/container/soul_blast_furnace.png");
    public MultiBlockSoulBlastFurnaceScreen(MultiBlockSoulBlastFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new MultiBlockSoulBlastFurnaceRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE);
    }
}
