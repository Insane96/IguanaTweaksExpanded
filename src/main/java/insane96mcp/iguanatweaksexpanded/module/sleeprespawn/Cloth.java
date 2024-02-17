package insane96mcp.iguanatweaksexpanded.module.sleeprespawn;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Cloth", description = "Zombies can drop cloth instead of Rotten Flesh. Cloth is used to make beds.")
@LoadFeature(module = Modules.Ids.SLEEP_RESPAWN)
public class Cloth extends Feature {

	public static final RegistryObject<Item> CLOTH = ITERegistries.ITEMS.register("cloth", () -> new Item(new Item.Properties()));

	@Config
	@Label(name = "Data Pack", description = "Enables a Data Pack that makes zombies drop cloth instead of rotten flesh and beds require Cloth to be crafted.")
	public static Boolean dataPack = true;

	public Cloth(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "cloth", Component.literal("IguanaTweaks Expanded Cloth"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && dataPack));
	}
}