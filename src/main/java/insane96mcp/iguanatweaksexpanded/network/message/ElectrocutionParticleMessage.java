package insane96mcp.iguanatweaksexpanded.network.message;

import insane96mcp.iguanatweaksexpanded.network.ClientNetworkHandler;
import insane96mcp.iguanatweaksexpanded.network.NetworkHandler;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ElectrocutionParticleMessage {
    IntList ids;

    public ElectrocutionParticleMessage(IntList ids) {
        this.ids = ids;
    }

    public static void encode(ElectrocutionParticleMessage pkt, FriendlyByteBuf buf) {
        buf.writeIntIdList(pkt.ids);
    }

    public static ElectrocutionParticleMessage decode(FriendlyByteBuf buf) {
        return new ElectrocutionParticleMessage(buf.readIntIdList());
    }

    public static void handle(final ElectrocutionParticleMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientNetworkHandler.summonSpark(message.ids));
        ctx.get().setPacketHandled(true);
    }

    public static void sync(ServerLevel level, IntList entitiesAffected) {
        Object msg = new ElectrocutionParticleMessage(entitiesAffected);
        for (ServerPlayer levelPlayer : level.players()) {
            NetworkHandler.CHANNEL.sendTo(msg, levelPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }
}
