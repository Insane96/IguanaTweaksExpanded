package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.InsaneSurvivalExtra;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class BaneOfNoses extends BonusDamageEnchantment {
    public static final TagKey<EntityType<?>> AFFECTED_BY_BANE_OF_NOSES = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "enchantments/bane_of_noses"));
    public BaneOfNoses() {
        super(Rarity.UNCOMMON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isAffectedByEnchantment(Entity target) {
        return target.getType().is(AFFECTED_BY_BANE_OF_NOSES);
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity entity, int lvl) {
        if (!(entity instanceof LivingEntity livingentity))
            return;

        if (lvl > 0 && livingentity.getType().is(AFFECTED_BY_BANE_OF_NOSES)) {
            int i = 20 + attacker.getRandom().nextInt(10 * lvl);
            livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, i, 2));
        }
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(EnchantmentsFeature.class);
    }
}
