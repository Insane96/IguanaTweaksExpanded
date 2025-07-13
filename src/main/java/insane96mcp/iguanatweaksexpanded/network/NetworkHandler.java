package insane96mcp.iguanatweaksexpanded.network;

import insane96mcp.iguanatweaksexpanded.InsaneSE;
import insane96mcp.iguanatweaksexpanded.network.message.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = Integer.toString(5);
	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(InsaneSE.MOD_ID, "network_channel"))
			.clientAcceptedVersions(s -> true)
			.serverAcceptedVersions(s -> true)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();

	static int index = 0;

	public static void init() {
		CHANNEL.registerMessage(++index, JumpMidAirMessage.class, JumpMidAirMessage::encode, JumpMidAirMessage::decode, JumpMidAirMessage::handle);
		CHANNEL.registerMessage(++index, SyncISEEnchantingTableStatus.class, SyncISEEnchantingTableStatus::encode, SyncISEEnchantingTableStatus::decode, SyncISEEnchantingTableStatus::handle);
		CHANNEL.registerMessage(++index, SyncISEEnchantingTableLearnedEnchantments.class, SyncISEEnchantingTableLearnedEnchantments::encode, SyncISEEnchantingTableLearnedEnchantments::decode, SyncISEEnchantingTableLearnedEnchantments::handle);
		CHANNEL.registerMessage(++index, SyncISEEnchantingTableEnchantments.class, SyncISEEnchantingTableEnchantments::encode, SyncISEEnchantingTableEnchantments::decode, SyncISEEnchantingTableEnchantments::handle);
		CHANNEL.registerMessage(++index, SendMultiBlockFurnaceGhostData.class, SendMultiBlockFurnaceGhostData::encode, SendMultiBlockFurnaceGhostData::decode, SendMultiBlockFurnaceGhostData::handle);
	}
}
