package insane96mcp.iguanatweaksexpanded.data.generator.client;

import insane96mcp.iguanatweaksexpanded.InsaneSE;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.List;

public class ISEBlockModelsProvider extends BlockModelProvider {
    public ISEBlockModelsProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    List<String> poorRichOres = List.of("iron", "gold", "copper");

    @Override
    protected void registerModels() {
        for (String poorRichOre : poorRichOres) {
            cubeAll("poor_%s_ore".formatted(poorRichOre), "block/poor_%s_ore".formatted(poorRichOre));
            cubeAll("rich_%s_ore".formatted(poorRichOre), "block/rich_%s_ore".formatted(poorRichOre));
            cubeAll("poor_deepslate_%s_ore".formatted(poorRichOre), "block/poor_deepslate_%s_ore".formatted(poorRichOre));
            cubeAll("rich_deepslate_%s_ore".formatted(poorRichOre), "block/rich_deepslate_%s_ore".formatted(poorRichOre));
        }

        cubeAll("durium_scrap_block", "block/durium_scrap_block");
        cubeAll("durium_block", "block/durium_block");
        cubeAll("durium_ore", "block/durium_ore");
        cubeAll("deepslate_durium_ore", "block/deepslate_durium_ore");

        cubeAll("keego_ore", "block/keego_ore");
        cubeAll("keego_block", "block/keego_block");

        cubeAll("quaron_block", "block/quaron_block");

        cubeAll("soul_steel_block", "block/soul_steel_block");

        cross("cyan_flower", new ResourceLocation(InsaneSE.MOD_ID, "block/cyan_flower")).renderType("cutout");
        flowerPotCross("potted_cyan_flower", new ResourceLocation(InsaneSE.MOD_ID, "block/cyan_flower"));
    }

    public BlockModelBuilder flowerPotCross(String name, ResourceLocation plant) {
        return singleTexture(name, ResourceLocation.tryParse(BLOCK_FOLDER + "/flower_pot_cross"), "plant", plant);
    }

    public BlockModelBuilder cubeAll(String name, String texture) {
        return super.cubeAll(InsaneSE.RESOURCE_PREFIX + name, new ResourceLocation(InsaneSE.MOD_ID, texture));
    }
}
