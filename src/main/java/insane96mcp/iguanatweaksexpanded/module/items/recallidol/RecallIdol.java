package insane96mcp.iguanatweaksexpanded.module.items.recallidol;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEGlobalLootModifierProvider;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.data.lootmodifier.InjectLootTableModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Recall Idol", description = "Back to base")
@LoadFeature(module = Modules.Ids.ITEMS)
public class RecallIdol extends Feature {
	public static final RegistryObject<Item> ITEM = ITERegistries.ITEMS.register("recall_idol", () -> new RecallIdolItem(new Item.Properties().stacksTo(8)));

	public RecallIdol(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	private static final String path = "item/recall_idol/";

	public static void addGlobalLoot(ITEGlobalLootModifierProvider provider) {
		provider.add(path + "recall_idol", new InjectLootTableModifier(new ResourceLocation("minecraft:chests/end_city_treasure"), new ResourceLocation("iguanatweaksexpanded:chests/injection/recall_idol")));
	}
}