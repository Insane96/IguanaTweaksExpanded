package insane96mcp.iguanatweaksexpanded.module.items.flintexpansion;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.item.ILItemTier;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Flint Expansion", description = "Add flint tools and shield.")
@LoadFeature(module = Modules.Ids.ITEMS, canBeDisabled = false)
public class FlintExpansion extends Feature {

	public static final SPShieldMaterial SHIELD_MATERIAL = new SPShieldMaterial("flint", 33, () -> Items.FLINT, 9, Rarity.COMMON);

	public static final SimpleBlockWithItem FLINT_ROCK = SimpleBlockWithItem.register("flint_rock", () -> new GroundFlintBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 1F).offsetType(BlockBehaviour.OffsetType.XZ).dynamicShape()));

	public static final ILItemTier ITEM_TIER = new ILItemTier(1, 99, 6f, 1.5f, 7, () -> Ingredient.of(Items.FLINT));

	public static final RegistryObject<Item> SWORD = ITERegistries.ITEMS.register("flint_sword", () -> new SwordItem(ITEM_TIER, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> SHOVEL = ITERegistries.ITEMS.register("flint_shovel", () -> new ShovelItem(ITEM_TIER, 1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> PICKAXE = ITERegistries.ITEMS.register("flint_pickaxe", () -> new PickaxeItem(ITEM_TIER, 1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> AXE = ITERegistries.ITEMS.register("flint_axe", () -> new AxeItem(ITEM_TIER, 7.0F, -3.1F, new Item.Properties()));
	public static final RegistryObject<Item> HOE = ITERegistries.ITEMS.register("flint_hoe", () -> new HoeItem(ITEM_TIER, -1, -2.0F, new Item.Properties()));

	public static final RegistryObject<SPShieldItem> SHIELD = ITERegistries.registerShield("flint_shield", SHIELD_MATERIAL);

	@Config
	@Label(name = "Disable Stone Tools", description = "If true, a data pack will be enabled that disables stone tools crafting and generation in chests will be replaced with flint ones")
	public static Boolean disableStoneTools = true;

	public FlintExpansion(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "disable_stone_tools", Component.literal("IguanaTweaks Expanded Disable Stone Tools"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && disableStoneTools));
	}
}