package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class ForgeIngredientSlot extends Slot {
    public ForgeIngredientSlot(Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
    }

    /*@Override
    public int getMaxStackSize() {
        return 99;
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return pStack.getMaxStackSize() > 1 ? this.getMaxStackSize() : 1;
    }*/
}
