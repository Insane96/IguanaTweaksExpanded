package insane96mcp.iguanatweaksexpanded.module.items.solarium.item;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.item.ITEArmorMaterial;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.Solarium;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.EnumMap;

public class SolariumArmorItem extends ArmorItem {

	private static final ITEArmorMaterial ARMOR_MATERIAL = new ITEArmorMaterial(IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium", 12, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 2);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 4);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 5);
		p_266652_.put(ArmorItem.Type.HELMET, 2);
	}), 12, SoundEvents.ARMOR_EQUIP_IRON, 0f, 0f, () -> Ingredient.of(Solarium.SOLARIUM_BALL.get()));
	public SolariumArmorItem(Type pType, Properties pProperties) {
		super(ARMOR_MATERIAL, pType, pProperties);
	}

	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity entity, int pSlotId, boolean pIsSelected) {
		super.inventoryTick(pStack, pLevel, entity, pSlotId, pIsSelected);
		Solarium.healGear(pStack, entity, pLevel);
	}

	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !ItemStack.isSameItem(oldStack, newStack);
	}
}
