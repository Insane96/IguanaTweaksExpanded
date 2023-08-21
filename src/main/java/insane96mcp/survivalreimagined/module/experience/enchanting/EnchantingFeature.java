package insane96mcp.survivalreimagined.module.experience.enchanting;

import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.survivalreimagined.base.SimpleBlockWithItem;
import insane96mcp.survivalreimagined.module.Modules;
import insane96mcp.survivalreimagined.module.misc.DataPacks;
import insane96mcp.survivalreimagined.setup.IntegratedDataPack;
import insane96mcp.survivalreimagined.setup.SRBlockEntityTypes;
import insane96mcp.survivalreimagined.setup.SRMenuType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Enchanting", description = "Change some enchanting related stuff.")
@LoadFeature(module = Modules.Ids.EXPERIENCE)
public class EnchantingFeature extends Feature {
	@Config
	@Label(name = "Nether Enchanting Table", description = "Enables a data pack that makes the enchanting table require nether access to be made")
	public static Boolean netherEnchantingTable = true;
	public static final SimpleBlockWithItem ENSORCELLER = SimpleBlockWithItem.register("ensorceller", () -> new EnsorcellerBlock(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE).strength(3.5f)));
	public static final RegistryObject<BlockEntityType<EnsorcellerBlockEntity>> ENSORCELLER_BLOCK_ENTITY_TYPE = SRBlockEntityTypes.REGISTRY.register("ensorceller", () -> BlockEntityType.Builder.of(EnsorcellerBlockEntity::new, ENSORCELLER.block().get()).build(null));
	public static final RegistryObject<MenuType<EnsorcellerMenu>> ENSORCELLER_MENU_TYPE = SRMenuType.REGISTRY.register("ensorceller", () -> new MenuType<>(EnsorcellerMenu::new, FeatureFlags.VANILLA_SET));

	public EnchantingFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedDataPack.INTEGRATED_DATA_PACKS.add(new IntegratedDataPack(PackType.SERVER_DATA, "nether_enchanting_table", Component.literal("Survival Reimagined Nether Enchanting Table"), () -> this.isEnabled() && !DataPacks.disableAllDataPacks && netherEnchantingTable));
	}
}