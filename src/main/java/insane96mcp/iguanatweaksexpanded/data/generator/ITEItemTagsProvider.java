package insane96mcp.iguanatweaksexpanded.data.generator;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.integration.Allurement;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.*;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse.CurseOfDumbness;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse.CurseOfShortArm;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse.CurseOfSlowStrike;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse.CurseOfTheVoid;
import insane96mcp.iguanatweaksexpanded.module.items.copper.CopperExpansion;
import insane96mcp.iguanatweaksexpanded.module.items.flintexpansion.FlintExpansion;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.Solarium;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.durium.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.quaron.Quaron;
import insane96mcp.iguanatweaksexpanded.module.world.coalfire.CoalCharcoal;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.Luck;
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
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ITEItemTagsProvider extends ItemTagsProvider {

    public static final TagKey<Item> WOODEN_HAND_EQUIPMENT = ITEItemTagsProvider.create("equipment/hand/wooden");
    public static final TagKey<Item> STONE_HAND_EQUIPMENT = ITEItemTagsProvider.create("equipment/hand/stone");
    public static final TagKey<Item> FLINT_HAND_EQUIPMENT = ITEItemTagsProvider.create("equipment/hand/flint");
    public static final TagKey<Item> COPPER_HAND_EQUIPMENT = ITEItemTagsProvider.create("equipment/hand/copper");
    public static final TagKey<Item> CHAINED_COPPER_ARMOR = ITEItemTagsProvider.create("equipment/armor/chained_copper");
    public static final TagKey<Item> FORGE_HAMMERS = ITEItemTagsProvider.create("equipment/forge_hammers");

    public ITEItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, tagLookupCompletableFuture, modId, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantingFeature.NOT_ENCHANTABLE)
                .add(Items.BOOK, Items.FLINT_AND_STEEL, Items.SHEARS, Items.BRUSH, Items.ELYTRA)
                .add(CoalCharcoal.FIRESTARTER.get());
        tag(Smartness.ACCEPTS_ENCHANTMENT)
                .addTags(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT)
                .add(Items.FISHING_ROD, Quaron.FISHING_ROD.get());
        tag(Reach.ACCEPTS_ENCHANTMENT)
                .addTag(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT)
                .addTag(Tags.Items.ARMORS_CHESTPLATES);
        tag(PartBreaker.ACCEPTS_ENCHANTMENT)
                .addTag(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT);
        tag(Padding.ACCEPTS_ENCHANTMENT)
                .addTag(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT);
        tag(AirStealer.ACCEPTS_ENCHANTMENT)
                .addTags(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT);
        tag(Adrenaline.ACCEPTS_ENCHANTMENT)
                .addTags(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT);
        tag(CurseOfDumbness.ACCEPTS_ENCHANTMENT)
                .addTag(Smartness.ACCEPTS_ENCHANTMENT);
        tag(CurseOfShortArm.ACCEPTS_ENCHANTMENT)
                .addTag(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT);
        tag(CurseOfSlowStrike.ACCEPTS_ENCHANTMENT)
                .addTag(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT);
        tag(CurseOfTheVoid.ACCEPTS_ENCHANTMENT)
                .addTag(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT)
                .add(Items.FISHING_ROD, Quaron.FISHING_ROD.get());
        tag(Allurement.ACCEPTS_LAUNCH_ENCHANTMENT)
                .addTag(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT);
        tag(FORGE_HAMMERS)
                .add(Forging.WOODEN_HAMMER.get(), Forging.STONE_HAMMER.get(), Forging.FLINT_HAMMER.get(), Forging.COPPER_HAMMER.get(), Forging.GOLDEN_HAMMER.get(), Forging.IRON_HAMMER.get(), Forging.COATED_COPPER_HAMMER.get(), Forging.SOLARIUM_HAMMER.get(), Forging.GOLDEN_HAMMER.get(), Forging.KEEGO_HAMMER.get(), Forging.DIAMOND_HAMMER.get(), Forging.SOUL_STEEL_HAMMER.get(), Forging.QUARON_HAMMER.get(), Forging.NETHERITE_HAMMER.get());
        //ITR
        tag(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT)
                .addTag(FORGE_HAMMERS);
        tag(Luck.ACCEPTS_ENCHANTMENT)
                .add(Quaron.FISHING_ROD.get());
        tag(ItemStats.NOT_UNBREAKABLE)
                .add(CoalCharcoal.FIRESTARTER.get());
        //Vanilla
        tag(ItemTags.PICKAXES).add(FlintExpansion.PICKAXE.get(), Solarium.PICKAXE.get(), Durium.PICKAXE.get(), CopperExpansion.COPPER_PICKAXE.get(), CopperExpansion.COATED_PICKAXE.get(), SoulSteel.PICKAXE.get(), Keego.PICKAXE.get(), Quaron.PICKAXE.get());
        tag(ItemTags.AXES).add(FlintExpansion.AXE.get(), Solarium.AXE.get(), Durium.AXE.get(), CopperExpansion.COPPER_AXE.get(), CopperExpansion.COATED_AXE.get(), SoulSteel.AXE.get(), Keego.AXE.get(), Quaron.AXE.get());
        tag(ItemTags.SHOVELS).add(FlintExpansion.SHOVEL.get(), Solarium.SHOVEL.get(), Durium.SHOVEL.get(), CopperExpansion.COPPER_SHOVEL.get(), CopperExpansion.COATED_SHOVEL.get(), SoulSteel.SHOVEL.get(), Keego.SHOVEL.get(), Quaron.SHOVEL.get());
        tag(ItemTags.SWORDS).add(FlintExpansion.SWORD.get(), Solarium.SWORD.get(), Durium.SWORD.get(), CopperExpansion.COPPER_SWORD.get(), CopperExpansion.COATED_SWORD.get(), SoulSteel.SWORD.get(), Keego.SWORD.get(), Quaron.SWORD.get());
        tag(ItemTags.HOES).add(FlintExpansion.HOE.get(), Solarium.HOE.get(), Durium.HOE.get(), CopperExpansion.COPPER_HOE.get(), CopperExpansion.COATED_HOE.get(), SoulSteel.HOE.get(), Keego.HOE.get(), Quaron.SHOVEL.get());

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(Solarium.HELMET.get(), Solarium.CHESTPLATE.get(), Solarium.LEGGINGS.get(), Solarium.BOOTS.get())
                .add(Durium.HELMET.get(), Durium.CHESTPLATE.get(), Durium.LEGGINGS.get(), Durium.BOOTS.get())
                .add(CopperExpansion.HELMET.get(), CopperExpansion.CHESTPLATE.get(), CopperExpansion.LEGGINGS.get(), CopperExpansion.BOOTS.get())
                .add(SoulSteel.HELMET.get(), SoulSteel.CHESTPLATE.get(), SoulSteel.LEGGINGS.get(), SoulSteel.BOOTS.get())
                .add(Keego.HELMET.get(), Keego.CHESTPLATE.get(), Keego.LEGGINGS.get(), Keego.BOOTS.get())
                .add(Quaron.HELMET.get(), Quaron.CHESTPLATE.get(), Quaron.LEGGINGS.get(), Quaron.BOOTS.get());

        tag(ItemTags.ARROWS).add(Fletching.QUARTZ_ARROW_ITEM.get(), Fletching.DIAMOND_ARROW_ITEM.get(), Fletching.EXPLOSIVE_ARROW_ITEM.get(), Fletching.TORCH_ARROW_ITEM.get(), Fletching.ICE_ARROW_ITEM.get());

        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(Items.NETHER_STAR).add(Durium.INGOT.get(), SoulSteel.INGOT.get(), Keego.GEM.get(), Quaron.INGOT.get());

        //Forge
        tag(Tags.Items.ARMORS_HELMETS)
                .add(Solarium.HELMET.get(), Durium.HELMET.get(), CopperExpansion.HELMET.get(), SoulSteel.HELMET.get(), Keego.HELMET.get(), Quaron.HELMET.get());
        tag(Tags.Items.ARMORS_CHESTPLATES)
                .add(Solarium.CHESTPLATE.get(), Durium.CHESTPLATE.get(), CopperExpansion.CHESTPLATE.get(), SoulSteel.CHESTPLATE.get(), Keego.CHESTPLATE.get(), Quaron.CHESTPLATE.get());
        tag(Tags.Items.ARMORS_LEGGINGS)
                .add(Solarium.LEGGINGS.get(), Durium.LEGGINGS.get(), CopperExpansion.LEGGINGS.get(), SoulSteel.LEGGINGS.get(), Keego.LEGGINGS.get(), Quaron.LEGGINGS.get());
        tag(Tags.Items.ARMORS_BOOTS)
                .add(Solarium.BOOTS.get(), Durium.BOOTS.get(), CopperExpansion.BOOTS.get(), SoulSteel.BOOTS.get(), Keego.BOOTS.get(), Quaron.BOOTS.get());

        tag(Tags.Items.TOOLS_FISHING_RODS)
                .add(Quaron.FISHING_ROD.get());
        tag(Tags.Items.SHEARS)
                .add(Durium.SHEARS.get());
    }

    public static TagKey<Item> create(String tagName) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, tagName));
    }
}
