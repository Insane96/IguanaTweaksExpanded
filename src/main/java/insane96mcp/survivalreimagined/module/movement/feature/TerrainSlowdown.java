package insane96mcp.survivalreimagined.module.movement.feature;

import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.util.IdTagMatcher;
import insane96mcp.insanelib.util.MCUtils;
import insane96mcp.survivalreimagined.base.SRFeature;
import insane96mcp.survivalreimagined.data.IdTagValue;
import insane96mcp.survivalreimagined.module.Modules;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Label(name = "Terrain Slowdown", description = "Slowdown based off the terrain you're walking on. Custom Terrain Slowdown are controlled via json in this feature's folder")
@LoadFeature(module = Modules.Ids.MOVEMENT)
public class TerrainSlowdown extends SRFeature {
	private static final UUID MATERIAL_SLOWDOWN_UUID = UUID.fromString("a849043f-b280-4789-bafd-5da8e8e1078e");

	public static final ArrayList<IdTagValue> CUSTOM_TERRAIN_SLOWDOWN_DEFAULT = new ArrayList<>(List.of(
		new IdTagValue(IdTagMatcher.Type.TAG, "minecraft:ice", 0.55d)
	));
	public static final ArrayList<IdTagValue> customTerrainSlowdown = new ArrayList<>();
	@Config
	@Label(name = "Prevent Snow slowdown with Leather Boots")
	public static Boolean preventSnowSlowdownWithLeatherBoots = true;

	public TerrainSlowdown(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	@Override
	public void loadConfigOptions() {
		super.loadConfigOptions();
		//TODO Sync
		JSON_CONFIGS.add(new JsonConfig<>("custom_terrain_slowdown.json", customTerrainSlowdown, CUSTOM_TERRAIN_SLOWDOWN_DEFAULT, IdTagValue.LIST_TYPE));
	}

	@Override
	public void loadJsonConfigs() {
		super.loadJsonConfigs();
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (!this.isEnabled()
				|| event.phase != TickEvent.Phase.START
				|| !event.player.onGround())
			return;

		double onTerrainSlowdown = 0d;
		int blocks = 0;
		AABB bb = event.player.getBoundingBox();
		int mX = Mth.floor(bb.minX);
		int mY = Mth.floor(bb.minY);
		int mZ = Mth.floor(bb.minZ);
		for (int x2 = mX; x2 < bb.maxX; x2++) {
			for (int z2 = mZ; z2 < bb.maxZ; z2++) {
				BlockState state = event.player.level().getBlockState(BlockPos.containing(x2, event.player.position().y - 0.02d, z2));
				if (state.isAir())
					continue;
				double blockSlowdown = 0d;
				for (IdTagValue idTagValue : customTerrainSlowdown) {
					if (idTagValue.matchesBlock(state.getBlock())) {
						blockSlowdown = idTagValue.value;
						blocks++;
						break;
					}
				}
				onTerrainSlowdown += blockSlowdown;
			}
		}
		if (blocks != 0)
			onTerrainSlowdown /= blocks;

		//TODO Custom in terrain slowdown
		double inTerrainSlowdown = 0d;
		/*for (int x2 = mX; x2 < bb.maxX; x2++) {
			for (int y2 = mY; y2 < bb.maxY; y2++) {
				for (int z2 = mZ; z2 < bb.maxZ; z2++) {
					BlockState state = event.player.level.getBlockState(new BlockPos(x2, y2, z2));
					if (state.isAir())
						continue;
					if ((state.getMaterial() == Material.SNOW || state.getMaterial() == Material.TOP_SNOW)
							&& event.player.getItemBySlot(EquipmentSlot.FEET).is(Items.LEATHER_BOOTS)
							&& preventSnowSlowdownWithLeatherBoots)
						continue;
					double blockSlowdown = 0d;
					if ((state.getMaterial() == Material.SNOW || state.getMaterial() == Material.TOP_SNOW)
							&& event.player.getItemBySlot(EquipmentSlot.FEET).is(Items.LEATHER_BOOTS)
							&& preventSnowSlowdownWithLeatherBoots)
						continue;
					inTerrainSlowdown += blockSlowdown;
				}
			}
		}*/

		double slowdown = onTerrainSlowdown + inTerrainSlowdown;

		AttributeModifier modifier = event.player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(MATERIAL_SLOWDOWN_UUID);
		if (slowdown != 0d) {
			if (modifier == null) {
				MCUtils.applyModifier(event.player, Attributes.MOVEMENT_SPEED, MATERIAL_SLOWDOWN_UUID, "material slowdown", -slowdown, AttributeModifier.Operation.MULTIPLY_BASE, false);
			}
			else if (modifier.getAmount() != -slowdown) {
				event.player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MATERIAL_SLOWDOWN_UUID);
				MCUtils.applyModifier(event.player, Attributes.MOVEMENT_SPEED, MATERIAL_SLOWDOWN_UUID, "material slowdown", -slowdown, AttributeModifier.Operation.MULTIPLY_BASE, false);
			}
		}
		else {
			if (modifier != null) {
				event.player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MATERIAL_SLOWDOWN_UUID);
			}
		}
	}
}