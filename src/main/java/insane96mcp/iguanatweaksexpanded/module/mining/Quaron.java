package insane96mcp.iguanatweaksexpanded.module.mining;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.item.ITEArmorMaterial;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.item.ILItemTier;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;

@Label(name = "Quaron", description = "Add Quaron, a new metal made by alloying Iron and Amethyst")
@LoadFeature(module = Modules.Ids.MINING)
public class Quaron extends Feature {
	public static final TagKey<Item> TOOL_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "equipment/hand/tools/quaron"));

	public static final SimpleBlockWithItem BLOCK = SimpleBlockWithItem.register("quaron_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 7.0F).sound(SoundType.METAL)));

	public static final RegistryObject<Item> INGOT = ITERegistries.ITEMS.register("quaron_ingot", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> NUGGET = ITERegistries.ITEMS.register("quaron_nugget", () -> new Item(new Item.Properties()));

	public static final ILItemTier ITEM_TIER = new ILItemTier(3, 1942, 6f, 2.5f, 11, () -> Ingredient.of(INGOT.get()));

	public static final RegistryObject<Item> SWORD = ITERegistries.ITEMS.register("quaron_sword", () -> new SwordItem(ITEM_TIER, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> SHOVEL = ITERegistries.ITEMS.register("quaron_shovel", () -> new ShovelItem(ITEM_TIER, 1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> PICKAXE = ITERegistries.ITEMS.register("quaron_pickaxe", () -> new PickaxeItem(ITEM_TIER, 1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> AXE = ITERegistries.ITEMS.register("quaron_axe", () -> new AxeItem(ITEM_TIER, 6.0F, -3.1F, new Item.Properties()));
	public static final RegistryObject<Item> HOE = ITERegistries.ITEMS.register("quaron_hoe", () -> new HoeItem(ITEM_TIER, -2, -1.0F, new Item.Properties()));

	private static final ITEArmorMaterial ARMOR_MATERIAL = new ITEArmorMaterial(IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron", 20, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 2);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 5);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 6);
		p_266652_.put(ArmorItem.Type.HELMET, 2);
	}), 6, SoundEvents.ARMOR_EQUIP_IRON, 0f, 0f, () -> Ingredient.of(INGOT.get()));

	public static final RegistryObject<Item> HELMET = ITERegistries.ITEMS.register("quaron_helmet", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> CHESTPLATE = ITERegistries.ITEMS.register("quaron_chestplate", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> LEGGINGS = ITERegistries.ITEMS.register("quaron_leggings", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> BOOTS = ITERegistries.ITEMS.register("quaron_boots", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final SPShieldMaterial SHIELD_MATERIAL = new SPShieldMaterial("quaron", 441, INGOT, 9, Rarity.COMMON);

	public static final RegistryObject<SPShieldItem> SHIELD = ITERegistries.registerShield("quaron_shield", SHIELD_MATERIAL);

	public Quaron(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBlockBreak(PlayerEvent.BreakSpeed event) {
		if (!this.isEnabled()
				|| !event.getEntity().getMainHandItem().is(TOOL_EQUIPMENT)
				|| event.getState().requiresCorrectToolForDrops())
			return;

		int effLevel = event.getEntity().getMainHandItem().getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY);
		float bonus = EnchantmentsFeature.applyMiningSpeedModifiers(1f + (effLevel * 0.25f), false, event.getEntity());
		event.setNewSpeed(event.getOriginalSpeed() + bonus);
	}
}