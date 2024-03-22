package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.items.copper.CopperToolsExpansion;
import insane96mcp.iguanatweaksexpanded.module.items.flintexpansion.FlintExpansion;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.Solarium;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.durium.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.quaron.Quaron;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Forging")
@LoadFeature(module = Modules.Ids.MINING)
public class Forging extends Feature {
	public static final SimpleBlockWithItem FORGE = SimpleBlockWithItem.register("forge", () -> new ForgeBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL)));
	public static final RegistryObject<BlockEntityType<ForgeBlockEntity>> FORGE_BLOCK_ENTITY_TYPE = ITERegistries.BLOCK_ENTITY_TYPES.register("forge", () -> BlockEntityType.Builder.of(ForgeBlockEntity::new, FORGE.block().get()).build(null));

	public static final RegistryObject<RecipeType<ForgeRecipe>> FORGE_RECIPE_TYPE = ITERegistries.RECIPE_TYPES.register("forging", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return "forging";
		}
	});
	public static final RegistryObject<ForgeRecipeSerializer> FORGE_RECIPE_SERIALIZER = ITERegistries.RECIPE_SERIALIZERS.register("forging", ForgeRecipeSerializer::new);
	public static final RegistryObject<MenuType<ForgeMenu>> FORGE_MENU_TYPE = ITERegistries.MENU_TYPES.register("forge", () -> new MenuType<>(ForgeMenu::new, FeatureFlags.VANILLA_SET));

	public static final RegistryObject<ForgeHammerItem> WOODEN_HAMMER = ITERegistries.ITEMS.register("wooden_hammer", () -> new ForgeHammerItem(Tiers.WOOD, 35, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> STONE_HAMMER = ITERegistries.ITEMS.register("stone_hammer", () -> new ForgeHammerItem(Tiers.STONE, 30, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> FLINT_HAMMER = ITERegistries.ITEMS.register("flint_hammer", () -> new ForgeHammerItem(FlintExpansion.ITEM_TIER, 25, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> COPPER_HAMMER = ITERegistries.ITEMS.register("copper_hammer", () -> new ForgeHammerItem(CopperToolsExpansion.COPPER_ITEM_TIER, 20, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> GOLDEN_HAMMER = ITERegistries.ITEMS.register("golden_hammer", () -> new ForgeHammerItem(Tiers.GOLD, 8, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> IRON_HAMMER = ITERegistries.ITEMS.register("iron_hammer", () -> new ForgeHammerItem(Tiers.IRON, 25, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> SOLARIUM_HAMMER = ITERegistries.ITEMS.register("solarium_hammer", () -> new SolariumForgeHammerItem(Solarium.ITEM_TIER, 30, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> DURIUM_HAMMER = ITERegistries.ITEMS.register("durium_hammer", () -> new ForgeHammerItem(Durium.ITEM_TIER, 30, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> COATED_COPPER_HAMMER = ITERegistries.ITEMS.register("coated_copper_hammer", () -> new ForgeHammerItem(CopperToolsExpansion.COATED_ITEM_TIER, 25, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> QUARON_HAMMER = ITERegistries.ITEMS.register("quaron_hammer", () -> new ForgeHammerItem(Quaron.ITEM_TIER, 20, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> KEEGO_HAMMER = ITERegistries.ITEMS.register("keego_hammer", () -> new KeegoForgeHammerItem(Keego.ITEM_TIER, 18, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> DIAMOND_HAMMER = ITERegistries.ITEMS.register("diamond_hammer", () -> new ForgeHammerItem(Tiers.DIAMOND, 15, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> SOUL_STEEL_HAMMER = ITERegistries.ITEMS.register("soul_steel_hammer", () -> new ForgeHammerItem(SoulSteel.ITEM_TIER, 20, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> NETHERITE_HAMMER = ITERegistries.ITEMS.register("netherite_hammer", () -> new ForgeHammerItem(Tiers.NETHERITE, 15, new Item.Properties()));

	@Config
	@Label(name = "Unforgable enchanted items", description = "Enchanted items can't be forged")
	public static Boolean unforgableEnchantedItems = true;

	@Config
	@Label(name = "Forging Equipment Crafting Data Pack", description = """
			Enables the following changes to vanilla data pack:
			* All metal gear requires a forge to be made
			* Diamond Gear requires Gold gear to be forged
			* Gold Gear requires Flint / Leather gear to be forged
			* Iron Gear requires Stone / Chained Copper gear to be forged
			* Buckets, Flint and Steel and Shears require a forge to be made""")
	public static Boolean forgingEquipment = true;

	public Forging(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "forging_equipment", Component.literal("IguanaTweaks Expanded Forging Equipment"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && forgingEquipment));
	}
}
