package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import net.minecraft.world.item.ItemStack;

public interface DurabilityModifier {
    float getDurabilityMultiplier(ItemStack stack);
}
