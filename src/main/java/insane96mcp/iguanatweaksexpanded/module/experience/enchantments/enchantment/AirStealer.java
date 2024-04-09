package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AirStealer extends Enchantment {
    public static TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_air_stealer");
    public static EnchantmentCategory CATEGORY = EnchantmentCategory.create("accepts_air_stealer", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public AirStealer() {
        super(Rarity.UNCOMMON, CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int level) {
        return 2 + 10 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
    }

    public static void onAttack(LivingEntity attacker, LivingEntity attacked) {
        ItemStack mainHandItem = attacker.getMainHandItem();
        int lvl = mainHandItem.getEnchantmentLevel(NewEnchantmentsFeature.AIR_STEALER.get());
        if (lvl <= 0)
            return;

        int ticksStolen = 10 * lvl;
        attacked.setAirSupply(attacked.getAirSupply() - ticksStolen);
        attacker.setAirSupply(attacker.getAirSupply() + ticksStolen);
    }
}
