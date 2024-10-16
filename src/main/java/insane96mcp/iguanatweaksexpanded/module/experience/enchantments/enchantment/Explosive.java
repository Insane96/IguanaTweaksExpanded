package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class Explosive extends BonusDamageEnchantment {
    public Explosive() {
        super(Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public float getDamageBonus(LivingEntity attacker, Entity target, ItemStack stack, int lvl) {
        return 0f;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public static float getExplosionPower(int lvl, ItemStack stack) {
        return 1 + ((lvl - 1) * 0.5f);
    }

    public static void onKill(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity killer))
            return;
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.EXPLOSIVE.get(), killer);
        if (lvl <= 0)
            return;

        event.getEntity().level().explode(event.getEntity(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), getExplosionPower(lvl, killer.getMainHandItem()), Level.ExplosionInteraction.BLOCK);
    }
}
