package insane96mcp.iguanatweaksexpanded.event;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;

public class ITEEventFactory {
    /**
     * Returns the experience dropped
     */
    public static int onEnchantmentBlockBreak(ServerPlayer player, Level level, BlockPos pos, BlockState state) {
        EnchantmentBlockBreakEvent event = new EnchantmentBlockBreakEvent(level, pos, state, player);
        MinecraftForge.EVENT_BUS.post(event);
        // Handle if the event is canceled
        if (event.isCanceled())
        {
            // Let the client know the block still exists
            player.connection.send(new ClientboundBlockUpdatePacket(level, pos));

            // Update any tile entity data for this block
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null)
            {
                Packet<?> pkt = blockEntity.getUpdatePacket();
                if (pkt != null)
                {
                    player.connection.send(pkt);
                }
            }
        }
        return event.isCanceled() ? -1 : event.getExpToDrop();
    }

    public static void onBlockDestroyPosts(LevelAccessor level, BlockPos pos, BlockState state, Player player) {
        DestroyBlockPostEvent event = new DestroyBlockPostEvent(level, pos, state, player);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
