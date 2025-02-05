package insane96mcp.iguanatweaksexpanded.module.mining.keego;

import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.shieldsplus.setup.SPItems;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

public class KeegoShield extends SPShieldItem {
    public static final SPShieldMaterial SHIELD_MATERIAL = new SPShieldMaterial("keego", 452, Keego.GEM, 9, Rarity.COMMON);
    public KeegoShield(Properties p_43089_) {
        super(SHIELD_MATERIAL, p_43089_);
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSameItem(oldStack, newStack);
    }

    public static RegistryObject<SPShieldItem> registerShield(String id) {
        Item.Properties properties = new Item.Properties().durability(SHIELD_MATERIAL.durability).rarity(SHIELD_MATERIAL.rarity);
        RegistryObject<SPShieldItem> shield = ISERegistries.ITEMS.register(id, () -> new KeegoShield(properties));
        SPItems.SHIELDS.add(shield);
        return shield;
    }
}
