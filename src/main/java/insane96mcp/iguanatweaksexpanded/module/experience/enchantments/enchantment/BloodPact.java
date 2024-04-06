package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;

public class BloodPact extends Enchantment implements IEnchantmentTooltip {
    public static TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_blood_pact");
    public static EnchantmentCategory CATEGORY = EnchantmentCategory.create("accepts_blood_pact", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public static ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "blood_pact"));
    public BloodPact() {
        super(Rarity.VERY_RARE, CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 2 + 10 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof MendingEnchantment) && super.checkCompatibility(other);
    }

    public static void trySuckingAndRepairing(LivingEntity entity) {
        ItemStack mainHandItem = entity.getMainHandItem();
        if (mainHandItem.getEnchantmentLevel(NewEnchantmentsFeature.BLOOD_PACT.get()) <= 0)
            return;

        if (entity.getRandom().nextFloat() < 0.1f) {
            entity.hurt(entity.damageSources().source(DAMAGE_TYPE), 1);
            mainHandItem.setDamageValue(Math.max(mainHandItem.getDamageValue() - 10, -1));
        }
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.DARK_PURPLE);
    }
}
