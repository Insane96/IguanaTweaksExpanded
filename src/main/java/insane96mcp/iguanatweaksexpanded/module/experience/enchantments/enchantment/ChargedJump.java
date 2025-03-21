package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.InsaneSurvivalOverhaul;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ChargedJump extends Enchantment {

    public static final String CHARGED_JUMP = InsaneSurvivalOverhaul.RESOURCE_PREFIX + "charged_jump_enchantment";

    public ChargedJump() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, new EquipmentSlot[] {EquipmentSlot.LEGS});
    }

    @Override
    public int getMinCost(int level) {
        return 20 + (level - 1) * 30;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 20;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public static int getTimeToCharge() {
        return 40;
    }

    public static float getJumpBoost(LivingEntity entity, float original) {
        if (!isReadyToJump(entity))
            return original;
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CHARGED_JUMP.get(), entity);
        if (lvl > 0)
            return original + 0.4f;
        return original;
    }

    public static boolean isReadyToJump(LivingEntity entity) {
        return entity.getPersistentData().getByte(CHARGED_JUMP) >= getTimeToCharge();
    }

    public static void tryChargeJump(LivingEntity entity) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.CHARGED_JUMP.get(), entity);
        if (lvl <= 0)
            return;
        if (!entity.isCrouching() || !entity.onGround()) {
            entity.getPersistentData().putByte(CHARGED_JUMP, (byte) 0);
            return;
        }
        boolean wasReadyToJump = isReadyToJump(entity);
        entity.getPersistentData().putByte(CHARGED_JUMP, (byte) (entity.getPersistentData().getByte(CHARGED_JUMP) + 1));
        if (isReadyToJump(entity) && !wasReadyToJump) {
            entity.playSound(SoundEvents.DOLPHIN_AMBIENT, 1.0F, 2.0F);
        }
    }

}
