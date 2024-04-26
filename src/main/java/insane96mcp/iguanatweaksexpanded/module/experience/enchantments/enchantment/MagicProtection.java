package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEDamageTypeTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.protection.ITRProtectionEnchantment;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class MagicProtection extends ITRProtectionEnchantment {
    public static TagKey<DamageType> SOURCES_REDUCED = ITEDamageTypeTagsProvider.create("enchantments/protection/magic");
    public MagicProtection() {
        super(Rarity.UNCOMMON);
    }

    public static void reduceBadEffectsDuration(LivingEntity entity, MobEffectInstance effectInstance) {
        if (effectInstance.getEffect().isBeneficial()
                || effectInstance.getEffect().isInstantenous())
            return;

        int level = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.MAGIC_PROTECTION.get(), entity);
        if (level == 0)
            return;

        effectInstance.duration -= (int) (effectInstance.duration * (0.05f * level));
    }

    public int getMaxLevel() {
        return 4;
    }

    public int getBaseCost() {
        return 5;
    }

    public int getCostPerLevel() {
        return 8;
    }

    public float getDamageReductionPerLevel() {
        return 0.08F;
    }

    public boolean isSourceReduced(DamageSource source) {
        return source.is(SOURCES_REDUCED);
    }
}
