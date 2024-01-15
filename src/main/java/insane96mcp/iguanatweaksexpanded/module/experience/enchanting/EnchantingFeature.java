package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.SRRegistries;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.module.experience.anvils.Anvils;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Enchanting", description = "Change some enchanting related stuff.")
@LoadFeature(module = Modules.Ids.EXPERIENCE)
public class EnchantingFeature extends Feature {
	public static final SimpleBlockWithItem ENCHANTING_TABLE = SimpleBlockWithItem.register("enchanting_table", () -> new SREnchantingTable(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE).strength(3.5f)));
	public static final RegistryObject<BlockEntityType<SREnchantingTableBlockEntity>> ENCHANTING_TABLE_BLOCK_ENTITY = SRRegistries.BLOCK_ENTITY_TYPES.register("enchanting_table", () -> BlockEntityType.Builder.of(SREnchantingTableBlockEntity::new, ENCHANTING_TABLE.block().get()).build(null));

    public static final String INFUSED_ITEM = IguanaTweaksExpanded.RESOURCE_PREFIX + "infused";

    @Config
    @Label(name = "No enchantment merge", description = "Enchanted items can no longer be merged with other enchanted items")
    public static Boolean noEnchantmentMerge = false;

	public static final RegistryObject<MenuType<SREnchantingTableMenu>> ENCHANTING_TABLE_MENU_TYPE = SRRegistries.MENU_TYPES.register("enchanting_table", () -> new MenuType<>(SREnchantingTableMenu::new, FeatureFlags.VANILLA_SET));

	public EnchantingFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

    @Override
    public void readConfig(ModConfigEvent event) {
        super.readConfig(event);
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!this.isEnabled()
                || !noEnchantmentMerge)
            return;

        if (event.getRight().isEnchanted() || (event.getLeft().isEnchanted() && event.getRight().is(Items.ENCHANTED_BOOK)))
            event.setOutput(ItemStack.EMPTY);
    }

    public static float getCost(Enchantment enchantment, int lvl) {
        if (lvl <= 0)
            return 0;
        /*int minCost = enchantment.getMinCost(lvl);
        int maxCost = enchantment.getMaxCost(lvl);
        return (minCost + maxCost) / 20f;*/
        int rarityCost = Anvils.getRarityCost(enchantment);
        return (float) (rarityCost * lvl + (Math.pow(lvl, 0.25f)));
    }
}