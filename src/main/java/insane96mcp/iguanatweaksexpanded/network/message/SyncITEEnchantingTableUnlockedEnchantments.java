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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static insane96mcp.iguanatweaksexpanded.network.NetworkHandler.CHANNEL;

public class SyncITEEnchantingTableUnlockedEnchantments {
	List<Enchantment> treasureEnchantments;

	public SyncITEEnchantingTableUnlockedEnchantments(List<Enchantment> treasureEnchantments) {
		this.treasureEnchantments = treasureEnchantments;
	}

	public static void encode(SyncITEEnchantingTableUnlockedEnchantments pkt, FriendlyByteBuf buf) {
		buf.writeInt(pkt.treasureEnchantments.size());
		for (Enchantment enchantment : pkt.treasureEnchantments) {
			buf.writeResourceLocation(ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
		}
	}

	public static SyncITEEnchantingTableUnlockedEnchantments decode(FriendlyByteBuf buf) {
		List<Enchantment> enchantments = new ArrayList<>();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			enchantments.add(ForgeRegistries.ENCHANTMENTS.getValue(buf.readResourceLocation()));
		}
		return new SyncITEEnchantingTableUnlockedEnchantments(enchantments);
	}

	public static void handle(final SyncITEEnchantingTableUnlockedEnchantments message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (!(Minecraft.getInstance().screen instanceof ITEEnchantingTableScreen iteEnchantingTableScreen))
				return;

			iteEnchantingTableScreen.learnedEnchantments.clear();
			iteEnchantingTableScreen.learnedEnchantments.addAll(message.treasureEnchantments);
			iteEnchantingTableScreen.forceUpdateEnchantmentsList = true;
		});
		ctx.get().setPacketHandled(true);
	}

	public static void sync(ServerLevel level, ITEEnchantingTableBlockEntity blockEntity) {
		Object msg = new SyncITEEnchantingTableUnlockedEnchantments(blockEntity.learnedEnchantments);
		level.players().forEach(player -> CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT));
	}
}
