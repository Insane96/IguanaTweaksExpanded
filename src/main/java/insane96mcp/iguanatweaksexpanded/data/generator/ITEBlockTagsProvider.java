package insane96mcp.iguanatweaksexpanded.data.generator;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.items.crate.Crate;
import insane96mcp.iguanatweaksexpanded.module.items.explosivebarrel.ExplosiveBarrel;
import insane96mcp.iguanatweaksexpanded.module.items.flintexpansion.FlintExpansion;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.durium.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block.MultiBlockBlastFurnaceBlock;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block.MultiBlockSoulBlastFurnaceBlock;
import insane96mcp.iguanatweaksexpanded.module.mining.quaron.Quaron;
import insane96mcp.iguanatweaksexpanded.module.movement.minecarts.Minecarts;
import insane96mcp.iguanatweaksexpanded.module.world.coalfire.CoalCharcoal;
import insane96mcp.iguanatweaksexpanded.module.world.oregeneration.OreGeneration;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ITEBlockTagsProvider extends BlockTagsProvider {
    public static final TagKey<Block> RESPAWN_OBELISK_BLOCKS_TO_ROT = create("structures/respawn_obelisk/blocks_to_rot");

    public ITEBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //Vanilla Tags
        /*tag(BlockTags.MINEABLE_WITH_HOE)
                .add(Solarium.SOLIUM_MOSS.block().get());*/

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(Crate.BLOCK.get(), ExplosiveBarrel.BLOCK.block().get(), Fletching.FLETCHING_TABLE.block().get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(CoalCharcoal.CHARCOAL_LAYER.block().get())
                .add(CoalCharcoal.SOUL_SAND_HELLISH_COAL_ORE.block().get(), CoalCharcoal.SOUL_SOIL_HELLISH_COAL_ORE.block().get())
                .add(Durium.SAND_ORE.block().get(), Durium.GRAVEL_ORE.block().get(), Durium.CLAY_ORE.block().get(), Durium.DIRT_ORE.block().get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Durium.BLOCK.block().get(), Durium.ORE.block().get(), Durium.DEEPSLATE_ORE.block().get(), Durium.SCRAP_BLOCK.block().get())
                .add(SoulSteel.BLOCK.block().get())
                .add(OreGeneration.IRON_ORE_ROCK.block().get()).add(OreGeneration.COPPER_ORE_ROCK.block().get()).add(OreGeneration.GOLD_ORE_ROCK.block().get())
                .add(OreGeneration.POOR_RICH_IRON_ORE.poorOre().block().get(), OreGeneration.POOR_RICH_IRON_ORE.richOre().block().get(), OreGeneration.POOR_RICH_IRON_ORE.poorDeepslateOre().block().get(), OreGeneration.POOR_RICH_IRON_ORE.richDeepslateOre().block().get())
                .add(OreGeneration.POOR_RICH_GOLD_ORE.poorOre().block().get(), OreGeneration.POOR_RICH_GOLD_ORE.richOre().block().get(), OreGeneration.POOR_RICH_GOLD_ORE.poorDeepslateOre().block().get(), OreGeneration.POOR_RICH_GOLD_ORE.richDeepslateOre().block().get())
                .add(OreGeneration.POOR_RICH_COPPER_ORE.poorOre().block().get(), OreGeneration.POOR_RICH_COPPER_ORE.richOre().block().get(), OreGeneration.POOR_RICH_COPPER_ORE.poorDeepslateOre().block().get(), OreGeneration.POOR_RICH_COPPER_ORE.richDeepslateOre().block().get())
                .add(MultiBlockFurnaces.BLAST_FURNACE.block().get(), MultiBlockFurnaces.SOUL_BLAST_FURNACE.block().get())
                .add(Forging.FORGE.block().get())
                .add(FlintExpansion.FLINT_ROCK.block().get())
                .add(Quaron.BLOCK.block().get())
                .add(EnchantingFeature.ENCHANTING_TABLE.block().get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(Crate.BLOCK.get())
                .add(OreGeneration.POOR_RICH_IRON_ORE.poorOre().block().get(), OreGeneration.POOR_RICH_IRON_ORE.richOre().block().get(), OreGeneration.POOR_RICH_IRON_ORE.poorDeepslateOre().block().get(), OreGeneration.POOR_RICH_IRON_ORE.richDeepslateOre().block().get())
                .add(OreGeneration.POOR_RICH_COPPER_ORE.poorOre().block().get(), OreGeneration.POOR_RICH_COPPER_ORE.richOre().block().get(), OreGeneration.POOR_RICH_COPPER_ORE.poorDeepslateOre().block().get(), OreGeneration.POOR_RICH_COPPER_ORE.richDeepslateOre().block().get())
                .add(EnchantingFeature.ENCHANTING_TABLE.block().get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(OreGeneration.POOR_RICH_GOLD_ORE.poorOre().block().get(), OreGeneration.POOR_RICH_GOLD_ORE.richOre().block().get(), OreGeneration.POOR_RICH_GOLD_ORE.poorDeepslateOre().block().get(), OreGeneration.POOR_RICH_GOLD_ORE.richDeepslateOre().block().get())
                .addTag(Durium.BLOCK_ORES)
                .add(SoulSteel.BLOCK.block().get())
                .add(Quaron.BLOCK.block().get())
                .add(CoalCharcoal.SOUL_SAND_HELLISH_COAL_ORE.block().get(), CoalCharcoal.SOUL_SOIL_HELLISH_COAL_ORE.block().get());

        tag(BlockTags.RAILS)
                .add(Minecarts.COPPER_POWERED_RAIL.block().get(), Minecarts.GOLDEN_POWERED_RAIL.block().get(), Minecarts.NETHER_INFUSED_POWERED_RAIL.block().get());

        tag(BlockTags.IRON_ORES)
                .add(OreGeneration.POOR_RICH_IRON_ORE.poorOre().block().get(), OreGeneration.POOR_RICH_IRON_ORE.richOre().block().get(), OreGeneration.POOR_RICH_IRON_ORE.poorDeepslateOre().block().get(), OreGeneration.POOR_RICH_IRON_ORE.richDeepslateOre().block().get());
        tag(BlockTags.GOLD_ORES)
                .add(OreGeneration.POOR_RICH_GOLD_ORE.poorOre().block().get(), OreGeneration.POOR_RICH_GOLD_ORE.richOre().block().get(), OreGeneration.POOR_RICH_GOLD_ORE.poorDeepslateOre().block().get(), OreGeneration.POOR_RICH_GOLD_ORE.richDeepslateOre().block().get());
        tag(BlockTags.COPPER_ORES)
                .add(OreGeneration.POOR_RICH_COPPER_ORE.poorOre().block().get(), OreGeneration.POOR_RICH_COPPER_ORE.richOre().block().get(), OreGeneration.POOR_RICH_COPPER_ORE.poorDeepslateOre().block().get(), OreGeneration.POOR_RICH_COPPER_ORE.richDeepslateOre().block().get());

        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(Durium.BLOCK.block().get(), SoulSteel.BLOCK.block().get(), Quaron.BLOCK.block().get(), Keego.BLOCK.block().get());

        tag(BlockTags.REPLACEABLE_BY_TREES)
                .add(OreGeneration.COPPER_ORE_ROCK.block().get(), OreGeneration.IRON_ORE_ROCK.block().get(), OreGeneration.GOLD_ORE_ROCK.block().get())
                .add(FlintExpansion.FLINT_ROCK.block().get());

        //Mod's tags
        tag(RESPAWN_OBELISK_BLOCKS_TO_ROT)
                .add(Blocks.COBBLESTONE).add(Blocks.MOSSY_COBBLESTONE).add(Blocks.STONE_BRICKS).add(Blocks.STONE_BRICK_SLAB).add(Blocks.SANDSTONE).add(Blocks.SANDSTONE_SLAB).add(Blocks.ORANGE_TERRACOTTA).add(Blocks.COBBLESTONE_SLAB).add(Blocks.SNOW_BLOCK).add(Blocks.SNOW).add(Blocks.BLUE_ICE).add(Blocks.DEEPSLATE_BRICKS).add(Blocks.DEEPSLATE_BRICK_SLAB).add(Blocks.COBBLED_DEEPSLATE).add(Blocks.BIRCH_PLANKS).add(Blocks.BIRCH_SLAB).add(Blocks.BIRCH_LOG).add(Blocks.PRISMARINE).add(Blocks.PRISMARINE_BRICKS).add(Blocks.PRISMARINE_BRICK_SLAB);

        tag(MultiBlockBlastFurnaceBlock.BOTTOM_BLOCKS_TAG)
                .add(Blocks.SMOOTH_STONE, Blocks.SMOOTH_STONE_SLAB);
        tag(MultiBlockBlastFurnaceBlock.MIDDLE_BLOCKS_TAG)
                .add(Blocks.BRICKS);
        tag(MultiBlockBlastFurnaceBlock.TOP_BLOCKS_TAG)
                .add(Blocks.BRICKS, Blocks.BRICK_STAIRS);

        tag(MultiBlockSoulBlastFurnaceBlock.BOTTOM_BLOCKS_TAG)
                .add(Blocks.GILDED_BLACKSTONE, Blocks.CHISELED_POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS);
        tag(MultiBlockSoulBlastFurnaceBlock.MIDDLE_BLOCKS_TAG)
                .add(Blocks.RED_NETHER_BRICKS);
        tag(MultiBlockSoulBlastFurnaceBlock.TOP_BLOCKS_TAG)
                .add(Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_STAIRS);

        tag(Durium.BLOCK_ORES)
                .add(Durium.ORE.block().get(), Durium.DEEPSLATE_ORE.block().get(), Durium.SAND_ORE.block().get(), Durium.GRAVEL_ORE.block().get(), Durium.CLAY_ORE.block().get(), Durium.DIRT_ORE.block().get());
    }

    public static TagKey<Block> create(String tagName) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, tagName));
    }
}
