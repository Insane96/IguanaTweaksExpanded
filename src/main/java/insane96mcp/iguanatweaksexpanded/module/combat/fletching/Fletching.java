package insane96mcp.iguanatweaksexpanded.module.combat.fletching;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.block.ITEFletchingTableBlock;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.crafting.FletchingRecipe;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.data.FletchingRecipeSerializer;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.entity.projectile.DiamondArrow;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.entity.projectile.ExplosiveArrow;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.entity.projectile.QuartzArrow;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.entity.projectile.TorchArrow;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.inventory.FletchingMenu;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.item.ITEArrowItem;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Fletching table", description = "Gives a use to the fletching table.")
@LoadFeature(module = Modules.Ids.COMBAT)
public class Fletching extends Feature {
	public static final SimpleBlockWithItem FLETCHING_TABLE = SimpleBlockWithItem.register("fletching_table", () -> new ITEFletchingTableBlock(BlockBehaviour.Properties.copy(Blocks.FLETCHING_TABLE)));

	public static final RegistryObject<RecipeType<FletchingRecipe>> FLETCHING_RECIPE_TYPE = ITERegistries.RECIPE_TYPES.register("fletching", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return "fletching";
		}
	});
	public static final RegistryObject<FletchingRecipeSerializer> FLETCHING_RECIPE_SERIALIZER = ITERegistries.RECIPE_SERIALIZERS.register("fletching", FletchingRecipeSerializer::new);
	public static final RegistryObject<MenuType<FletchingMenu>> FLETCHING_MENU_TYPE = ITERegistries.MENU_TYPES.register("fletching", () -> new MenuType<>(FletchingMenu::new, FeatureFlags.VANILLA_SET));

	public static final RegistryObject<EntityType<Arrow>> QUARTZ_ARROW = ITERegistries.ENTITY_TYPES.register("quartz_arrow", () ->
			EntityType.Builder.<Arrow>of(QuartzArrow::new, MobCategory.MISC)
					.sized(0.5F, 0.5F)
					.clientTrackingRange(4)
					.updateInterval(3)
					.build("quartz_arrow"));

	public static final RegistryObject<EntityType<Arrow>> DIAMOND_ARROW = ITERegistries.ENTITY_TYPES.register("diamond_arrow", () ->
			EntityType.Builder.<Arrow>of(DiamondArrow::new, MobCategory.MISC)
					.sized(0.5F, 0.5F)
					.clientTrackingRange(4)
					.updateInterval(3)
					.build("diamond_arrow"));

	public static final RegistryObject<EntityType<ExplosiveArrow>> EXPLOSIVE_ARROW = ITERegistries.ENTITY_TYPES.register("explosive_arrow", () ->
			EntityType.Builder.<ExplosiveArrow>of(ExplosiveArrow::new, MobCategory.MISC)
					.sized(0.5F, 0.5F)
					.clientTrackingRange(4)
					.updateInterval(3)
					.build("explosive_arrow"));

	public static final RegistryObject<EntityType<TorchArrow>> TORCH_ARROW = ITERegistries.ENTITY_TYPES.register("torch_arrow", () ->
			EntityType.Builder.<TorchArrow>of(TorchArrow::new, MobCategory.MISC)
					.sized(0.5F, 0.5F)
					.clientTrackingRange(4)
					.updateInterval(3)
					.build("torch_arrow"));

	public static final RegistryObject<ITEArrowItem> QUARTZ_ARROW_ITEM = ITERegistries.ITEMS.register("quartz_arrow", () -> new ITEArrowItem(QUARTZ_ARROW::get, 2f, new Item.Properties()));
	public static final RegistryObject<ITEArrowItem> DIAMOND_ARROW_ITEM = ITERegistries.ITEMS.register("diamond_arrow", () -> new ITEArrowItem(DIAMOND_ARROW::get, 3f, new Item.Properties()));
	public static final RegistryObject<ITEArrowItem> EXPLOSIVE_ARROW_ITEM = ITERegistries.ITEMS.register("explosive_arrow", () -> new ITEArrowItem(EXPLOSIVE_ARROW::get, 0f, new Item.Properties()));
	public static final RegistryObject<ITEArrowItem> TORCH_ARROW_ITEM = ITERegistries.ITEMS.register("torch_arrow", () -> new ITEArrowItem(TORCH_ARROW::get, 2f, new Item.Properties()));

	@Config
	@Label(name = "Fletching Data Pack", description = """
			Enables the following changes:
			* Replaces the vanilla fletching table recipe with the mod's one
			* Adds more arrows recipes""")
	public static Boolean dataPack = true;

	public Fletching(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "fletching", Component.literal("IguanaTweaks Expanded Fletching"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && dataPack));
	}
}