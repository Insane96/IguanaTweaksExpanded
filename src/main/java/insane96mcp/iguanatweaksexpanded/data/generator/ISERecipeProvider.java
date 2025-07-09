package insane96mcp.iguanatweaksexpanded.data.generator;

import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import insane96mcp.iguanatweaksexpanded.InsaneSurvivalExtra;
import insane96mcp.iguanatweaksexpanded.module.items.altimeter.Altimeter;
import insane96mcp.iguanatweaksexpanded.module.items.crate.PortableCrate;
import insane96mcp.iguanatweaksexpanded.module.items.explosivebarrel.ExplosiveBarrel;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.Solarium;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.durium.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.ForgeRecipeBuilder;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.miningcharge.MiningCharge;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.data.MultiItemSmeltingRecipeBuilder;
import insane96mcp.iguanatweaksexpanded.module.mining.oregeneration.BeegOreVeins;
import insane96mcp.iguanatweaksexpanded.module.mining.quaron.Quaron;
import insane96mcp.iguanatweaksexpanded.module.mining.repairkit.RepairKits;
import insane96mcp.iguanatweaksreborn.module.items.copper.CopperEquipment;
import insane96mcp.iguanatweaksreborn.module.items.flintexpansion.FlintExpansion;
import insane96mcp.iguanatweaksreborn.module.sleeprespawn.death.Death;
import insane96mcp.iguanatweaksreborn.module.world.coalfire.CoalFire;
import insane96mcp.insanelib.base.FeatureEnabledCondition;
import insane96mcp.shieldsplus.setup.SPItems;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ISERecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ISERecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, PortableCrate.ITEM.get())
                .pattern("nnn")
                .pattern("ibi")
                .pattern("nnn")
                .define('n', Durium.NUGGET.get())
                .define('i', Items.IRON_INGOT)
                .define('b', Items.BARREL)
                .unlockedBy("has_durium", has(Durium.INGOT.get()))
                .unlockedBy("has_barrel", has(Items.BARREL))
                .save(writer);

        ConditionalRecipe.builder()
                .addCondition(new NotCondition(new ModLoadedCondition("caverns_and_chasms")))
                .addRecipe(writerConsumer -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Altimeter.ITEM.get())
                        .pattern(" i ")
                        .pattern("frf")
                        .pattern(" f ")
                        .define('f', Durium.INGOT.get())
                        .define('i', Items.IRON_INGOT)
                        .define('r', Items.REDSTONE)
                        .unlockedBy("has_durium_ingot", has(Durium.INGOT.get()))
                        .save(writerConsumer))
                .build(writer, ResourceLocation.fromNamespaceAndPath(InsaneSurvivalExtra.MOD_ID, "altimeter"));

        //Solarium ball and forging
        featureBoundRecipe(writer, "Solarium", Solarium.SOLARIUM_BALL.get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Solarium.SOLARIUM_BALL.get(), 1)
                        .requires(Solarium.SOLIUM_MOSS.item().get(), 9)
                        .unlockedBy("has_solium_moss", has(Solarium.SOLIUM_MOSS.item().get()))
                        .save(recipe)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.PICKAXE.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 3, Items.WOODEN_PICKAXE, Solarium.PICKAXE.get(), 6)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.AXE.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 3, Items.WOODEN_AXE, Solarium.AXE.get(), 6)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.SWORD.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 2, Items.WOODEN_SWORD, Solarium.SWORD.get(), 6)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.HOE.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 2, Items.WOODEN_HOE, Solarium.HOE.get(), 6)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.SHOVEL.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 1, Items.WOODEN_SHOVEL, Solarium.SHOVEL.get(), 6)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.ShieldsPlusIntegration.SHIELD.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 4, SPItems.WOODEN_SHIELD.get(), Solarium.ShieldsPlusIntegration.SHIELD.get(), 6)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.HELMET.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 5, Items.LEATHER_HELMET, Solarium.HELMET.get(), 4)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.CHESTPLATE.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 8, Items.LEATHER_CHESTPLATE, Solarium.CHESTPLATE.get(), 6)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.LEGGINGS.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 7, Items.LEATHER_LEGGINGS, Solarium.LEGGINGS.get(), 5)
        );
        featureBoundRecipe(writer, "Solarium", Solarium.BOOTS.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 4, Items.LEATHER_BOOTS, Solarium.BOOTS.get(), 4)
        );

        //Durium Block, Ingot, Nugget, Scrap, forging
        featureBoundRecipe(writer, "Durium", Durium.BLOCK.item().get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Durium.BLOCK.item().get(), 1)
                        .requires(Durium.INGOT.get(), 9)
                        .unlockedBy("has_ingot", has(Durium.INGOT.get()))
                        .save(recipe)
        );
        featureBoundRecipe(writer, "Durium",
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.INGOT.get(), 9)
                        .requires(Durium.BLOCK.item().get(), 1)
                        .unlockedBy("has_ingot", has(Durium.INGOT.get()))
                        .save(recipe),
                ResourceLocation.parse(InsaneSurvivalExtra.RESOURCE_PREFIX + "durium_ingot_from_block")
        );
        featureBoundRecipe(writer, "Durium",
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.INGOT.get(), 1)
                        .requires(Durium.NUGGET.get(), 9)
                        .unlockedBy("has_nuggets", has(Durium.NUGGET.get()))
                        .save(recipe),
                ResourceLocation.parse(InsaneSurvivalExtra.RESOURCE_PREFIX + "durium_ingot_from_nuggets")
        );
        featureBoundRecipe(writer, "Durium", Durium.NUGGET.get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.NUGGET.get(), 9)
                        .requires(Durium.INGOT.get(), 1)
                        .unlockedBy("has_ingot", has(Durium.INGOT.get()))
                        .save(recipe)
        );
        featureBoundRecipe(writer, "Durium", Durium.SCRAP_BLOCK.item().get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.SCRAP_BLOCK.item().get(), 1)
                        .requires(Durium.SCRAP_PIECE.get(), 9)
                        .unlockedBy("has_piece", has(Durium.SCRAP_PIECE.get()))
                        .save(recipe)
        );
        featureBoundRecipe(writer, "Durium", Durium.SCRAP_PIECE.get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.SCRAP_PIECE.get(), 9)
                        .requires(Durium.SCRAP_BLOCK.item().get(), 1)
                        .unlockedBy("has_piece", has(Durium.SCRAP_PIECE.get()))
                        .save(recipe)
        );

        addBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Durium.SCRAP_BLOCK.item().get()), Ingredient.of(ItemTags.SAND), Ingredient.of(Items.CLAY_BALL)), Durium.SCRAP_PIECE.get(), Durium.INGOT.get(), 5f, 800);
        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Durium.SCRAP_BLOCK.item().get()), Ingredient.of(ItemTags.SAND), Ingredient.of(Items.CLAY_BALL)), Durium.SCRAP_PIECE.get(), Durium.INGOT.get(), 5f, 1200, 0.3f);

        featureBoundRecipe(writer, "Durium", Durium.PICKAXE.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 3, FlintExpansion.PICKAXE.get(), Durium.PICKAXE.get(), 14)
        );
        featureBoundRecipe(writer, "Durium", Durium.AXE.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 3, FlintExpansion.AXE.get(), Durium.AXE.get(), 14)
        );
        featureBoundRecipe(writer, "Durium", Durium.SWORD.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 2, FlintExpansion.SWORD.get(), Durium.SWORD.get(), 14)
        );
        featureBoundRecipe(writer, "Durium", Durium.HOE.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 2, FlintExpansion.HOE.get(), Durium.HOE.get(), 14)
        );
        featureBoundRecipe(writer, "Durium", Durium.SHOVEL.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 1, FlintExpansion.SHOVEL.get(), Durium.SHOVEL.get(), 14)
        );
        featureBoundRecipe(writer, "Durium", Durium.SHEARS.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 2, Items.IRON_NUGGET, Durium.SHEARS.get(), 14)
        );
        featureBoundRecipe(writer, "Durium", Durium.ShieldsPlusIntegration.SHIELD.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 4, FlintExpansion.ShieldsPlusIntegration.SHIELD.get(), Durium.ShieldsPlusIntegration.SHIELD.get(), 14)
        );
        featureBoundRecipe(writer, "Durium", Durium.HELMET.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 5, Items.CHAINMAIL_HELMET, Durium.HELMET.get(), 10)
        );
        featureBoundRecipe(writer, "Durium", Durium.CHESTPLATE.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 8, Items.CHAINMAIL_CHESTPLATE, Durium.CHESTPLATE.get(), 14)
        );
        featureBoundRecipe(writer, "Durium", Durium.LEGGINGS.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 7, Items.CHAINMAIL_LEGGINGS, Durium.LEGGINGS.get(), 12)
        );
        featureBoundRecipe(writer, "Durium", Durium.BOOTS.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 4, Items.CHAINMAIL_BOOTS, Durium.BOOTS.get(), 9)
        );

        //Keego
        featureBoundRecipe(writer, "Keego", Keego.PICKAXE.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 3, CopperEquipment.PICKAXE.get(), Keego.PICKAXE.get(), 13)
        );
        featureBoundRecipe(writer, "Keego", Keego.AXE.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 3, CopperEquipment.AXE.get(), Keego.AXE.get(), 13)
        );
        featureBoundRecipe(writer, "Keego", Keego.SWORD.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 2, CopperEquipment.SWORD.get(), Keego.SWORD.get(), 13)
        );
        featureBoundRecipe(writer, "Keego", Keego.HOE.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 2, CopperEquipment.HOE.get(), Keego.HOE.get(), 13)
        );
        featureBoundRecipe(writer, "Keego", Keego.SHOVEL.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 1, CopperEquipment.SHOVEL.get(), Keego.SHOVEL.get(), 13)
        );
        featureBoundRecipe(writer, "Keego", Keego.ShieldsPlusIntegration.SHIELD.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 4, CopperEquipment.ShieldsPlusIntegration.SHIELD.get(), Keego.ShieldsPlusIntegration.SHIELD.get(), 13)
        );
        featureBoundRecipe(writer, "Keego", Keego.HELMET.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 5, CopperEquipment.HELMET.get(), Keego.HELMET.get(), 9)
        );
        featureBoundRecipe(writer, "Keego", Keego.CHESTPLATE.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 8, CopperEquipment.CHESTPLATE.get(), Keego.CHESTPLATE.get(), 13)
        );
        featureBoundRecipe(writer, "Keego", Keego.LEGGINGS.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 7, CopperEquipment.LEGGINGS.get(), Keego.LEGGINGS.get(), 11)
        );
        featureBoundRecipe(writer, "Keego", Keego.BOOTS.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 4, CopperEquipment.BOOTS.get(), Keego.BOOTS.get(), 8)
        );
        featureBoundRecipe(writer, "Keego", Keego.BLOCK.block().get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Keego.BLOCK.block().get(), 1)
                        .requires(Keego.GEM.get(), 9)
                        .unlockedBy("has_keego", has(Keego.GEM.get()))
                        .save(recipe)
        );
        featureBoundRecipe(writer, "Keego", Keego.GEM.get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Keego.GEM.get(), 9)
                        .requires(Keego.BLOCK.block().get(), 1)
                        .unlockedBy("has_keego", has(Keego.GEM.get()))
                        .save(recipe)
        );

        //Quaron
        featureBoundRecipe(writer, "Quaron", Quaron.BLOCK.block().get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Quaron.BLOCK.block().get(), 1)
                        .requires(Quaron.INGOT.get(), 9)
                        .unlockedBy("has_quaron", has(Quaron.INGOT.get()))
                        .save(recipe)
        );
        featureBoundRecipe(writer, "Quaron",
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Quaron.INGOT.get(), 9)
                        .requires(Quaron.BLOCK.block().get(), 1)
                        .unlockedBy("has_quaron", has(Quaron.INGOT.get()))
                        .save(recipe),
                ResourceLocation.parse(InsaneSurvivalExtra.RESOURCE_PREFIX + "quaron_ingot_from_block")
        );
        featureBoundRecipe(writer, "Quaron",
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Quaron.INGOT.get(), 1)
                        .requires(Quaron.NUGGET.get(), 9)
                        .unlockedBy("has_nuggets", has(Quaron.NUGGET.get()))
                        .save(recipe),
                ResourceLocation.parse(InsaneSurvivalExtra.RESOURCE_PREFIX + "quaron_ingot_from_nuggets")
        );
        featureBoundRecipe(writer, "Quaron", Quaron.NUGGET.get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Quaron.NUGGET.get(), 9)
                        .requires(Quaron.INGOT.get(), 1)
                        .unlockedBy("has_ingot", has(Quaron.INGOT.get()))
                        .save(recipe)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.PICKAXE.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 3, Items.WOODEN_PICKAXE, Quaron.PICKAXE.get(), 17)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.AXE.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 3, Items.WOODEN_AXE, Quaron.AXE.get(), 17)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.SWORD.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 2, Items.WOODEN_SWORD, Quaron.SWORD.get(), 17)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.HOE.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 2, Items.WOODEN_HOE, Quaron.HOE.get(), 17)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.SHOVEL.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 1, Items.WOODEN_SHOVEL, Quaron.SHOVEL.get(), 17)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.FISHING_ROD.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 1, Items.FISHING_ROD, Quaron.FISHING_ROD.get(), 17)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.ShieldsPlusIntegration.SHIELD.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 4, SPItems.WOODEN_SHIELD.get(), Quaron.ShieldsPlusIntegration.SHIELD.get(), 17)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.HELMET.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 5, Items.LEATHER_HELMET, Quaron.HELMET.get(), 12)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.CHESTPLATE.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 8, Items.LEATHER_CHESTPLATE, Quaron.CHESTPLATE.get(), 17)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.LEGGINGS.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 7, Items.LEATHER_LEGGINGS, Quaron.LEGGINGS.get(), 15)
        );
        featureBoundRecipe(writer, "Quaron", Quaron.BOOTS.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 4, Items.LEATHER_BOOTS, Quaron.BOOTS.get(), 11)
        );
        addBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.RAW_IRON), Ingredient.of(Items.AMETHYST_CLUSTER), Ingredient.of(Items.AMETHYST_CLUSTER), Ingredient.of(Items.BLAZE_ROD)), Items.RAW_IRON, Quaron.INGOT.get(), 8f, 800);
        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.RAW_IRON), Ingredient.of(Items.AMETHYST_CLUSTER), Ingredient.of(Items.AMETHYST_CLUSTER), Ingredient.of(Items.BLAZE_ROD)), Items.RAW_IRON, Quaron.INGOT.get(),8f, 1200, 0.3f);

        //Soul Steel
        copySmithingTemplate(writer, SoulSteel.UPGRADE_SMITHING_TEMPLATE.get(), Items.NETHERRACK);
        String modIdPrefix = InsaneSurvivalExtra.RESOURCE_PREFIX;
        List.of(
                Map.entry(SoulSteel.AXE.get(),          Quaron.AXE.get()),
                Map.entry(SoulSteel.PICKAXE.get(),      Quaron.PICKAXE.get()),
                Map.entry(SoulSteel.SHOVEL.get(),       Quaron.SHOVEL.get()),
                Map.entry(SoulSteel.HOE.get(),          Quaron.HOE.get()),
                Map.entry(SoulSteel.SWORD.get(),        Quaron.SWORD.get()),
                Map.entry(SoulSteel.ShieldsPlusIntegration.SHIELD.get(), Quaron.ShieldsPlusIntegration.SHIELD.get()),
                Map.entry(SoulSteel.HELMET.get(),       Quaron.HELMET.get()),
                Map.entry(SoulSteel.CHESTPLATE.get(),   Quaron.CHESTPLATE.get()),
                Map.entry(SoulSteel.LEGGINGS.get(),     Quaron.LEGGINGS.get()),
                Map.entry(SoulSteel.BOOTS.get(),        Quaron.BOOTS.get())
        ).forEach(entry -> {
            ResourceLocation id = ResourceLocation.parse(modIdPrefix + ForgeRegistries.ITEMS.getKey(entry.getKey()).getPath());
            ConditionalRecipe.builder()
                    .addCondition(new FeatureEnabledCondition("Soul steel"))
                    .addRecipe(consumer -> SmithingTransformRecipeBuilder.smithing(
                                    Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()),
                                    Ingredient.of(entry.getValue()),
                                    Ingredient.of(SoulSteel.INGOT.get()),
                                    RecipeCategory.COMBAT,
                                    entry.getKey()
                            )
                            .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                            .save(consumer, id))
                    .build(writer, id);
        });
        featureBoundRecipe(writer, "Soul steel",
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SoulSteel.INGOT.get(), 9)
                        .requires(SoulSteel.BLOCK.block().get(), 1)
                        .unlockedBy("has_ingot", has(SoulSteel.INGOT.get()))
                        .save(recipe),
                ResourceLocation.parse(modIdPrefix + "soul_steel_ingot_from_block")
        );
        featureBoundRecipe(writer, "Soul steel",
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SoulSteel.INGOT.get(), 1)
                        .requires(SoulSteel.NUGGET.get(), 9)
                        .unlockedBy("has_nuggets", has(SoulSteel.NUGGET.get()))
                        .save(recipe),
                ResourceLocation.parse(modIdPrefix + "soul_steel_ingot_from_nuggets")
        );
        featureBoundRecipe(writer, "Soul steel", SoulSteel.NUGGET.get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SoulSteel.NUGGET.get(), 9)
                        .requires(SoulSteel.INGOT.get(), 1)
                        .unlockedBy("has_ingot", has(SoulSteel.INGOT.get()))
                        .save(recipe)
        );
        featureBoundRecipe(writer, "Soul steel", SoulSteel.INGOT.get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SoulSteel.INGOT.get(), 1)
                        .requires(Items.NETHERITE_SCRAP, 4)
                        .requires(Items.IRON_INGOT, 2)
                        .requires(CoalFire.HELLISH_COAL.get(), 2)
                        .requires(Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS), 1)
                        .unlockedBy("has_ingot", has(SoulSteel.INGOT.get()))
                        .save(recipe)
        );

        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.IRON_INGOT), Ingredient.of(CoalFire.HELLISH_COAL.get()), Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS)), CoalFire.HELLISH_COAL.get(), SoulSteel.INGOT.get(), 8f, 1200);

        featureBoundRecipe(writer, "Explosive barrel", ExplosiveBarrel.BLOCK.item().get(),
                recipe -> ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ExplosiveBarrel.BLOCK.item().get())
                        .requires(Items.TNT)
                        .requires(Items.BARREL)
                        .requires(Items.GUNPOWDER)
                        .unlockedBy("has_tnt", has(Items.TNT))
                        .unlockedBy("has_barrel", has(Items.BARREL))
                        .save(recipe)
        );

        featureBoundRecipe(writer, "Mining charge", MiningCharge.MINING_CHARGE.item().get(),
                recipe -> ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, MiningCharge.MINING_CHARGE.item().get(), 2)
                        .pattern(" T ")
                        .pattern(" S ")
                        .pattern("CCC")
                        .define('C', Durium.NUGGET.get())
                        .define('T', Items.TNT)
                        .define('S', Items.SLIME_BALL)
                        .unlockedBy("has_tnt", has(Items.TNT))
                        .save(recipe)
        );

        //Soul Blast Furnace
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, MultiBlockFurnaces.SOUL_BLAST_FURNACE.item().get())
                .pattern("GNG")
                .pattern("GFG")
                .pattern("BBB")
                .define('G', Items.GOLD_INGOT)
                .define('N', Items.NETHERITE_INGOT)
                .define('F', Items.FURNACE)
                .define('B', Items.NETHER_BRICKS)
                .unlockedBy("has_netherite", has(Items.NETHERITE_INGOT))
                .save(writer);

        //Blast furnace recipes
        //Copper
        addBlastingRecipe(writer, Items.RAW_COPPER, Items.COPPER_INGOT, 0.7f, 100);
        addBlastingRecipe(writer, Items.COPPER_ORE, Items.RAW_COPPER, 0.7f, 200, 6f);
        addBlastingRecipe(writer, Items.DEEPSLATE_COPPER_ORE, Items.RAW_COPPER, 0.7f, 200, 6f);
        addBlastingRecipe(writer, BeegOreVeins.COPPER_ORE_ROCK.item().get(), Items.COPPER_INGOT, 0.7f, 200, 2f);
        //Iron
        addBlastingRecipe(writer, Items.RAW_IRON, Items.IRON_INGOT, 1f, 200);
        addBlastingRecipe(writer, Items.IRON_ORE, Items.RAW_IRON, 1f, 400, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_IRON_ORE, Items.RAW_IRON, 1f, 400, 1f);
        addBlastingRecipe(writer, BeegOreVeins.IRON_ORE_ROCK.item().get(), Items.IRON_INGOT, 1f, 400, 1f);
        addBlastingRecipe(writer, Items.IRON_DOOR, Items.IRON_NUGGET, 0f, 200, 4.4f);
        addBlastingRecipe(writer, Death.GRAVE.item().get(), Items.IRON_INGOT, 0f, 200);
        addBlastingRecipe(writer, Items.SMITHING_TABLE, Items.IRON_INGOT, 0f, 200);
        addBlastingRecipe(writer, Items.CAULDRON, Items.IRON_INGOT, 0f, 200, 6f);
        //Gold
        addBlastingRecipe(writer, Items.RAW_GOLD, Items.GOLD_INGOT, 2f, 200);
        addBlastingRecipe(writer, Items.GOLD_ORE, Items.RAW_GOLD, 2f, 400, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_GOLD_ORE, Items.RAW_GOLD, 2f, 400, 1f);
        addBlastingRecipe(writer, Items.NETHER_GOLD_ORE, Items.GOLD_NUGGET, 2f, 400, 7f);
        addBlastingRecipe(writer, BeegOreVeins.GOLD_ORE_ROCK.item().get(), Items.GOLD_INGOT, 2f, 200, 1f);
        //Durium
        addBlastingRecipe(writer, Durium.ITEM_ORES, Durium.SCRAP_PIECE.get(), 2f, 400, 9f);
        //Other
        addBlastingRecipe(writer, Items.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, 5f, 400);
        addBlastingRecipe(writer, Items.COAL_ORE, Items.COAL, 0.7f, 200, 1f);
        addBlastingRecipe(writer, Items.LAPIS_ORE, Items.LAPIS_LAZULI, 1f, 400, 12f);
        addBlastingRecipe(writer, Items.REDSTONE_ORE, Items.REDSTONE, 2f, 400, 8f);
        addBlastingRecipe(writer, Items.EMERALD_ORE, Items.EMERALD, 4f, 800, 1f);
        addBlastingRecipe(writer, Items.DIAMOND_ORE, Items.DIAMOND, 4f, 800, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_COAL_ORE, Items.COAL, 0.7f, 200, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_LAPIS_ORE, Items.LAPIS_LAZULI, 1f, 400, 12f);
        addBlastingRecipe(writer, Items.DEEPSLATE_REDSTONE_ORE, Items.REDSTONE, 2f, 400, 8f);
        addBlastingRecipe(writer, Items.DEEPSLATE_EMERALD_ORE, Items.EMERALD, 4f, 800, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_DIAMOND_ORE, Items.DIAMOND, 4f, 800, 1f);
        addBlastingRecipe(writer, Items.NETHER_QUARTZ_ORE, Items.QUARTZ, 2f, 400, 1f);

        //Soul Blast furnace recipes
        //Copper
        addSoulBlastingRecipe(writer, Items.RAW_COPPER, Items.COPPER_INGOT, 0.7f, 150);
        addSoulBlastingRecipe(writer, Items.COPPER_ORE, Items.RAW_COPPER, 0.7f, 300, 4.2f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_COPPER_ORE, Items.RAW_COPPER, 0.7f, 300, 4.2f);
        addSoulBlastingRecipe(writer, BeegOreVeins.COPPER_ORE_ROCK.item().get(), Items.COPPER_INGOT, 0.7f, 300, 2f);
        //Iron
        addSoulBlastingRecipe(writer, Items.RAW_IRON, Items.IRON_INGOT, 1f, 300);
        addSoulBlastingRecipe(writer, Items.IRON_ORE, Items.RAW_IRON, 1f, 600, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_IRON_ORE, Items.RAW_IRON, 1f, 600, 0.3f);
        addSoulBlastingRecipe(writer, BeegOreVeins.IRON_ORE_ROCK.item().get(), Items.IRON_INGOT, 1f, 600, 0.3f);
        addSoulBlastingRecipe(writer, Items.IRON_DOOR, Items.IRON_NUGGET, 0f, 300, 4.4f);
        addSoulBlastingRecipe(writer, Death.GRAVE.item().get(), Items.IRON_INGOT, 0f, 300);
        addSoulBlastingRecipe(writer, Items.SMITHING_TABLE, Items.IRON_INGOT, 0f, 300);
        addSoulBlastingRecipe(writer, Items.CAULDRON, Items.IRON_INGOT, 0f, 300, 6f);
        //Gold
        addSoulBlastingRecipe(writer, Items.RAW_GOLD, Items.GOLD_INGOT, 2f, 300);
        addSoulBlastingRecipe(writer, Items.GOLD_ORE, Items.RAW_GOLD, 2f, 600, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_GOLD_ORE, Items.RAW_GOLD, 2f, 600, 0.3f);
        addSoulBlastingRecipe(writer, Items.NETHER_GOLD_ORE, Items.GOLD_NUGGET, 2f, 600, 4.2f);
        addSoulBlastingRecipe(writer, BeegOreVeins.GOLD_ORE_ROCK.item().get(), Items.GOLD_INGOT, 2f, 300, 0.3f);
        //Durium
        addSoulBlastingRecipe(writer, Durium.ITEM_ORES, Durium.SCRAP_PIECE.get(), 2f, 600, 6f);
        //Other
        addSoulBlastingRecipe(writer, Items.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, 5f, 600);
        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Items.GOLD_INGOT)), Items.NETHERITE_SCRAP, Items.NETHERITE_INGOT, 8f, 1200);
        addSoulBlastingRecipe(writer, Items.COAL_ORE, Items.COAL, 0.7f, 300, 0.3f);
        addSoulBlastingRecipe(writer, Items.LAPIS_ORE, Items.LAPIS_LAZULI, 1f, 600, 7.5f);
        addSoulBlastingRecipe(writer, Items.REDSTONE_ORE, Items.REDSTONE, 2f, 600, 4.2f);
        addSoulBlastingRecipe(writer, Items.EMERALD_ORE, Items.EMERALD, 4f, 1200, 0.3f);
        addSoulBlastingRecipe(writer, Items.DIAMOND_ORE, Items.DIAMOND, 4f, 1200, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_COAL_ORE, Items.COAL, 0.7f, 300, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_LAPIS_ORE, Items.LAPIS_LAZULI, 1f, 600, 7.5f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_REDSTONE_ORE, Items.REDSTONE, 2f, 600, 4.2f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_EMERALD_ORE, Items.EMERALD, 4f, 1200, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_DIAMOND_ORE, Items.DIAMOND, 4f, 1200, 0.3f);
        addSoulBlastingRecipe(writer, Items.NETHER_QUARTZ_ORE, Items.QUARTZ, 2f, 600, 0.3f);

        //<editor-fold desc="Chained Copper Armor">
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CopperEquipment.HELMET.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_copper_armor", has(CopperEquipment.HELMET.get()))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "smelting_chained_copper_helmet");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CopperEquipment.CHESTPLATE.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_copper_armor", has(CopperEquipment.CHESTPLATE.get()))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "smelting_chained_copper_chestplate");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CopperEquipment.LEGGINGS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_copper_armor", has(CopperEquipment.LEGGINGS.get()))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "smelting_chained_copper_leggings");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CopperEquipment.BOOTS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_copper_armor", has(CopperEquipment.BOOTS.get()))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "smelting_chained_copper_boots");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CopperEquipment.HELMET.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_copper_armor", has(CopperEquipment.HELMET.get()))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "blasting_chained_copper_helmet");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CopperEquipment.CHESTPLATE.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_copper_armor", has(CopperEquipment.CHESTPLATE.get()))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "blasting_chained_copper_chestplate");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CopperEquipment.LEGGINGS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_copper_armor", has(CopperEquipment.LEGGINGS.get()))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "blasting_chained_copper_leggings");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CopperEquipment.BOOTS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_copper_armor", has(CopperEquipment.BOOTS.get()))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "blasting_chained_copper_boots");
        //</editor-fold>

        //Hellish Coal
        addBlastingRecipe(writer, CoalFire.ITEM_ORES, CoalFire.HELLISH_COAL.get(), 1.2f, 200, 1f);
        addSoulBlastingRecipe(writer, CoalFire.ITEM_ORES, CoalFire.HELLISH_COAL.get(), 1.2f, 150, 0.3f);

        //<editor-fold desc="Recycle recipes">
        recycleGear(writer, CopperEquipment.HELMET.get(), Items.COPPER_INGOT, 200, 5);
        recycleGear(writer, CopperEquipment.CHESTPLATE.get(), Items.COPPER_INGOT, 200, 8);
        recycleGear(writer, CopperEquipment.LEGGINGS.get(), Items.COPPER_INGOT, 200, 7);
        recycleGear(writer, CopperEquipment.BOOTS.get(), Items.COPPER_INGOT, 200, 4);
        recycleGear(writer, Items.IRON_HELMET, Items.IRON_NUGGET, 200, 45);
        recycleGear(writer, Items.IRON_CHESTPLATE, Items.IRON_NUGGET, 200, 72);
        recycleGear(writer, Items.IRON_LEGGINGS, Items.IRON_NUGGET, 200, 63);
        recycleGear(writer, Items.IRON_BOOTS, Items.IRON_NUGGET, 200, 36);
        recycleGear(writer, Durium.HELMET.get(), Durium.NUGGET.get(), 200, 45);
        recycleGear(writer, Durium.CHESTPLATE.get(), Durium.NUGGET.get(), 200, 72);
        recycleGear(writer, Durium.LEGGINGS.get(), Durium.NUGGET.get(), 200, 63);
        recycleGear(writer, Durium.BOOTS.get(), Durium.NUGGET.get(), 200, 36);
        recycleGear(writer, Quaron.HELMET.get(), Quaron.NUGGET.get(), 200, 45);
        recycleGear(writer, Quaron.CHESTPLATE.get(), Quaron.NUGGET.get(), 200, 72);
        recycleGear(writer, Quaron.LEGGINGS.get(), Quaron.NUGGET.get(), 200, 63);
        recycleGear(writer, Quaron.BOOTS.get(), Quaron.NUGGET.get(), 200, 36);
        recycleGear(writer, Items.GOLDEN_HELMET, Items.GOLD_NUGGET, 200, 45);
        recycleGear(writer, Items.GOLDEN_CHESTPLATE, Items.GOLD_NUGGET, 200, 72);
        recycleGear(writer, Items.GOLDEN_LEGGINGS, Items.GOLD_NUGGET, 200, 63);
        recycleGear(writer, Items.GOLDEN_BOOTS, Items.GOLD_NUGGET, 200, 36);
        recycleGear(writer, Items.CHAINMAIL_HELMET, Items.IRON_NUGGET, 200, 15);
        recycleGear(writer, Items.CHAINMAIL_CHESTPLATE, Items.IRON_NUGGET, 200, 24);
        recycleGear(writer, Items.CHAINMAIL_LEGGINGS, Items.IRON_NUGGET, 200, 21);
        recycleGear(writer, Items.CHAINMAIL_BOOTS, Items.IRON_NUGGET, 200, 12);
        recycleGear(writer, Keego.HELMET.get(), Keego.GEM.get(), 200, 5);
        recycleGear(writer, Keego.CHESTPLATE.get(), Keego.GEM.get(), 200, 8);
        recycleGear(writer, Keego.LEGGINGS.get(), Keego.GEM.get(), 200, 7);
        recycleGear(writer, Keego.BOOTS.get(), Keego.GEM.get(), 200, 4);
        recycleGear(writer, Items.DIAMOND_HELMET, Items.DIAMOND, 200, 5);
        recycleGear(writer, Items.DIAMOND_CHESTPLATE, Items.DIAMOND, 200, 8);
        recycleGear(writer, Items.DIAMOND_LEGGINGS, Items.DIAMOND, 200, 7);
        recycleGear(writer, Items.DIAMOND_BOOTS, Items.DIAMOND, 200, 4);
        recycleGear(writer, Items.NETHERITE_HELMET, Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, Items.NETHERITE_LEGGINGS, Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, Items.NETHERITE_BOOTS, Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, SoulSteel.HELMET.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGear(writer, SoulSteel.CHESTPLATE.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGear(writer, SoulSteel.LEGGINGS.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGear(writer, SoulSteel.BOOTS.get(), SoulSteel.NUGGET.get(), 200, 9);

        recycleGear(writer, CopperEquipment.PICKAXE.get(), Items.COPPER_INGOT, 200, 3);
        recycleGear(writer, CopperEquipment.AXE.get(), Items.COPPER_INGOT, 200, 3);
        recycleGear(writer, CopperEquipment.SHOVEL.get(), Items.COPPER_INGOT, 200, 1);
        recycleGear(writer, CopperEquipment.HOE.get(), Items.COPPER_INGOT, 200, 2);
        recycleGear(writer, CopperEquipment.SWORD.get(), Items.COPPER_INGOT, 200, 2);
        recycleGear(writer, Items.IRON_PICKAXE, Items.IRON_NUGGET, 200, 27);
        recycleGear(writer, Items.IRON_AXE, Items.IRON_NUGGET, 200, 27);
        recycleGear(writer, Items.IRON_SHOVEL, Items.IRON_NUGGET, 200, 9);
        recycleGear(writer, Items.IRON_HOE, Items.IRON_NUGGET, 200, 18);
        recycleGear(writer, Items.IRON_SWORD, Items.IRON_NUGGET, 200, 18);
        recycleGear(writer, Durium.PICKAXE.get(), Durium.NUGGET.get(), 200, 27);
        recycleGear(writer, Durium.AXE.get(), Durium.NUGGET.get(), 200, 27);
        recycleGear(writer, Durium.SHOVEL.get(), Durium.NUGGET.get(), 200, 9);
        recycleGear(writer, Durium.HOE.get(), Durium.NUGGET.get(), 200, 18);
        recycleGear(writer, Durium.SHEARS.get(), Durium.NUGGET.get(), 200, 18);
        recycleGear(writer, Durium.SWORD.get(), Durium.NUGGET.get(), 200, 18);
        recycleGear(writer, Quaron.PICKAXE.get(), Quaron.NUGGET.get(), 200, 27);
        recycleGear(writer, Quaron.AXE.get(), Quaron.NUGGET.get(), 200, 27);
        recycleGear(writer, Quaron.SHOVEL.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGear(writer, Quaron.HOE.get(), Quaron.NUGGET.get(), 200, 18);
        recycleGear(writer, Quaron.SWORD.get(), Quaron.NUGGET.get(), 200, 18);
        recycleGear(writer, Items.GOLDEN_PICKAXE, Items.GOLD_NUGGET, 200, 27);
        recycleGear(writer, Items.GOLDEN_AXE, Items.GOLD_NUGGET, 200, 27);
        recycleGear(writer, Items.GOLDEN_SHOVEL, Items.GOLD_NUGGET, 200, 9);
        recycleGear(writer, Items.GOLDEN_HOE, Items.GOLD_NUGGET, 200, 18);
        recycleGear(writer, Items.GOLDEN_SWORD, Items.GOLD_NUGGET, 200, 18);
        recycleGear(writer, Keego.PICKAXE.get(), Keego.GEM.get(), 200, 3);
        recycleGear(writer, Keego.AXE.get(), Keego.GEM.get(), 200, 3);
        recycleGear(writer, Keego.SHOVEL.get(), Keego.GEM.get(), 200, 1);
        recycleGear(writer, Keego.HOE.get(), Keego.GEM.get(), 200, 2);
        recycleGear(writer, Keego.SWORD.get(), Keego.GEM.get(), 200, 2);
        recycleGear(writer, Items.DIAMOND_PICKAXE, Items.DIAMOND, 200, 3);
        recycleGear(writer, Items.DIAMOND_AXE, Items.DIAMOND, 200, 3);
        recycleGear(writer, Items.DIAMOND_SHOVEL, Items.DIAMOND, 200, 1);
        recycleGear(writer, Items.DIAMOND_HOE, Items.DIAMOND, 200, 2);
        recycleGear(writer, Items.DIAMOND_SWORD, Items.DIAMOND, 200, 2);
        recycleGear(writer, Items.NETHERITE_PICKAXE, Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, Items.NETHERITE_AXE, Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, Items.NETHERITE_SHOVEL, Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, Items.NETHERITE_HOE, Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, Items.NETHERITE_SWORD, Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, SoulSteel.PICKAXE.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGear(writer, SoulSteel.AXE.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGear(writer, SoulSteel.SHOVEL.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGear(writer, SoulSteel.HOE.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGear(writer, SoulSteel.SWORD.get(), SoulSteel.NUGGET.get(), 200, 9);

        //Recycle Shields
        recycleGear(writer, CopperEquipment.ShieldsPlusIntegration.SHIELD.get(), Items.COPPER_INGOT, 200, 4);
        recycleGear(writer, Items.SHIELD, Items.IRON_NUGGET, 200, 36);
        recycleGear(writer, SPItems.IRON_SHIELD.get(), Items.IRON_NUGGET, 200, 36);
        recycleGear(writer, Durium.ShieldsPlusIntegration.SHIELD.get(), Durium.NUGGET.get(), 200, 36);
        recycleGear(writer, SPItems.GOLDEN_SHIELD.get(), Items.GOLD_NUGGET, 200, 36);
        recycleGear(writer, Quaron.ShieldsPlusIntegration.SHIELD.get(), Quaron.NUGGET.get(), 200, 36);
        recycleGear(writer, Keego.ShieldsPlusIntegration.SHIELD.get(), Keego.GEM.get(), 200, 4);
        recycleGear(writer, SPItems.DIAMOND_SHIELD.get(), Items.DIAMOND, 200, 1);
        recycleGear(writer, SPItems.NETHERITE_SHIELD.get(), Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, SoulSteel.ShieldsPlusIntegration.SHIELD.get(), SoulSteel.NUGGET.get(), 200, 9);

        //Recycle Horse Armor
        addBlastingRecipe(writer, Items.IRON_HORSE_ARMOR, Items.IRON_NUGGET, 0, 200, 20.6f);
        addBlastingRecipe(writer, Items.GOLDEN_HORSE_ARMOR, Items.GOLD_NUGGET, 0, 200, 20.6f);
        addBlastingRecipe(writer, Items.DIAMOND_HORSE_ARMOR, Items.DIAMOND, 0, 200, 1.4f);
        addSoulBlastingRecipe(writer, Items.IRON_HORSE_ARMOR, Items.IRON_NUGGET, 0, 300, 35);
        addSoulBlastingRecipe(writer, Items.GOLDEN_HORSE_ARMOR, Items.GOLD_NUGGET, 0, 300, 35);
        addSoulBlastingRecipe(writer, Items.DIAMOND_HORSE_ARMOR, Items.DIAMOND, 0, 300, 3);
        //</editor-fold>

        //<editor-fold desc="Forge Recipes">
        featureBoundRecipe(writer, "Forging", Forging.FORGE.item().get(),
                recipe -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Forging.FORGE.item().get())
                        .pattern("ISI")
                        .pattern(" c ")
                        .pattern("cCc")
                        .define('S', Items.SMOOTH_STONE)
                        .define('I', Items.IRON_BLOCK)
                        .define('c', Items.COPPER_INGOT)
                        .define('C', Items.COPPER_BLOCK)
                        .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                        .save(recipe)
        );

        hammerCraftingRecipe(writer, Forging.WOODEN_HAMMER.get(), ItemTags.PLANKS);
        hammerCraftingRecipe(writer, Forging.STONE_HAMMER.get(), ItemTags.STONE_TOOL_MATERIALS);
        hammerCraftingRecipe(writer, Forging.FLINT_HAMMER.get(), Items.FLINT);
        hammerCraftingRecipe(writer, Forging.COPPER_HAMMER.get(), Items.COPPER_INGOT);
        appendGearToName = true;
        featureBoundRecipe(writer, "Forging",
                forgeRecipeBuilder(Items.IRON_INGOT, 5, Forging.STONE_HAMMER.get(), Forging.IRON_HAMMER.get(), 10), ResourceLocation.parse(modIdPrefix + "iron_hammer_from_stone_hammer")
        );
        featureBoundRecipe(writer, "Forging",
                forgeRecipeBuilder(Items.IRON_INGOT, 5, Forging.FLINT_HAMMER.get(), Forging.IRON_HAMMER.get(), 8), ResourceLocation.parse(modIdPrefix + "iron_hammer_from_flint_hammer")
        );
        appendGearToName = false;
        featureBoundRecipe(writer, "Forging", Forging.SOLARIUM_HAMMER.get(),
                forgeRecipeBuilder(Solarium.SOLARIUM_BALL.get(), 5, Forging.WOODEN_HAMMER.get(), Forging.SOLARIUM_HAMMER.get(), 6)
        );
        featureBoundRecipe(writer, "Forging", Forging.DURIUM_HAMMER.get(),
                forgeRecipeBuilder(Durium.INGOT.get(), 5, Forging.STONE_HAMMER.get(), Forging.DURIUM_HAMMER.get(), 14)
        );
        featureBoundRecipe(writer, "Forging", Forging.QUARON_HAMMER.get(),
                forgeRecipeBuilder(Quaron.INGOT.get(), 5, Forging.WOODEN_HAMMER.get(), Forging.QUARON_HAMMER.get(), 17)
        );
        featureBoundRecipe(writer, "Forging", Forging.GOLDEN_HAMMER.get(),
                forgeRecipeBuilder(Items.GOLD_INGOT, 5, Forging.COPPER_HAMMER.get(), Forging.GOLDEN_HAMMER.get(), 6)
        );
        featureBoundRecipe(writer, "Forging", Forging.KEEGO_HAMMER.get(),
                forgeRecipeBuilder(Keego.GEM.get(), 5, Forging.FLINT_HAMMER.get(), Forging.KEEGO_HAMMER.get(), 16)
        );
        featureBoundRecipe(writer, "Forging", Forging.DIAMOND_HAMMER.get(),
                forgeRecipeBuilder(Items.DIAMOND, 5, Forging.GOLDEN_HAMMER.get(), Forging.DIAMOND_HAMMER.get(), 16)
        );

        featureBoundRecipe(writer, "Soul Steel", Forging.SOUL_STEEL_HAMMER.get(),
                recipe -> SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()),
                                Ingredient.of(Forging.QUARON_HAMMER.get()),
                                Ingredient.of(SoulSteel.INGOT.get()),
                                RecipeCategory.TOOLS,
                                Forging.SOUL_STEEL_HAMMER.get()
                        )
                        .unlocks("has_material", has(SoulSteel.INGOT.get()))
                        .save(recipe, ResourceLocation.parse(InsaneSurvivalExtra.RESOURCE_PREFIX + "soul_steel_hammer"))
        );
        featureBoundRecipe(writer, "Forging", Forging.NETHERITE_HAMMER.get(),
                recipe -> SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                Ingredient.of(Forging.DIAMOND_HAMMER.get()),
                                Ingredient.of(Items.NETHERITE_INGOT),
                                RecipeCategory.TOOLS,
                                Forging.NETHERITE_HAMMER.get()
                        )
                        .unlocks("has_material", has(Items.NETHERITE_INGOT))
                        .save(recipe, ResourceLocation.parse(InsaneSurvivalExtra.RESOURCE_PREFIX + "netherite_hammer"))
        );

        //Recycle Forge Hammers
        recycleGear(writer, Forging.COPPER_HAMMER.get(), Items.COPPER_INGOT, 200, 5);
        recycleGear(writer, Forging.IRON_HAMMER.get(), Items.IRON_NUGGET, 200, 45);
        recycleGear(writer, Forging.DURIUM_HAMMER.get(), Durium.NUGGET.get(), 200, 45);
        recycleGear(writer, Forging.QUARON_HAMMER.get(), Quaron.NUGGET.get(), 200, 45);
        recycleGear(writer, Forging.GOLDEN_HAMMER.get(), Items.GOLD_NUGGET, 200, 45);
        recycleGear(writer, Forging.KEEGO_HAMMER.get(), Keego.GEM.get(), 200, 5);
        recycleGear(writer, Forging.DIAMOND_HAMMER.get(), Items.DIAMOND, 200, 5);
        recycleGear(writer, Forging.NETHERITE_HAMMER.get(), Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, Forging.SOUL_STEEL_HAMMER.get(), SoulSteel.NUGGET.get(), 200, 9);
        //</editor-fold>

        //<editor-fold desc="Repair Kits">
        appendMaterialToName = true;
        addRepairKitRecipe(writer, ItemTags.PLANKS, Items.OAK_PLANKS, new Color(184, 148, 95));
        //addRepairKitRecipe(writer, ItemTags.STONE_TOOL_MATERIALS, Items.COBBLESTONE, new Color(136, 135, 136));
        addRepairKitRecipe(writer, Items.FLINT, new Color(61, 60, 60));
        addRepairKitRecipe(writer, Items.COPPER_INGOT, new Color(209, 104, 69));
        addRepairKitRecipe(writer, Items.GOLD_INGOT, new Color(253, 245, 95));
        addRepairKitRecipe(writer, Items.IRON_INGOT, new Color(216, 216, 216));
        addRepairKitRecipe(writer, Solarium.SOLARIUM_BALL.get(), new Color(164, 162, 10));
        addRepairKitRecipe(writer, Durium.INGOT.get(), new Color(20, 90, 111));
        addRepairKitRecipe(writer, Items.OBSIDIAN, new Color(26, 19, 47));
        addRepairKitRecipe(writer, Keego.GEM.get(), new Color(0, 133, 213));
        addRepairKitRecipe(writer, Quaron.INGOT.get(), new Color(227, 190, 255));
        addRepairKitRecipe(writer, Items.DIAMOND, new Color(161, 251, 232));
        addRepairKitRecipe(writer, SoulSteel.INGOT.get(), new Color(73, 55, 44));
        addRepairKitRecipe(writer, Items.NETHERITE_INGOT, new Color(76, 65, 67));

        addRepairKitRecipeRequiresMod(writer, "caverns_and_chasms", CCItems.SILVER_INGOT.get(), new Color(206, 213, 229, 255));
        addRepairKitRecipeRequiresMod(writer, "caverns_and_chasms", CCItems.NECROMIUM_INGOT.get(), new Color(176, 189, 182, 255));
        addRepairKitRecipeRequiresMod(writer, "caverns_and_chasms", CCItems.LIVING_FLESH.get(), new Color(152, 136, 139, 255));
        appendMaterialToName = false;
        //</editor-fold>

        addPoorRichOreRecipes(writer, BeegOreVeins.POOR_RICH_COPPER_ORE, Items.COPPER_INGOT, 0.75f, 100, 7f);
        addPoorRichOreRecipes(writer, BeegOreVeins.POOR_RICH_IRON_ORE, Items.IRON_INGOT, 1f, 200, 1f);
        addPoorRichOreRecipes(writer, BeegOreVeins.POOR_RICH_GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 1f);
    }

    public static void featureBoundRecipe(Consumer<FinishedRecipe> writer, String featureName, ItemLike resultItem, Consumer<Consumer<FinishedRecipe>> recipeBuilder) {
        featureBoundRecipe(writer, featureName, recipeBuilder, ForgeRegistries.ITEMS.getKey(resultItem.asItem()));
    }

    public static void featureBoundRecipe(Consumer<FinishedRecipe> writer, String featureName, Consumer<Consumer<FinishedRecipe>> recipeBuilder, ResourceLocation name) {
        ConditionalRecipe.builder()
                .addCondition(new FeatureEnabledCondition(featureName))
                .addRecipe(recipeBuilder)
                .build(writer, name);
    }

    private ItemStack generateRepairKitStack(ItemLike material, Color color) {
        ItemStack resultStack = new ItemStack(RepairKits.REPAIR_KIT.get(), 1);
        resultStack.getOrCreateTag().putString("repair_item", ForgeRegistries.ITEMS.getKey(material.asItem()).toString());
        CompoundTag colorNbt = new CompoundTag();
        colorNbt.putInt("r", color.getRed());
        colorNbt.putInt("g", color.getGreen());
        colorNbt.putInt("b", color.getBlue());
        //noinspection DataFlowIssue
        resultStack.getTag().put("color", colorNbt);
        return resultStack;
    }

    private void addRepairKitRecipe(Consumer<FinishedRecipe> writer, ItemLike material, Color color) {
        featureBoundRecipe(writer, "Repair kits",
                forgeRecipeBuilder(material, 2, Items.AMETHYST_SHARD, generateRepairKitStack(material, color), 4), ResourceLocation.parse(InsaneSurvivalExtra.RESOURCE_PREFIX + ForgeRegistries.ITEMS.getKey(material.asItem()).getPath() + "_repair_kit")
        );
    }

    private void addRepairKitRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> materialTag, ItemLike material, Color color) {
        featureBoundRecipe(writer, "Repair kits",
                forgeRecipeBuilder(materialTag, 2, Items.AMETHYST_SHARD, generateRepairKitStack(material, color), 4), ResourceLocation.parse(InsaneSurvivalExtra.RESOURCE_PREFIX + ForgeRegistries.ITEMS.getKey(material.asItem()).getPath() + "_repair_kit")
        );
    }

    private void addRepairKitRecipeRequiresMod(Consumer<FinishedRecipe> writer, String modId, ItemLike material, Color color) {
        forgeRecipeRequiresMod(writer, modId, material, 2, Items.AMETHYST_SHARD, generateRepairKitStack(material, color), 4);
    }

    private void addRepairKitRecipeRequiresMod(Consumer<FinishedRecipe> writer, String modId, TagKey<Item> materialTag, ItemLike material, Color color) {
        forgeRecipeRequiresMod(writer, modId, materialTag, 2, Items.AMETHYST_SHARD, generateRepairKitStack(material, color), 4);
    }

    private void addPoorRichOreRecipes(Consumer<FinishedRecipe> writer, BeegOreVeins.PoorRichOre poorRichOre, Item smeltOutput, float experience, int cookingTime, float baseOutputIncrease) {
        addBlastingRecipe(writer, poorRichOre.poorOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease / 2f);
        addBlastingRecipe(writer, poorRichOre.poorDeepslateOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease / 2f);
        addBlastingRecipe(writer, poorRichOre.richOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease * 1.5f);
        addBlastingRecipe(writer, poorRichOre.richDeepslateOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease * 1.5f);
        addSoulBlastingRecipe(writer, poorRichOre.poorOre().item().get(), smeltOutput, experience, (int) (cookingTime * 1.5f), baseOutputIncrease / 2f * 0.3f);
        addSoulBlastingRecipe(writer, poorRichOre.poorDeepslateOre().item().get(), smeltOutput, experience, (int) (cookingTime * 1.5f), baseOutputIncrease / 2f * 0.3f);
        addSoulBlastingRecipe(writer, poorRichOre.richOre().item().get(), smeltOutput, experience, (int) (cookingTime * 1.5f), baseOutputIncrease * 1.5f * 0.3f);
        addSoulBlastingRecipe(writer, poorRichOre.richDeepslateOre().item().get(), smeltOutput, experience, (int) (cookingTime * 1.5f), baseOutputIncrease * 1.5f * 0.3f);
    }

    public static void addBlastingRecipe(Consumer<FinishedRecipe> writer, Item item, Item result, float experience, int cookingTime) {
        addBlastingRecipe(writer, item, result, experience, cookingTime, 0f);
    }

    public static void addBlastingRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> itemTag, Item result, float experience, int cookingTime, float outputIncrease) {
        String resultPath = ForgeRegistries.ITEMS.getKey(result).getPath();
        MultiItemSmeltingRecipeBuilder.blasting(
                        NonNullList.of(Ingredient.EMPTY, Ingredient.of(itemTag)),
                        RecipeCategory.MISC,
                        result,
                        cookingTime
                )
                .experience(experience)
                .outputIncrease(outputIncrease)
                .group(resultPath)
                .unlockedBy("has_" + itemTag.location().getPath(), has(itemTag))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "blast_furnace/" + resultPath + "_from_" + itemTag.location().getPath());
    }

    public static void addBlastingRecipe(Consumer<FinishedRecipe> writer, Item item, Item result, float experience, int cookingTime, float outputIncrease) {
        String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
        String resultPath = ForgeRegistries.ITEMS.getKey(result).getPath();
        MultiItemSmeltingRecipeBuilder.blasting(
                        NonNullList.of(Ingredient.EMPTY, Ingredient.of(item)),
                        RecipeCategory.MISC,
                        result,
                        cookingTime
                )
                .experience(experience)
                .outputIncrease(outputIncrease)
                .group(resultPath)
                .unlockedBy("has_" + itemPath, has(item))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "blast_furnace/" + resultPath + "_from_" + itemPath);
    }

    public static void addBlastingAlloy(Consumer<FinishedRecipe> writer, NonNullList<Ingredient> items, Item unlockingItem, Item result, float experience, int cookingTime) {
        addBlastingAlloy(writer, items, unlockingItem, result, experience, cookingTime, 0f);
    }

    public static void addBlastingAlloy(Consumer<FinishedRecipe> writer, NonNullList<Ingredient> items, Item unlockingItem, Item result, float experience, int cookingTime, float outputIncrease) {
        if (items.isEmpty())
            throw new IndexOutOfBoundsException("items cannot be empty");
        String itemPath = ForgeRegistries.ITEMS.getKey(items.get(0).getItems()[0].getItem()).getPath();
        String resultPath = ForgeRegistries.ITEMS.getKey(result).getPath();

        MultiItemSmeltingRecipeBuilder.blasting(
                        items,
                        RecipeCategory.MISC,
                        result,
                        cookingTime
                )
                .experience(experience)
                .outputIncrease(outputIncrease)
                .unlockedBy("has_" + itemPath, has(unlockingItem))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "blast_furnace/alloy/" + resultPath);
    }

    public static void addSoulBlastingRecipe(Consumer<FinishedRecipe> writer, Item item, Item result, float experience, int cookingTime) {
        addSoulBlastingRecipe(writer, item, result, experience, cookingTime, 0f);
    }

    public static void addSoulBlastingRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> itemTag, Item result, float experience, int cookingTime, float outputIncrease) {
        String resultPath = ForgeRegistries.ITEMS.getKey(result).getPath();
        MultiItemSmeltingRecipeBuilder.soulBlasting(
                        NonNullList.of(Ingredient.EMPTY, Ingredient.of(itemTag)),
                        RecipeCategory.MISC,
                        result,
                        cookingTime
                )
                .experience(experience)
                .outputIncrease(outputIncrease)
                .group(resultPath)
                .unlockedBy("has_" + itemTag.location().getPath(), has(itemTag))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "soul_blast_furnace/" + resultPath + "_from_" + itemTag.location().getPath());
    }

    public static void addSoulBlastingRecipe(Consumer<FinishedRecipe> writer, Item item, Item result, float experience, int cookingTime, float outputIncrease) {
        String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
        String resultPath = ForgeRegistries.ITEMS.getKey(result).getPath();
        MultiItemSmeltingRecipeBuilder.soulBlasting(
                        NonNullList.of(Ingredient.EMPTY, Ingredient.of(item)),
                        RecipeCategory.MISC,
                        result,
                        cookingTime
                )
                .experience(experience)
                .outputIncrease(outputIncrease)
                .group(resultPath)
                .unlockedBy("has_" + itemPath, has(item))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "soul_blast_furnace/" + resultPath + "_from_" + itemPath);
    }

    public static void addSoulBlastingAlloy(Consumer<FinishedRecipe> writer, NonNullList<Ingredient> items, Item unlockingItem, Item result, float experience, int cookingTime) {
        addSoulBlastingAlloy(writer, items, unlockingItem, result, experience, cookingTime, 0f);
    }

    public static void addSoulBlastingAlloy(Consumer<FinishedRecipe> writer, NonNullList<Ingredient> items, Item unlockingItem, Item result, float experience, int cookingTime, float outputIncrease) {
        if (items.isEmpty())
            throw new IndexOutOfBoundsException("items cannot be empty");
        String itemPath = ForgeRegistries.ITEMS.getKey(items.get(0).getItems()[0].getItem()).getPath();
        String resultPath = ForgeRegistries.ITEMS.getKey(result).getPath();

        MultiItemSmeltingRecipeBuilder.soulBlasting(
                        items,
                        RecipeCategory.MISC,
                        result,
                        cookingTime
                )
                .experience(experience)
                .outputIncrease(outputIncrease)
                .unlockedBy("has_" + itemPath, has(unlockingItem))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "soul_blast_furnace/alloy/" + resultPath);
    }

    private void recycleGear(Consumer<FinishedRecipe> writer, Item itemToRecycle, Item output, int baseCookingTime, int amountAtMaxDurability) {
        MultiItemSmeltingRecipeBuilder.blasting(
                        NonNullList.of(Ingredient.EMPTY, Ingredient.of(itemToRecycle)),
                        RecipeCategory.COMBAT,
                        output,
                        baseCookingTime / 2)
                .recycle(amountAtMaxDurability, 0.6f)
                .group("recycle_" + ForgeRegistries.ITEMS.getKey(output).getPath())
                .unlockedBy("has_armor", has(itemToRecycle))
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "blast_furnace/recycle/" + ForgeRegistries.ITEMS.getKey(itemToRecycle).getPath());

        MultiItemSmeltingRecipeBuilder.soulBlasting(
                        NonNullList.of(Ingredient.EMPTY, Ingredient.of(itemToRecycle)),
                        RecipeCategory.COMBAT,
                        output,
                        baseCookingTime)
                .recycle(amountAtMaxDurability)
                .unlockedBy("has_armor", has(itemToRecycle))
                .group("recycle_" + ForgeRegistries.ITEMS.getKey(output).getPath())
                .save(writer, InsaneSurvivalExtra.RESOURCE_PREFIX + "soul_blast_furnace/recycle/" + ForgeRegistries.ITEMS.getKey(itemToRecycle).getPath());
    }

    private void hammerCraftingRecipe(Consumer<FinishedRecipe> writer, Item hammer, Item material) {
        featureBoundRecipe(writer, "Forging", hammer,
                recipe -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hammer)
                        .pattern("MMM")
                        .pattern("MSM")
                        .pattern(" S ")
                        .define('S', Items.STICK)
                        .define('M', material)
                        .unlockedBy("has_material", has(material))
                        .save(recipe)
        );
    }

    private void hammerCraftingRecipe(Consumer<FinishedRecipe> writer, Item hammer, TagKey<Item> materialTag) {
        featureBoundRecipe(writer, "Forging", hammer,
                recipe -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hammer)
                        .pattern("MMM")
                        .pattern("MSM")
                        .pattern(" S ")
                        .define('S', Items.STICK)
                        .define('M', materialTag)
                        .unlockedBy("has_material", has(materialTag))
                        .save(recipe)
        );
    }

    private boolean appendMaterialToName = false;
    private boolean appendGearToName = false;
    private void forgeRecipeRequiresMod(Consumer<FinishedRecipe> writer, String modId, ItemLike material, int amount, ItemLike gear, ItemStack result, int smashesRequired) {
        ResourceLocation recipeId = RecipeBuilder.getDefaultRecipeId(result.getItem());
        if (appendMaterialToName)
            recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(ForgeRegistries.ITEMS.getKey(material.asItem()).getPath() + "_");
        if (appendGearToName)
            recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(ForgeRegistries.ITEMS.getKey(gear.asItem()).getPath() + "_");
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition(modId))
                .addRecipe(writerConsumer -> ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(material), amount, Ingredient.of(gear), result, smashesRequired)
                        .awardExperience(smashesRequired)
                        .unlockedBy("has_material", has(material))
                        .save(writerConsumer))
                .build(writer, recipeId);
    }
    private void forgeRecipe(Consumer<FinishedRecipe> writer, ItemLike material, int amount, ItemLike gear, ItemStack result, int smashesRequired) {
        ResourceLocation recipeId = RecipeBuilder.getDefaultRecipeId(result.getItem());
        if (appendMaterialToName)
            recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(ForgeRegistries.ITEMS.getKey(material.asItem()).getPath() + "_");
        if (appendGearToName)
            recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(ForgeRegistries.ITEMS.getKey(gear.asItem()).getPath() + "_");
        ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(material), amount, Ingredient.of(gear), result, smashesRequired)
                .awardExperience(smashesRequired)
                .unlockedBy("has_material", has(material))
                .save(writer, recipeId);
    }

    private void forgeRecipeRequiresMod(Consumer<FinishedRecipe> writer, String modId, TagKey<Item> materialTag, int amount, ItemLike gear, ItemStack result, int smashesRequired) {
        ResourceLocation recipeId = RecipeBuilder.getDefaultRecipeId(result.getItem());
        if (appendMaterialToName)
            recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(materialTag.location().getPath() + "_");
        if (appendGearToName)
            recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(ForgeRegistries.ITEMS.getKey(gear.asItem()).getPath() + "_");
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition(modId))
                .addRecipe(writerConsumer -> ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(materialTag), amount, Ingredient.of(gear), result, smashesRequired)
                        .awardExperience(smashesRequired)
                        .unlockedBy("has_material", has(materialTag))
                        .save(writerConsumer))
                .build(writer, recipeId);
    }

    private void forgeRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> materialTag, int amount, ItemLike gear, ItemStack result, int smashesRequired) {
        ResourceLocation recipeId = RecipeBuilder.getDefaultRecipeId(result.getItem());
        if (appendMaterialToName)
            recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(materialTag.location().getPath() + "_");
        if (appendGearToName)
            recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(ForgeRegistries.ITEMS.getKey(gear.asItem()).getPath() + "_");
        ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(materialTag), amount, Ingredient.of(gear), result, smashesRequired)
                .awardExperience(smashesRequired)
                .unlockedBy("has_material", has(materialTag))
                .save(writer, recipeId);
    }

    private Consumer<Consumer<FinishedRecipe>> forgeRecipeBuilder(ItemLike material, int amount, ItemLike gear, ItemStack result, int smashesRequired) {
        return recipeWriter -> ForgeRecipeBuilder.forging(RecipeCategory.TOOLS,
                        Ingredient.of(material), amount,
                        Ingredient.of(gear), result, smashesRequired)
                .awardExperience(smashesRequired)
                .unlockedBy("has_material", has(material))
                .save(recipeWriter);
    }

    private Consumer<Consumer<FinishedRecipe>> forgeRecipeBuilder(ItemLike material, int amount, ItemLike gear, ItemLike result, int smashesRequired) {
        return forgeRecipeBuilder(material, amount, gear, new ItemStack(result), smashesRequired);
    }

    private Consumer<Consumer<FinishedRecipe>> forgeRecipeBuilder(TagKey<Item> materialTag, int amount, ItemLike gear, ItemStack result, int smashesRequired) {
        return recipeWriter -> {
            ResourceLocation recipeId = RecipeBuilder.getDefaultRecipeId(result.getItem());
            if (appendMaterialToName)
                recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(materialTag.location().getPath() + "_");
            if (appendGearToName)
                recipeId = ForgeRegistries.ITEMS.getKey(result.getItem()).withPrefix(ForgeRegistries.ITEMS.getKey(gear.asItem()).getPath() + "_");

            ForgeRecipeBuilder.forging(RecipeCategory.TOOLS,
                            Ingredient.of(materialTag), amount,
                            Ingredient.of(gear), result, smashesRequired)
                    .awardExperience(smashesRequired)
                    .unlockedBy("has_material", has(materialTag))
                    .save(recipeWriter, recipeId);
        };
    }

}
