package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class CurseOfExplosion extends Enchantment {
    public CurseOfExplosion() {
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

    public static void onDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player))
            return;

        int lvl = 0;
        for (ItemStack stack : player.getArmorSlots()) {
            //lvl += stack.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_EXPLOSION.get());
        }
        if (lvl == 0)
            return;
        player.getPersistentData().putByte("explode", (byte) lvl);
    }

    public static void onEntityRemoved(EntityLeaveLevelEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player))
            return;

        int lvl = player.getPersistentData().getByte("explode");
        if (lvl > 0) {
            Explosion explosion = event.getLevel().explode(player, player.getX(), player.getY(), player.getZ(), 1f + (lvl * 0.5f), Level.ExplosionInteraction.BLOCK);
            player.connection.send(new ClientboundExplodePacket(player.getX(), player.getY(), player.getZ(), 1f + (lvl * 0.5f), explosion.getToBlow(), explosion.getHitPlayers().get(player)));
        }
    }
}
