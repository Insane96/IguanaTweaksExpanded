package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;

public class CurseOfStaticCharge extends Enchantment {
    public CurseOfStaticCharge() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
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
        if (event.getEntity().level().isClientSide
                || !event.getEntity().level().isThundering()
                || event.getEntity().tickCount % 20 != 2
                || !event.getEntity().level().canSeeSky(event.getEntity().blockPosition())
                || EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_STATIC_CHARGE.get(), event.getEntity()) <= 0)
            return;

        if (event.getEntity().getRandom().nextInt(5000) == 0) {
            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(event.getEntity().level());
            if (lightningbolt != null) {
                lightningbolt.moveTo(event.getEntity().position().add(0, event.getEntity().getBbHeight(), 0));
                event.getEntity().level().addFreshEntity(lightningbolt);
            }
        }
    }
}
