package insane96mcp.iguanatweaksexpanded.integration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import insane96mcp.iguanatweaksexpanded.InsaneSE;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.crafting.FletchingRecipe;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.items.altimeter.Altimeter;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.ForgeRecipe;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.AbstractMultiItemSmeltingRecipe;
import insane96mcp.insanelib.InsaneLib;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@EmiEntrypoint
public class ISEEmiPlugin implements EmiPlugin {
	public static final ResourceLocation FORGE_CATEGORY_ID = new ResourceLocation(InsaneSE.MOD_ID, "forging");
	public static final EmiStack FORGE_WORKSTATION = EmiStack.of(Forging.FORGE.item().get());
	public static final EmiRecipeCategory FORGE_RECIPE_CATEGORY = new EmiRecipeCategory(FORGE_CATEGORY_ID, FORGE_WORKSTATION);

	public static final ResourceLocation FLETCHING_CATEGORY_ID = InsaneSE.location("fletching");
	public static final EmiStack FLETCHING_WORKSTATION = EmiStack.of(Fletching.FLETCHING_TABLE.item().get());
	public static final EmiRecipeCategory FLETCHING_RECIPE_CATEGORY = new EmiRecipeCategory(FLETCHING_CATEGORY_ID, FLETCHING_WORKSTATION);

	public static final ResourceLocation BLAST_FURNACE_CATEGORY_ID = InsaneSE.location("blast_furnace");
	public static final EmiStack BLAST_FURNACE_WORKSTATION = EmiStack.of(MultiBlockFurnaces.BLAST_FURNACE.item().get());
	public static final EmiRecipeCategory BLAST_FURNACE_CATEGORY = new EmiRecipeCategory(BLAST_FURNACE_CATEGORY_ID, BLAST_FURNACE_WORKSTATION);

	public static final ResourceLocation SOUL_BLAST_FURNACE_CATEGORY_ID = InsaneSE.location("soul_blast_furnace");
	public static final EmiStack SOUL_BLAST_FURNACE_WORKSTATION = EmiStack.of(MultiBlockFurnaces.SOUL_BLAST_FURNACE.item().get());
	public static final EmiRecipeCategory SOUL_BLAST_FURNACE_CATEGORY = new EmiRecipeCategory(SOUL_BLAST_FURNACE_CATEGORY_ID, SOUL_BLAST_FURNACE_WORKSTATION);

	@Override
	public void register(EmiRegistry registry) {
		RecipeManager manager = registry.getRecipeManager();

		if (Feature.isEnabled(Forging.class)) {
			registry.addCategory(FORGE_RECIPE_CATEGORY);
			registry.addWorkstation(FORGE_RECIPE_CATEGORY, FORGE_WORKSTATION);
			for (ForgeRecipe forgeRecipe : manager.getAllRecipesFor(Forging.FORGE_RECIPE_TYPE.get())) {
				registry.addRecipe(new EmiForgeRecipe(forgeRecipe));
			}
		}

		if (Feature.isEnabled(MultiBlockFurnaces.class)) {
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

			registry.removeRecipes(emiRecipe -> emiRecipe.getCategory() == VanillaEmiRecipeCategories.BLASTING);
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.BLAST_FURNACE));
			if (MultiBlockFurnaces.hideBlastingCategoryInEMI) {
				registry.removeRecipes(emiRecipe -> emiRecipe.getCategory() == VanillaEmiRecipeCategories.BLASTING);
			}
		}

		if (Feature.isEnabled(Fletching.class)) {
			registry.addCategory(FLETCHING_RECIPE_CATEGORY);
			registry.addWorkstation(FLETCHING_RECIPE_CATEGORY, FLETCHING_WORKSTATION);
			for (FletchingRecipe fletchingRecipe : manager.getAllRecipesFor(Fletching.FLETCHING_RECIPE_TYPE.get())) {
				registry.addRecipe(new EmiFletchingRecipe(fletchingRecipe));
			}
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
					InsaneSE.location("info_grindstone")));

			if (EnchantingFeature.enablePurifyItems) {
				CompoundTag tag = new CompoundTag();
				ListTag lore = new ListTag();
				lore.add(StringTag.valueOf(""));
				lore.add(StringTag.valueOf(Component.Serializer.toJson(EnchantingFeature.PURIFIED_COMPONENT)));
				CompoundTag display = new CompoundTag();
				display.put("Lore", lore);
				tag.put("display", display);
				ItemStack output = new ItemStack(Items.DIAMOND_PICKAXE, 1);
				output.setTag(tag);
				registry.addRecipe(new EmiAnvilRecipe(InsaneSE.location("enchant_purify_item"), Items.DIAMOND_PICKAXE, Items.EXPERIENCE_BOTTLE, output));
				registry.addRecipe(createSimpleInfo(Items.EXPERIENCE_BOTTLE, Component.translatable("emi.info.iguanatweaksexpanded.experience_bottle"), "purify_item"));
			}

			if (EnchantingFeature.enchantingTableRequiresLearning) {
				ItemStack stack = new ItemStack(EnchantingFeature.ENCHANTING_TABLE.item().get());
				CompoundTag nbt = new CompoundTag();
				CompoundTag blockEntityTag = new CompoundTag();
				ListTag listTag = new ListTag();
				for (var enchantment : ForgeRegistries.ENCHANTMENTS.getEntries()) {
					if (!enchantment.getValue().isDiscoverable())
						continue;
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
				loreTag.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("iguanatweaksexpanded.emi.every_enchantment"))));
				displayTag.put("Lore", loreTag);
				stack.getOrCreateTag().put("display", displayTag);
				registry.addEmiStackAfter(EmiStack.of(stack), emiStack -> emiStack.getItemStack().is(EnchantingFeature.ENCHANTING_TABLE.item().get()));
			}

			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.ENCHANTING_TABLE));
		}
		if (Feature.isEnabled(Fletching.class)) {
			registry.removeEmiStacks(emiStack -> emiStack.getItemStack().is(Items.FLETCHING_TABLE));
		}
		if (Feature.isEnabled(Altimeter.class)) {
			registry.addRecipe(createSimpleInfo(Altimeter.ITEM.get(), Component.translatable("emi.info.iguanatweaksexpanded.altimeter"), "info_altimeter"));
		}
		if (Feature.isEnabled(Keego.class)) {
			registry.addRecipe(createSimpleInfo(Keego.KEEGO_TOOL_EQUIPMENT, Component.translatable("emi.info.iguanatweaksexpanded.keego"), "info_keego_mining"));
			registry.addRecipe(createSimpleInfo(Keego.KEEGO_HAND_EQUIPMENT, Component.translatable("emi.info.iguanatweaksexpanded.keego"), "info_keego_attacking"));
			registry.addRecipe(createSimpleInfo(Keego.KEEGO_ARMOR_EQUIPMENT, Component.translatable("emi.info.iguanatweaksexpanded.keego"), "info_keego_moving"));
		}
	}

	public EmiInfoRecipe createSimpleInfo(Item item, Component component, String id) {
		return new EmiInfoRecipe(List.of(emiIngredientOf(item)), List.of(component), InsaneSE.location(id));
	}

	public EmiInfoRecipe createSimpleInfo(TagKey<Item> itemTag, Component component, String id) {
		return new EmiInfoRecipe(List.of(emiIngredientOf(itemTag)), List.of(component), InsaneSE.location(id));
	}

	public static EmiIngredient emiIngredientOf(Item item) {
		return EmiIngredient.of(Ingredient.of(item));
	}

	public static EmiIngredient emiIngredientOf(TagKey<Item> itemTag) {
		return EmiIngredient.of(Ingredient.of(itemTag));
	}
}
