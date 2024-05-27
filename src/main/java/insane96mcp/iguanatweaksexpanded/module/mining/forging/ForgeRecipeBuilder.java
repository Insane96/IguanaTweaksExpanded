package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import com.google.gson.JsonObject;
import insane96mcp.iguanatweaksexpanded.setup.client.ITEBookCategory;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ForgeRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ITEBookCategory bookCategory;
    final Ingredient ingredient;
    final int ingredientAmount;
    final Ingredient gear;
    private final ItemStack result;
    protected final int smashesRequired;
    protected float experience;
    private final RecipeSerializer<? extends ForgeRecipe> serializer;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public ForgeRecipeBuilder(RecipeCategory pCategory, ITEBookCategory pBookCategory, Ingredient ingredient, int ingredientAmount, Ingredient gear, ItemStack pResult, int smashesRequired, RecipeSerializer<? extends ForgeRecipe> pSerializer) {
        this.category = pCategory;
        this.bookCategory = pBookCategory;
        this.ingredient = ingredient;
        this.ingredientAmount = ingredientAmount;
        this.gear = gear;
        this.result = pResult;
        this.smashesRequired = smashesRequired;
        this.serializer = pSerializer;
    }

    public static ForgeRecipeBuilder forging(RecipeCategory pCategory, Ingredient ingredient, int ingredientAmount, Ingredient gear, ItemStack pResult, int smashesRequired) {
        return new ForgeRecipeBuilder(pCategory, ITEBookCategory.FORGE_MISC, ingredient, ingredientAmount, gear, pResult, smashesRequired, Forging.FORGE_RECIPE_SERIALIZER.get());
    }

    public ForgeRecipeBuilder awardExperience(float experience) {
        this.experience = experience;
        return this;
    }

    @Override
    public ForgeRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new ForgeRecipeBuilder.Result(pRecipeId, this.bookCategory, this.ingredient, this.ingredientAmount, this.gear, this.result, this.smashesRequired, this.experience, this.advancement, pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.serializer));
    }

    /**
     * Makes sure that this obtainable.
     */
    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ITEBookCategory category;
        private final Ingredient ingredient;
        private final int ingredientAmount;
        private final Ingredient gear;
        private final ItemStack result;
        private final int smashesRequired;
        private final float experience;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends ForgeRecipe> serializer;

        public Result(ResourceLocation id, ITEBookCategory bookCategory, Ingredient ingredient, int ingredientAmount, Ingredient gear, ItemStack pResult, int smashesRequired, float experience, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends ForgeRecipe> pSerializer) {
            this.id = id;
            this.category = bookCategory;
            this.ingredient = ingredient;
            this.ingredientAmount = ingredientAmount;
            this.gear = gear;
            this.result = pResult;
            this.smashesRequired = smashesRequired;
            this.experience = experience;
            this.serializer = pSerializer;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        public void serializeRecipeData(JsonObject jObject) {
            jObject.addProperty("category", this.category.getSerializedName());
            jObject.add("ingredient", this.ingredient.toJson());
            jObject.addProperty("amount", this.ingredientAmount);
            jObject.add("gear", this.gear.toJson());
            if (this.result.getTag() != null)
            {
                JsonObject resultObj = new JsonObject();
                resultObj.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result.getItem()).toString());
                resultObj.addProperty("nbt", this.result.getTag().toString());
                jObject.add("result", resultObj);
            }
            else {
                jObject.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result.getItem()).toString());
            }
            jObject.addProperty("smashes_required", this.smashesRequired);
            if (this.experience > 0)
                jObject.addProperty("experience", this.experience);
        }

        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @javax.annotation.Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
