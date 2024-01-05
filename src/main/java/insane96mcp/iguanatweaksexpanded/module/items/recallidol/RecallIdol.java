package insane96mcp.iguanatweaksexpanded.module.items.recallidol;

import insane96mcp.iguanatweaksexpanded.data.generator.SRGlobalLootModifierProvider;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.SRRegistries;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.data.lootmodifier.InjectLootTableModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Altimeter", description = "Check your altitude")
@LoadFeature(module = Modules.Ids.ITEMS)
public class RecallIdol extends Feature {
	public static final RegistryObject<Item> ITEM = SRRegistries.ITEMS.register("recall_idol", () -> new RecallIdolItem(new Item.Properties().stacksTo(1)));

	public RecallIdol(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	private static final String path = "item/recall_idol";

	public static void addGlobalLoot(SRGlobalLootModifierProvider provider) {
		provider.add(path + "chests/recall_idol", new InjectLootTableModifier(new ResourceLocation("minecraft:chests/end_city_treasure"), new ResourceLocation("iguanatweaksexpanded:chests/recall_idol")));
	}
}