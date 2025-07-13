package insane96mcp.iguanatweaksexpanded.utils;

import insane96mcp.iguanatweaksexpanded.InsaneSE;

public class LogHelper {
	public static void error(String format, Object... args) {
		InsaneSE.LOGGER.error(String.format(format, args));
	}

	public static void warn(String format, Object... args) {
		InsaneSE.LOGGER.warn(String.format(format, args));
	}

	public static void info(String format, Object... args) {
		InsaneSE.LOGGER.info(String.format(format, args));
	}

	public static void debug(String format, Object... args) {
		InsaneSE.LOGGER.debug(String.format(format, args));
	}
}
