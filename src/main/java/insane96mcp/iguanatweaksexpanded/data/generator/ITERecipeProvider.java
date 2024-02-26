package insane96mcp.iguanatweaksexpanded.data.generator;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.hungerhealth.fooddrinks.FoodDrinks;
import insane96mcp.iguanatweaksexpanded.module.items.ChainedCopperArmor;
import insane96mcp.iguanatweaksexpanded.module.items.altimeter.Altimeter;
import insane96mcp.iguanatweaksexpanded.module.items.copper.CopperToolsExpansion;
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

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CopperToolsExpansion.COPPER_AXE.get())
                .pattern("ff")
                .pattern("fs")
                .pattern(" s")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CopperToolsExpansion.COPPER_SHOVEL.get())
                .pattern("f")
                .pattern("s")
                .pattern("s")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CopperToolsExpansion.COPPER_PICKAXE.get())
                .pattern("fff")
                .pattern(" s ")
                .pattern(" s ")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CopperToolsExpansion.COPPER_HOE.get())
                .pattern("ff")
                .pattern(" s")
                .pattern(" s")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CopperToolsExpansion.COPPER_SWORD.get())
                .pattern("f")
                .pattern("f")
                .pattern("s")
                .define('f', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(writer);

        //Coated Copper
        forgeRecipe(writer, Items.OBSIDIAN, 3, CopperToolsExpansion.COPPER_PICKAXE.get(), CopperToolsExpansion.COATED_PICKAXE.get(), 12, 9);
        forgeRecipe(writer, Items.OBSIDIAN, 3, CopperToolsExpansion.COPPER_AXE.get(), CopperToolsExpansion.COATED_AXE.get(), 12, 9);
        forgeRecipe(writer, Items.OBSIDIAN, 2, CopperToolsExpansion.COPPER_SWORD.get(), CopperToolsExpansion.COATED_SWORD.get(), 12, 9);
        forgeRecipe(writer, Items.OBSIDIAN, 2, CopperToolsExpansion.COPPER_HOE.get(), CopperToolsExpansion.COATED_HOE.get(), 12, 9);
        forgeRecipe(writer, Items.OBSIDIAN, 1, CopperToolsExpansion.COPPER_SHOVEL.get(), CopperToolsExpansion.COATED_SHOVEL.get(), 12, 9);
        forgeRecipe(writer, Items.OBSIDIAN, 4, CopperToolsExpansion.COPPER_SHIELD.get(), CopperToolsExpansion.COATED_SHIELD.get(), 12, 9);

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
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ChainedCopperArmor.HELMET.get())
                .pattern("cmc")
                .pattern("m m")
                .define('c', Items.IRON_NUGGET)
                .define('m', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_chain", has(Items.IRON_NUGGET))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ChainedCopperArmor.CHESTPLATE.get())
                .pattern("c c")
                .pattern("mcm")
                .pattern("mmm")
                .define('c', Items.IRON_NUGGET)
                .define('m', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_chain", has(Items.IRON_NUGGET))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ChainedCopperArmor.LEGGINGS.get())
                .pattern("ccc")
                .pattern("m m")
                .pattern("m m")
                .define('c', Items.IRON_NUGGET)
                .define('m', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_chain", has(Items.IRON_NUGGET))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ChainedCopperArmor.BOOTS.get())
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

        //Solarium
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Solarium.SOLARIUM_BALL.get(), 1)
                .requires(Solarium.SOLIUM_MOSS.item().get(), 9)
                .unlockedBy("has_solium_moss", has(Solarium.SOLIUM_MOSS.item().get()))
                .save(writer);
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_AXE), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.TOOLS, Solarium.AXE.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_axe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_PICKAXE), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.TOOLS, Solarium.PICKAXE.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_pickaxe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_SHOVEL), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.TOOLS, Solarium.SHOVEL.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_shovel");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_HOE), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.TOOLS, Solarium.HOE.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_hoe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_SWORD), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.COMBAT, Solarium.SWORD.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_sword");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(SPItems.IRON_SHIELD.get()), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.COMBAT, Solarium.SHIELD.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_shield");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_HELMET), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.COMBAT, Solarium.HELMET.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_helmet");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_CHESTPLATE), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.COMBAT, Solarium.CHESTPLATE.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_chestplate");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_LEGGINGS), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.COMBAT, Solarium.LEGGINGS.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_leggings");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_BOOTS), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.COMBAT, Solarium.BOOTS.get())
                .unlocks("has_solarium_moss_ball", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_boots");

        //Durium Block, Ingot, Nugget, Scrap, smithing
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

        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_AXE), Ingredient.of(Durium.INGOT.get()), RecipeCategory.TOOLS, Durium.AXE.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_axe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_PICKAXE), Ingredient.of(Durium.INGOT.get()), RecipeCategory.TOOLS, Durium.PICKAXE.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_pickaxe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_SHOVEL), Ingredient.of(Durium.INGOT.get()), RecipeCategory.TOOLS, Durium.SHOVEL.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_shovel");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_HOE), Ingredient.of(Durium.INGOT.get()), RecipeCategory.TOOLS, Durium.HOE.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_hoe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_SWORD), Ingredient.of(Durium.INGOT.get()), RecipeCategory.COMBAT, Durium.SWORD.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_sword");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(SPItems.IRON_SHIELD.get()), Ingredient.of(Durium.INGOT.get()), RecipeCategory.COMBAT, Durium.SHIELD.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_shield");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_HELMET), Ingredient.of(Durium.INGOT.get()), RecipeCategory.COMBAT, Durium.HELMET.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_helmet");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_CHESTPLATE), Ingredient.of(Durium.INGOT.get()), RecipeCategory.COMBAT, Durium.CHESTPLATE.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_chestplate");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_LEGGINGS), Ingredient.of(Durium.INGOT.get()), RecipeCategory.COMBAT, Durium.LEGGINGS.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_leggings");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.IRON_BOOTS), Ingredient.of(Durium.INGOT.get()), RecipeCategory.COMBAT, Durium.BOOTS.get())
                .unlocks("has_durium", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_boots");

        //Keego
        forgeRecipe(writer, Keego.GEM.get(), 3, Items.GOLDEN_PICKAXE, Keego.PICKAXE.get(), 16, 15);
        forgeRecipe(writer, Keego.GEM.get(), 3, Items.GOLDEN_AXE, Keego.AXE.get(), 16, 15);
        forgeRecipe(writer, Keego.GEM.get(), 2, Items.GOLDEN_SWORD, Keego.SWORD.get(), 16, 15);
        forgeRecipe(writer, Keego.GEM.get(), 2, Items.GOLDEN_HOE, Keego.HOE.get(), 16, 15);
        forgeRecipe(writer, Keego.GEM.get(), 1, Items.GOLDEN_SHOVEL, Keego.SHOVEL.get(), 16, 15);
        forgeRecipe(writer, Keego.GEM.get(), 4, SPItems.GOLDEN_SHIELD.get(), Keego.SHIELD.get(), 17, 18);
        forgeRecipe(writer, Keego.GEM.get(), 5, Items.GOLDEN_HELMET, Keego.HELMET.get(), 18, 17);
        forgeRecipe(writer, Keego.GEM.get(), 8, Items.GOLDEN_CHESTPLATE, Keego.CHESTPLATE.get(), 25, 23);
        forgeRecipe(writer, Keego.GEM.get(), 7, Items.GOLDEN_LEGGINGS, Keego.LEGGINGS.get(), 22, 21);
        forgeRecipe(writer, Keego.GEM.get(), 4, Items.GOLDEN_BOOTS, Keego.BOOTS.get(), 16, 15);
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
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.WOODEN_AXE), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.TOOLS, Quaron.AXE.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_axe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.WOODEN_PICKAXE), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.TOOLS, Quaron.PICKAXE.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_pickaxe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.WOODEN_SHOVEL), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.TOOLS, Quaron.SHOVEL.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_shovel");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.WOODEN_HOE), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.TOOLS, Quaron.HOE.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_hoe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.WOODEN_SWORD), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.COMBAT, Quaron.SWORD.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_sword");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(SPItems.WOODEN_SHIELD.get()), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.COMBAT, Quaron.SHIELD.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_shield");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.FISHING_ROD), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.TOOLS, Quaron.FISHING_ROD.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_fishing_rod");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.LEATHER_HELMET), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.COMBAT, Quaron.HELMET.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_helmet");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.LEATHER_CHESTPLATE), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.COMBAT, Quaron.CHESTPLATE.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_chestplate");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.LEATHER_LEGGINGS), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.COMBAT, Quaron.LEGGINGS.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_leggings");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Items.LEATHER_BOOTS), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.COMBAT, Quaron.BOOTS.get())
                .unlocks("has_quaron", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_boots");
        addBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.RAW_IRON), Ingredient.of(Items.AMETHYST_BLOCK), Ingredient.of(Items.BLAZE_ROD)), Items.RAW_IRON, Quaron.INGOT.get(), 8f, 800);
        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.RAW_IRON), Ingredient.of(Items.AMETHYST_BLOCK), Ingredient.of(Items.BLAZE_ROD)), Items.RAW_IRON, Quaron.INGOT.get(),8f, 1600, 0.3f);

        //Soul Steel
        copySmithingTemplate(writer, SoulSteel.UPGRADE_SMITHING_TEMPLATE.get(), Items.NETHERRACK);
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Items.IRON_AXE), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, SoulSteel.AXE.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_axe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Items.IRON_PICKAXE), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, SoulSteel.PICKAXE.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_pickaxe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Items.IRON_SHOVEL), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, SoulSteel.SHOVEL.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_shovel");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Items.IRON_HOE), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, SoulSteel.HOE.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_hoe");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Items.IRON_SWORD), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.SWORD.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_sword");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(SPItems.IRON_SHIELD.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.SHIELD.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_shield");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Items.IRON_HELMET), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.HELMET.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_helmet");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Items.IRON_CHESTPLATE), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.CHESTPLATE.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_chestplate");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Items.IRON_LEGGINGS), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.LEGGINGS.get())
                .unlocks("has_soul_steel", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_leggings");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Items.IRON_BOOTS), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.COMBAT, SoulSteel.BOOTS.get())
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

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.EGG), RecipeCategory.FOOD, FoodDrinks.OVER_EASY_EGG.get(), 0.35f, 600)
                .unlockedBy("has_egg", has(Items.EGG))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "over_easy_egg_from_campfire");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.EGG), RecipeCategory.FOOD, FoodDrinks.OVER_EASY_EGG.get(), 0.35f, 200)
                .unlockedBy("has_egg", has(Items.EGG))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "over_easy_egg_from_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.EGG), RecipeCategory.FOOD, FoodDrinks.OVER_EASY_EGG.get(), 0.35f, 100)
                .unlockedBy("has_egg", has(Items.EGG))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "over_easy_egg_from_smoking");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodDrinks.MILK_BOTTLE.get(), 3)
                .requires(Ingredient.of(Items.MILK_BUCKET))
                .requires(Ingredient.of(Items.GLASS_BOTTLE), 3)
                .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                .save(writer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(FoodDrinks.MILK_BOTTLE.get()), RecipeCategory.FOOD, FoodDrinks.CHEESE_SLICE.get(), 0.35f, 200)
                .unlockedBy("has_milk", has(FoodDrinks.MILK_BOTTLE.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "cheese_slice_from_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(FoodDrinks.MILK_BOTTLE.get()), RecipeCategory.FOOD, FoodDrinks.CHEESE_SLICE.get(), 0.35f, 100)
                .unlockedBy("has_milk", has(FoodDrinks.MILK_BOTTLE.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "cheese_slice_from_smoking");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, CoalCharcoal.FIRESTARTER.get())
                .requires(Items.FLINT, 2)
                .requires(Items.IRON_INGOT, 1)
                .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET))
                .save(writer);

        //Mining Charge
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, MiningCharge.MINING_CHARGE.item().get(), 2)
                .pattern("GTG")
                .pattern("CsC")
                .pattern("CCC")
                .define('G', Items.GUNPOWDER)
                .define('C', Durium.NUGGET.get())
                .define('T', Items.TNT)
                .define('s', Items.SLIME_BALL)
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
        addBlastingRecipe(writer, Items.COPPER_ORE, Items.COPPER_INGOT, 0.7f, 100, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_COPPER_ORE, Items.COPPER_INGOT, 0.7f, 100, 1f);
        addBlastingRecipe(writer, OreGeneration.COPPER_ORE_ROCK.item().get(), Items.COPPER_INGOT, 0.7f, 100, 1f);
        //Iron
        addBlastingRecipe(writer, Items.RAW_IRON, Items.IRON_INGOT, 1f, 200, 0.3f);
        addBlastingRecipe(writer, Items.IRON_ORE, Items.IRON_INGOT, 1f, 200, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_IRON_ORE, Items.IRON_INGOT, 1f, 200, 1f);
        addBlastingRecipe(writer, OreGeneration.IRON_ORE_ROCK.item().get(), Items.IRON_INGOT, 1f, 200, 1f);
        addBlastingRecipe(writer, Items.IRON_DOOR, Items.IRON_INGOT, 0f, 200);
        addBlastingRecipe(writer, Death.GRAVE.item().get(), Items.IRON_INGOT, 0f, 200);
        //Gold
        addBlastingRecipe(writer, Items.RAW_GOLD, Items.GOLD_INGOT, 2f, 200, 0.3f);
        addBlastingRecipe(writer, Items.GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 1f);
        addBlastingRecipe(writer, Items.DEEPSLATE_GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 1f);
        addBlastingRecipe(writer, Items.NETHER_GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 1f);
        addBlastingRecipe(writer, OreGeneration.GOLD_ORE_ROCK.item().get(), Items.GOLD_INGOT, 2f, 200, 1f);
        //Durium
        addBlastingRecipe(writer, Durium.ORE.item().get(), Durium.SCRAP_PIECE.get(), 2f, 200, 1f);
        addBlastingRecipe(writer, Durium.DEEPSLATE_ORE.item().get(), Durium.SCRAP_PIECE.get(), 2f, 200, 1f);
        //Other
        addBlastingRecipe(writer, Items.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, 5f, 400);

        //Soul Blast furnace recipes
        //Copper
        addSoulBlastingRecipe(writer, Items.RAW_COPPER, Items.COPPER_INGOT, 0.7f, 200);
        addSoulBlastingRecipe(writer, Items.COPPER_ORE, Items.COPPER_INGOT, 0.7f, 200, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_COPPER_ORE, Items.COPPER_INGOT, 0.7f, 200, 0.3f);
        addSoulBlastingRecipe(writer, OreGeneration.COPPER_ORE_ROCK.item().get(), Items.COPPER_INGOT, 0.7f, 200);
        //Iron
        addSoulBlastingRecipe(writer, Items.RAW_IRON, Items.IRON_INGOT, 1f, 400);
        addSoulBlastingRecipe(writer, Items.IRON_ORE, Items.IRON_INGOT, 1f, 400, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_IRON_ORE, Items.IRON_INGOT, 1f, 400, 0.3f);
        addSoulBlastingRecipe(writer, OreGeneration.IRON_ORE_ROCK.item().get(), Items.IRON_INGOT, 1f, 400);
        addSoulBlastingRecipe(writer, Items.IRON_DOOR, Items.IRON_INGOT, 0f, 400);
        addSoulBlastingRecipe(writer, Death.GRAVE.item().get(), Items.IRON_INGOT, 0f, 400);
        //Gold
        addSoulBlastingRecipe(writer, Items.RAW_GOLD, Items.GOLD_INGOT, 2f, 200);
        addSoulBlastingRecipe(writer, Items.GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 0.3f);
        addSoulBlastingRecipe(writer, Items.DEEPSLATE_GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 0.3f);
        addSoulBlastingRecipe(writer, Items.NETHER_GOLD_ORE, Items.GOLD_INGOT, 2f, 200, 0.3f);
        addSoulBlastingRecipe(writer, OreGeneration.GOLD_ORE_ROCK.item().get(), Items.GOLD_INGOT, 2f, 200);
        //Durium
        addSoulBlastingRecipe(writer, Durium.ORE.item().get(), Durium.SCRAP_PIECE.get(), 2f, 400, 0.3f);
        addSoulBlastingRecipe(writer, Durium.DEEPSLATE_ORE.item().get(), Durium.SCRAP_PIECE.get(), 2f, 400, 0.3f);
        //Other
        addSoulBlastingRecipe(writer, Items.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, 5f, 800);
        addSoulBlastingAlloy(writer, NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.NETHERITE_SCRAP), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Items.GOLD_INGOT)), Items.NETHERITE_SCRAP, Items.NETHERITE_INGOT, 8f, 1600, 0.3f);

        //<editor-fold desc="Chained Copper Armor">
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ChainedCopperArmor.HELMET.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_chained_armor", has(ChainedCopperArmor.HELMET.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "smelting_chained_copper_helmet");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ChainedCopperArmor.CHESTPLATE.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_chained_armor", has(ChainedCopperArmor.CHESTPLATE.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "smelting_chained_copper_chestplate");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ChainedCopperArmor.LEGGINGS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_chained_armor", has(ChainedCopperArmor.LEGGINGS.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "smelting_chained_copper_leggings");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ChainedCopperArmor.BOOTS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        200
                )
                .unlockedBy("has_chained_armor", has(ChainedCopperArmor.BOOTS.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "smelting_chained_copper_boots");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ChainedCopperArmor.HELMET.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_chained_armor", has(ChainedCopperArmor.HELMET.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blasting_chained_copper_helmet");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ChainedCopperArmor.CHESTPLATE.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_chained_armor", has(ChainedCopperArmor.CHESTPLATE.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blasting_chained_copper_chestplate");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ChainedCopperArmor.LEGGINGS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_chained_armor", has(ChainedCopperArmor.LEGGINGS.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blasting_chained_copper_leggings");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ChainedCopperArmor.BOOTS.get()),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0,
                        100
                )
                .unlockedBy("has_chained_armor", has(ChainedCopperArmor.BOOTS.get()))
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
        recycleGearBlasting(writer, ChainedCopperArmor.HELMET.get(), Items.IRON_NUGGET, 200, 6);
        recycleGearBlasting(writer, ChainedCopperArmor.CHESTPLATE.get(), Items.IRON_NUGGET, 200, 9);
        recycleGearBlasting(writer, ChainedCopperArmor.LEGGINGS.get(), Items.IRON_NUGGET, 200, 9);
        recycleGearBlasting(writer, ChainedCopperArmor.BOOTS.get(), Items.IRON_NUGGET, 200, 6);
        recycleGearBlasting(writer, Items.IRON_HELMET, Items.IRON_NUGGET, 200, 45);
        recycleGearBlasting(writer, Items.IRON_CHESTPLATE, Items.IRON_NUGGET, 200, 72);
        recycleGearBlasting(writer, Items.IRON_LEGGINGS, Items.IRON_NUGGET, 200, 63);
        recycleGearBlasting(writer, Items.IRON_BOOTS, Items.IRON_NUGGET, 200, 36);
        recycleGearBlasting(writer, Durium.HELMET.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Durium.CHESTPLATE.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Durium.LEGGINGS.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Durium.BOOTS.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Quaron.HELMET.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Quaron.CHESTPLATE.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Quaron.LEGGINGS.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Quaron.BOOTS.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Items.GOLDEN_HELMET, Items.GOLD_NUGGET, 200, 45);
        recycleGearBlasting(writer, Items.GOLDEN_CHESTPLATE, Items.GOLD_NUGGET, 200, 72);
        recycleGearBlasting(writer, Items.GOLDEN_LEGGINGS, Items.GOLD_NUGGET, 200, 63);
        recycleGearBlasting(writer, Items.GOLDEN_BOOTS, Items.GOLD_NUGGET, 200, 36);
        recycleGearBlasting(writer, Items.CHAINMAIL_HELMET, Items.IRON_NUGGET, 200, 15);
        recycleGearBlasting(writer, Items.CHAINMAIL_CHESTPLATE, Items.IRON_NUGGET, 200, 24);
        recycleGearBlasting(writer, Items.CHAINMAIL_LEGGINGS, Items.IRON_NUGGET, 200, 21);
        recycleGearBlasting(writer, Items.CHAINMAIL_BOOTS, Items.IRON_NUGGET, 200, 12);
        recycleGearBlasting(writer, Items.DIAMOND_HELMET, Items.DIAMOND, 200, 5);
        recycleGearBlasting(writer, Items.DIAMOND_CHESTPLATE, Items.DIAMOND, 200, 8);
        recycleGearBlasting(writer, Items.DIAMOND_LEGGINGS, Items.DIAMOND, 200, 7);
        recycleGearBlasting(writer, Items.DIAMOND_BOOTS, Items.DIAMOND, 200, 4);
        recycleGearBlasting(writer, Items.NETHERITE_HELMET, Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, Items.NETHERITE_LEGGINGS, Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, Items.NETHERITE_BOOTS, Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, SoulSteel.HELMET.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, SoulSteel.CHESTPLATE.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, SoulSteel.LEGGINGS.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, SoulSteel.BOOTS.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Keego.HELMET.get(), Keego.GEM.get(), 200, 1);
        recycleGearBlasting(writer, Keego.CHESTPLATE.get(), Keego.GEM.get(), 200, 1);
        recycleGearBlasting(writer, Keego.LEGGINGS.get(), Keego.GEM.get(), 200, 1);
        recycleGearBlasting(writer, Keego.BOOTS.get(), Keego.GEM.get(), 200, 1);

        recycleGearBlasting(writer, CopperToolsExpansion.COPPER_PICKAXE.get(), Items.COPPER_INGOT, 200, 3);
        recycleGearBlasting(writer, CopperToolsExpansion.COPPER_AXE.get(), Items.COPPER_INGOT, 200, 3);
        recycleGearBlasting(writer, CopperToolsExpansion.COPPER_SHOVEL.get(), Items.COPPER_INGOT, 200, 1);
        recycleGearBlasting(writer, CopperToolsExpansion.COPPER_HOE.get(), Items.COPPER_INGOT, 200, 2);
        recycleGearBlasting(writer, CopperToolsExpansion.COPPER_SWORD.get(), Items.COPPER_INGOT, 200, 2);
        recycleGearBlasting(writer, Items.IRON_PICKAXE, Items.IRON_NUGGET, 200, 27);
        recycleGearBlasting(writer, Items.IRON_AXE, Items.IRON_NUGGET, 200, 27);
        recycleGearBlasting(writer, Items.IRON_SHOVEL, Items.IRON_NUGGET, 200, 9);
        recycleGearBlasting(writer, Items.IRON_HOE, Items.IRON_NUGGET, 200, 18);
        recycleGearBlasting(writer, Items.IRON_SWORD, Items.IRON_NUGGET, 200, 18);
        recycleGearBlasting(writer, Durium.PICKAXE.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Durium.AXE.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Durium.SHOVEL.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Durium.HOE.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Durium.SWORD.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Quaron.PICKAXE.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Quaron.AXE.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Quaron.SHOVEL.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Quaron.HOE.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Quaron.SWORD.get(), Quaron.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Items.GOLDEN_PICKAXE, Items.GOLD_NUGGET, 200, 27);
        recycleGearBlasting(writer, Items.GOLDEN_AXE, Items.GOLD_NUGGET, 200, 27);
        recycleGearBlasting(writer, Items.GOLDEN_SHOVEL, Items.GOLD_NUGGET, 200, 9);
        recycleGearBlasting(writer, Items.GOLDEN_HOE, Items.GOLD_NUGGET, 200, 18);
        recycleGearBlasting(writer, Items.GOLDEN_SWORD, Items.GOLD_NUGGET, 200, 18);
        recycleGearBlasting(writer, CopperToolsExpansion.COATED_PICKAXE.get(), Items.OBSIDIAN, 200, 3);
        recycleGearBlasting(writer, CopperToolsExpansion.COATED_AXE.get(), Items.OBSIDIAN, 200, 3);
        recycleGearBlasting(writer, CopperToolsExpansion.COATED_SHOVEL.get(), Items.OBSIDIAN, 200, 1);
        recycleGearBlasting(writer, CopperToolsExpansion.COATED_HOE.get(), Items.OBSIDIAN, 200, 2);
        recycleGearBlasting(writer, CopperToolsExpansion.COATED_SWORD.get(), Items.OBSIDIAN, 200, 2);
        recycleGearBlasting(writer, Items.DIAMOND_PICKAXE, Items.DIAMOND, 200, 3);
        recycleGearBlasting(writer, Items.DIAMOND_AXE, Items.DIAMOND, 200, 3);
        recycleGearBlasting(writer, Items.DIAMOND_SHOVEL, Items.DIAMOND, 200, 1);
        recycleGearBlasting(writer, Items.DIAMOND_HOE, Items.DIAMOND, 200, 2);
        recycleGearBlasting(writer, Items.DIAMOND_SWORD, Items.DIAMOND, 200, 2);
        recycleGearBlasting(writer, Items.NETHERITE_PICKAXE, Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, Items.NETHERITE_AXE, Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, Items.NETHERITE_SHOVEL, Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, Items.NETHERITE_HOE, Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, Items.NETHERITE_SWORD, Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, SoulSteel.PICKAXE.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, SoulSteel.AXE.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, SoulSteel.SHOVEL.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, SoulSteel.HOE.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, SoulSteel.SWORD.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Keego.PICKAXE.get(), Keego.GEM.get(), 200, 1);
        recycleGearBlasting(writer, Keego.AXE.get(), Keego.GEM.get(), 200, 1);
        recycleGearBlasting(writer, Keego.SHOVEL.get(), Keego.GEM.get(), 200, 1);
        recycleGearBlasting(writer, Keego.HOE.get(), Keego.GEM.get(), 200, 1);
        recycleGearBlasting(writer, Keego.SWORD.get(), Keego.GEM.get(), 200, 1);

        //Recycle Shields
        recycleGearBlasting(writer, CopperToolsExpansion.COPPER_SHIELD.get(), Items.COPPER_INGOT, 200, 4);
        recycleGearBlasting(writer, Items.SHIELD, Items.IRON_NUGGET, 200, 36);
        recycleGearBlasting(writer, Durium.SHIELD.get(), Durium.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, SPItems.GOLDEN_SHIELD.get(), Items.GOLD_NUGGET, 200, 36);
        recycleGearBlasting(writer, CopperToolsExpansion.COATED_SHIELD.get(), Items.OBSIDIAN, 200, 4);
        recycleGearBlasting(writer, Quaron.SHIELD.get(), Quaron.NUGGET.get(), 200, 36);
        recycleGearBlasting(writer, SPItems.DIAMOND_SHIELD.get(), Items.DIAMOND, 200, 1);
        recycleGearBlasting(writer, SPItems.NETHERITE_SHIELD.get(), Items.NETHERITE_INGOT, 200, 1);
        recycleGearBlasting(writer, SoulSteel.SHIELD.get(), SoulSteel.NUGGET.get(), 200, 9);
        recycleGearBlasting(writer, Keego.SHIELD.get(), Keego.GEM.get(), 200, 1);

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
        forgeRecipe(writer, Items.IRON_INGOT, 5, Forging.STONE_HAMMER.get(), Forging.IRON_HAMMER.get(), 10, 8f);
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Forging.IRON_HAMMER.get()), Ingredient.of(Solarium.SOLARIUM_BALL.get()), RecipeCategory.TOOLS, Forging.SOLARIUM_HAMMER.get())
                .unlocks("has_material", has(Solarium.SOLARIUM_BALL.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "solarium_hammer");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Forging.IRON_HAMMER.get()), Ingredient.of(Durium.INGOT.get()), RecipeCategory.TOOLS, Forging.DURIUM_HAMMER.get())
                .unlocks("has_material", has(Durium.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "durium_hammer");
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(Forging.WOODEN_HAMMER.get()), Ingredient.of(Quaron.INGOT.get()), RecipeCategory.TOOLS, Forging.QUARON_HAMMER.get())
                .unlocks("has_material", has(Quaron.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "quaron_hammer");
        forgeRecipe(writer, Items.GOLD_INGOT, 5, Forging.FLINT_HAMMER.get(), Forging.GOLDEN_HAMMER.get(), 4, 9f);
        forgeRecipe(writer, Items.OBSIDIAN, 5, Forging.COPPER_HAMMER.get(), Forging.COATED_COPPER_HAMMER.get(), 12, 9f);
        forgeRecipe(writer, Items.DIAMOND, 5, Forging.GOLDEN_HAMMER.get(), Forging.DIAMOND_HAMMER.get(), 16, 15f);
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(SoulSteel.UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(Forging.IRON_HAMMER.get()), Ingredient.of(SoulSteel.INGOT.get()), RecipeCategory.TOOLS, Forging.SOUL_STEEL_HAMMER.get())
                .unlocks("has_material", has(SoulSteel.INGOT.get()))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "soul_steel_hammer");
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Forging.DIAMOND_HAMMER.get()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, Forging.NETHERITE_HAMMER.get())
                .unlocks("has_material", has(Items.NETHERITE_INGOT))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "netherite_hammer");

        addPoorRichOreRecipes(writer, OreGeneration.POOR_RICH_COPPER_ORE, Items.COPPER_INGOT, 0.75f, 100);
        addPoorRichOreRecipes(writer, OreGeneration.POOR_RICH_IRON_ORE, Items.IRON_INGOT, 1f, 200);
        addPoorRichOreRecipes(writer, OreGeneration.POOR_RICH_GOLD_ORE, Items.GOLD_INGOT, 2f, 200);
    }

    private void addPoorRichOreRecipes(Consumer<FinishedRecipe> writer, OreGeneration.PoorRichOre poorRichOre, Item smeltOutput, float experience, int cookingTime) {
        for (Item item : poorRichOre.getAllItems()) {
            addBlastingRecipe(writer, item, smeltOutput, experience, cookingTime);
            addSoulBlastingRecipe(writer, item, smeltOutput, experience, cookingTime);
        }
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

    private void recycleGearBlasting(Consumer<FinishedRecipe> writer, Item itemToRecycle, Item output, int baseCookingTime, int amountAtMaxDurability) {
        MultiItemSmeltingRecipeBuilder.blasting(
                        NonNullList.of(Ingredient.EMPTY, Ingredient.of(itemToRecycle)),
                        RecipeCategory.COMBAT,
                        output,
                        baseCookingTime)
                .recycle(amountAtMaxDurability, 0.6f)
                .group("recycle_" + ForgeRegistries.ITEMS.getKey(output).getPath())
                .unlockedBy("has_armor", has(itemToRecycle))
                .save(writer, IguanaTweaksExpanded.RESOURCE_PREFIX + "blast_furnace/recycle_" + ForgeRegistries.ITEMS.getKey(itemToRecycle).getPath());

        MultiItemSmeltingRecipeBuilder.soulBlasting(
                        NonNullList.of(Ingredient.EMPTY, Ingredient.of(itemToRecycle)),
                        RecipeCategory.COMBAT,
                        output,
                        baseCookingTime / 2)
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

    private void forgeRecipe(Consumer<FinishedRecipe> writer, Item material, int amount, Item gear, Item result, int smashesRequired, float experience) {
        ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(material), amount, Ingredient.of(gear), result, smashesRequired)
                .awardExperience(experience)
                .unlockedBy("has_material", has(material))
                .save(writer);
    }

    private void forgeRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> materialTag, int amount, Item gear, Item result, int smashesRequired, float experience) {
        ForgeRecipeBuilder.forging(RecipeCategory.TOOLS, Ingredient.of(materialTag), amount, Ingredient.of(gear), result, smashesRequired)
                .awardExperience(experience)
                .unlockedBy("has_material", has(materialTag))
                .save(writer);
    }
}
