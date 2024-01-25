package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.SRDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedDataPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.module.experience.anvils.Anvils;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.data.lootmodifier.InjectLootTableModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Enchanting", description = "Change some enchanting related stuff. Items in not_enchantable tag cannot be enchanted")
@LoadFeature(module = Modules.Ids.EXPERIENCE)
public class EnchantingFeature extends Feature {
    public static final TagKey<Item> NOT_ENCHANTABLE = ITEItemTagsProvider.create("not_enchantable");
	public static final SimpleBlockWithItem ENCHANTING_TABLE = SimpleBlockWithItem.register("enchanting_table", () -> new SREnchantingTable(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE)));
	public static final RegistryObject<BlockEntityType<SREnchantingTableBlockEntity>> ENCHANTING_TABLE_BLOCK_ENTITY = ITERegistries.BLOCK_ENTITY_TYPES.register("enchanting_table", () -> BlockEntityType.Builder.of(SREnchantingTableBlockEntity::new, ENCHANTING_TABLE.block().get()).build(null));

    public static final String INFUSED_ITEM = IguanaTweaksExpanded.RESOURCE_PREFIX + "infused";
    @Config
    @Label(name = "No enchantment merge", description = "Enchanted items can no longer be merged with other enchanted items")
    public static Boolean noEnchantmentMerge = true;
    @Config
    @Label(name = "No enchanted smithing", description = "Enchanted items can no longer be upgraded (e.g. netherite)")
    public static Boolean noEnchantedSmithing = true;
    @Config
    @Label(name = "Tools enchantability fix", description = "If enabled, the enchantability of the following item is changed to allow enchanting. Tridents, Fishing Rods, Flint and Steel, Brush, ")
    public static Boolean toolsEnchantabilityFix = true;

    public static final RegistryObject<Item> CLEANSED_LAPIS = ITERegistries.ITEMS.register("cleansed_lapis", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_LAPIS = ITERegistries.ITEMS.register("ancient_lapis", () -> new Item(new Item.Properties().fireResistant()));

	public static final RegistryObject<MenuType<SREnchantingTableMenu>> ENCHANTING_TABLE_MENU_TYPE = ITERegistries.MENU_TYPES.register("enchanting_table", () -> new MenuType<>(SREnchantingTableMenu::new, FeatureFlags.VANILLA_SET));

	public EnchantingFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
        IntegratedDataPack.INTEGRATED_DATA_PACKS.add(new IntegratedDataPack(PackType.SERVER_DATA, "enchanting_table", Component.literal("IguanaTweaks Expanded Enchanting Table"), () -> this.isEnabled() && !SRDataPacks.disableAllDataPacks));
	}

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!this.isEnabled())
            return;
        preventMergingEnchantedItems(event);
        cleansedLapis(event);
    }

    public void preventMergingEnchantedItems(AnvilUpdateEvent event) {
        if (!noEnchantmentMerge)
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
        //return (float) (1 + rarityCost * lvl * (Math.pow(lvl, 0.25f)));
        return rarityCost * lvl + (lvl / 4f);
    }

    public void cleansedLapis(final AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        if (!left.isEnchantable() || left.isEnchanted())
            return;

        ItemStack right = event.getRight().copy();
        /*for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
            LogHelper.info(enchantment.getDescriptionId() + ": " + enchantment.getMinCost(1));
        }*/
        if (!right.is(CLEANSED_LAPIS.get()))
            return;
        event.setCost(0);
        event.setMaterialCost(1);
        ItemStack result = left.copy();
        left.getOrCreateTag().putBoolean(EnchantingFeature.INFUSED_ITEM, true);
        event.setOutput(result);
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (!isEnabled(EnchantingFeature.class)
                || event.getItemStack().getTag() == null
                || !(event.getEntity() instanceof Player))
            return;

        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof AnvilScreen) && !(mc.screen instanceof SREnchantingTableScreen))
            return;

        if (event.getItemStack().getTag().contains(INFUSED_ITEM)) {
            event.getToolTip().add(Component.empty());
            event.getToolTip().add(Component.literal("Enchanting Infused").withStyle(ChatFormatting.DARK_PURPLE));
        }

        if (event.getItemStack().getTag().contains("PendingEnchantments") && !(mc.screen instanceof GrindstoneScreen) && !event.getItemStack().isEnchanted()) {
            event.getToolTip().add(Component.literal("Has pending enchantments").withStyle(ChatFormatting.DARK_GRAY));
            if (mc.screen instanceof SREnchantingTableScreen) {
                ListTag enchantmentsListTag = event.getItemStack().getTag().getList("PendingEnchantments", CompoundTag.TAG_COMPOUND);
                for (int i = 0; i < enchantmentsListTag.size(); ++i) {
                    CompoundTag compoundtag = enchantmentsListTag.getCompound(i);
                    Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(compoundtag.getString("id")));
                    short lvl = compoundtag.getShort("lvl");
                    MutableComponent mutablecomponent = CommonComponents.space().append(Component.translatable(enchantment.getDescriptionId())).withStyle(ChatFormatting.DARK_GRAY);
                    if (lvl != 1 || enchantment.getMaxLevel() != 1) {
                        mutablecomponent.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + lvl));
                    }
                    event.getToolTip().add(mutablecomponent);
                }
            }
        }
    }

    private static final String path = "experience/enchanting";
    public static void addGlobalLoot(GlobalLootModifierProvider provider) {
        provider.add(path + "blocks/lapis_ore", new InjectLootTableModifier(new ResourceLocation("minecraft:blocks/lapis_ore"), new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "blocks/injection/cleansed_lapis")));
        provider.add(path + "blocks/deepslate_lapis_ore", new InjectLootTableModifier(new ResourceLocation("minecraft:blocks/deepslate_lapis_ore"), new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "blocks/injection/cleansed_lapis")));
    }
}