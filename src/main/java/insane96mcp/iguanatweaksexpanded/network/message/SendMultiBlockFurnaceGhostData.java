package insane96mcp.iguanatweaksexpanded.network.message;

import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block.AbstractMultiBlockFurnace;
import insane96mcp.iguanatweaksexpanded.utils.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static insane96mcp.iguanatweaksexpanded.network.NetworkHandler.CHANNEL;

public class SendMultiBlockFurnaceGhostData {

	BlockPos pos;

	public SendMultiBlockFurnaceGhostData(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(SendMultiBlockFurnaceGhostData pkt, FriendlyByteBuf buf) {
		buf.writeBlockPos(pkt.pos);
	}

	public static SendMultiBlockFurnaceGhostData decode(FriendlyByteBuf buf) {
		return new SendMultiBlockFurnaceGhostData(buf.readBlockPos());
	}

	public static void handle(final SendMultiBlockFurnaceGhostData message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (MultiBlockFurnaces.GHOST_BLOCKS_DATA.stream().anyMatch(ghostBlocksData -> ghostBlocksData.furnacePos.equals(message.pos)))
				return;
			MultiBlockFurnaces.GhostBlocksData ghostBlocksData = new MultiBlockFurnaces.GhostBlocksData(message.pos);
			ClientLevel level = Minecraft.getInstance().level;
			BlockState state = level.getBlockState(message.pos);
			AbstractMultiBlockFurnace abstractMultiBlockFurnace = (AbstractMultiBlockFurnace) state.getBlock();
			Direction direction = state.getValue(AbstractMultiBlockFurnace.FACING);
			BlockPos midBlock = message.pos.relative(direction.getOpposite()).above();
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			for (Map.Entry<Vec3i, TagKey<Block>> entry : abstractMultiBlockFurnace.getRelativePosBlockTags().entrySet()) {
				mutableBlockPos.set(midBlock.offset(entry.getKey()));
				BlockState stateRelative = level.getBlockState(mutableBlockPos);
				Block block = stateRelative.getBlock();
				if (block.equals(abstractMultiBlockFurnace) || stateRelative.is(entry.getValue()))
					continue;
				Optional<HolderSet.Named<Block>> optionalBlocks = BuiltInRegistries.BLOCK.getTag(entry.getValue());
				List<Block> blocks = optionalBlocks
						.map(holderSet -> holderSet
								.stream()
								.filter(blockHolder -> blockHolder.value().isCollisionShapeFullBlock(blockHolder.value().defaultBlockState(), level, message.pos))
								.map(Holder::value)
								.toList()
						)
						.orElse(new ArrayList<>());
				if (blocks.isEmpty()) {
					LogHelper.warn("%s has no blocks".formatted(entry.getValue().toString()));
					continue;
				}
				BlockState stateNeeded = blocks.get(level.random.nextInt(blocks.size())).defaultBlockState;
				ghostBlocksData.posAndStates.put(mutableBlockPos.immutable(), stateNeeded);
			}
			MultiBlockFurnaces.GHOST_BLOCKS_DATA.add(ghostBlocksData);
		});
		ctx.get().setPacketHandled(true);
	}

	public static void sync(ServerPlayer player, BlockPos pos) {
		Object msg = new SendMultiBlockFurnaceGhostData(pos);
		CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}
}
