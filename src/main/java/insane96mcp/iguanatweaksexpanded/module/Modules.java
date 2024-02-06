package insane96mcp.iguanatweaksexpanded.module;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.setup.ITECommonConfig;
import insane96mcp.insanelib.base.Module;
import net.minecraftforge.fml.config.ModConfig;

public class Modules {

	public static Module combat;
	public static Module experience;
	public static Module hungerHealth;
	public static Module items;
	public static Module mining;
	public static Module misc;
	public static Module mobs;
	public static Module movement;
	public static Module sleepRespawn;
	public static Module world;

	public static void init() {
		combat = Module.Builder.create(Ids.COMBAT, "Combat", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
		experience = Module.Builder.create(Ids.EXPERIENCE, "Experience", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
		hungerHealth = Module.Builder.create(Ids.HUNGER_HEALTH, "Hunger & Health", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
		hungerHealth = Module.Builder.create(Ids.ITEMS, "Items", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
		mining = Module.Builder.create(Ids.MINING, "Mining", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
		misc = Module.Builder.create(Ids.MISC, "Miscellaneous", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
		mobs = Module.Builder.create(Ids.MOBS, "Mobs", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
		movement = Module.Builder.create(Ids.MOVEMENT, "Movement", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
		sleepRespawn = Module.Builder.create(Ids.SLEEP_RESPAWN, "Sleep & Respawn", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
		world = Module.Builder.create(Ids.WORLD, "World", ModConfig.Type.COMMON, ITECommonConfig.builder).build();
	}

	public static class Ids {
		public static final String COMBAT = IguanaTweaksExpanded.RESOURCE_PREFIX + "combat";
		public static final String EXPERIENCE = IguanaTweaksExpanded.RESOURCE_PREFIX + "experience";
		public static final String HUNGER_HEALTH = IguanaTweaksExpanded.RESOURCE_PREFIX + "hunger_health";
		public static final String ITEMS = IguanaTweaksExpanded.RESOURCE_PREFIX + "items";
		public static final String MINING = IguanaTweaksExpanded.RESOURCE_PREFIX + "mining";
		public static final String MISC = IguanaTweaksExpanded.RESOURCE_PREFIX + "misc";
		public static final String MOBS = IguanaTweaksExpanded.RESOURCE_PREFIX + "mobs";
		public static final String MOVEMENT = IguanaTweaksExpanded.RESOURCE_PREFIX + "movement";
		public static final String SLEEP_RESPAWN = IguanaTweaksExpanded.RESOURCE_PREFIX + "sleep_respawn";
		public static final String WORLD = IguanaTweaksExpanded.RESOURCE_PREFIX + "world";
	}
}
