package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.UUID;

public class CurseOfAnchor extends Enchantment {
    public static final UUID MODIFIER_UUID = UUID.fromString("89e80393-f1e7-445d-b4ed-de1d7bcf1c2a");
    public CurseOfAnchor() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR, EquipmentSlot.values());
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

    public static void apply(LivingEvent.LivingTickEvent event) {
        if (event.getEntity().getDeltaMovement().y >= 0)
            return;
        /*int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_ANCHOR.get(), event.getEntity());
        if (lvl <= 0)
            return;

        double speed = -0.2d;
        if (event.getEntity().isInFluidType())
            speed *= 2;
        event.getEntity().move(MoverType.SELF, new Vec3(0, speed, 0));*/
    }
}
