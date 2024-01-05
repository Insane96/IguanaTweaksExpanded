package insane96mcp.iguanatweaksexpanded.data.generator;

import insane96mcp.iguanatweaksexpanded.module.experience.Lapis;
import insane96mcp.iguanatweaksexpanded.module.items.recallidol.RecallIdol;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.movement.minecarts.Minecarts;
import insane96mcp.iguanatweaksexpanded.module.world.coalfire.CoalCharcoal;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class SRGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public SRGlobalLootModifierProvider(PackOutput output, String modid) {
        super(output, modid);
    }


    @Override
    protected void start() {
        Minecarts.addGlobalLoot(this);
        CoalCharcoal.addGlobalLoot(this);
        Lapis.addGlobalLoot(this);
        RecallIdol.addGlobalLoot(this);
        SoulSteel.addGlobalLoot(this);
    }
}
