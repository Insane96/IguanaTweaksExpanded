package insane96mcp.iguanatweaksexpanded.data.generator;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ISEGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ISEGlobalLootModifierProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void start() {
        NewEnchantmentsFeature.addGlobalLoot(this);
    }
}
