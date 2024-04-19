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

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinCost(int pEnchantmentLevel) {
        return pEnchantmentLevel * 25;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return this.getMinCost(pEnchantmentLevel) + 50;
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

        float damageThrough = 0f;
        for (int i = 0; i < event.getAmount(); i++) {
            if (event.getPlayer().getRandom().nextInt(8) == 0)
                damageThrough++;
        }
        if (damageThrough > 0)
            event.getPlayer().hurt(event.getPlayer().damageSources().source(DAMAGE_TYPE), damageThrough);
        event.setAmount((int) damageThrough);
    }
}
