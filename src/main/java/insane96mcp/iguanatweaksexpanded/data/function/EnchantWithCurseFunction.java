package insane96mcp.iguanatweaksexpanded.data.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class EnchantWithCurseFunction extends LootItemConditionalFunction {
    protected EnchantWithCurseFunction(LootItemCondition[] pPredicates) {
        super(pPredicates);
    }

    @Override
    protected ItemStack run(ItemStack pStack, LootContext pContext) {
        RandomSource random = pContext.getRandom();
        Enchantment enchantment;
        boolean isBook = pStack.is(Items.BOOK) || pStack.is(Items.ENCHANTED_BOOK);
        List<Enchantment> list = ForgeRegistries.ENCHANTMENTS.getValues()
                .stream()
                .filter(Enchantment::isCurse)
                .filter((ench) -> isBook || ench.canEnchant(pStack))
                .toList();

        if (list.isEmpty()) {
            IguanaTweaksExpanded.LOGGER.warn("Couldn't find a compatible curse for {}", pStack);
            return pStack;
        }

        enchantment = list.get(random.nextInt(list.size()));

        return enchantItem(pStack, enchantment, random);
    }

    @Override
    public LootItemFunctionType getType() {
        return ITERegistries.ENCHANT_WITH_CURSE.get();
    }

    private static ItemStack enchantItem(ItemStack pStack, Enchantment pEnchantment, RandomSource pRandom) {
        int i = Mth.nextInt(pRandom, pEnchantment.getMinLevel(), pEnchantment.getMaxLevel());
        if (pStack.is(Items.BOOK)) {
            pStack = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(pStack, new EnchantmentInstance(pEnchantment, i));
        }
        else if (pStack.is(Items.ENCHANTED_BOOK))
            EnchantedBookItem.addEnchantment(pStack, new EnchantmentInstance(pEnchantment, i));
        else
            pStack.enchant(pEnchantment, i);

        return pStack;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<EnchantWithCurseFunction> {
        public void serialize(JsonObject pJson, EnchantWithCurseFunction pValue, JsonSerializationContext pSerializationContext) {
            super.serialize(pJson, pValue, pSerializationContext);
        }

        public EnchantWithCurseFunction deserialize(JsonObject pObject, JsonDeserializationContext pDeserializationContext, LootItemCondition[] pConditions) {
            return new EnchantWithCurseFunction(pConditions);
        }
    }
}
