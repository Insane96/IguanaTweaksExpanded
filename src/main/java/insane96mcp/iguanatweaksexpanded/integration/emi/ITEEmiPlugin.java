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
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.EnchantingFeature;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.ForgeRecipe;
import insane96mcp.iguanatweaksexpanded.module.mining.forging.Forging;
import insane96mcp.iguanatweaksexpanded.module.world.coalfire.CoalCharcoal;
import insane96mcp.insanelib.InsaneLib;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@EmiEntrypoint
public class ITEEmiPlugin implements EmiPlugin {
	public static final ResourceLocation FORGE_CATEGORY_ID = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "forge");
	public static final EmiStack FORGE_WORKSTATION = EmiStack.of(Forging.FORGE.item().get());
	public static final EmiRecipeCategory FORGE_RECIPE_CATEGORY = new EmiRecipeCategory(FORGE_CATEGORY_ID, FORGE_WORKSTATION);

	@Override
	public void register(EmiRegistry registry) {
		registry.addCategory(FORGE_RECIPE_CATEGORY);

		registry.addWorkstation(FORGE_RECIPE_CATEGORY, FORGE_WORKSTATION);
		RecipeManager manager = registry.getRecipeManager();
		for (ForgeRecipe forgeRecipe : manager.getAllRecipesFor(Forging.FORGE_RECIPE_TYPE.get())) {
			registry.addRecipe(new EmiForgeRecipe(forgeRecipe));
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
		registry.removeRecipes(emiRecipe -> emiRecipe.getCategory() == VanillaEmiRecipeCategories.ANVIL_REPAIRING);
		if (Feature.isEnabled(EnchantingFeature.class)) {
			registry.removeRecipes(emiRecipe -> emiRecipe.getCategory() == VanillaEmiRecipeCategories.GRINDING);
			String key = EnchantingFeature.betterGrindstoneXp ? "emi.info.iguanatweaksexpanded.grindstone" : "emi.info.grindstone";
			registry.addRecipe(new EmiInfoRecipe(
					List.of(emiIngredientOf(Items.GRINDSTONE)),
					List.of(Component.translatable(key, InsaneLib.ONE_DECIMAL_FORMATTER.format(EnchantingFeature.getGrindstonePercentageXpGiven() * 100f))),
					new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "info_grindstone")));
		}
		registry.removeRecipes(emiRecipe -> emiRecipe.getId() != null && emiRecipe.getId().getPath().startsWith("/crafting/repairing"));
	}

	public static EmiIngredient emiIngredientOf(Item item) {
		return EmiIngredient.of(Ingredient.of(item));
	}
}
