package insane96mcp.iguanatweaksexpanded.network.message;

import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.SREnchantingTableMenu;
import insane96mcp.iguanatweaksexpanded.network.NetworkHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static insane96mcp.iguanatweaksexpanded.network.NetworkHandler.CHANNEL;

public class SyncSREnchantingTableEnchantments {
	List<EnchantmentInstance> enchantmentInstances;

	public SyncSREnchantingTableEnchantments(List<EnchantmentInstance> enchantmentInstances) {
		this.enchantmentInstances = enchantmentInstances;
	}

	public static void encode(SyncSREnchantingTableEnchantments pkt, FriendlyByteBuf buf) {
		buf.writeShort(pkt.enchantmentInstances.size());
		for (EnchantmentInstance enchantmentInstance : pkt.enchantmentInstances) {
			buf.writeResourceLocation(ForgeRegistries.ENCHANTMENTS.getKey(enchantmentInstance.enchantment));
			buf.writeShort(enchantmentInstance.level);
		}
	}

	public static SyncSREnchantingTableEnchantments decode(FriendlyByteBuf buf) {
		List<EnchantmentInstance> enchantments = new ArrayList<>();
		int size = buf.readShort();
		for (int i = 0; i < size; i++) {
			enchantments.add(new EnchantmentInstance(ForgeRegistries.ENCHANTMENTS.getValue(buf.readResourceLocation()), buf.readShort()));
		}
		return new SyncSREnchantingTableEnchantments(enchantments);
	}

	public static void handle(final SyncSREnchantingTableEnchantments message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (NetworkHelper.getSidedPlayer(ctx.get()).containerMenu instanceof SREnchantingTableMenu enchantingTableMenu) {
				enchantingTableMenu.updateEnchantmentsChosen(message.enchantmentInstances);
			}
		});
		ctx.get().setPacketHandled(true);
	}

	public static void sync(List<EnchantmentInstance> enchantmentInstances) {
		Object msg = new SyncSREnchantingTableEnchantments(enchantmentInstances);
		CHANNEL.sendTo(msg, Minecraft.getInstance().player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
	}
}
