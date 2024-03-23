package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class DoubleJump extends Enchantment implements IEnchantmentTooltip {
    public DoubleJump() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int level) {
        return 20 + (level - 1) * 30;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 20;
    }

    public boolean checkCompatibility(Enchantment other) {
        if (other instanceof ProtectionEnchantment otherProtection)
            return otherProtection.type != ProtectionEnchantment.Type.FALL;
        else return super.checkCompatibility(other);
    }

    public static boolean extraJump(Player entity) {
        if (entity.onGround()
                || entity.onClimbable()
                || entity.isInWaterOrBubble())
            return false;

        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.MA_JUMP.get(), entity);
        if (getRemainingJumps(entity, lvl) <= 0)
            return false;

        entity.jumpFromGround();
        entity.getPersistentData().putInt("double_jumps", entity.getPersistentData().getInt("double_jumps") + 1);
        //Set fallDistance to 0 to prevent falling sound client-side
        entity.fallDistance = 0f;
        playAnimation(entity);
        return true;
    }

    public static void playAnimation(Player player) {
        RandomSource random = player.level().getRandom();
        if (!player.level().isClientSide) {
            ((ServerLevel)player.level()).sendParticles(ParticleTypes.CLOUD, player.getX() - 0.25f + random.nextFloat() * 0.5f, player.getY(), player.getZ() - 0.25f + random.nextFloat() * 0.5f, 5, 0, 0, 0, 0);
        }

        player.playSound(SoundEvents.FIREWORK_ROCKET_LAUNCH, 0.5f, 2f);
    }

    public static int getRemainingJumps(Player entity, int lvl) {
        return lvl - entity.getPersistentData().getInt("double_jumps");
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", lvl).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
