package insane96mcp.iguanatweaksexpanded.module.mining.durium;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.data.generator.ITEBlockTagsProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.item.ITEArmorMaterial;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.item.ILItemTier;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;

@Label(name = "Durium", description = "Add Durium, a new metal made by alloying Durium Scrap (found as scrap pieces in ores in the Overworld) and can be used to upgrade Iron Equipment")
@LoadFeature(module = Modules.Ids.MINING, canBeDisabled = false)
public class Durium extends Feature {

	public static final TagKey<Block> BLOCK_ORES = ITEBlockTagsProvider.create("durium_ores");
	public static final TagKey<Item> ITEM_ORES = ITEItemTagsProvider.create("durium_ores");

	public static final SimpleBlockWithItem ORE = SimpleBlockWithItem.register("durium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(2, 4)));
	public static final SimpleBlockWithItem DEEPSLATE_ORE = SimpleBlockWithItem.register("deepslate_durium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(ORE.block().get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(2, 4)));
	public static final SimpleBlockWithItem SAND_ORE = SimpleBlockWithItem.register("sand_durium_ore", () -> new FallingDuriumOreBlock(BlockBehaviour.Properties.copy(Blocks.SAND).requiresCorrectToolForDrops().strength(1F, 1F), UniformInt.of(2, 4), Blocks.SAND));
	public static final SimpleBlockWithItem GRAVEL_ORE = SimpleBlockWithItem.register("gravel_durium_ore", () -> new FallingDuriumOreBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL).requiresCorrectToolForDrops().strength(1.2F, 1.2F), UniformInt.of(2, 4), Blocks.GRAVEL));
	public static final SimpleBlockWithItem CLAY_ORE = SimpleBlockWithItem.register("clay_durium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.CLAY).requiresCorrectToolForDrops().strength(1.2F, 1.2F), UniformInt.of(2, 4)));
	public static final SimpleBlockWithItem DIRT_ORE = SimpleBlockWithItem.register("dirt_durium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).requiresCorrectToolForDrops().strength(1F, 1F), UniformInt.of(2, 4)));
	public static final SimpleBlockWithItem SCRAP_BLOCK = SimpleBlockWithItem.register("durium_scrap_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 7.0F).sound(SoundType.METAL)));
	public static final RegistryObject<Item> SCRAP_PIECE = ITERegistries.ITEMS.register("durium_scrap_piece", () -> new Item(new Item.Properties()));

	public static final SimpleBlockWithItem BLOCK = SimpleBlockWithItem.register("durium_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 7.0F).sound(SoundType.METAL)));

	public static final RegistryObject<Item> INGOT = ITERegistries.ITEMS.register("durium_ingot", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> NUGGET = ITERegistries.ITEMS.register("durium_nugget", () -> new Item(new Item.Properties()));

	public static final ILItemTier ITEM_TIER = new ILItemTier(2, 570, 6f, 2f, 14, () -> Ingredient.of(INGOT.get()));

	public static final RegistryObject<Item> SWORD = ITERegistries.ITEMS.register("durium_sword", () -> new SwordItem(ITEM_TIER, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> SHOVEL = ITERegistries.ITEMS.register("durium_shovel", () -> new ShovelItem(ITEM_TIER, 1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> PICKAXE = ITERegistries.ITEMS.register("durium_pickaxe", () -> new PickaxeItem(ITEM_TIER, 1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> AXE = ITERegistries.ITEMS.register("durium_axe", () -> new AxeItem(ITEM_TIER, 6.0F, -3.1F, new Item.Properties()));
	public static final RegistryObject<Item> HOE = ITERegistries.ITEMS.register("durium_hoe", () -> new HoeItem(ITEM_TIER, -2, -1.0F, new Item.Properties()));

	private static final ITEArmorMaterial ARMOR_MATERIAL = new ITEArmorMaterial(IguanaTweaksExpanded.RESOURCE_PREFIX + "durium", 20, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 2);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 5);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 6);
		p_266652_.put(ArmorItem.Type.HELMET, 2);
	}), 6, SoundEvents.ARMOR_EQUIP_IRON, 0f, 0f, () -> Ingredient.of(INGOT.get()));

	public static final RegistryObject<Item> HELMET = ITERegistries.ITEMS.register("durium_helmet", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> CHESTPLATE = ITERegistries.ITEMS.register("durium_chestplate", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> LEGGINGS = ITERegistries.ITEMS.register("durium_leggings", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> BOOTS = ITERegistries.ITEMS.register("durium_boots", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final SPShieldMaterial SHIELD_MATERIAL = new SPShieldMaterial("durium", 452, INGOT, 9, Rarity.COMMON);

	public static final RegistryObject<SPShieldItem> SHIELD = ITERegistries.registerShield("durium_shield", SHIELD_MATERIAL);
	public static final RegistryObject<Item> SHEARS = ITERegistries.ITEMS.register("durium_shears", () -> new DuriumShears((new Item.Properties()).durability(252)));

	@Config
	@Label(name = "Durium Lodestone", description = "Enables a data pack that makes Lodestone require Durium instead of Netherite.")
	public static Boolean duriumLodestone = true;

	public Durium(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "durium_lodestone", Component.literal("IguanaTweaks Expanded Durium Lodestone"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && duriumLodestone));
	}
}