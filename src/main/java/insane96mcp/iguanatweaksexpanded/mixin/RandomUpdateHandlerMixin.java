package insane96mcp.iguanatweaksexpanded.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import sereneseasons.handler.season.RandomUpdateHandler;
import sereneseasons.init.ModTags;
import sereneseasons.season.SeasonHooks;

@Mixin(RandomUpdateHandler.class)
public class RandomUpdateHandlerMixin {
    /**
     * @author Insane96MCP
     * @reason Backport of the fix for the melting of ice
     */
    @Overwrite(remap = false)
    private static void meltInChunk(ChunkMap chunkManager, LevelChunk chunkIn, float meltChance) {
        ServerLevel world = chunkManager.level;
        ChunkPos chunkpos = chunkIn.getPos();
        int i = chunkpos.getMinBlockX();
        int j = chunkpos.getMinBlockZ();

        if (meltChance > 0.0F && world.random.nextFloat() < meltChance) {
            BlockPos topAirPos = world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, world.getBlockRandomPos(i, 0, j, 15));
            BlockPos topGroundPos = topAirPos.below();
            BlockState aboveGroundState = world.getBlockState(topAirPos);
            BlockState groundState = world.getBlockState(topGroundPos);
            Holder<Biome> biome = world.getBiome(topAirPos);
            Holder<Biome> groundBiome = world.getBiome(topGroundPos);

            if (!biome.is(ModTags.Biomes.BLACKLISTED_BIOMES)
                    && SeasonHooks.getBiomeTemperature(world, biome, topGroundPos) >= 0.15F
                    && aboveGroundState.getBlock() == Blocks.SNOW)
                world.setBlockAndUpdate(topAirPos, Blocks.AIR.defaultBlockState());

            if (!groundBiome.is(ModTags.Biomes.BLACKLISTED_BIOMES)
                    && SeasonHooks.getBiomeTemperature(world, groundBiome, topGroundPos) >= 0.15F
                    && groundState.getBlock() == Blocks.ICE)
                ((IceBlockInvoker) Blocks.ICE).invokeMelt(groundState, world, topGroundPos);
        }

    }
}
