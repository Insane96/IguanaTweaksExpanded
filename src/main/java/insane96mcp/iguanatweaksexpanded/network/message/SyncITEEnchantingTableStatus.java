package insane96mcp.iguanatweaksexpanded.network.message;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.ITEEnchantingTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static insane96mcp.iguanatweaksexpanded.network.NetworkHandler.CHANNEL;

public class SyncITEEnchantingTableStatus {
	BlockPos pos;
	ItemStack item;
	List<Enchantment> treasureEnchantments;

	public SyncITEEnchantingTableStatus(BlockPos pos, ItemStack itemToEnchant, List<Enchantment> treasureEnchantments) {
		this.pos = pos;
		this.item = itemToEnchant;
		this.treasureEnchantments = treasureEnchantments;
	}

	public static void encode(SyncITEEnchantingTableStatus pkt, FriendlyByteBuf buf) {
		buf.writeBlockPos(pkt.pos);
		buf.writeItem(pkt.item);

		buf.writeInt(pkt.treasureEnchantments.size());
		for (Enchantment enchantment : pkt.treasureEnchantments) {
			buf.writeResourceLocation(ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
		}
	}

	public static SyncITEEnchantingTableStatus decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		ItemStack stack = buf.readItem();
		List<Enchantment> enchantments = new ArrayList<>();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			enchantments.add(ForgeRegistries.ENCHANTMENTS.getValue(buf.readResourceLocation()));
		}
		return new SyncITEEnchantingTableStatus(pos, stack, enchantments);
	}

	public static void handle(final SyncITEEnchantingTableStatus message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (!(Minecraft.getInstance().level.getBlockEntity(message.pos) instanceof ITEEnchantingTableBlockEntity blockEntity))
				return;

			blockEntity.setItem(0, message.item);
			blockEntity.learnedEnchantments.clear();
			for (Enchantment enchantment : message.treasureEnchantments) {
				blockEntity.learnEnchantment(enchantment);
			}
		});
		ctx.get().setPacketHandled(true);
	}

	public static void sync(ServerLevel level, BlockPos pos, ITEEnchantingTableBlockEntity blockEntity) {
		Object msg = new SyncITEEnchantingTableStatus(pos, blockEntity.getItem(0), blockEntity.learnedEnchantments);
		level.players().forEach(player -> CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT));
	}
}
