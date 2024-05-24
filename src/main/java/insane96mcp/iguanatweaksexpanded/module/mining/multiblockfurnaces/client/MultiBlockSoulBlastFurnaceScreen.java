package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.client;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.inventory.MultiBlockSoulBlastFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MultiBlockSoulBlastFurnaceScreen extends AbstractMultiBlockFurnaceScreen<MultiBlockSoulBlastFurnaceMenu>{
    public static final ResourceLocation TEXTURE = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "textures/gui/container/soul_blast_furnace.png");
    public MultiBlockSoulBlastFurnaceScreen(MultiBlockSoulBlastFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new MultiBlockSoulBlastFurnaceRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pX, int pY) {
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        guiGraphics.blit(this.texture, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            guiGraphics.blit(this.texture, leftPos + 16, topPos + 35 + 12 - k, 176, 12 - k, 14, k + 1);
            if (this.menu.getBurnDuration() > 0 && this.menu.getBurnTime() > this.menu.getBurnDuration()) {
                guiGraphics.drawString(this.font, Component.literal("%d".formatted(this.menu.getBurnTime() / this.menu.getBurnDuration() + 1)), leftPos + 28, topPos + 41, 0x00FF0000, true);
            }
        }

        int l = this.menu.getBurnProgress();
        guiGraphics.blit(this.texture, leftPos + 105, topPos + 34, 176, 14, l + 1, 16);
    }
}
