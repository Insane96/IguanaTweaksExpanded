package insane96mcp.iguanatweaksexpanded.module.items.crate;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class CrateTooltip implements TooltipComponent {
    private final NonNullList<ItemStack> items;

    public CrateTooltip(NonNullList<ItemStack> pItems) {
        this.items = pItems;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }
}