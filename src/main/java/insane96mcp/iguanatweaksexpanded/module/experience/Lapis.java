package insane96mcp.iguanatweaksexpanded.module.experience;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.setup.SRRegistries;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.data.lootmodifier.InjectLootTableModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Lapis", description = "New lapis for better enchanting.")
@LoadFeature(module = Modules.Ids.EXPERIENCE)
public class Lapis extends Feature {

	public static final RegistryObject<Item> CLEANSED_LAPIS = SRRegistries.ITEMS.register("cleansed_lapis", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ANCIENT_LAPIS = SRRegistries.ITEMS.register("ancient_lapis", () -> new Item(new Item.Properties().fireResistant()));

	public Lapis(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	@SubscribeEvent
	public void onAnvilUpdate(final AnvilUpdateEvent event) {
		if (!this.isEnabled())
			return;

		ItemStack left = event.getLeft();
		if (!left.isEnchantable() || left.isEnchanted())
			return;

		ItemStack right = event.getRight();
		if (!right.is(CLEANSED_LAPIS.get()))
			return;
		event.setCost(0);
		event.setMaterialCost(1);
		ItemStack result = left.copy();
		left.getOrCreateTag().putBoolean(EnchantingFeature.INFUSED_ITEM, true);
		event.setOutput(result);
	}

	private static final String path = "experience/lapis";

	public static void addGlobalLoot(GlobalLootModifierProvider provider) {
		provider.add(path + "blocks/lapis_ore", new InjectLootTableModifier(new ResourceLocation("minecraft:blocks/lapis_ore"), new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "blocks/injection/cleansed_lapis")));
		provider.add(path + "blocks/deepslate_lapis_ore", new InjectLootTableModifier(new ResourceLocation("minecraft:blocks/deepslate_lapis_ore"), new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "blocks/injection/cleansed_lapis")));
	}
}
