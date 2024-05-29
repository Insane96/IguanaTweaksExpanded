package insane96mcp.iguanatweaksexpanded.module.world.seasons;

import com.unixkitty.timecontrol.Config;
import com.unixkitty.timecontrol.network.ModNetworkDispatcher;
import com.unixkitty.timecontrol.network.packet.ConfigS2CPacket;
import insane96mcp.iguanatweaksexpanded.utils.LogHelper;
import sereneseasons.api.season.Season;

public class TimeControlIntegration {
    public static void updateDayNightLength(Season.SubSeason season) {
        switch (season) {
            case MID_SPRING, MID_AUTUMN -> {
                Config.day_length_seconds.set(10 * 60);
                Config.night_length_seconds.set(10 * 60);
            }
            case EARLY_SPRING, LATE_AUTUMN -> {
                Config.day_length_seconds.set((int) ((10 - Seasons.timeControlDayNightShift * 1) * 60));
                Config.night_length_seconds.set((int) ((10 + Seasons.timeControlDayNightShift * 1) * 60));
            }
            case EARLY_WINTER, LATE_WINTER -> {
                Config.day_length_seconds.set((int) ((10 - Seasons.timeControlDayNightShift * 2) * 60));
                Config.night_length_seconds.set((int) ((10 + Seasons.timeControlDayNightShift * 2) * 60));
            }
            case MID_WINTER -> {
                Config.day_length_seconds.set((int) ((10 - Seasons.timeControlDayNightShift * 3) * 60));
                Config.night_length_seconds.set((int) ((10 + Seasons.timeControlDayNightShift * 3) * 60));
            }
            case LATE_SPRING, EARLY_AUTUMN -> {
                Config.day_length_seconds.set((int) ((10 + Seasons.timeControlDayNightShift * 1) * 60));
                Config.night_length_seconds.set((int) ((10 - Seasons.timeControlDayNightShift * 1) * 60));
            }
            case EARLY_SUMMER, LATE_SUMMER -> {
                Config.day_length_seconds.set((int) ((10 + Seasons.timeControlDayNightShift * 2) * 60));
                Config.night_length_seconds.set((int) ((10 - Seasons.timeControlDayNightShift * 2) * 60));
            }
            case MID_SUMMER -> {
                Config.day_length_seconds.set((int) ((10 + Seasons.timeControlDayNightShift * 3) * 60));
                Config.night_length_seconds.set((int) ((10 - Seasons.timeControlDayNightShift * 3) * 60));
            }
        }
        LogHelper.info("Updated Time Control day length to %s and night length to %s", Config.day_length_seconds.get(), Config.night_length_seconds.get());
        ModNetworkDispatcher.send(new ConfigS2CPacket());
    }
}
