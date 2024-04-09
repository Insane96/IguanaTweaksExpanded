package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Vindication extends Enchantment {

    public static final String STACKED_DAMAGE = IguanaTweaksExpanded.RESOURCE_PREFIX + "stacked_vindication_damage";

    public Vindication() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[] {EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 8;
    }

    public static float maxStackedDamage(int lvl) {
        return lvl * 2f;
    }

    public static void tryStackDamage(LivingEntity attacked, DamageSource source, float damageAmount) {
        if (!(source.getEntity() instanceof LivingEntity))
            return;

        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.VINDICATION.get(), attacked);
        if (lvl == 0)
            return;
        float stackedDamage = attacked.getPersistentData().getFloat(STACKED_DAMAGE);
        float maxStackedDamage = maxStackedDamage(lvl);
        if (stackedDamage >= maxStackedDamage)
            return;
        stackedDamage = Math.min(maxStackedDamage, stackedDamage + damageAmount);
        attacked.getPersistentData().putFloat(STACKED_DAMAGE, stackedDamage);
    }

    public static void tryApplyDamage(LivingHurtEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof LivingEntity attacker))
            return;

        event.setAmount(event.getAmount() + attacker.getPersistentData().getFloat(STACKED_DAMAGE));
        attacker.getPersistentData().remove(STACKED_DAMAGE);
    }
}
