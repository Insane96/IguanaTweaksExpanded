package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class CurseOfObscurity extends Enchantment {
    public CurseOfObscurity() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.values());
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

    public static void apply(LivingDamageEvent event) {
        if (EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_OBSCURITY.get(), event.getEntity()) == 0
                || event.getEntity().getRandom().nextInt(3) != 0)
            return;

        event.getEntity().addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 5 * 20));
    }
}
