package insane96mcp.iguanatweaksexpanded.data.generator;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.hungerhealth.fooddrinks.FoodDrinks;
import insane96mcp.iguanatweaksexpanded.module.items.altimeter.Altimeter;
import insane96mcp.iguanatweaksexpanded.module.items.copper.CopperExpansion;
import insane96mcp.iguanatweaksexpanded.module.items.crate.Crate;
import insane96mcp.iguanatweaksexpanded.module.items.explosivebarrel.ExplosiveBarrel;
import insane96mcp.iguanatweaksexpanded.module.items.flintexpansion.FlintExpansion;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.Solarium;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.durium.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.ForgeRecipeBuilder;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.miningcharge.MiningCharge;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.data.MultiItemSmeltingRecipeBuilder;
import insane96mcp.iguanatweaksexpanded.module.mining.quaron.Quaron;
import insane96mcp.iguanatweaksexpanded.module.sleeprespawn.Cloth;
import insane96mcp.iguanatweaksexpanded.module.world.coalfire.CoalCharcoal;
import insane96mcp.iguanatweaksexpanded.module.world.oregeneration.OreGeneration;
import insane96mcp.iguanatweaksreborn.module.sleeprespawn.death.Death;
import insane96mcp.shieldsplus.setup.SPItems;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ITERecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ITERecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FlintExpansion.AXE.get())
                .pattern("ff")
                .pattern("fs")
                .pattern(" s")
                .define('f', Items.FLINT)
                .define('s', Items.STICK)
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FlintExpansion.SHOVEL.get())
                .pattern("f")
                .pattern("s")
                .pattern("s")
                .define('f', Items.FLINT)
                .define('s', Items.STICK)
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FlintExpansion.PICKAXE.get())
                .pattern("fff")
                .pattern(" s ")
                .pattern(" s ")
                .define('f', Items.FLINT)
                .define('s', Items.STICK)
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FlintExpansion.HOE.get())
                .pattern("ff")
                .pattern(" s")
                .pattern(" s")
                .define('f', Items.FLINT)
                .define('s', Items.STICK)
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, FlintExpansion.SWORD.get())
                .pattern("f")
                .pattern("f")
                .pattern("s")
                .define('f', Items.FLINT)
                .define('s', Items.STICK)
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, FlintExpansion.SHIELD.get())
                .pattern(" f ")
                .pattern("fWf")
                .pattern(" f ")
                .define('f', Items.FLINT)
                .define('W', SPItems.WOODEN_SHIELD.get())
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CopperExpansion.COPPER_AXE.get())
                .pattern("ff")
                .pattern("fs")
                .pattern(" s")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CopperExpansion.COPPER_SHOVEL.get())
                .pattern("f")
                .pattern("s")
                .pattern("s")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CopperExpansion.COPPER_PICKAXE.get())
                .pattern("fff")
                .pattern(" s ")
                .pattern(" s ")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CopperExpansion.COPPER_HOE.get())
                .pattern("ff")
                .pattern(" s")
                .pattern(" s")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CopperExpansion.COPPER_SWORD.get())
                .pattern("f")
                .pattern("f")
                .pattern("s")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);

        //Coated Copper
        forgeRecipe(writer, Items.OBSIDIAN, 3, CopperExpansion.COPPER_PICKAXE.get(), CopperExpansion.COATED_PICKAXE.get(), 12);
        forgeRecipe(writer, Items.OBSIDIAN, 3, CopperExpansion.COPPER_AXE.get(), CopperExpansion.COATED_AXE.get(), 12);
        forgeRecipe(writer, Items.OBSIDIAN, 2, CopperExpansion.COPPER_SWORD.get(), CopperExpansion.COATED_SWORD.get(), 12);
        forgeRecipe(writer, Items.OBSIDIAN, 2, CopperExpansion.COPPER_HOE.get(), CopperExpansion.COATED_HOE.get(), 12);
        forgeRecipe(writer, Items.OBSIDIAN, 1, CopperExpansion.COPPER_SHOVEL.get(), CopperExpansion.COATED_SHOVEL.get(), 12);
        forgeRecipe(writer, Items.OBSIDIAN, 4, CopperExpansion.COPPER_SHIELD.get(), CopperExpansion.COATED_SHIELD.get(), 12);

        ConditionalRecipe.builder()
                .addCondition(not(modLoaded("tconstruct")))
                .addRecipe(
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.FLINT)
                                .requires(Items.GRAVEL, 3)
                                .unlockedBy("has_gravel", has(Items.GRAVEL))
                                ::save
                )
                .build(writer, IguanaTweaksExpanded.MOD_ID, "flint_from_gravel");

        //Chained Copper Armor
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CopperExpansion.HELMET.get())
                .pattern("cmc")
                .pattern("m m")
                .define('c', Items.IRON_NUGGET)
                .define('m', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_chain", has(Items.IRON_NUGGET))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CopperExpansion.CHESTPLATE.get())
                .pattern("c c")
                .pattern("mcm")
                .pattern("mmm")
                .define('c', Items.IRON_NUGGET)
                .define('m', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_chain", has(Items.IRON_NUGGET))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CopperExpansion.LEGGINGS.get())
                .pattern("ccc")
                .pattern("m m")
                .pattern("m m")
                .define('c', Items.IRON_NUGGET)
                .define('m', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_chain", has(Items.IRON_NUGGET))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CopperExpansion.BOOTS.get())
                .pattern("c c")
                .pattern("m m")
                .define('c', Items.IRON_NUGGET)
                .define('m', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_chain", has(Items.IRON_NUGGET))
                .save(writer);

        //Chainmail Armor
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_HELMET)
                .pattern("CcC")
                .pattern("c c")
                .define('c', Items.CHAIN)
                .define('C', Cloth.CLOTH.get())
                .unlockedBy("has_cloth", has(Cloth.CLOTH.get()))
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "chainmail_helmet");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_CHESTPLATE)
                .pattern("C C")
                .pattern("cCc")
                .pattern("ccc")
                .define('c', Items.CHAIN)
                .define('C', Cloth.CLOTH.get())
                .unlockedBy("has_cloth", has(Cloth.CLOTH.get()))
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "chainmail_chestplate");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_LEGGINGS)
                .pattern("CCC")
                .pattern("c c")
                .pattern("c c")
                .define('c', Items.CHAIN)
                .define('C', Cloth.CLOTH.get())
                .unlockedBy("has_cloth", has(Cloth.CLOTH.get()))
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "chainmail_leggings");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_BOOTS)
                .pattern("C C")
                .pattern("c c")
                .define('c', Items.CHAIN)
                .define('C', Cloth.CLOTH.get())
                .unlockedBy("has_cloth", has(Cloth.CLOTH.get()))
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "chainmail_boots");

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, Crate.ITEM.get())
                .pattern("nnn")
                .pattern("ibi")
                .pattern("nnn")
                .define('n', Durium.NUGGET.get())
                .define('i', Items.IRON_INGOT)
                .define('b', Items.BARREL)
                .unlockedBy("has_durium", has(Durium.INGOT.get()))
                .unlockedBy("has_barrel", has(Items.BARREL))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Altimeter.ITEM.get())
                .pattern(" i ")
                .pattern("frf")
                .pattern(" f ")
                .define('f', Durium.INGOT.get())
                .define('i', Items.IRON_INGOT)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_durium_ingot", has(Durium.INGOT.get()))
                .save(writer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EnchantingFeature.ANCIENT_LAPIS.get(), 1)
                .requires(EnchantingFeature.CLEANSED_LAPIS.get(), 1)
                .requires(Items.NETHERITE_SCRAP, 1)
                .requires(Items.EXPERIENCE_BOTTLE, 1)
                .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP))
                .unlockedBy("has_cleansed_lapis", has(EnchantingFeature.CLEANSED_LAPIS.get()))
                .save(writer);

        //Solarium ball and forging
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Solarium.SOLARIUM_BALL.get(), 1)
                .requires(Solarium.SOLIUM_MOSS.item().get(), 9)
                .unlockedBy("has_solium_moss", has(Solarium.SOLIUM_MOSS.item().get()))
                .save(writer);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 3, Items.WOODEN_PICKAXE, Solarium.PICKAXE.get(), 6);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 3, Items.WOODEN_AXE, Solarium.AXE.get(), 6);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 2, Items.WOODEN_SWORD, Solarium.SWORD.get(), 6);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 2, Items.WOODEN_HOE, Solarium.HOE.get(), 6);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 1, Items.WOODEN_SHOVEL, Solarium.SHOVEL.get(), 6);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 4, SPItems.WOODEN_SHIELD.get(), Solarium.SHIELD.get(), 6);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 5, Items.LEATHER_HELMET, Solarium.HELMET.get(), 4);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 8, Items.LEATHER_CHESTPLATE, Solarium.CHESTPLATE.get(), 6);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 7, Items.LEATHER_LEGGINGS, Solarium.LEGGINGS.get(), 5);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 4, Items.LEATHER_BOOTS, Solarium.BOOTS.get(), 4);

        //Durium Block, Ingot, Nugget, Scrap, forging
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Durium.BLOCK.item().get(), 1)
                .requires(Durium.INGOT.get(), 9)
                .unlockedBy("has_ingot", has(Durium.INGOT.get()))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.INGOT.get(), 9)
                .requires(Durium.BLOCK.item().get(), 1)
                .unlockedBy("has_ingot", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_ingot_from_block");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.INGOT.get(), 1)
                .requires(Durium.NUGGET.get(), 9)
                .unlockedBy("has_nuggets", has(Durium.NUGGET.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_ingot_from_nuggets");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.NUGGET.get(), 9)
                .requires(Durium.INGOT.get(), 1)
                .unlockedBy("has_ingot", has(Durium.INGOT.get()))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.SCRAP_BLOCK.item().get(), 1)
                .requires(Durium.SCRAP_PIECE.get(), 9)
                .unlockedBy("has_piece", has(Durium.SCRAP_PIECE.get()))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Durium.SCRAP_PIECE.get(), 9)
                .requires(Durium.SCRAP_BLOCK.item().get(), 1)
                .unlockedBy("has_piece", has(Durium.SCRAP_PIECE.get()))
                .save(writer);
        addBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Durium.SCRAP_BLOCK.item().get()), Ingredient.of(ItemTags.SAND), Ingredient.of(Items.CLAY_BALL)), Durium.SCRAP_PIECE.get(), Durium.INGOT.get(), 5f, 800);
        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Durium.SCRAP_BLOCK.item().get()), Ingredient.of(ItemTags.SAND), Ingredient.of(Items.CLAY_BALL)), Durium.SCRAP_PIECE.get(), Durium.INGOT.get(), 5f, 1600, 0.3f);

        forgeRecipe(writer, Durium.INGOT.get(), 3, Items.STONE_PICKAXE, Durium.PICKAXE.get(), 14);
        forgeRecipe(writer, Durium.INGOT.get(), 3, Items.STONE_AXE, Durium.AXE.get(), 14);
        forgeRecipe(writer, Durium.INGOT.get(), 2, Items.STONE_SWORD, Durium.SWORD.get(), 14);
        forgeRecipe(writer, Durium.INGOT.get(), 2, Items.STONE_HOE, Durium.HOE.get(), 14);
        forgeRecipe(writer, Durium.INGOT.get(), 1, Items.STONE_SHOVEL, Durium.SHOVEL.get(), 14);
        forgeRecipe(writer, Durium.INGOT.get(), 2, Items.SHEARS, Durium.SHEARS.get(), 14);
        forgeRecipe(writer, Durium.INGOT.get(), 4, SPItems.STONE_SHIELD.get(), Durium.SHIELD.get(), 14);
        forgeRecipe(writer, Durium.INGOT.get(), 5, CopperExpansion.HELMET.get(), Durium.HELMET.get(), 10);
        forgeRecipe(writer, Durium.INGOT.get(), 8, CopperExpansion.CHESTPLATE.get(), Durium.CHESTPLATE.get(), 14);
        forgeRecipe(writer, Durium.INGOT.get(), 7, CopperExpansion.LEGGINGS.get(), Durium.LEGGINGS.get(), 12);
        forgeRecipe(writer, Durium.INGOT.get(), 4, CopperExpansion.BOOTS.get(), Durium.BOOTS.get(), 9);

        //Keego
        forgeRecipe(writer, Keego.GEM.get(), 3, FlintExpansion.PICKAXE.get(), Keego.PICKAXE.get(), 13);
        forgeRecipe(writer, Keego.GEM.get(), 3, FlintExpansion.AXE.get(), Keego.AXE.get(), 13);
        forgeRecipe(writer, Keego.GEM.get(), 2, FlintExpansion.SWORD.get(), Keego.SWORD.get(), 13);
        forgeRecipe(writer, Keego.GEM.get(), 2, FlintExpansion.HOE.get(), Keego.HOE.get(), 13);
        forgeRecipe(writer, Keego.GEM.get(), 1, FlintExpansion.SHOVEL.get(), Keego.SHOVEL.get(), 13);
        forgeRecipe(writer, Keego.GEM.get(), 4, FlintExpansion.SHIELD.get(), Keego.SHIELD.get(), 13);
        forgeRecipe(writer, Keego.GEM.get(), 5, Items.CHAINMAIL_HELMET, Keego.HELMET.get(), 9);
        forgeRecipe(writer, Keego.GEM.get(), 8, Items.CHAINMAIL_CHESTPLATE, Keego.CHESTPLATE.get(), 13);
        forgeRecipe(writer, Keego.GEM.get(), 7, Items.CHAINMAIL_LEGGINGS, Keego.LEGGINGS.get(), 11);
        forgeRecipe(writer, Keego.GEM.get(), 4, Items.CHAINMAIL_BOOTS, Keego.BOOTS.get(), 8);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Keego.BLOCK.block().get(), 1)
                .requires(Keego.GEM.get(), 9)
                .unlockedBy("has_keego", has(Keego.GEM.get()))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Keego.GEM.get(), 9)
                .requires(Keego.BLOCK.block().get(), 1)
                .unlockedBy("has_keego", has(Keego.GEM.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "keego_from_block");


        //Quaron
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Quaron.BLOCK.block().get(), 1)
                .requires(Quaron.INGOT.get(), 9)
                .unlockedBy("has_quaron", has(Quaron.INGOT.get()))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Quaron.INGOT.get(), 9)
                .requires(Quaron.BLOCK.block().get(), 1)
                .unlockedBy("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_ingot_from_block");
        forgeRecipe(writer, Quaron.INGOT.get(), 3, Items.WOODEN_PICKAXE, Quaron.PICKAXE.get(), 17);
        forgeRecipe(writer, Quaron.INGOT.get(), 3, Items.WOODEN_AXE, Quaron.AXE.get(), 17);
        forgeRecipe(writer, Quaron.INGOT.get(), 2, Items.WOODEN_SWORD, Quaron.SWORD.get(), 17);
        forgeRecipe(writer, Quaron.INGOT.get(), 2, Items.WOODEN_HOE, Quaron.HOE.get(), 17);
        forgeRecipe(writer, Quaron.INGOT.get(), 1, Items.WOODEN_SHOVEL, Quaron.SHOVEL.get(), 17);
        forgeRecipe(writer, Quaron.INGOT.get(), 1, Items.FISHING_ROD, Quaron.FISHING_ROD.get(), 17);
        forgeRecipe(writer, Quaron.INGOT.get(), 4, SPItems.WOODEN_SHIELD.get(), Quaron.SHIELD.get(), 17);
        forgeRecipe(writer, Quaron.INGOT.get(), 5, Items.LEATHER_HELMET, Quaron.HELMET.get(), 12);
        forgeRecipe(writer, Quaron.INGOT.get(), 8, Items.LEATHER_CHESTPLATE, Quaron.CHESTPLATE.get(), 17);
        forgeRecipe(writer, Quaron.INGOT.get(), 7, Items.LEATHER_LEGGINGS, Quaron.LEGGINGS.get(), 15);
        forgeRecipe(writer, Quaron.INGOT.get(), 4, Items.LEATHER_BOOTS, Quaron.BOOTS.get(), 11);
        addBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.RAW_IRON), Ingredient.of(Items.AMETHYST_CLUSTER), Ingredient.of(Items.AMETHYST_CLUSTER), Ingredient.of(Items.BLAZE_ROD)), Items.RAW_IRON, Quaron.INGOT.get(), 8f, 800);
        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.RAW_IRON), Ingredient.of(Items.AMETHYST_CLUSTER), Ingredient.of(Items.AMETHYST_CLUSTER), Ingredient.of(Items.BLAZE_ROD)), Items.RAW_IRON, Quaron.INGOT.get(),8f, 1600, 0.3f);

        //Soul Steel
        copySmithingTemplate(writer, SoulSteel.UPGRADE_SMITHING_TEMPLATE.get(), Items.NETHERRACK);
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.AXE.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, SoulSteel.AXE.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_axe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.PICKAXE.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, SoulSteel.PICKAXE.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_pickaxe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.SHOVEL.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, SoulSteel.SHOVEL.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_shovel");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.HOE.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, SoulSteel.HOE.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_hoe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.SWORD.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.SWORD.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_sword");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.SHIELD.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.SHIELD.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_shield");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.HELMET.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.HELMET.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_helmet");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.CHESTPLATE.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.CHESTPLATE.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_chestplate");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.LEGGINGS.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.LEGGINGS.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_leggings");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Quaron.BOOTS.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.BOOTS.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_boots");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SoulSteel.BLOCK.block().get(), 1)
                .requires(SoulSteel.INGOT.get(), 9)
                .unlockedBy("has_ingot", has(SoulSteel.INGOT.get()))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SoulSteel.INGOT.get(), 9)
                .requires(SoulSteel.BLOCK.block().get(), 1)
                .unlockedBy("has_ingot", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_ingot_from_block");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SoulSteel.INGOT.get(), 1)
                .requires(SoulSteel.NUGGET.get(), 9)
                .unlockedBy("has_nuggets", has(SoulSteel.NUGGET.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_ingot_from_nuggets");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SoulSteel.NUGGET.get(), 9)
                .requires(SoulSteel.INGOT.get(), 1)
                .unlockedBy("has_ingot", has(SoulSteel.INGOT.get()))
                .save(writer);
        addBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Durium.INGOT.get()), Ingredient.of(Durium.INGOT.get()), Ingredient.of(CoalCharcoal.HELLISH_COAL.get()), Ingredient.of(Items.SOUL_SAND, Items.SOUL_SOIL)), CoalCharcoal.HELLISH_COAL.get(), SoulSteel.INGOT.get(), 8f, 800);
        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Durium.INGOT.get()), Ingredient.of(Durium.INGOT.get()), Ingredient.of(CoalCharcoal.HELLISH_COAL.get()), Ingredient.of(Items.SOUL_SAND, Items.SOUL_SOIL)), CoalCharcoal.HELLISH_COAL.get(), SoulSteel.INGOT.get(),8f, 1600, 0.3f);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ExplosiveBarrel.BLOCK.item().get())
                .requires(Items.TNT, 1)
                .requires(Items.BARREL, 1)
                .requires(Items.GUNPOWDER, 2)
                .unlockedBy("has_tnt", has(Items.TNT))
                .unlockedBy("has_barrel", has(Items.BARREL))
                .save(writer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodDrinks.BROWN_MUSHROOM_STEW.get())
                .requires(Items.BOWL, 1)
                .requires(Items.BROWN_MUSHROOM, 2)
                .unlockedBy("has_bowl", has(Items.BOWL))
                .unlockedBy("has_mushroom", has(Items.BROWN_MUSHROOM))
                .save(writer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodDrinks.RED_MUSHROOM_STEW.get())
                .requires(Items.BOWL, 1)
                .requires(Items.RED_MUSHROOM, 2)
                .unlockedBy("has_bowl", has(Items.BOWL))
                .unlockedBy("has_mushroom", has(Items.RED_MUSHROOM))
                .save(writer);

        ConditionalRecipe.builder()
                .addCondition(not(modLoaded("incubation")))
                .addRecipe(
                        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.EGG), RecipeCategory.FOOD, FoodDrinks.OVER_EASY_EGG.get(), 0.35f, 600)
                                .unlockedBy("has_egg", has(Items.EGG))
                                ::save
                )
                .build(writer, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "over_easy_egg_from_campfire"));
        ConditionalRecipe.builder()
                .addCondition(not(modLoaded("incubation")))
                .addRecipe(
                        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.EGG), RecipeCategory.FOOD, FoodDrinks.OVER_EASY_EGG.get(), 0.35f, 600)
                                .unlockedBy("has_egg", has(Items.EGG))
                                ::save
                )
                .build(writer, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "over_easy_egg_from_smelting"));
        ConditionalRecipe.builder()
                .addCondition(not(modLoaded("incubation")))
                .addRecipe(
                        SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.EGG), RecipeCategory.FOOD, FoodDrinks.OVER_EASY_EGG.get(), 0.35f, 600)
                                .unlockedBy("has_egg", has(Items.EGG))
                                ::save
                )
                .build(writer, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "over_easy_egg_from_smoking"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodDrinks.MILK_BOTTLE.get(), 3)
                .requires(Ingredient.of(Items.MILK_BUCKET))
                .requires(Ingredient.of(Items.GLASS_BOTTLE), 3)
                .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodDrinks.UNCOOKED_CHEESE_SLICE.get(), 3)
                .requires(Ingredient.of(FoodDrinks.MILK_BOTTLE.get()), 3)
                .requires(Ingredient.of(FoodDrinks.RENNET.get()))
                .unlockedBy("has_milk_bottle", has(FoodDrinks.MILK_BOTTLE.get()))
                .save(writer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(FoodDrinks.UNCOOKED_CHEESE_SLICE.get()), RecipeCategory.FOOD, FoodDrinks.CHEESE_SLICE.get(), 0.35f, 200)
                .unlockedBy("has_milk", has(FoodDrinks.MILK_BOTTLE.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "cheese_slice_from_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(FoodDrinks.UNCOOKED_CHEESE_SLICE.get()), RecipeCategory.FOOD, FoodDrinks.CHEESE_SLICE.get(), 0.35f, 100)
                .unlockedBy("has_milk", has(FoodDrinks.MILK_BOTTLE.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "cheese_slice_from_smoking");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, CoalCharcoal.FIRESTARTER.get())
                .requires(Items.FLINT, 2)
                .requires(Items.IRON_INGOT, 1)
                .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET))
                .save(writer);

        //Mining Charge
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, MiningCharge.MINING_CHARGE.item().get(), 2)
                .pattern(" T ")
                .pattern(" S ")
                .pattern("CCC")
                .define('C', Durium.NUGGET.get())
                .define('T', Items.TNT)
                .define('S', Items.SLIME_BALL)
                .unlockedBy("has_tnt", has(Items.TNT))
                .save(writer);

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
        addBlastingRecipe(writer, Items.RAW_COPPER, Items.COPPER_INGOT, 0.7f, 100, 0.3f);
        addBlastingRecipe(writer, Items.COPPER_ORE, Items.COPPER_INGOT, 0.7f, 50, 6f);
        addBlastingRecipe(writer, Items.DEEPSLATE_COPPER_ORE, Items.COPPER_INGOT, 0.7f, 50, 6f);
        addBlastingRecipe(writer, OreGeneration.COPPER_ORE_ROCK.item().get(), Items.COPPER_INGOT, 0.7f, 50, 6f);
        //Iron
        addBlastingRecipe(writer, Items.RAW_IRON, Items.IRON_INGOT, 1f, 200, 0.3f);
        addBlastingRecipe(writer, Items.IRON_ORE, Items.IRON_INGOT, 1f, 100, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_IRON_ORE, Items.IRON_INGOT, 1f, 100, 1f);
        addBlastingRecipe(writer, OreGeneration.IRON_ORE_ROCK.item().get(), Items.IRON_INGOT, 1f, 100, 1f);
        addBlastingRecipe(writer, Items.IRON_DOOR, Items.IRON_INGOT, 0f, 200);
        addBlastingRecipe(writer, Death.GRAVE.item().get(), Items.IRON_INGOT, 0f, 200);
        //Gold
        addBlastingRecipe(writer, Items.RAW_GOLD, Items.GOLD_INGOT, 2f, 200, 0.3f);
        addBlastingRecipe(writer, Items.GOLD_ORE, Items.GOLD_INGOT, 2f, 100, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_GOLD_ORE, Items.GOLD_INGOT, 2f, 100, 1f);
        addBlastingRecipe(writer, Items.NETHER_GOLD_ORE, Items.GOLD_INGOT, 2f, 100, 1f);
        addBlastingRecipe(writer, OreGeneration.GOLD_ORE_ROCK.item().get(), Items.GOLD_INGOT, 2f, 100, 1f);
        //Durium
        addBlastingRecipe(writer, Durium.ORE.item().get(), Durium.SCRAP_PIECE.get(), 2f, 100, 1f);
        addBlastingRecipe(writer, Durium.DEEPSLATE_ORE.item().get(), Durium.SCRAP_PIECE.get(), 2f, 100, 1f);
        //Other
        addBlastingRecipe(writer, Items.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, 5f, 400);
        addBlastingRecipe(writer, Items.COAL_ORE, Items.COAL, 0.7f, 50, 1f);
        addBlastingRecipe(writer, Items.LAPIS_ORE, Items.LAPIS_LAZULI, 1f, 100, 13f);
        addBlastingRecipe(writer, Items.REDSTONE_ORE, Items.REDSTONE, 2f, 100, 4f);
        addBlastingRecipe(writer, Items.EMERALD_ORE, Items.EMERALD, 4f, 200, 1f);
        addBlastingRecipe(writer, Items.DIAMOND_ORE, Items.DIAMOND, 4f, 200, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_COAL_ORE, Items.COAL, 0.7f, 50, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_LAPIS_ORE, Items.LAPIS_LAZULI, 1f, 100, 13f);
        addBlastingRecipe(writer, Items.DEEPSLATE_REDSTONE_ORE, Items.REDSTONE, 2f, 100, 4f);
        addBlastingRecipe(writer, Items.DEEPSLATE_EMERALD_ORE, Items.EMERALD, 4f, 200, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_DIAMOND_ORE, Items.DIAMOND, 4f, 200, 1f);
        addBlastingRecipe(writer, Items.NETHER_QUARTZ_ORE, Items.QUARTZ, 2f, 100, 3f);

        //Soul Blast furnace recipes
        //Copper
        addSoulBlastingRecipe(writer, Items.RAW_COPPER, Items.COPPER_INGOT, 0.7f, 200);
        addSoulBlastingRecipe(writer, Items.COPPER_ORE, Items.COPPER_INGOT, 0.7f, 100, 2f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_COPPER_ORE, Items.COPPER_INGOT, 0.7f, 100, 2f);
        addSoulBlastingRecipe(writer, OreGeneration.COPPER_ORE_ROCK.item().get(), Items.COPPER_INGOT, 0.7f, 100, 2f);
        //Iron
        addSoulBlastingRecipe(writer, Items.RAW_IRON, Items.IRON_INGOT, 1f, 400);
        addSoulBlastingRecipe(writer, Items.IRON_ORE, Items.IRON_INGOT, 1f, 200, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_IRON_ORE, Items.IRON_INGOT, 1f, 200, 0.3f);
        addSoulBlastingRecipe(writer, OreGeneration.IRON_ORE_ROCK.item().get(), Items.IRON_INGOT, 1f, 200, 0.3f);
        addSoulBlastingRecipe(writer, Items.IRON_DOOR, Items.IRON_INGOT, 0f, 200);
        addSoulBlastingRecipe(writer, Death.GRAVE.item().get(), Items.IRON_INGOT, 0f, 200);
        //Gold
        addSoulBlastingRecipe(writer, Items.RAW_GOLD, Items.GOLD_INGOT, 2f, 400);
        addSoulBlastingRecipe(writer, Items.GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 0.3f);
        addSoulBlastingRecipe(writer, Items.NETHER_GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 0.3f);
        addSoulBlastingRecipe(writer, OreGeneration.GOLD_ORE_ROCK.item().get(), Items.GOLD_INGOT, 2f, 200, 0.3f);
        //Durium
        addSoulBlastingRecipe(writer, Durium.ORE.item().get(), Durium.SCRAP_PIECE.get(), 2f, 200, 0.3f);
        addSoulBlastingRecipe(writer, Durium.DEEPSLATE_ORE.item().get(), Durium.SCRAP_PIECE.get(), 2f, 200, 0.3f);
        //Other
        addSoulBlastingRecipe(writer, Items.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, 5f, 800);
        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Items.GOLD_INGOT)), Items.NETHERITE_SCRAP, Items.NETHERITE_INGOT, 8f, 1600, 0.3f);
        addSoulBlastingRecipe(writer, Items.COAL_ORE, Items.COAL, 0.7f, 100, 0.3f);
        addSoulBlastingRecipe(writer, Items.LAPIS_ORE, Items.LAPIS_LAZULI, 1f, 200, 4.3f);
        addSoulBlastingRecipe(writer, Items.REDSTONE_ORE, Items.REDSTONE, 2f, 200, 1.3f);
        addSoulBlastingRecipe(writer, Items.EMERALD_ORE, Items.EMERALD, 4f, 400, 0.3f);
        addSoulBlastingRecipe(writer, Items.DIAMOND_ORE, Items.DIAMOND, 4f, 400, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_COAL_ORE, Items.COAL, 0.7f, 100, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_LAPIS_ORE, Items.LAPIS_LAZULI, 1f, 200, 4.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_REDSTONE_ORE, Items.REDSTONE, 2f, 200, 1.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_EMERALD_ORE, Items.EMERALD, 4f, 400, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_DIAMOND_ORE, Items.DIAMOND, 4f, 400, 0.3f);
        addSoulBlastingRecipe(writer, Items.NETHER_QUARTZ_ORE, Items.QUARTZ, 2f, 200, 1f);

        //<editor-fold desc="Chained Copper Armor">
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CopperExpansion.HELMET.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_chained_armor", has(CopperExpansion.HELMET.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "smelting_chained_copper_helmet");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CopperExpansion.CHESTPLATE.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_chained_armor", has(CopperExpansion.CHESTPLATE.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "smelting_chained_copper_chestplate");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CopperExpansion.LEGGINGS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_chained_armor", has(CopperExpansion.LEGGINGS.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "smelting_chained_copper_leggings");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CopperExpansion.BOOTS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_chained_armor", has(CopperExpansion.BOOTS.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "smelting_chained_copper_boots");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CopperExpansion.HELMET.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_chained_armor", has(CopperExpansion.HELMET.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blasting_chained_copper_helmet");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CopperExpansion.CHESTPLATE.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_chained_armor", has(CopperExpansion.CHESTPLATE.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blasting_chained_copper_chestplate");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CopperExpansion.LEGGINGS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_chained_armor", has(CopperExpansion.LEGGINGS.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blasting_chained_copper_leggings");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CopperExpansion.BOOTS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_chained_armor", has(CopperExpansion.BOOTS.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blasting_chained_copper_boots");
        //</editor-fold>

        //Hellish Coal
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CoalCharcoal.SOUL_SAND_HELLISH_COAL_ORE.item().get()),
                        RecipeCategory.MISC,
                        CoalCharcoal.HELLISH_COAL.get(),
                        1.2f,
                        200
                )
                .unlockedBy("has_hellish_coal_ore", has(CoalCharcoal.SOUL_SAND_HELLISH_COAL_ORE.item().get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "hellish_coal_from_smelting_soul_sand_ore");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(CoalCharcoal.SOUL_SOIL_HELLISH_COAL_ORE.item().get()),
                        RecipeCategory.MISC,
                        CoalCharcoal.HELLISH_COAL.get(),
                        1.2f,
                        200
                )
                .unlockedBy("has_hellish_coal_ore", has(CoalCharcoal.SOUL_SOIL_HELLISH_COAL_ORE.item().get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "hellish_coal_from_smelting_soul_soil_ore");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CoalCharcoal.SOUL_SAND_HELLISH_COAL_ORE.item().get()),
                        RecipeCategory.MISC,
                        CoalCharcoal.HELLISH_COAL.get(),
                        1.2f,
                        100
                )
                .unlockedBy("has_hellish_coal_ore", has(CoalCharcoal.SOUL_SAND_HELLISH_COAL_ORE.item().get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "hellish_coal_from_blasting_soul_sand_ore");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(CoalCharcoal.SOUL_SOIL_HELLISH_COAL_ORE.item().get()),
                        RecipeCategory.MISC,
                        CoalCharcoal.HELLISH_COAL.get(),
                        1.2f,
                        100
                )
                .unlockedBy("has_hellish_coal_ore", has(CoalCharcoal.SOUL_SOIL_HELLISH_COAL_ORE.item().get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "hellish_coal_from_blasting_soul_soil_ore");

        //Recycle recipes
        recycleGear(writer, CopperExpansion.HELMET.get(), Items.IRON_NUGGET, 200, 6);
        recycleGear(writer, CopperExpansion.CHESTPLATE.get(), Items.IRON_NUGGET, 200, 9);
        recycleGear(writer, CopperExpansion.LEGGINGS.get(), Items.IRON_NUGGET, 200, 9);
        recycleGear(writer, CopperExpansion.BOOTS.get(), Items.IRON_NUGGET, 200, 6);
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

        recycleGear(writer, CopperExpansion.COPPER_PICKAXE.get(), Items.COPPER_INGOT, 200, 3);
        recycleGear(writer, CopperExpansion.COPPER_AXE.get(), Items.COPPER_INGOT, 200, 3);
        recycleGear(writer, CopperExpansion.COPPER_SHOVEL.get(), Items.COPPER_INGOT, 200, 1);
        recycleGear(writer, CopperExpansion.COPPER_HOE.get(), Items.COPPER_INGOT, 200, 2);
        recycleGear(writer, CopperExpansion.COPPER_SWORD.get(), Items.COPPER_INGOT, 200, 2);
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
        recycleGear(writer, CopperExpansion.COATED_PICKAXE.get(), Items.OBSIDIAN, 200, 3);
        recycleGear(writer, CopperExpansion.COATED_AXE.get(), Items.OBSIDIAN, 200, 3);
        recycleGear(writer, CopperExpansion.COATED_SHOVEL.get(), Items.OBSIDIAN, 200, 1);
        recycleGear(writer, CopperExpansion.COATED_HOE.get(), Items.OBSIDIAN, 200, 2);
        recycleGear(writer, CopperExpansion.COATED_SWORD.get(), Items.OBSIDIAN, 200, 2);
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
        recycleGear(writer, CopperExpansion.COPPER_SHIELD.get(), Items.COPPER_INGOT, 200, 4);
        recycleGear(writer, Items.SHIELD, Items.IRON_NUGGET, 200, 36);
        recycleGear(writer, SPItems.IRON_SHIELD.get(), Items.IRON_NUGGET, 200, 36);
        recycleGear(writer, Durium.SHIELD.get(), Durium.NUGGET.get(), 200, 36);
        recycleGear(writer, SPItems.GOLDEN_SHIELD.get(), Items.GOLD_NUGGET, 200, 36);
        recycleGear(writer, CopperExpansion.COATED_SHIELD.get(), Items.OBSIDIAN, 200, 4);
        recycleGear(writer, Quaron.SHIELD.get(), Quaron.NUGGET.get(), 200, 36);
        recycleGear(writer, Keego.SHIELD.get(), Keego.GEM.get(), 200, 4);
        recycleGear(writer, SPItems.DIAMOND_SHIELD.get(), Items.DIAMOND, 200, 1);
        recycleGear(writer, SPItems.NETHERITE_SHIELD.get(), Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, SoulSteel.SHIELD.get(), SoulSteel.NUGGET.get(), 200, 9);

        //Forge recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Forging.FORGE.item().get())
                .pattern("ISI")
                .pattern(" c ")
                .pattern("cCc")
                .define('S', Items.SMOOTH_STONE)
                .define('I', Items.IRON_BLOCK)
                .define('c', Items.COPPER_INGOT)
                .define('C', Items.COPPER_BLOCK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);

        hammerCraftingRecipe(writer, Forging.WOODEN_HAMMER.get(), ItemTags.PLANKS);
        hammerCraftingRecipe(writer, Forging.STONE_HAMMER.get(), ItemTags.STONE_TOOL_MATERIALS);
        hammerCraftingRecipe(writer, Forging.FLINT_HAMMER.get(), Items.FLINT);
        hammerCraftingRecipe(writer, Forging.COPPER_HAMMER.get(), Items.COPPER_INGOT);
        forgeRecipe(writer, Items.IRON_INGOT, 5, Forging.STONE_HAMMER.get(), Forging.IRON_HAMMER.get(), 10);
        forgeRecipe(writer, Solarium.SOLARIUM_BALL.get(), 5, Forging.WOODEN_HAMMER.get(), Forging.SOLARIUM_HAMMER.get(), 6);
        forgeRecipe(writer, Durium.INGOT.get(), 5, Forging.STONE_HAMMER.get(), Forging.DURIUM_HAMMER.get(), 14);
        forgeRecipe(writer, Quaron.INGOT.get(), 5, Forging.WOODEN_HAMMER.get(), Forging.QUARON_HAMMER.get(), 17);
        forgeRecipe(writer, Items.GOLD_INGOT, 5, Forging.COPPER_HAMMER.get(), Forging.GOLDEN_HAMMER.get(), 6);
        forgeRecipe(writer, Items.OBSIDIAN, 5, Forging.COPPER_HAMMER.get(), Forging.COATED_COPPER_HAMMER.get(), 12);
        forgeRecipe(writer, Keego.GEM.get(), 5, Forging.FLINT_HAMMER.get(), Forging.KEEGO_HAMMER.get(), 16);
        forgeRecipe(writer, Items.DIAMOND, 5, Forging.GOLDEN_HAMMER.get(), Forging.DIAMOND_HAMMER.get(), 16);
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Forging.IRON_HAMMER.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, Forging.SOUL_STEEL_HAMMER.get())
                .unlocks("has_material", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_hammer");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Forging.DIAMOND_HAMMER.get()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, Forging.NETHERITE_HAMMER.get())
                .unlocks("has_material", has(Items.NETHERITE_INGOT))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "netherite_hammer");

        //Recycle Forge Hammers
        recycleGear(writer, Forging.COPPER_HAMMER.get(), Items.COPPER_INGOT, 200, 5);
        recycleGear(writer, Forging.IRON_HAMMER.get(), Items.IRON_NUGGET, 200, 45);
        recycleGear(writer, Forging.DURIUM_HAMMER.get(), Durium.NUGGET.get(), 200, 45);
        recycleGear(writer, Forging.QUARON_HAMMER.get(), Quaron.NUGGET.get(), 200, 45);
        recycleGear(writer, Forging.GOLDEN_HAMMER.get(), Items.GOLD_NUGGET, 200, 45);
        recycleGear(writer, Forging.COATED_COPPER_HAMMER.get(), Items.OBSIDIAN, 200, 5);
        recycleGear(writer, Forging.KEEGO_HAMMER.get(), Keego.GEM.get(), 200, 5);
        recycleGear(writer, Forging.DIAMOND_HAMMER.get(), Items.DIAMOND, 200, 5);
        recycleGear(writer, Forging.NETHERITE_HAMMER.get(), Items.NETHERITE_INGOT, 200, 1);
        recycleGear(writer, Forging.SOUL_STEEL_HAMMER.get(), SoulSteel.NUGGET.get(), 200, 9);

        addPoorRichOreRecipes(writer, OreGeneration.POOR_RICH_COPPER_ORE, Items.COPPER_INGOT, 0.75f, 100, 6f);
        addPoorRichOreRecipes(writer, OreGeneration.POOR_RICH_IRON_ORE, Items.IRON_INGOT, 1f, 200, 1f);
        addPoorRichOreRecipes(writer, OreGeneration.POOR_RICH_GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 1f);
    }

    private void addPoorRichOreRecipes(Consumer<FinishedRecipe> writer, OreGeneration.PoorRichOre poorRichOre, Item smeltOutput, float experience, int cookingTime, float baseOutputIncrease) {
        addBlastingRecipe(writer, poorRichOre.poorOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease / 2f);
        addBlastingRecipe(writer, poorRichOre.poorDeepslateOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease / 2f);
        addBlastingRecipe(writer, poorRichOre.richOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease * 1.5f);
        addBlastingRecipe(writer, poorRichOre.richDeepslateOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease * 1.5f);
        addSoulBlastingRecipe(writer, poorRichOre.poorOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease / 2f * 0.3f);
        addSoulBlastingRecipe(writer, poorRichOre.poorDeepslateOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease / 2f * 0.3f);
        addSoulBlastingRecipe(writer, poorRichOre.richOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease * 1.5f * 0.3f);
        addSoulBlastingRecipe(writer, poorRichOre.richDeepslateOre().item().get(), smeltOutput, experience, cookingTime, baseOutputIncrease * 1.5f * 0.3f);
    }

    public static void addBlastingRecipe(Consumer<FinishedRecipe> writer, Item item, Item result, float experience, int cookingTime) {
        addBlastingRecipe(writer, item, result, experience, cookingTime, 0f);
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
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blast_furnace/" + resultPath + "_from_" + itemPath);
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
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blast_furnace/alloy/" + resultPath);
    }

    public static void addSoulBlastingRecipe(Consumer<FinishedRecipe> writer, Item item, Item result, float experience, int cookingTime) {
        addSoulBlastingRecipe(writer, item, result, experience, cookingTime, 0f);
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
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_blast_furnace/" + resultPath + "_from_" + itemPath);
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
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_blast_furnace/alloy/" + resultPath);
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
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blast_furnace/recycle_" + ForgeRegistries.ITEMS.getKey(itemToRecycle).getPath());

        MultiItemSmeltingRecipeBuilder.soulBlasting(
                        NonNullList.of(Ingredient.EMPTY, Ingredient.of(itemToRecycle)),
                        RecipeCategory.COMBAT,
                        output,
                        baseCookingTime)
                .recycle(amountAtMaxDurability)
                .unlockedBy("has_armor", has(itemToRecycle))
                .group("recycle_" + ForgeRegistries.ITEMS.getKey(output).getPath())
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_furnace/recycle_" + ForgeRegistries.ITEMS.getKey(itemToRecycle).getPath());
    }

    private void hammerCraftingRecipe(Consumer<FinishedRecipe> writer, Item hammer, Item material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hammer)
                .pattern("MMM")
                .pattern("MSM")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('M', material)
                .unlockedBy("has_material", has(material))
                .save(writer);
    }

    private void hammerCraftingRecipe(Consumer<FinishedRecipe> writer, Item hammer, TagKey<Item> materialTag) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hammer)
                .pattern("MMM")
                .pattern("MSM")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('M', materialTag)
                .unlockedBy("has_material", has(materialTag))
                .save(writer);
    }

    private void forgeRecipe(Consumer<FinishedRecipe> writer, Item material, int amount, Item gear, Item result, int smashesRequired) {
        ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(material), amount, Ingredient.of(gear), result, smashesRequired)
                .awardExperience(smashesRequired)
                .unlockedBy("has_material", has(material))
                .save(writer);
    }

    private void forgeRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> materialTag, int amount, Item gear, Item result, int smashesRequired) {
        ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(materialTag), amount, Ingredient.of(gear), result, smashesRequired)
                .awardExperience(smashesRequired)
                .unlockedBy("has_material", has(materialTag))
                .save(writer);
    }

    private void forgeRecipe(Consumer<FinishedRecipe> writer, Item materialTag, int amount, TagKey<Item> gear, Item result, int smashesRequired) {
        ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(materialTag), amount, Ingredient.of(gear), result, smashesRequired)
                .awardExperience(smashesRequired)
                .unlockedBy("has_material", has(materialTag))
                .save(writer);
    }

    private void forgeRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> materialTag, int amount, TagKey<Item> gear, Item result, int smashesRequired) {
        ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(materialTag), amount, Ingredient.of(gear), result, smashesRequired)
                .awardExperience(smashesRequired)
                .unlockedBy("has_material", has(materialTag))
                .save(writer);
    }
}
