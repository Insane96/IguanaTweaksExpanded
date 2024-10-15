package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.util.MCUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.List;
import java.util.UUID;

public class CurseOfUnstableMotion extends Enchantment {
    public static final UUID MODIFIER_UUID = UUID.fromString("7567b3ad-a4c6-4700-8bf0-cd8c4356c155");
    public CurseOfUnstableMotion() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_LEGS, EquipmentSlot.values());
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 25;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return 50;
    }

    public static void tick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity().level().isClientSide)
            return;

        if (EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_UNSTABLE_MOTION.get(), event.getEntity()) <= 0
                && event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).getModifier(MODIFIER_UUID) != null) {
            event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MODIFIER_UUID);
        }
        else {
            RandomSource random = event.getEntity().getRandom();
            if (event.getEntity().tickCount % 200 != random.nextInt(200))
                return;

            event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MODIFIER_UUID);
            double amount = Speeds.getRandomSpeed(random).sample(random);
            MCUtils.applyModifier(event.getEntity(), Attributes.MOVEMENT_SPEED, MODIFIER_UUID, "Curse of Unstable Motion", amount, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
        }
    }

    private static class Speeds {
        public static final UniformFloat REALLY_SLOW = UniformFloat.of(-0.5f, -0.4f);
        public static final UniformFloat NEUTRAL = UniformFloat.of(-0.15f, 0.15f);
        public static final UniformFloat REALLY_FAST = UniformFloat.of(2f, 3f);

        public static final List<UniformFloat> SPEEDS = List.of(REALLY_SLOW, NEUTRAL, REALLY_FAST);

        public static UniformFloat getRandomSpeed(RandomSource random) {
            return SPEEDS.get(random.nextInt(SPEEDS.size()));
        }
    }
}
