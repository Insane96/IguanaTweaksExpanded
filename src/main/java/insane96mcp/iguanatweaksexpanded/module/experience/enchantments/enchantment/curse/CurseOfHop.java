package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingEvent;

public class CurseOfHop extends Enchantment {
    public CurseOfHop() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, EquipmentSlot.values());
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
        if (event.getEntity().tickCount % 20 != 8)
            return;

        if (event.getEntity().getRandom().nextInt(60) == 0) {
            if (event.getEntity() instanceof Player player) {
                player.jumpFromGround();
            }
            else if (event.getEntity() instanceof Mob mob) {
                mob.getJumpControl().jump();
            }
            float rot = event.getEntity().getYRot();
            if (event.getEntity().getRandom().nextBoolean())
                rot = (rot + 180) % 360;
            float radian = rot * ((float)Math.PI / 180F);
            event.getEntity().setDeltaMovement(event.getEntity().getDeltaMovement().add((double)(-Mth.sin(radian) * 0.4F), 0.0D, (double)(Mth.cos(radian) * 0.4F)));
            event.getEntity().setJumping(true);
        }
        /*if (event.getEntity().getDeltaMovement().y >= 0)
            return;*/
        /*int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_ANCHOR.get(), event.getEntity());
        if (lvl <= 0)
            return;

        double speed = -0.2d;
        if (event.getEntity().isInFluidType())
            speed *= 2;
        event.getEntity().move(MoverType.SELF, new Vec3(0, speed, 0));*/
    }
}
