package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.insanelib.base.Feature;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;

public class Keego extends Enchantment {

    @SuppressWarnings("deprecation")
    static final EnchantmentCategory ALL_ITEMS = EnchantmentCategory.create("all_items", item -> EnchantmentCategory.ARMOR_LEGS.canEnchant(item) || EnchantmentCategory.DIGGER.canEnchant(item) || item.builtInRegistryHolder().is(BonusDamageEnchantment.ACCEPTS_ENCHANTMENT));

    public Keego() {
        super(Rarity.RARE, ALL_ITEMS, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 1 + 10 * (pEnchantmentLevel - 1);
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    @Override
    public boolean isDiscoverable() {
        return Feature.isEnabled(NewEnchantmentsFeature.class);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (!event.getEntity().hasEffect(NewEnchantmentsFeature.MINING_MOMENTUM.get())
                || !event.getEntity().getMainHandItem().isCorrectToolForDrops(event.getState()))
            return;

        //noinspection DataFlowIssue
        int lvl = event.getEntity().getEffect(NewEnchantmentsFeature.MINING_MOMENTUM.get()).getAmplifier() + 1;
        event.setNewSpeed(event.getNewSpeed() * (1 + lvl * 0.075f));
    }

    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer().getMainHandItem().getEnchantmentLevel(NewEnchantmentsFeature.KEEGO.get()) <= 0)
            return;
        int amplifier = 0;
        if (event.getPlayer().hasEffect(NewEnchantmentsFeature.MINING_MOMENTUM.get()))
            //noinspection DataFlowIssue
            amplifier = event.getPlayer().getEffect(NewEnchantmentsFeature.MINING_MOMENTUM.get()).getAmplifier() + 1;

        int duration = (int) (1f / event.getState().getDestroyProgress(event.getPlayer(), event.getLevel(), event.getPos()) + 5) * 3 + 1;
        event.getPlayer().addEffect(new MobEffectInstance(NewEnchantmentsFeature.MINING_MOMENTUM.get(), Math.max(duration, 10), Math.min(amplifier, 23), false, false, true));
    }

    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity().level().isClientSide
                || event.getEntity().isCrouching()
                || !event.getEntity().onGround())
            return;

        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.KEEGO.get(), event.getEntity());
        if (lvl <= 0)
            return;
        if (event.getEntity().walkDist % 5 < event.getEntity().walkDistO % 5) {
            int amplifier = 0;
            if (event.getEntity().hasEffect(NewEnchantmentsFeature.MOVEMENT_MOMENTUM.get()))
                //noinspection DataFlowIssue
                amplifier = event.getEntity().getEffect(NewEnchantmentsFeature.MOVEMENT_MOMENTUM.get()).getAmplifier() + 1;

            event.getEntity().addEffect(new MobEffectInstance(NewEnchantmentsFeature.MOVEMENT_MOMENTUM.get(), 80, Math.min(amplifier, 7), false, false, true));
        }
    }

    public static void onHurt(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer serverPlayer)
                || serverPlayer.getAttackStrengthScale(0.5f) <= 0.9f
                || !(event.getSource().getDirectEntity() instanceof LivingEntity livingEntity)
                || livingEntity.getMainHandItem().getEnchantmentLevel(NewEnchantmentsFeature.KEEGO.get()) <= 0)
            return;

        int amplifier = 0;
        if (serverPlayer.hasEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()))
            //noinspection DataFlowIssue
            amplifier = serverPlayer.getEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()).getAmplifier() + 1;

        double duration = ((4 - serverPlayer.getAttribute(Attributes.ATTACK_SPEED).getValue()) * 20d);
        serverPlayer.addEffect(new MobEffectInstance(NewEnchantmentsFeature.ATTACK_MOMENTUM.get(), (int) Math.max(duration, 10), Math.min(amplifier, 7), false, false, true));
    }
}
