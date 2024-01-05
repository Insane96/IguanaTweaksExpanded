package insane96mcp.iguanatweaksexpanded.network;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.network.message.ElectrocutionParticleMessage;
import insane96mcp.iguanatweaksexpanded.network.message.JumpMidAirMessage;
import insane96mcp.iguanatweaksexpanded.network.message.SyncForgeStatus;
import insane96mcp.iguanatweaksexpanded.network.message.SyncSREnchantingTableStatus;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = Integer.toString(3);
	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "network_channel"))
			.clientAcceptedVersions(s -> true)
			.serverAcceptedVersions(s -> true)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();

	public static void init() {
		CHANNEL.registerMessage(9, JumpMidAirMessage.class, JumpMidAirMessage::encode, JumpMidAirMessage::decode, JumpMidAirMessage::handle);
		CHANNEL.registerMessage(11, SyncForgeStatus.class, SyncForgeStatus::encode, SyncForgeStatus::decode, SyncForgeStatus::handle);
		CHANNEL.registerMessage(12, ElectrocutionParticleMessage.class, ElectrocutionParticleMessage::encode, ElectrocutionParticleMessage::decode, ElectrocutionParticleMessage::handle);
		CHANNEL.registerMessage(13, SyncSREnchantingTableStatus.class, SyncSREnchantingTableStatus::encode, SyncSREnchantingTableStatus::decode, SyncSREnchantingTableStatus::handle);
	}
}
