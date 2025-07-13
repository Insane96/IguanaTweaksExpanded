package insane96mcp.iguanatweaksexpanded.module;

import insane96mcp.iguanatweaksexpanded.InsaneSE;
import insane96mcp.iguanatweaksexpanded.setup.ISECommonConfig;
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
		combat = Module.Builder.create(Ids.COMBAT, "Combat", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
		experience = Module.Builder.create(Ids.EXPERIENCE, "Experience", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
		hungerHealth = Module.Builder.create(Ids.HUNGER_HEALTH, "Hunger & Health", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
		hungerHealth = Module.Builder.create(Ids.ITEMS, "Items", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
		mining = Module.Builder.create(Ids.MINING, "Mining", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
		misc = Module.Builder.create(Ids.MISC, "Miscellaneous", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
		mobs = Module.Builder.create(Ids.MOBS, "Mobs", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
		movement = Module.Builder.create(Ids.MOVEMENT, "Movement", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
		sleepRespawn = Module.Builder.create(Ids.SLEEP_RESPAWN, "Sleep & Respawn", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
		world = Module.Builder.create(Ids.WORLD, "World", ModConfig.Type.COMMON, ISECommonConfig.builder).build();
	}

	public static class Ids {
		public static final String COMBAT = InsaneSE.RESOURCE_PREFIX + "combat";
		public static final String EXPERIENCE = InsaneSE.RESOURCE_PREFIX + "experience";
		public static final String HUNGER_HEALTH = InsaneSE.RESOURCE_PREFIX + "hunger_health";
		public static final String ITEMS = InsaneSE.RESOURCE_PREFIX + "items";
		public static final String MINING = InsaneSE.RESOURCE_PREFIX + "mining";
		public static final String MISC = InsaneSE.RESOURCE_PREFIX + "misc";
		public static final String MOBS = InsaneSE.RESOURCE_PREFIX + "mobs";
		public static final String MOVEMENT = InsaneSE.RESOURCE_PREFIX + "movement";
		public static final String SLEEP_RESPAWN = InsaneSE.RESOURCE_PREFIX + "sleep_respawn";
		public static final String WORLD = InsaneSE.RESOURCE_PREFIX + "world";
	}
}
