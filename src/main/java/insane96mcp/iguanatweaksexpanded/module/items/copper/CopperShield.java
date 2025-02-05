package insane96mcp.iguanatweaksexpanded.module.items.copper;

import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.shieldsplus.setup.SPItems;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class CopperShield extends SPShieldItem {
	public static final SPShieldMaterial SHIELD_MATERIAL = new SPShieldMaterial("copper", 134, () -> Items.COPPER_INGOT, 10, Rarity.COMMON);
	public CopperShield(Properties p_43089_) {
		super(SHIELD_MATERIAL, p_43089_);
	}

	@Override
	public int getCooldown(ItemStack stack, @Nullable LivingEntity entity, Level level) {
		int baseCooldown = super.getCooldown(stack, entity, level);
		if (entity == null)
			return baseCooldown;

		int y = CopperExpansion.getNormalizedY(entity.getBlockY(), level);
		return (int) (baseCooldown - (y / 144f * 30));
	}

	public static RegistryObject<SPShieldItem> registerShield(String id) {
		Properties properties = new Properties().durability(SHIELD_MATERIAL.durability).rarity(SHIELD_MATERIAL.rarity);
		RegistryObject<SPShieldItem> shield = ISERegistries.ITEMS.register(id, () -> new CopperShield(properties));
		SPItems.SHIELDS.add(shield);
		return shield;
	}
}
