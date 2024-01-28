package insane96mcp.iguanatweaksexpanded.network.message;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.SREnchantingTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static insane96mcp.iguanatweaksexpanded.network.NetworkHandler.CHANNEL;

public class SyncSREnchantingTableStatus {
	BlockPos pos;
	ItemStack item;
	//boolean empowered;

	public SyncSREnchantingTableStatus(BlockPos pos, ItemStack itemToEnchant/*, boolean empowered*/) {
		this.pos = pos;
		this.item = itemToEnchant;
		//this.empowered = empowered;
	}

	public static void encode(SyncSREnchantingTableStatus pkt, FriendlyByteBuf buf) {
		buf.writeBlockPos(pkt.pos);
		buf.writeItem(pkt.item);
		//buf.writeBoolean(pkt.empowered);
	}

	public static SyncSREnchantingTableStatus decode(FriendlyByteBuf buf) {
		return new SyncSREnchantingTableStatus(buf.readBlockPos(), buf.readItem()/*, buf.readBoolean()*/);
	}

	public static void handle(final SyncSREnchantingTableStatus message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (!(Minecraft.getInstance().level.getBlockEntity(message.pos) instanceof SREnchantingTableBlockEntity blockEntity))
				return;

			blockEntity.setItem(0, message.item);
			//blockEntity.empowered = message.empowered;
		});
		ctx.get().setPacketHandled(true);
	}

	public static void sync(ServerLevel level, BlockPos pos, SREnchantingTableBlockEntity blockEntity) {
		Object msg = new SyncSREnchantingTableStatus(pos, blockEntity.getItem(0)/*, blockEntity.empowered*/);
		level.players().forEach(player -> CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT));
	}
}
