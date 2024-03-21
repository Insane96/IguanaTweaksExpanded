package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class CurseOfEnder extends Enchantment implements IEnchantmentTooltip {
    public CurseOfEnder() {
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

    public static void onHurt(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide)
            return;
        Level level = event.getEntity().level();
        for (ItemStack stack : entity.getArmorSlots()) {
            int lvl = stack.getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_ENDER.get());
            if (lvl <= 0 || level.getRandom().nextFloat() >= 0.20f)
                continue;
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();

            for (int i = 0; i < 16; ++i) {
                double tpX = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 12.0D;
                double tpY = Mth.clamp(entity.getY() + (double)(entity.getRandom().nextInt(12) - 6), level.getMinBuildHeight(), (level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1));
                double tpZ = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 12.0D;
                if (entity.isPassenger())
                    entity.stopRiding();

                Vec3 vec3 = entity.position();
                level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entity));
                if (entity.randomTeleport(tpX, tpY, tpZ, true)) {
                    SoundEvent soundevent = entity instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
                    level.playSound(null, x, y, z, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    entity.playSound(soundevent, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }

    @Override
    public Component getTooltip(ItemStack itemStack, int i) {
        return Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.RED);
    }
}
