package insane96mcp.iguanatweaksexpanded.data.generator;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.items.ChainedCopperArmor;
import insane96mcp.iguanatweaksexpanded.module.items.copper.CopperToolsExpansion;
import insane96mcp.iguanatweaksexpanded.module.items.flintexpansion.FlintExpansion;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.Solarium;
import insane96mcp.iguanatweaksexpanded.module.mining.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.world.coalfire.CoalCharcoal;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.iguanatweaksreborn.module.items.itemstats.ItemStats;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ITEItemTagsProvider extends ItemTagsProvider {

    public static final TagKey<Item> WOODEN_HAND_EQUIPMENT = ITEItemTagsProvider.create("equipment/hand/wooden");
    public static final TagKey<Item> STONE_HAND_EQUIPMENT = ITEItemTagsProvider.create("equipment/hand/stone");
    public static final TagKey<Item> FLINT_HAND_EQUIPMENT = ITEItemTagsProvider.create("equipment/hand/flint");
    public static final TagKey<Item> COPPER_HAND_EQUIPMENT = ITEItemTagsProvider.create("equipment/hand/copper");
    public static final TagKey<Item> CHAINED_COPPER_ARMOR = ITEItemTagsProvider.create("equipment/armor/chained_copper");
    public static final TagKey<Item> HAMMERS = ITEItemTagsProvider.create("equipment/hammers");

    public ITEItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, tagLookupCompletableFuture, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantingFeature.NOT_ENCHANTABLE)
                .add(Items.BOOK, Items.FLINT_AND_STEEL, Items.SHEARS, Items.BRUSH, Items.ELYTRA)
                .add(CoalCharcoal.FIRESTARTER.get());
        tag(HAMMERS)
                .add(Forging.WOODEN_HAMMER.get(), Forging.STONE_HAMMER.get(), Forging.FLINT_HAMMER.get(), Forging.COPPER_HAMMER.get(), Forging.GOLDEN_HAMMER.get(), Forging.IRON_HAMMER.get(), Forging.SOLARIUM_HAMMER.get(), Forging.GOLDEN_HAMMER.get(), Forging.KEEGO_HAMMER.get(), Forging.DIAMOND_HAMMER.get(), Forging.SOUL_STEEL_HAMMER.get(), Forging.NETHERITE_HAMMER.get());
        //ITR
        tag(BonusDamageEnchantment.ACCEPTS_DAMAGE_ENCHANTMENTS)
                .addTag(HAMMERS);
        tag(ItemStats.NOT_UNBREAKABLE)
                .add(CoalCharcoal.FIRESTARTER.get());
        //Vanilla
        tag(ItemTags.PICKAXES).add(FlintExpansion.PICKAXE.get(), Solarium.PICKAXE.get(), Durium.PICKAXE.get(), CopperToolsExpansion.COPPER_PICKAXE.get(), CopperToolsExpansion.COATED_PICKAXE.get(), SoulSteel.PICKAXE.get(), Keego.PICKAXE.get());
        tag(ItemTags.AXES).add(FlintExpansion.AXE.get(), Solarium.AXE.get(), Durium.AXE.get(), CopperToolsExpansion.COPPER_AXE.get(), CopperToolsExpansion.COATED_AXE.get(), SoulSteel.AXE.get(), Keego.AXE.get());
        tag(ItemTags.SHOVELS).add(FlintExpansion.SHOVEL.get(), Solarium.SHOVEL.get(), Durium.SHOVEL.get(), CopperToolsExpansion.COPPER_SHOVEL.get(), CopperToolsExpansion.COATED_SHOVEL.get(), SoulSteel.SHOVEL.get(), Keego.SHOVEL.get());
        tag(ItemTags.SWORDS).add(FlintExpansion.SWORD.get(), Solarium.SWORD.get(), Durium.SWORD.get(), CopperToolsExpansion.COPPER_SWORD.get(), CopperToolsExpansion.COATED_SWORD.get(), SoulSteel.SWORD.get(), Keego.SWORD.get());
        tag(ItemTags.HOES).add(FlintExpansion.HOE.get(), Solarium.HOE.get(), Durium.HOE.get(), CopperToolsExpansion.COPPER_HOE.get(), CopperToolsExpansion.COATED_HOE.get(), SoulSteel.HOE.get(), Keego.HOE.get());

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(Solarium.HELMET.get(), Solarium.CHESTPLATE.get(), Solarium.LEGGINGS.get(), Solarium.BOOTS.get())
                .add(Durium.HELMET.get(), Durium.CHESTPLATE.get(), Durium.LEGGINGS.get(), Durium.BOOTS.get())
                .add(ChainedCopperArmor.HELMET.get(), ChainedCopperArmor.CHESTPLATE.get(), ChainedCopperArmor.LEGGINGS.get(), ChainedCopperArmor.BOOTS.get())
                .add(SoulSteel.HELMET.get(), SoulSteel.CHESTPLATE.get(), SoulSteel.LEGGINGS.get(), SoulSteel.BOOTS.get())
                .add(Keego.HELMET.get(), Keego.CHESTPLATE.get(), Keego.LEGGINGS.get(), Keego.BOOTS.get());

        tag(ItemTags.ARROWS).add(Fletching.QUARTZ_ARROW_ITEM.get(), Fletching.DIAMOND_ARROW_ITEM.get(), Fletching.EXPLOSIVE_ARROW_ITEM.get(), Fletching.TORCH_ARROW_ITEM.get());

        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(Items.NETHER_STAR).add(Durium.INGOT.get(), SoulSteel.INGOT.get(), Keego.GEM.get());
    }

    public static TagKey<Item> create(String tagName) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, tagName));
    }
}
