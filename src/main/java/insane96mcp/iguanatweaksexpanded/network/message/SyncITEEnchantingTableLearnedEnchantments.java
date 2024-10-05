package insane96mcp.iguanatweaksexpanded.network.message;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.ITEEnchantingTableBlockEntity;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.ITEEnchantingTableScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Supplier;

import static insane96mcp.iguanatweaksexpanded.network.NetworkHandler.CHANNEL;

public class SyncITEEnchantingTableLearnedEnchantments {
	Map<Enchantment, Integer> knownEnchantments;

	public SyncITEEnchantingTableLearnedEnchantments(Map<Enchantment, Integer> knownEnchantments) {
		this.knownEnchantments = knownEnchantments;
	}

	public static void encode(SyncITEEnchantingTableLearnedEnchantments pkt, FriendlyByteBuf buf) {
		buf.writeMap(pkt.knownEnchantments,
				(buf1, enchantment) -> buf1.writeResourceLocation(ForgeRegistries.ENCHANTMENTS.getKey(enchantment)),
				FriendlyByteBuf::writeInt);
	}

	public static SyncITEEnchantingTableLearnedEnchantments decode(FriendlyByteBuf buf) {
		Map<Enchantment, Integer> knownEnchantments = buf.readMap(
				buf1 -> ForgeRegistries.ENCHANTMENTS.getValue(buf1.readResourceLocation()),
				FriendlyByteBuf::readInt
		);
		return new SyncITEEnchantingTableLearnedEnchantments(knownEnchantments);
	}

	public static void handle(final SyncITEEnchantingTableLearnedEnchantments message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (!(Minecraft.getInstance().screen instanceof ITEEnchantingTableScreen iteEnchantingTableScreen))
				return;

			iteEnchantingTableScreen.learnedEnchantments.clear();
			iteEnchantingTableScreen.learnedEnchantments.putAll(message.knownEnchantments);
			iteEnchantingTableScreen.forceUpdateEnchantmentsList = true;
		});
		ctx.get().setPacketHandled(true);
	}

	public static void sync(ServerLevel level, ITEEnchantingTableBlockEntity blockEntity) {
		Object msg = new SyncITEEnchantingTableLearnedEnchantments(blockEntity.learnedEnchantments);
		level.players().forEach(player -> CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT));
	}
}
