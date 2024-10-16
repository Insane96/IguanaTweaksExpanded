package insane96mcp.iguanatweaksexpanded.integration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.crafting.FletchingRecipe;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.items.altimeter.Altimeter;
import insane96mcp.iguanatweaksexpanded.module.items.flintexpansion.FlintExpansion;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.ForgeRecipe;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.AbstractMultiItemSmeltingRecipe;
import insane96mcp.iguanatweaksexpanded.module.mining.repairkit.RepairKitItem;
import insane96mcp.iguanatweaksexpanded.module.mining.repairkit.RepairKits;
import insane96mcp.iguanatweaksexpanded.module.movement.minecarts.Minecarts;
import insane96mcp.iguanatweaksexpanded.module.world.coalfire.CoalCharcoal;
import insane96mcp.insanelib.InsaneLib;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.shieldsplus.setup.SPItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@EmiEntrypoint
public class ITEEmiPlugin implements EmiPlugin {
	public static final ResourceLocation FORGE_CATEGORY_ID = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "forging");
	public static final EmiStack FORGE_WORKSTATION = EmiStack.of(Forging.FORGE.item().get());
	public static final EmiRecipeCategory FORGE_RECIPE_CATEGORY = new EmiRecipeCategory(FORGE_CATEGORY_ID, FORGE_WORKSTATION);

	public static final ResourceLocation FLETCHING_CATEGORY_ID = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "fletching");
	public static final EmiStack FLETCHING_WORKSTATION = EmiStack.of(Fletching.FLETCHING_TABLE.item().get());
	public static final EmiRecipeCategory FLETCHING_RECIPE_CATEGORY = new EmiRecipeCategory(FLETCHING_CATEGORY_ID, FLETCHING_WORKSTATION);

	public static final ResourceLocation BLAST_FURNACE_CATEGORY_ID = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "blast_furnace");
	public static final EmiStack BLAST_FURNACE_WORKSTATION = EmiStack.of(MultiBlockFurnaces.BLAST_FURNACE.item().get());
	public static final EmiRecipeCategory BLAST_FURNACE_CATEGORY = new EmiRecipeCategory(BLAST_FURNACE_CATEGORY_ID, BLAST_FURNACE_WORKSTATION);

	public static final ResourceLocation SOUL_BLAST_FURNACE_CATEGORY_ID = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "soul_blast_furnace");
	public static final EmiStack SOUL_BLAST_FURNACE_WORKSTATION = EmiStack.of(MultiBlockFurnaces.SOUL_BLAST_FURNACE.item().get());
	public static final EmiRecipeCategory SOUL_BLAST_FURNACE_CATEGORY = new EmiRecipeCategory(SOUL_BLAST_FURNACE_CATEGORY_ID, SOUL_BLAST_FURNACE_WORKSTATION);

	@Override
	public void register(EmiRegistry registry) {
		RecipeManager manager = registry.getRecipeManager();

		registry.addCategory(FORGE_RECIPE_CATEGORY);
		registry.addWorkstation(FORGE_RECIPE_CATEGORY, FORGE_WORKSTATION);
		for (ForgeRecipe forgeRecipe : manager.getAllRecipesFor(Forging.FORGE_RECIPE_TYPE.get())) {
			registry.addRecipe(new EmiForgeRecipe(forgeRecipe));
			if (forgeRecipe.getResult().getItem() instanceof RepairKitItem) {
				registry.addEmiStack(EmiStack.of(forgeRecipe.getResult()));
			}
		}

		registry.addCategory(BLAST_FURNACE_CATEGORY);
		registry.addWorkstation(BLAST_FURNACE_CATEGORY, BLAST_FURNACE_WORKSTATION);
		for (AbstractMultiItemSmeltingRecipe multiItemSmeltingRecipe : manager.getAllRecipesFor(MultiBlockFurnaces.BLASTING_RECIPE_TYPE.get())) {
			registry.addRecipe(new EmiBlastFurnaceRecipe(multiItemSmeltingRecipe));
		}

		registry.addCategory(SOUL_BLAST_FURNACE_CATEGORY);
		registry.addWorkstation(SOUL_BLAST_FURNACE_CATEGORY, SOUL_BLAST_FURNACE_WORKSTATION);
		for (AbstractMultiItemSmeltingRecipe multiItemSmeltingRecipe : manager.getAllRecipesFor(MultiBlockFurnaces.SOUL_BLASTING_RECIPE_TYPE.get())) {
			registry.addRecipe(new EmiSoulBlastFurnaceRecipe(multiItemSmeltingRecipe));
		}

		registry.addCategory(FLETCHING_RECIPE_CATEGORY);
		registry.addWorkstation(FLETCHING_RECIPE_CATEGORY, FLETCHING_WORKSTATION);
		for (FletchingRecipe fletchingRecipe : manager.getAllRecipesFor(Fletching.FLETCHING_RECIPE_TYPE.get())) {
			registry.addRecipe(new EmiFletchingRecipe(fletchingRecipe));
		}

		if (Feature.isEnabled(CoalCharcoal.class) && CoalCharcoal.charcoalFromBurntLogsChance > 0) {
			Ingredient fire = Ingredient.of(CoalCharcoal.FIRESTARTER.get(), Items.FLINT_AND_STEEL);
			registry.addRecipe(EmiWorldInteractionRecipe.builder()
					.id(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "charcoal_from_burning_logs"))
					.leftInput(EmiIngredient.of(ItemTags.LOGS_THAT_BURN))
					.rightInput(EmiIngredient.of(fire), false, slotWidget -> slotWidget.appendTooltip(Component.literal("Basically fire").withStyle(ChatFormatting.GREEN)))
					.output(EmiStack.of(Items.CHARCOAL)).build());
			registry.addRecipe(EmiWorldInteractionRecipe.builder()
					.id(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "charcoal_layer_from_burning_logs"))
					.leftInput(EmiIngredient.of(ItemTags.LOGS_THAT_BURN))
					.rightInput(EmiIngredient.of(fire), false, slotWidget -> slotWidget.appendTooltip(Component.literal("Basically fire").withStyle(ChatFormatting.GREEN)))
					.output(EmiStack.of(CoalCharcoal.CHARCOAL_LAYER.item().get())).build());
		}
		//registry.removeRecipes(emiRecipe -> emiRecipe.getCategory() == VanillaEmiRecipeCategories.ANVIL_REPAIRING);
		if (Feature.isEnabled(EnchantingFeature.class)) {
			registry.removeRecipes(emiRecipe -> emiRecipe.getCategory() == VanillaEmiRecipeCategories.GRINDING);
			String key = EnchantingFeature.grindstoneBetterXp ? "emi.info.iguanatweaksexpanded.grindstone" : "emi.info.grindstone";
			String key2 = "";
			if (EnchantingFeature.grindstoneTreasureEnchantmentExtraction) {
				if (EnchantingFeature.grindstoneEnchantmentExtraction) {
					key2 = "emi.info.iguanatweaksexpanded.grindstone2";
				}
				else {
					key2 = "emi.info.iguanatweaksexpanded.grindstone3";
				}
			}
			registry.addRecipe(new EmiInfoRecipe(
					List.of(emiIngredientOf(Items.GRINDSTONE)),
					List.of(Component.translatable(key, InsaneLib.ONE_DECIMAL_FORMATTER.format(EnchantingFeature.getGrindstonePercentageXpGiven() * 100f)),
							Component.translatable(key2, InsaneLib.ONE_DECIMAL_FORMATTER.format(EnchantingFeature.getGrindstonePercentageXpGiven() * 100f))),
					new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "info_grindstone")));

			registry.addRecipe(createSimpleInfo(EnchantingFeature.CLEANSED_LAPIS.get(), "info_cleansed_lapis", Component.translatable("emi.info.iguanatweaksexpanded.cleansed_lapis")));
			registry.addRecipe(createSimpleInfo(EnchantingFeature.ENCHANTED_CLEANSED_LAPIS.get(), "info_enchanted_cleansed_lapis", Component.translatable("emi.info.iguanatweaksexpanded.enchanted_cleansed_lapis")));
			CompoundTag tag = new CompoundTag();
			ListTag lore = new ListTag();
			lore.add(StringTag.valueOf(""));
			lore.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("iguanatweaksexpanded.infused_item").withStyle(ChatFormatting.DARK_PURPLE))));
			CompoundTag display = new CompoundTag();
			display.put("Lore", lore);
			tag.put("display", display);

			ItemStack output = new ItemStack(Items.DIAMOND_SWORD, 1);
			output.setTag(tag);
			registry.addRecipe(new EmiAnvilRecipe(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "cleansed_lapis_use"), Items.DIAMOND_SWORD, EnchantingFeature.CLEANSED_LAPIS.get(), output));

			registry.addRecipe(new EmiAnvilRecipe(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "enchanted_cleansed_lapis_use"), EnchantingFeature.CLEANSED_LAPIS.get(), Items.EXPERIENCE_BOTTLE, new ItemStack(EnchantingFeature.ENCHANTED_CLEANSED_LAPIS.get())));
			tag = new CompoundTag();
			lore = new ListTag();
			lore.add(StringTag.valueOf(""));
			lore.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("iguanatweaksexpanded.empowered_item").withStyle(ChatFormatting.DARK_PURPLE))));
			display = new CompoundTag();
			display.put("Lore", lore);
			tag.put("display", display);
			output = new ItemStack(Items.DIAMOND_PICKAXE, 1);
			output.setTag(tag);
			registry.addRecipe(new EmiAnvilRecipe(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "enchanted_cleansed_lapis_use"), Items.DIAMOND_PICKAXE, EnchantingFeature.ENCHANTED_CLEANSED_LAPIS.get(), output));

			if (EnchantingFeature.enchantingTableRequiresLearning) {
				ItemStack stack = new ItemStack(EnchantingFeature.ENCHANTING_TABLE.item().get());
				CompoundTag nbt = new CompoundTag();
				CompoundTag blockEntityTag = new CompoundTag();
				ListTag listTag = new ListTag();
				for (var enchantment : ForgeRegistries.ENCHANTMENTS.getEntries()) {
					/*if (enchantment.getValue().isCurse())
						continue;*/
					CompoundTag compoundTag = new CompoundTag();
					String id = enchantment.getKey().location().toString();
					int lvl = enchantment.getValue().getMaxLevel();
					compoundTag.putString("id", id);
					compoundTag.putInt("lvl", lvl);
					listTag.add(compoundTag);
				}
				blockEntityTag.put("learned_enchantments", listTag);
				stack.getOrCreateTag().put("BlockEntityTag", blockEntityTag);

				CompoundTag displayTag = new CompoundTag();
				ListTag loreTag = new ListTag();
				loreTag.add(StringTag.valueOf(""));
				loreTag.add(StringTag.valueOf(Component.Serializer.toJson(Component.literal("With every enchantment learned"))));
				displayTag.put("Lore", loreTag);
				stack.getOrCreateTag().put("display", displayTag);
				registry.addEmiStackAfter(EmiStack.of(stack), emiStack -> emiStack.getItemStack().is(EnchantingFeature.ENCHANTING_TABLE.item().get()));
			}

			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.ENCHANTING_TABLE));
		}
		if (Feature.isEnabled(MultiBlockFurnaces.class)) {
			registry.removeRecipes(emiRecipe -> emiRecipe.getCategory() == VanillaEmiRecipeCategories.BLASTING);
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.BLAST_FURNACE));
		}
		if (Feature.isEnabled(Fletching.class)) {
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.FLETCHING_TABLE));
		}
		if (Feature.isEnabled(Minecarts.class)) {
			registry.addRecipe(createSimpleInfo(Minecarts.COPPER_POWERED_RAIL.item().get(), "info_copper_powered_rail", Component.translatable("emi.info.iguanatweaksexpanded.copper_powered_rail")));
			registry.addRecipe(createSimpleInfo(Minecarts.GOLDEN_POWERED_RAIL.item().get(), "info_golden_powered_rail", Component.translatable("emi.info.iguanatweaksexpanded.golden_powered_rail")));
			registry.addRecipe(createSimpleInfo(Minecarts.NETHER_INFUSED_POWERED_RAIL.item().get(), "info_nether_infused_powered_rail", Component.translatable("emi.info.iguanatweaksexpanded.nether_infused_powered_rail")));
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.POWERED_RAIL));
		}
		if (Feature.isEnabled(MultiBlockFurnaces.class) && MultiBlockFurnaces.hideBlastingCategoryInEMI) {
			registry.removeRecipes(emiRecipe -> emiRecipe.getCategory() == VanillaEmiRecipeCategories.BLASTING);
		}
		if (Feature.isEnabled(FlintExpansion.class) && FlintExpansion.disableStoneTools) {
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.STONE_PICKAXE));
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.STONE_AXE));
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.STONE_SHOVEL));
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.STONE_SWORD));
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.STONE_HOE));
			if (ModList.get().isLoaded("shieldsplus"))
				registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(SPItems.STONE_SHIELD.get()));
		}
		registry.addRecipe(createSimpleInfo(Altimeter.ITEM.get(), "info_altimeter", Component.translatable("emi.info.iguanatweaksexpanded.altimeter")));
		registry.addRecipe(createSimpleInfo(Keego.KEEGO_TOOL_EQUIPMENT, "info_keego_mining", Component.translatable("emi.info.iguanatweaksexpanded.keego_mining")));
		registry.addRecipe(createSimpleInfo(Keego.KEEGO_HAND_EQUIPMENT, "info_keego_attacking", Component.translatable("emi.info.iguanatweaksexpanded.keego_attacking")));
		registry.addRecipe(createSimpleInfo(Keego.KEEGO_ARMOR_EQUIPMENT, "info_keego_moving", Component.translatable("emi.info.iguanatweaksexpanded.keego_moving")));
		registry.addRecipe(createSimpleInfo(RepairKits.REPAIR_KIT.get(), "info_repair_kit", Component.translatable("emi.info.iguanatweaksexpanded.repair_kit")));
	}

	public EmiInfoRecipe createSimpleInfo(Item item, String id, Component component) {
		return new EmiInfoRecipe(List.of(emiIngredientOf(item)), List.of(component), new ResourceLocation(IguanaTweaksExpanded.MOD_ID, id));
	}

	public EmiInfoRecipe createSimpleInfo(TagKey<Item> itemTag, String id, Component component) {
		return new EmiInfoRecipe(List.of(emiIngredientOf(itemTag)), List.of(component), new ResourceLocation(IguanaTweaksExpanded.MOD_ID, id));
	}

	public static EmiIngredient emiIngredientOf(Item item) {
		return EmiIngredient.of(Ingredient.of(item));
	}

	public static EmiIngredient emiIngredientOf(TagKey<Item> itemTag) {
		return EmiIngredient.of(Ingredient.of(itemTag));
	}
}
