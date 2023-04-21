package insane96mcp.survivalreimagined.network.message;

import insane96mcp.survivalreimagined.module.combat.feature.Stats;
import insane96mcp.survivalreimagined.module.items.feature.StackSizes;
import insane96mcp.survivalreimagined.module.items.feature.ToolStats;
import insane96mcp.survivalreimagined.module.mining.feature.CustomHardness;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static insane96mcp.survivalreimagined.network.NetworkHandler.CHANNEL;

public class JsonConfigSyncMessage {

    ConfigType type;
    String json;
    int jsonSize;

    public JsonConfigSyncMessage(ConfigType type, String json) {
        this.type = type;
        this.json = json;
        this.jsonSize = json.length();
    }

    public static void encode(JsonConfigSyncMessage pkt, FriendlyByteBuf buf) {
        buf.writeEnum(pkt.type);
        buf.writeInt(pkt.jsonSize);
        buf.writeBytes(pkt.json.getBytes());
    }

    public static JsonConfigSyncMessage decode(FriendlyByteBuf buf) {
        ConfigType type = buf.readEnum(ConfigType.class);
        int size = buf.readInt();
        byte[] jsonByte = new byte[size];
        buf.readBytes(jsonByte);
        String json = new String(jsonByte);
        return new JsonConfigSyncMessage(type, json);
    }

    public static void handle(final JsonConfigSyncMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> message.type.consumer.accept(message.json));
        ctx.get().setPacketHandled(true);
    }

    public static void sync(ConfigType type, String json, ServerPlayer player) {
        Object msg = new JsonConfigSyncMessage(type, json);
        CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public enum ConfigType  {
        DURABILITIES(ToolStats::handleDurabilityPacket),
        EFFICIENCIES(ToolStats::handleEfficienciesPacket),
        ITEM_ATTRIBUTE_MODIFIERS(Stats::handleItemAttributeModifiersPacket),
        CUSTOM_FOOD_STACK_SIZES(StackSizes::handleCustomStackSizesPacket),
        CUSTOM_BLOCK_HARDNESS(CustomHardness::handleCustomBlockHardnessPacket);

        final Consumer<String> consumer;

        ConfigType(Consumer<String> consumer) {
            this.consumer = consumer;
        }

        public void consume(String json) {
            this.consumer.accept(json);
        }
    }
}