package insane96mcp.iguanatweaksexpanded.integration;

import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.shieldsplus.setup.SPItems;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ShieldsPlusRegistration {
    public static RegistryObject<SPShieldItem> registerShield(String id, SPShieldMaterial material) {
        Item.Properties properties = new Item.Properties().durability(material.durability).rarity(material.rarity);
        RegistryObject<SPShieldItem> shield = ISERegistries.ITEMS.register(id, () -> new SPShieldItem(material, properties));
        SPItems.SHIELDS.add(shield);
        return shield;
    }
}
