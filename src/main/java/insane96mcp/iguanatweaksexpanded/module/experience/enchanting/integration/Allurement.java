package insane96mcp.iguanatweaksexpanded.module.experience.enchanting.integration;

import insane96mcp.iguanatweaksreborn.data.generator.ITRItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Allurement {
    public static final TagKey<Item> ACCEPTS_LAUNCH_ENCHANTMENT = ITRItemTagsProvider.create("enchanting/allurement/accepts_launch_enchantments");
    public static final EnchantmentCategory LAUNCH_CATEGORY = EnchantmentCategory.create("launch_enchantment", item -> item.builtInRegistryHolder().is(ACCEPTS_LAUNCH_ENCHANTMENT));
}
