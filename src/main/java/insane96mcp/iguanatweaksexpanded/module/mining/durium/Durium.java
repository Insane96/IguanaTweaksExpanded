package insane96mcp.iguanatweaksexpanded.module.mining.durium;

import insane96mcp.iguanatweaksexpanded.InsaneSurvivalExtra;
import insane96mcp.iguanatweaksexpanded.data.generator.ISEBlockTagsProvider;
import insane96mcp.iguanatweaksexpanded.data.generator.ISEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.integration.ShieldsPlusRegistration;
import insane96mcp.iguanatweaksexpanded.item.ISEArmorMaterial;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ISEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.item.ILItemTier;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import net.minecraft.Util;
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

@LoadFeature(module = Modules.Ids.MINING, description = "Add Durium, a new metal made by alloying Durium Scrap (found as scrap pieces in ores in the Overworld) and can be used to upgrade Iron Equipment. Disabling this will disable ore generation and items in the creative inventory.")
public class Durium extends Feature {

	public static final TagKey<Block> BLOCK_ORES = ISEBlockTagsProvider.create("durium_ores");
	public static final TagKey<Item> ITEM_ORES = ISEItemTagsProvider.create("durium_ores");

	public static final SimpleBlockWithItem ORE = SimpleBlockWithItem.register("durium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(2, 4)));
	public static final SimpleBlockWithItem DEEPSLATE_ORE = SimpleBlockWithItem.register("deepslate_durium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(ORE.block().get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(2, 4)));
	public static final SimpleBlockWithItem SAND_ORE = SimpleBlockWithItem.register("sand_durium_ore", () -> new FallingDuriumOreBlock(BlockBehaviour.Properties.copy(Blocks.SAND).requiresCorrectToolForDrops().strength(1F, 1F), UniformInt.of(2, 4), Blocks.SAND));
	public static final SimpleBlockWithItem GRAVEL_ORE = SimpleBlockWithItem.register("gravel_durium_ore", () -> new FallingDuriumOreBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL).requiresCorrectToolForDrops().strength(1.2F, 1.2F), UniformInt.of(2, 4), Blocks.GRAVEL));
	public static final SimpleBlockWithItem CLAY_ORE = SimpleBlockWithItem.register("clay_durium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.CLAY).requiresCorrectToolForDrops().strength(1.2F, 1.2F), UniformInt.of(2, 4)));
	public static final SimpleBlockWithItem DIRT_ORE = SimpleBlockWithItem.register("dirt_durium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).requiresCorrectToolForDrops().strength(1F, 1F), UniformInt.of(2, 4)));
	public static final SimpleBlockWithItem SCRAP_BLOCK = SimpleBlockWithItem.register("durium_scrap_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 7.0F).sound(SoundType.METAL)));
	public static final RegistryObject<Item> SCRAP_PIECE = ISERegistries.ITEMS.register("durium_scrap_piece", () -> new Item(new Item.Properties()));

	public static final SimpleBlockWithItem BLOCK = SimpleBlockWithItem.register("durium_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 7.0F).sound(SoundType.METAL)));

	public static final RegistryObject<Item> INGOT = ISERegistries.ITEMS.register("durium_ingot", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> NUGGET = ISERegistries.ITEMS.register("durium_nugget", () -> new Item(new Item.Properties()));

	public static final ILItemTier ITEM_TIER = new ILItemTier(2, 570, 6f, 2f, 14, () -> Ingredient.of(INGOT.get()));

	public static final RegistryObject<Item> SWORD = ISERegistries.ITEMS.register("durium_sword", () -> new SwordItem(ITEM_TIER, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> SHOVEL = ISERegistries.ITEMS.register("durium_shovel", () -> new ShovelItem(ITEM_TIER, 1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> PICKAXE = ISERegistries.ITEMS.register("durium_pickaxe", () -> new PickaxeItem(ITEM_TIER, 1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> AXE = ISERegistries.ITEMS.register("durium_axe", () -> new AxeItem(ITEM_TIER, 6.0F, -3.1F, new Item.Properties()));
	public static final RegistryObject<Item> HOE = ISERegistries.ITEMS.register("durium_hoe", () -> new HoeItem(ITEM_TIER, -2, -1.0F, new Item.Properties()));

	private static final ISEArmorMaterial ARMOR_MATERIAL = new ISEArmorMaterial(InsaneSurvivalExtra.RESOURCE_PREFIX + "durium", 20, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 2);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 5);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 6);
		p_266652_.put(ArmorItem.Type.HELMET, 2);
	}), 6, SoundEvents.ARMOR_EQUIP_IRON, 0f, 0f, () -> Ingredient.of(INGOT.get()));

	public static final RegistryObject<Item> HELMET = ISERegistries.ITEMS.register("durium_helmet", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> CHESTPLATE = ISERegistries.ITEMS.register("durium_chestplate", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> LEGGINGS = ISERegistries.ITEMS.register("durium_leggings", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> BOOTS = ISERegistries.ITEMS.register("durium_boots", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final RegistryObject<Item> SHEARS = ISERegistries.ITEMS.register("durium_shears", () -> new DuriumShears((new Item.Properties()).durability(328)));

	public Durium(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		InsaneSurvivalExtra.addServerPack("durium", "Insane's Survival Extra Durium", () -> this.isEnabled() && !ISEDataPacks.disableAllDataPacks);
	}

	public static class ShieldsPlusIntegration {
		public static final SPShieldMaterial SHIELD_MATERIAL = new SPShieldMaterial("durium", 452, INGOT, 9, Rarity.COMMON);
		public static final RegistryObject<SPShieldItem> SHIELD = ShieldsPlusRegistration.registerShield("durium_shield", SHIELD_MATERIAL);

		public static void init() {

		}
	}
}