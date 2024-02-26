package insane96mcp.iguanatweaksexpanded.module.mining.quaron;

import net.minecraft.world.item.FishingRodItem;

public class QuaronFishingRod extends FishingRodItem {
    public QuaronFishingRod(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getEnchantmentValue() {
        return 11;
    }
}
