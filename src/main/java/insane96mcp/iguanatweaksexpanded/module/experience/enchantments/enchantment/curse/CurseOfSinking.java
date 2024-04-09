package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingEvent;

public class CurseOfSinking extends Enchantment {
    public CurseOfSinking() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, EquipmentSlot.values());
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

    public static void sink(LivingEvent.LivingTickEvent event) {
        /*if (!event.getEntity().isInFluidType())
            return;
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_SINKING.get(), event.getEntity());
        if (lvl == 0)
            return;
        event.getEntity().setDeltaMovement(event.getEntity().getDeltaMovement().add(0, -0.03d * event.getEntity().getAttributeValue(ForgeMod.SWIM_SPEED.get()), 0));*/
    }
}
