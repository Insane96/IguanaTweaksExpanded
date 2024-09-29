package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.setup.client.ITEBookCategory;
import insane96mcp.iguanatweaksreborn.module.experience.anvils.AnvilRepair;
import insane96mcp.iguanatweaksreborn.module.experience.anvils.Anvils;
import insane96mcp.insanelib.data.IdTagMatcher;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Optional;

public class ForgeRepairRecipe extends ForgeRecipe {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    final float maxRepair;
    final float costMultiplier;
    protected final int smashesRequired;

    public ForgeRepairRecipe(ResourceLocation id, Ingredient repairMaterial, int repairMaterialAmount, Ingredient gear, float maxRepair, float costMultiplier, int smashesRequired) {
		super(id, ITEBookCategory.FORGE_MISC, repairMaterial, repairMaterialAmount, gear, gear.getItems()[0], smashesRequired, 0f);
		this.type = Forging.FORGE_REPAIR_TYPE.get();
        this.id = id;
        this.maxRepair = maxRepair;
        this.costMultiplier = costMultiplier;
        this.smashesRequired = smashesRequired;
    }

    @Override
    public boolean matches(Container container, Level pLevel) {
        return this.ingredient.test(container.getItem(ForgeMenu.INGREDIENT_SLOT))
                //&& container.getItem(ForgeMenu.INGREDIENT_SLOT).getCount() >= this.ingredientAmount
                && this.gear.test(container.getItem(ForgeMenu.GEAR_SLOT));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack repairMaterial = container.getItem(ForgeMenu.INGREDIENT_SLOT);
        ItemStack gear = container.getItem(ForgeMenu.GEAR_SLOT);
        if (repairMaterial.isEmpty() || gear.isEmpty())
            return gear;

        ItemStack result = gear.copy();
        int repairItemCountCost;
        int maxPartialRepairDmg = Mth.ceil(result.getMaxDamage() * (1f - maxRepair));
        float amountRequired = ingredientAmount;
        if (Anvils.moreMaterialIfEnchanted > 0f && gear.isEnchanted()) {
            float increase = 0f;
            for (Integer lvl : EnchantmentHelper.getEnchantments(gear).values()) {
                increase += Anvils.moreMaterialIfEnchanted.floatValue() * lvl;
            }
            amountRequired *= 1 + increase;
        }
        if (Anvils.moreMaterialIfEnchantedFlat > 0f && gear.isEnchanted()) {
            float increase = 0f;
            for (Integer lvl : EnchantmentHelper.getEnchantments(gear).values()) {
                increase += Anvils.moreMaterialIfEnchantedFlat.floatValue() * lvl;
            }
            amountRequired += (increase * costMultiplier);
        }
        float repairSteps = Math.min(result.getDamageValue(), result.getMaxDamage() / amountRequired);
        if (repairSteps <= 0 || result.getDamageValue() <= maxPartialRepairDmg)
            return gear;

        float damageValue = result.getDamageValue();
        for (repairItemCountCost = 0; repairSteps > 0 && repairItemCountCost < repairMaterial.getCount() && damageValue > maxPartialRepairDmg; ++repairItemCountCost) {
            damageValue -= repairSteps;
            repairSteps = Math.min(damageValue, result.getMaxDamage() / amountRequired);
        }
        result.setDamageValue((int) Math.max(maxPartialRepairDmg, damageValue));
        return result;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.gear.getItems()[0];
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

    public ResourceLocation getId() {
        return this.id;
    }

    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Nullable
    public static ForgeRepairRecipe fromLeftAndRightAnvil(ItemStack gear, ItemStack repairMaterialStack) {
        Optional<AnvilRepair.RepairData> oRepairData = Anvils.getCustomAnvilRepair(gear, repairMaterialStack);

        if (oRepairData.isPresent()) {
            Ingredient repairMaterial;
            if (oRepairData.get().repairMaterial().type == IdTagMatcher.Type.ID)
                repairMaterial = Ingredient.of(oRepairData.get().repairMaterial().getAllItems().get(0));
            else
                repairMaterial = Ingredient.of(TagKey.create(Registries.ITEM, oRepairData.get().repairMaterial().location));
            return new ForgeRepairRecipe(new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "repair_%s_with_%s".formatted(ForgeRegistries.ITEMS.getKey(gear.getItem()).getPath(), ForgeRegistries.ITEMS.getKey(repairMaterialStack.getItem()).getPath())),
                    repairMaterial,
                    oRepairData.get().amountRequired(),
                    Ingredient.of(gear),
                    oRepairData.get().maxRepair(),
                    oRepairData.get().costMultiplier(),
                    4);
        }
        return null;
    }
}
