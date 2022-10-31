package insane96mcp.iguanatweaksreborn.module;

import insane96mcp.iguanatweaksreborn.setup.ITClientConfig;
import insane96mcp.iguanatweaksreborn.setup.ITCommonConfig;
import insane96mcp.insanelib.base.Module;
import net.minecraftforge.fml.config.ModConfig;

public class Modules {

	public static Module combat;
	public static Module experience;
	public static Module farming;
	public static Module hungerHealth;
	public static Module mining;
	public static Module misc;
	public static Module movement;
	public static Module sleepRespawn;
	public static Module stackSize;
	public static Module client;

	public static void init() {
		combat = Module.Builder.create(Ids.COMBAT, "Combat", ModConfig.Type.COMMON, ITCommonConfig.builder).build();
		experience = Module.Builder.create(Ids.EXPERIENCE, "Experience", ModConfig.Type.COMMON, ITCommonConfig.builder).build();
		farming = Module.Builder.create(Ids.FARMING, "Farming", ModConfig.Type.COMMON, ITCommonConfig.builder).build();
		hungerHealth = Module.Builder.create(Ids.HUNGER_HEALTH, "Hunger & Health", ModConfig.Type.COMMON, ITCommonConfig.builder).build();
		mining = Module.Builder.create(Ids.MINING, "Mining", ModConfig.Type.COMMON, ITCommonConfig.builder).build();
		misc = Module.Builder.create(Ids.MISC, "Miscellaneous", ModConfig.Type.COMMON, ITCommonConfig.builder).build();
		movement = Module.Builder.create(Ids.MOVEMENT, "Movement", ModConfig.Type.COMMON, ITCommonConfig.builder).build();
		sleepRespawn = Module.Builder.create(Ids.SLEEP_RESPAWN, "Sleep & Respawn", ModConfig.Type.COMMON, ITCommonConfig.builder).build();
		stackSize = Module.Builder.create(Ids.STACK_SIZE, "Stack Sizes", ModConfig.Type.COMMON, ITCommonConfig.builder).build();
		client = Module.Builder.create(Ids.CLIENT, "Client", ModConfig.Type.CLIENT, ITClientConfig.builder).build();
	}

	public static class Ids {
		public static final String COMBAT = IguanaTweaksReborn.RESOURCE_PREFIX + "combat";
		public static final String EXPERIENCE = IguanaTweaksReborn.RESOURCE_PREFIX + "experience";
		public static final String FARMING = IguanaTweaksReborn.RESOURCE_PREFIX + "farming";
		public static final String HUNGER_HEALTH = IguanaTweaksReborn.RESOURCE_PREFIX + "hunger_health";
		public static final String MINING = IguanaTweaksReborn.RESOURCE_PREFIX + "mining";
		public static final String MISC = IguanaTweaksReborn.RESOURCE_PREFIX + "misc";
		public static final String MOVEMENT = IguanaTweaksReborn.RESOURCE_PREFIX + "movement";
		public static final String SLEEP_RESPAWN = IguanaTweaksReborn.RESOURCE_PREFIX + "sleep_respawn";
		public static final String STACK_SIZE = IguanaTweaksReborn.RESOURCE_PREFIX + "stack_size";
		public static final String CLIENT = IguanaTweaksReborn.RESOURCE_PREFIX + "client";
	}
}
