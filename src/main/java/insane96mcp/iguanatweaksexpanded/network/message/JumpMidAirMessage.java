package insane96mcp.iguanatweaksexpanded.network.message;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.DoubleJump;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static insane96mcp.iguanatweaksexpanded.network.NetworkHandler.CHANNEL;

public class JumpMidAirMessage {
	public JumpMidAirMessage() {
	}

	public static void encode(JumpMidAirMessage pkt, FriendlyByteBuf buf) {
	}

	public static JumpMidAirMessage decode(FriendlyByteBuf buf) {
		return new JumpMidAirMessage();
	}

	public static void handle(final JumpMidAirMessage message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (ctx.get().getSender() != null) {
				ctx.get().getSender().jumpFromGround();
				DoubleJump.playAnimation(ctx.get().getSender());
			}
		});
		ctx.get().setPacketHandled(true);
	}

	public static void jumpMidAir(LocalPlayer player) {
		Object msg = new JumpMidAirMessage();
		CHANNEL.sendTo(msg, player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
	}
}
