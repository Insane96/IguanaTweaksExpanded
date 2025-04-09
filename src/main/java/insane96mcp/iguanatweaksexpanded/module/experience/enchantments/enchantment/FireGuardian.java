package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.InsaneSurvivalExtra;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class FireGuardian extends Enchantment {

    public static final String LAST_USED_FIRE_GUARDIAN = InsaneSurvivalExtra.MOD_ID + "last_used_fire_guardian";

    public FireGuardian() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMinCost(int level) {
        return 20 + (level - 1) * 30;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 20;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    static MobEffectInstance EFFECT = new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300);

    public static void onDamaged(LivingDamageEvent event) {
        if (event.isCanceled()
                || !event.getSource().is(DamageTypeTags.IS_FIRE)
                || event.getEntity().getEffect(MobEffects.FIRE_RESISTANCE) != null
                || event.getEntity().getPersistentData().getLong(LAST_USED_FIRE_GUARDIAN) + 600 > event.getEntity().level().getGameTime())
            return;
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.FIRE_GUARDIAN.get(), event.getEntity());
        if (lvl <= 0)
            return;

        event.getEntity().addEffect(new MobEffectInstance(EFFECT));
        event.getEntity().getPersistentData().putLong(LAST_USED_FIRE_GUARDIAN, event.getEntity().level().getGameTime());
        event.getEntity().playSound(SoundEvents.FIRE_EXTINGUISH);
        event.setCanceled(true);
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(EnchantmentsFeature.class);
    }
}
