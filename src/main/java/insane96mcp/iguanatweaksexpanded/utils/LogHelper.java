package insane96mcp.iguanatweaksexpanded.utils;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;

public class LogHelper {
	public static void error(String format, Object... args) {
		IguanaTweaksExpanded.LOGGER.error(String.format(format, args));
	}

	public static void warn(String format, Object... args) {
		IguanaTweaksExpanded.LOGGER.warn(String.format(format, args));
	}

	public static void info(String format, Object... args) {
		IguanaTweaksExpanded.LOGGER.info(String.format(format, args));
	}

	public static void debug(String format, Object... args) {
		IguanaTweaksExpanded.LOGGER.debug(String.format(format, args));
	}
}
