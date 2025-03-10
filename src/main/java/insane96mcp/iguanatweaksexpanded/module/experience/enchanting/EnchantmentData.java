package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import insane96mcp.insanelib.data.IdTagMatcher;
import net.minecraft.util.GsonHelper;

import java.util.ArrayList;

@JsonAdapter(EnchantmentData.Serializer.class)
public class EnchantmentData {
    public IdTagMatcher enchantment;
    public int costPerLevel;
    public int[] cost;

    public EnchantmentData(IdTagMatcher enchantment) {
        this.enchantment = enchantment;
    }

    public EnchantmentData(String enchantment) {
        this(IdTagMatcher.newId(enchantment));
    }

    public EnchantmentData costPerLevel(int costPerLevel) {
        this.costPerLevel = costPerLevel;
        return this;
    }

    public EnchantmentData cost(int... cost) {
        this.cost = cost;
        return this;
    }

    public int getCost(int lvl) {
        if (this.costPerLevel != 0)
            return this.costPerLevel * lvl;
        else if (this.cost.length >= lvl)
            return this.cost[lvl - 1];
        return 0;
    }

    public static final java.lang.reflect.Type LIST_TYPE = new TypeToken<ArrayList<EnchantmentData>>(){}.getType();
    public static class Serializer implements JsonDeserializer<EnchantmentData>, JsonSerializer<EnchantmentData> {
        @Override
        public EnchantmentData deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jObject = json.getAsJsonObject();
            IdTagMatcher idTagMatcher = context.deserialize(jObject.get("id"), IdTagMatcher.class);
            EnchantmentData enchantmentData = new EnchantmentData(idTagMatcher);
            if (!jObject.has("cost_per_level") && !jObject.has("costs"))
                throw new JsonParseException("Missing cost_per_level or costs field");

            if (jObject.has("cost_per_level"))
                enchantmentData.costPerLevel(GsonHelper.getAsInt(jObject, "cost_per_level"));

            if (jObject.has("costs")) {
                if (jObject.get("costs").isJsonPrimitive())
                    enchantmentData.cost(GsonHelper.getAsInt(jObject, "costs"));
                else {
                    JsonArray jsonArray = jObject.get("costs").getAsJsonArray();
                    int[] cost = new int[jsonArray.size()];
                    for (int i = 0; i < jsonArray.size(); i++) {
                        cost[i] = jsonArray.get(i).getAsInt();
                    }
                    enchantmentData.cost(cost);
                }
            }
            return enchantmentData;
        }

        @Override
        public JsonElement serialize(EnchantmentData src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jObject = new JsonObject();
            jObject.add("id", context.serialize(src.enchantment));
            if (src.cost != null) {
                if (src.cost.length > 1)
                    jObject.add("costs", context.serialize(src.cost));
                else if (src.cost.length == 1)
                        jObject.addProperty("costs", src.cost[0]);
            }
            if (src.costPerLevel != 0)
                jObject.addProperty("cost_per_level", src.costPerLevel);

            return jObject;
        }
    }
}