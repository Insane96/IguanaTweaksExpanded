package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.IguanaTweaksReborn;
import insane96mcp.iguanatweaksreborn.module.experience.PlayerExperience;
import insane96mcp.iguanatweaksreborn.module.experience.anvils.Anvils;
import insane96mcp.insanelib.base.JsonFeature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.data.IdTagValue;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Label(name = "Enchanting", description = "Adds a brand new enchanting table. If this feature is enabled a data pack is also enabled that changes the enchanting table recipe. Items in iguanatweaksexpanded:not_enchantable tag cannot be enchanted.")
@LoadFeature(module = Modules.Ids.EXPERIENCE)
public class EnchantingFeature extends JsonFeature {
    public static final TagKey<Item> NOT_ENCHANTABLE = ITEItemTagsProvider.create("not_enchantable");
	public static final SimpleBlockWithItem ENCHANTING_TABLE = SimpleBlockWithItem.register("enchanting_table", () -> new ITEEnchantingTable(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE)));
	public static final RegistryObject<BlockEntityType<ITEEnchantingTableBlockEntity>> ENCHANTING_TABLE_BLOCK_ENTITY = ITERegistries.BLOCK_ENTITY_TYPES.register("enchanting_table", () -> BlockEntityType.Builder.of(ITEEnchantingTableBlockEntity::new, ENCHANTING_TABLE.block().get()).build(null));
    public static final RegistryObject<MenuType<ITEEnchantingTableMenu>> ENCHANTING_TABLE_MENU_TYPE = ITERegistries.MENU_TYPES.register("enchanting_table", () -> new MenuType<>(ITEEnchantingTableMenu::new, FeatureFlags.VANILLA_SET));

    public static final String INFUSED_ITEM = IguanaTweaksExpanded.RESOURCE_PREFIX + "infused";
    public static final String EMPOWERED_ITEM = IguanaTweaksExpanded.RESOURCE_PREFIX + "empowered";
    @Config
    @Label(name = "No enchantment merge", description = "Enchanted items can no longer be merged with other enchanted items (also applies to enchanted books).")
    public static Boolean noEnchantmentMerge = true;
    @Config
    @Label(name = "No enchanted smithing", description = "Enchanted items can no longer be upgraded (e.g. netherite)")
    public static Boolean noEnchantedSmithing = true;
    @Config
    @Label(name = "Better grindstone xp", description = "If true, grindstone will give XP based off the new enchanting table. This is based off the ITR levelScalingFormula set to a fixed value")
    public static Boolean betterGrindstoneXp = true;

    public static final RegistryObject<Item> CLEANSED_LAPIS = ITERegistries.ITEMS.register("cleansed_lapis", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_LAPIS = ITERegistries.ITEMS.register("ancient_lapis", () -> new Item(new Item.Properties().fireResistant()));

    public static final List<IdTagValue> DEFAULT_ENCHANTMENT_BASE_COST = List.of(
            IdTagValue.newId(IguanaTweaksExpanded.RESOURCE_PREFIX + "expanded", 5f),
            IdTagValue.newId(IguanaTweaksExpanded.RESOURCE_PREFIX + "veining", 4f),
            IdTagValue.newId(IguanaTweaksExpanded.RESOURCE_PREFIX + "blasting", 1.8f),
            IdTagValue.newId(IguanaTweaksExpanded.RESOURCE_PREFIX + "burst_of_arrows", 8f),
            IdTagValue.newId(IguanaTweaksReborn.RESOURCE_PREFIX + "luck", 3.2f),
            IdTagValue.newId("minecraft:multishot", 5f),
            IdTagValue.newId("minecraft:quick_charge", 3f),
            IdTagValue.newId("minecraft:power", 2.2f),
            IdTagValue.newId("minecraft:piercing", 1.4f),
            IdTagValue.newId("minecraft:infinity", 2.5f),
            IdTagValue.newId("minecraft:soul_speed", 3f),
            IdTagValue.newId("minecraft:efficiency", 1.8f),
            IdTagValue.newId("minecraft:mending", 5f),
            IdTagValue.newId("minecraft:vanishing_curse", 2f),
            IdTagValue.newId("shieldsplus:aegis", 2f),
            IdTagValue.newId("shieldsplus:reinforced", 2f),
            IdTagValue.newId("shieldsplus:shield_bash", 4f)
    );
    public static final ArrayList<IdTagValue> enchantmentBaseCost = new ArrayList<>();

	public EnchantingFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "new_enchanting_table", Component.literal("IguanaTweaks Expanded New Enchanting Table"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks));
        addSyncType(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "enchantments_base_cost"), new SyncType(json -> loadAndReadJson(json, enchantmentBaseCost, DEFAULT_ENCHANTMENT_BASE_COST, IdTagValue.LIST_TYPE)));
        JSON_CONFIGS.add(new JsonConfig<>("enchantments_base_cost.json", enchantmentBaseCost, DEFAULT_ENCHANTMENT_BASE_COST, IdTagValue.LIST_TYPE, true, new ResourceLocation(IguanaTweaksExpanded.RESOURCE_PREFIX + "enchantments_base_cost")));
	}

    @Override
    public String getModConfigFolder() {
        return IguanaTweaksReborn.CONFIG_FOLDER;
    }

    @Override
    public void loadJsonConfigs() {
        super.loadJsonConfigs();
        /*for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
            if (EnchantmentsFeature.isEnchantmentDisabled(enchantment))
                continue;
            int maxLvl = enchantment.getMaxLevel();
            if (maxLvl > 1)
                maxLvl++;
            for (int i = 1; i <= maxLvl; i++) {
                LogHelper.debug("%s %d: %.1f", ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString(), i, getCost(enchantment, i));
            }
        }*/
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!this.isEnabled())
            return;
        preventMergingEnchantedItems(event);
        cleansedLapis(event);
        ancientLapis(event);
    }

    public void preventMergingEnchantedItems(AnvilUpdateEvent event) {
        if (!noEnchantmentMerge)
            return;

        if (event.getRight().isEnchanted() || (event.getLeft().isEnchanted() && event.getRight().is(Items.ENCHANTED_BOOK)))
            event.setCanceled(true);
    }

    public void cleansedLapis(final AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        if (!left.getItem().isEnchantable(left) || left.getTag() == null || left.getTag().contains(EnchantingFeature.INFUSED_ITEM))
            return;

        ItemStack right = event.getRight().copy();
        if (!right.is(CLEANSED_LAPIS.get()))
            return;
        event.setCost(0);
        event.setMaterialCost(1);
        ItemStack result = left.copy();
        result.getOrCreateTag().putBoolean(EnchantingFeature.INFUSED_ITEM, true);
        event.setOutput(result);
    }

    public void ancientLapis(final AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        if (!left.getItem().isEnchantable(left) || left.getTag() == null || left.getTag().contains(EnchantingFeature.EMPOWERED_ITEM))
            return;

        ItemStack right = event.getRight().copy();
        if (!right.is(ANCIENT_LAPIS.get()))
            return;
        event.setCost(0);
        event.setMaterialCost(1);
        ItemStack result = left.copy();
        result.getOrCreateTag().putBoolean(EnchantingFeature.EMPOWERED_ITEM, true);
        event.setOutput(result);
    }

    @SubscribeEvent
    public void onGrindstoneTake(GrindstoneEvent.OnTakeItem event) {
        if (!this.isEnabled()
                || !betterGrindstoneXp)
            return;

        float lvl = 0;
        for (Map.Entry<Enchantment, Integer> enchantment : EnchantmentHelper.getEnchantments(event.getTopItem()).entrySet()) {
            if (enchantment.getKey().isCurse())
                continue;
            lvl += getCost(enchantment.getKey(), enchantment.getValue());
        }
        for (Map.Entry<Enchantment, Integer> enchantment : EnchantmentHelper.getEnchantments(event.getBottomItem()).entrySet()) {
            if (enchantment.getKey().isCurse())
                continue;
            lvl += getCost(enchantment.getKey(), enchantment.getValue());
        }
        lvl = (int)Math.floor(lvl);

        event.setXp((int) (lvl * PlayerExperience.getBetterScalingLevel(30) * 0.8f));
    }

    public static int getCost(Enchantment enchantment, int lvl) {
        if (lvl <= 0)
            return 0;
        float baseCost = Anvils.getRarityCost(enchantment);
        for (IdTagValue enchantmentCost : enchantmentBaseCost) {
            if (enchantmentCost.id.matchesEnchantment(enchantment))
                baseCost = (float) enchantmentCost.value;
        }
        return (int) Math.round(baseCost * Math.pow(lvl, 1.11));
    }

    public static boolean canBeEnchanted(ItemStack stack) {
        return stack.getItem().isEnchantable(stack) && (!stack.isEnchanted() || hasOnlyCurses(stack)) && !stack.is(EnchantingFeature.NOT_ENCHANTABLE);
    }

    public static boolean hasOnlyCurses(ItemStack stack) {
        if (!stack.isEnchanted())
            return false;
        for (Map.Entry<Enchantment, Integer> enchantment : stack.getAllEnchantments().entrySet()) {
            if (!enchantment.getKey().isCurse())
                return false;
        }
        return true;
    }

    public static boolean hasCurses(ItemStack stack) {
        if (!stack.isEnchanted())
            return false;
        for (Map.Entry<Enchantment, Integer> enchantment : stack.getAllEnchantments().entrySet()) {
            if (enchantment.getKey().isCurse())
                return true;
        }
        return false;
    }

    public static float getCurseCost(ItemStack stack) {
        float cost = 0f;
        for (Map.Entry<Enchantment, Integer> enchantment : stack.getAllEnchantments().entrySet()) {
            if (enchantment.getKey().isCurse())
                cost += getCost(enchantment.getKey(), 1);
        }
        return cost;
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (!isEnabled(EnchantingFeature.class)
                || itemStack.getTag() == null
                || !(event.getEntity() instanceof Player))
            return;

        if (itemStack.is(Items.ENCHANTED_BOOK)) {
            for (Map.Entry<Enchantment, Integer> enchantment : EnchantmentHelper.getEnchantments(itemStack).entrySet()) {
                if (enchantment.getKey().isTreasureOnly() && !enchantment.getKey().isCurse()) {
                    event.getToolTip().add(Component.empty());
                    event.getToolTip().add(Component.translatable("iguanatweaksexpanded.apply_to_enchanting_table").withStyle(ChatFormatting.GREEN));
                    break;
                }
            }
        }

        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof AnvilScreen) && !(mc.screen instanceof ITEEnchantingTableScreen))
            return;

        if (itemStack.getTag().contains(INFUSED_ITEM)) {
            event.getToolTip().add(Component.empty());
            event.getToolTip().add(Component.translatable("iguanatweaksexpanded.infused_item").withStyle(ChatFormatting.DARK_PURPLE));
        }
        if (itemStack.getTag().contains(EMPOWERED_ITEM)) {
            event.getToolTip().add(Component.translatable("iguanatweaksexpanded.empowered_item").withStyle(ChatFormatting.DARK_PURPLE));
        }

        if (itemStack.getTag().contains("PendingEnchantments") && !(mc.screen instanceof GrindstoneScreen) && canBeEnchanted(itemStack)) {
            event.getToolTip().add(Component.literal("Has pending enchantments").withStyle(ChatFormatting.DARK_GRAY));
            if (mc.screen instanceof ITEEnchantingTableScreen) {
                ListTag enchantmentsListTag = itemStack.getTag().getList("PendingEnchantments", CompoundTag.TAG_COMPOUND);
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