package insane96mcp.iguanatweaksexpanded.module.world.seasons;

import com.unixkitty.timecontrol.Config;
import sereneseasons.api.season.Season;

public class TimeControlIntegration {
    public static void updateDayNightLength(Season.SubSeason season) {
        switch (season) {
            case EARLY_SPRING, LATE_AUTUMN -> {
                Config.day_length_minutes.set(9);
                Config.night_length_minutes.set(11);
            }
            case MID_SPRING, MID_AUTUMN -> {
                Config.day_length_minutes.set(10);
                Config.night_length_minutes.set(10);
            }
            case LATE_SPRING, EARLY_AUTUMN -> {
                Config.day_length_minutes.set(11);
                Config.night_length_minutes.set(9);
            }
            case EARLY_SUMMER, LATE_SUMMER -> {
                Config.day_length_minutes.set(12);
                Config.night_length_minutes.set(8);
            }
            case MID_SUMMER -> {
                Config.day_length_minutes.set(13);
                Config.night_length_minutes.set(7);
            }
            case EARLY_WINTER, LATE_WINTER -> {
                Config.day_length_minutes.set(8);
                Config.night_length_minutes.set(12);
            }
            case MID_WINTER -> {
                Config.day_length_minutes.set(7);
                Config.night_length_minutes.set(13);
            }
        }
    }
}
