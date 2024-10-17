package insane96mcp.iguanatweaksexpanded.network.message;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.ITEEnchantingTableBlockEntity;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.ITEEnchantingTableScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Supplier;

import static insane96mcp.iguanatweaksexpanded.network.NetworkHandler.CHANNEL;

public class SyncITEEnchantingTableStatus {
	BlockPos pos;
	ItemStack item;
	Map<Enchantment, Integer> knownEnchantments;

	public SyncITEEnchantingTableStatus(BlockPos pos, ItemStack itemToEnchant, Map<Enchantment, Integer> knownEnchantments) {
		this.pos = pos;
		this.item = itemToEnchant;
		this.knownEnchantments = knownEnchantments;
	}

	public static void encode(SyncITEEnchantingTableStatus pkt, FriendlyByteBuf buf) {
		buf.writeBlockPos(pkt.pos);
		buf.writeItem(pkt.item);

		buf.writeMap(pkt.knownEnchantments,
				(buf1, enchantment) -> buf1.writeResourceLocation(ForgeRegistries.ENCHANTMENTS.getKey(enchantment)),
                FriendlyByteBuf::writeInt);
	}

	public static SyncITEEnchantingTableStatus decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		ItemStack stack = buf.readItem();
		Map<Enchantment, Integer> knownEnchantments = buf.readMap(
				buf1 -> ForgeRegistries.ENCHANTMENTS.getValue(buf1.readResourceLocation()),
				FriendlyByteBuf::readInt
		);
		return new SyncITEEnchantingTableStatus(pos, stack, knownEnchantments);
	}

	public static void handle(final SyncITEEnchantingTableStatus message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (!(Minecraft.getInstance().level.getBlockEntity(message.pos) instanceof ITEEnchantingTableBlockEntity blockEntity))
				return;

			blockEntity.setItem(0, message.item);
			blockEntity.learnedEnchantments.clear();
			for (var knownEnchantment : message.knownEnchantments.entrySet()) {
				blockEntity.learnEnchantment(knownEnchantment.getKey(), knownEnchantment.getValue());
			}

			if (Minecraft.getInstance().screen instanceof ITEEnchantingTableScreen iteEnchantingTableScreen) {
				iteEnchantingTableScreen.forceUpdateEnchantmentsList = true;
			}
		});
		ctx.get().setPacketHandled(true);
	}

	public static void sync(ServerLevel level, BlockPos pos, ITEEnchantingTableBlockEntity blockEntity) {
		Object msg = new SyncITEEnchantingTableStatus(pos, blockEntity.getItem(0), blockEntity.learnedEnchantments);
		level.players().forEach(player -> CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT));
	}
}
