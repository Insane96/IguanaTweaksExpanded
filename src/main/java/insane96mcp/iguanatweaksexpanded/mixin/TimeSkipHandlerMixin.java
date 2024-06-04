package insane96mcp.iguanatweaksexpanded.mixin;

import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sereneseasons.handler.season.TimeSkipHandler;

@Mixin(TimeSkipHandler.class)
public class TimeSkipHandlerMixin {
    @Inject(method = "onWorldTick", at = @At("HEAD"), remap = false)
    private static void cancelTimeSkipHandlerTick(TickEvent.LevelTickEvent event, CallbackInfo ci) {
        /*if (!Seasons.changeTimeControl
                || event.phase != TickEvent.Phase.START
                || event.side != LogicalSide.SERVER
                || ServerConfig.progressSeasonWhileOffline.get())
            return;

        MinecraftServer server = ((ServerLevel) event.level).getServer();
        if (server.getPlayerList().getPlayerCount() == 0)
            ci.cancel();*/
    }
}
