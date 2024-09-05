package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;

public class Recovery extends Enchantment {

    public static final String DAMAGE_TO_REGEN = IguanaTweaksExpanded.RESOURCE_PREFIX + "damage_to_regen";

    public Recovery() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[] {EquipmentSlot.CHEST});
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 30;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return 50;
    }

    public static float damageToRegenRatio(int lvl) {
        return 0.5f;
    }

    /**
     * Speed per second
     */
    public static float regenSpeed() {
        return 0.35f;
    }

    public static void storeDamageToRegen(LivingEntity attacked, DamageSource source, float damageAmount) {
        if (!(source.getEntity() instanceof LivingEntity)
                || damageAmount == 0f)
            return;

        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.RECOVERY.get(), attacked);
        if (lvl == 0)
            return;
        float damageToRegenStored = attacked.getPersistentData().getFloat(DAMAGE_TO_REGEN);
        if (damageAmount > attacked.getHealth())
            damageAmount = attacked.getHealth();
        float damageToRegen = damageToRegenRatio(lvl) * damageAmount;
        if (damageToRegenStored > damageToRegen)
            return;
        attacked.getPersistentData().putFloat(DAMAGE_TO_REGEN, damageToRegen);
    }

    public static void regen(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().getPersistentData().contains(DAMAGE_TO_REGEN))
            return;

        float damageToRegenStored = event.getEntity().getPersistentData().getFloat(DAMAGE_TO_REGEN);
        float toRegen = regenSpeed() / 20f;
        if (toRegen > damageToRegenStored)
            toRegen = damageToRegenStored;
        event.getEntity().heal(toRegen);
        damageToRegenStored -= toRegen;
        event.getEntity().getPersistentData().putFloat(DAMAGE_TO_REGEN, damageToRegenStored);
        if (damageToRegenStored <= 0)
            event.getEntity().getPersistentData().remove(DAMAGE_TO_REGEN);
    }
}
