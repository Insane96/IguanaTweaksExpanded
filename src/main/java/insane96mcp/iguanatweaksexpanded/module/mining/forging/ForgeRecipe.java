package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import com.google.gson.JsonObject;
import insane96mcp.iguanatweaksexpanded.setup.client.ITEBookCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeRecipe implements Recipe<Container> {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    private final ITEBookCategory category;
    final Ingredient ingredient;
    final int ingredientAmount;
    final Ingredient gear;
    private final ItemStack result;
    protected final int smashesRequired;
    protected final float experience;

    public ForgeRecipe(ResourceLocation pId, ITEBookCategory pCategory, Ingredient ingredient, int ingredientAmount, Ingredient gear, ItemStack pResult, int smashesRequired, float experience) {
        this.type = Forging.FORGE_RECIPE_TYPE.get();
        this.category = pCategory;
        this.id = pId;
        this.ingredient = ingredient;
        this.ingredientAmount = ingredientAmount;
        this.gear = gear;
        this.result = pResult;
        this.smashesRequired = smashesRequired;
        this.experience = experience;
    }

    @Override
    public boolean matches(Container container, Level pLevel) {
        return this.ingredient.test(container.getItem(ForgeMenu.INGREDIENT_SLOT))
                && container.getItem(ForgeMenu.INGREDIENT_SLOT).getCount() >= this.ingredientAmount
                && this.gear.test(container.getItem(ForgeMenu.GEAR_SLOT));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return this.result;
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        nonnulllist.add(this.gear);
        return nonnulllist;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public int getIngredientAmount() {
        return this.ingredientAmount;
    }

    public Ingredient getGear() {
        return this.gear;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Forging.FORGE_RECIPE_SERIALIZER.get();
    }

    /**
     * Gets the times required to smash the forge with the hammer to craft the item
     */
    public int getSmashesRequired() {
        return this.smashesRequired;
    }

    public float getExperience() {
        return this.experience;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public RecipeType<?> getType() {
        return this.type;
    }

    public ITEBookCategory category() {
        return this.category;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(Forging.FORGE.item().get());
    }

    public static class ForgeRecipeSerializer implements RecipeSerializer<ForgeRecipe> {
        private final CookieBaker<ForgeRecipe> factory;

        public ForgeRecipeSerializer() {
            this.factory = ForgeRecipe::new;
        }

        public ForgeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            ITEBookCategory category = ITEBookCategory.CODEC.byName(GsonHelper.getAsString(pJson, "category", null), ITEBookCategory.FORGE_MISC);
            Ingredient ingredient = Ingredient.fromJson(pJson.getAsJsonObject("ingredient"));
            //Forge: Check if primitive string to keep vanilla or an object which can contain a count field.
            if (!pJson.has("result"))
                throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
            int ingredientAmount = GsonHelper.getAsInt(pJson, "amount", 1);
            Ingredient gear = Ingredient.fromJson(pJson.getAsJsonObject("gear"));
            ItemStack result;
            if (pJson.get("result").isJsonObject())
                result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pJson, "result"), true, true);
            else {
                String s1 = GsonHelper.getAsString(pJson, "result");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                result = new ItemStack(ForgeRegistries.ITEMS.getValue(resourcelocation));
            }
            int smashesRequired = GsonHelper.getAsInt(pJson, "smashes_required");
            float experience = GsonHelper.getAsFloat(pJson, "experience", 0);
            return this.factory.create(pRecipeId, category, ingredient, ingredientAmount, gear, result, smashesRequired, experience);
        }

        public ForgeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ITEBookCategory category = pBuffer.readEnum(ITEBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int ingredientAmount = pBuffer.readVarInt();
            Ingredient gear = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();
            int smashesRequired = pBuffer.readVarInt();
            float experience = pBuffer.readFloat();
            return this.factory.create(pRecipeId, category, ingredient, ingredientAmount, gear, result, smashesRequired, experience);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, ForgeRecipe pRecipe) {
            pBuffer.writeEnum(pRecipe.category());
            pRecipe.getIngredient().toNetwork(pBuffer);
            pBuffer.writeVarInt(pRecipe.getIngredientAmount());
            pRecipe.getGear().toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.getResultItem(RegistryAccess.EMPTY));
            pBuffer.writeVarInt(pRecipe.getSmashesRequired());
            pBuffer.writeFloat(pRecipe.getExperience());
        }

        public interface CookieBaker<T extends ForgeRecipe> {
            T create(ResourceLocation pId, ITEBookCategory pCategory, Ingredient ingredient, int ingredientAmount, Ingredient gear, ItemStack pResult, int smashesRequired, float experience);
        }
    }
}
