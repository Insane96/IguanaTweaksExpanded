package insane96mcp.iguanatweaksexpanded.module.items;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.item.ITEArmorMaterial;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;

@Label(name = "Chained Copper Armor", description = "Add Chained Copper Armor")
@LoadFeature(module = Modules.Ids.ITEMS)
public class ChainedCopperArmor extends Feature {

	private static final ITEArmorMaterial CHAINED_COPPER = new ITEArmorMaterial(IguanaTweaksExpanded.RESOURCE_PREFIX + "chained_copper", 10, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 1);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 3);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 4);
		p_266652_.put(ArmorItem.Type.HELMET, 1);
	}), 13, SoundEvents.ARMOR_EQUIP_CHAIN, 0f, 0f, () -> Ingredient.of(Items.COPPER_INGOT));

	public static final RegistryObject<Item> HELMET = ITERegistries.ITEMS.register("chained_copper_helmet", () -> new ArmorItem(CHAINED_COPPER, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> CHESTPLATE = ITERegistries.ITEMS.register("chained_copper_chestplate", () -> new ArmorItem(CHAINED_COPPER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> LEGGINGS = ITERegistries.ITEMS.register("chained_copper_leggings", () -> new ArmorItem(CHAINED_COPPER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> BOOTS = ITERegistries.ITEMS.register("chained_copper_boots", () -> new ArmorItem(CHAINED_COPPER, ArmorItem.Type.BOOTS, new Item.Properties()));


	public ChainedCopperArmor(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}
}