package insane96mcp.iguanatweaksexpanded.module.movement.minecarts;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.data.lootmodifier.ReplaceLootModifier;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

@Label(name = "Minecarts")
@LoadFeature(module = Modules.Ids.MOVEMENT, canBeDisabled = false)
public class Minecarts extends Feature {

	public static final SimpleBlockWithItem NETHER_INFUSED_POWERED_RAIL = SimpleBlockWithItem.register("nether_infused_powered_rail", () -> new ITEPoweredRail(BlockBehaviour.Properties.copy(Blocks.POWERED_RAIL), 1f, 0.18f));
	public static final SimpleBlockWithItem GOLDEN_POWERED_RAIL = SimpleBlockWithItem.register("golden_powered_rail", () -> new ITEPoweredRail(BlockBehaviour.Properties.copy(Blocks.POWERED_RAIL), 1f, 0.18f));
	public static final SimpleBlockWithItem COPPER_POWERED_RAIL = SimpleBlockWithItem.register("copper_powered_rail", () -> new ITEPoweredRail(BlockBehaviour.Properties.copy(Blocks.POWERED_RAIL).sound(SoundType.COPPER), 0.4f, 0.05f));

	@Config
	@Label(name = "Data Pack", description = "If true, enables a data pack that makes rails cheaper and adds recipes for new rails.")
	public static Boolean dataPack = true;

	public Minecarts(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "better_rails", Component.literal("IguanaTweaks Expanded Better Rails"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && dataPack));
	}

	/*@SubscribeEvent
	public void onMinecartJoinLevel(EntityJoinLevelEvent event) {
		if (!this.isEnabled()
				|| !(event.getEntity() instanceof AbstractMinecart minecart))
			return;

		minecart.setDragAir(1d);
		minecart.setMaxSpeedAirLateral(1.2f);
		minecart.setMaxSpeedAirVertical(1.2f);
	}*/

	private static final String path = "minecarts/";

	public static void addGlobalLoot(GlobalLootModifierProvider provider) {
		provider.add(path + "replace_vanilla_powered_rails", new ReplaceLootModifier.Builder(Items.POWERED_RAIL, GOLDEN_POWERED_RAIL.item().get())
				.applyToChestsOnly()
				.build()
		);
	}
}