package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.event.HurtItemStackEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;

public class BloodPact extends Enchantment {
    public static ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "blood_pact"));
    public BloodPact() {
        super(Rarity.VERY_RARE, EnchantmentCategory.VANISHABLE, EquipmentSlot.values());
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

    public static void trySuckingAndRepairing(HurtItemStackEvent event) {
        if (event.getPlayer() == null)
            return;
        if (event.getStack().getEnchantmentLevel(NewEnchantmentsFeature.BLOOD_PACT.get()) <= 0)
            return;

        for (int i = 0; i < event.getAmount(); i++) {
            if (event.getPlayer().getRandom().nextFloat() < 0.1f)
                event.getPlayer().hurt(event.getPlayer().damageSources().source(DAMAGE_TYPE), 1);
        }
        event.setAmount(0);
    }
}
