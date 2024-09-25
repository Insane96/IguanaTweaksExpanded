package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import insane96mcp.insanelib.data.IdTagMatcher;
import net.minecraft.util.GsonHelper;

import java.util.ArrayList;

public class EnchantmentData {
    public IdTagMatcher enchantment;
    public int[] cost;

    public EnchantmentData(IdTagMatcher enchantment, int... cost) {
        this.enchantment = enchantment;
        this.cost = cost;
    }

    public EnchantmentData(String enchantment, int... cost) {
        this.enchantment = IdTagMatcher.newId(enchantment);
        this.cost = cost;
    }

    public static final java.lang.reflect.Type LIST_TYPE = new TypeToken<ArrayList<EnchantmentData>>(){}.getType();
    public static class Serializer implements JsonDeserializer<EnchantmentData>, JsonSerializer<EnchantmentData> {
        @Override
        public EnchantmentData deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jObject = json.getAsJsonObject();
            IdTagMatcher idTagMatcher = context.deserialize(jObject.get("enchantment"), IdTagMatcher.class);
            if (jObject.get("cost").isJsonPrimitive())
                return new EnchantmentData(idTagMatcher, new int[]{GsonHelper.getAsInt(jObject, "cost")});
            else {
                JsonArray jsonArray = jObject.get("cost").getAsJsonArray();
                int[] cost = new int[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); i++) {
                    cost[i] = jsonArray.get(i).getAsInt();
                }
                return new EnchantmentData(idTagMatcher, cost);
            }
        }

        @Override
        public JsonElement serialize(EnchantmentData src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jObject = new JsonObject();
            jObject.add("enchantment", context.serialize(src.enchantment));
            if (src.cost.length > 1)
                jObject.add("value", context.serialize(src.cost));
            else
                jObject.addProperty("value", src.cost[0]);

            return jObject;
        }
    }
}