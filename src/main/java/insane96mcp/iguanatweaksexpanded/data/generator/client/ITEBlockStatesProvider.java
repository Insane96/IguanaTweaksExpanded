package insane96mcp.iguanatweaksexpanded.data.generator.client;

import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.durium.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.quaron.Quaron;
import insane96mcp.iguanatweaksexpanded.module.world.coalfire.CoalCharcoal;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ITEBlockStatesProvider extends BlockStateProvider {
    public ITEBlockStatesProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Durium.SCRAP_BLOCK.block().get());
        simpleBlock(Durium.BLOCK.block().get());
        simpleBlock(Durium.ORE.block().get());
        simpleBlock(Durium.DEEPSLATE_ORE.block().get());
        simpleBlock(SoulSteel.BLOCK.block().get());
        simpleBlock(Keego.ORE.block().get());
        simpleBlock(Keego.BLOCK.block().get());
        simpleBlock(Quaron.BLOCK.block().get());
        simpleBlock(CoalCharcoal.SOUL_SAND_HELLISH_COAL_ORE.block().get());
        simpleBlock(CoalCharcoal.SOUL_SOIL_HELLISH_COAL_ORE.block().get());
    }
}
