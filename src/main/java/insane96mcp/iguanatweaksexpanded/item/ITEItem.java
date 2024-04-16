package insane96mcp.iguanatweaksexpanded.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ITEItem extends Item {
    private final boolean hasFoil;

    public ITEItem(Properties pProperties, boolean hasFoil) {
        super(pProperties);
        this.hasFoil = hasFoil;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return this.hasFoil;
    }
}
