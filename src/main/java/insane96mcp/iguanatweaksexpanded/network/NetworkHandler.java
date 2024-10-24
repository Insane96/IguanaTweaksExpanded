package insane96mcp.iguanatweaksexpanded.network;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.network.message.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = Integer.toString(4);
	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "network_channel"))
			.clientAcceptedVersions(s -> true)
			.serverAcceptedVersions(s -> true)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();

	static int index = 0;

	public static void init() {
		CHANNEL.registerMessage(++index, JumpMidAirMessage.class, JumpMidAirMessage::encode, JumpMidAirMessage::decode, JumpMidAirMessage::handle);
		CHANNEL.registerMessage(++index, ElectrocutionParticleMessage.class, ElectrocutionParticleMessage::encode, ElectrocutionParticleMessage::decode, ElectrocutionParticleMessage::handle);
		CHANNEL.registerMessage(++index, SyncITEEnchantingTableStatus.class, SyncITEEnchantingTableStatus::encode, SyncITEEnchantingTableStatus::decode, SyncITEEnchantingTableStatus::handle);
		CHANNEL.registerMessage(++index, SyncITEEnchantingTableLearnedEnchantments.class, SyncITEEnchantingTableLearnedEnchantments::encode, SyncITEEnchantingTableLearnedEnchantments::decode, SyncITEEnchantingTableLearnedEnchantments::handle);
		CHANNEL.registerMessage(++index, SyncITEEnchantingTableEnchantments.class, SyncITEEnchantingTableEnchantments::encode, SyncITEEnchantingTableEnchantments::decode, SyncITEEnchantingTableEnchantments::handle);
		CHANNEL.registerMessage(++index, SendMultiBlockFurnaceGhostData.class, SendMultiBlockFurnaceGhostData::encode, SendMultiBlockFurnaceGhostData::decode, SendMultiBlockFurnaceGhostData::handle);
	}
}
