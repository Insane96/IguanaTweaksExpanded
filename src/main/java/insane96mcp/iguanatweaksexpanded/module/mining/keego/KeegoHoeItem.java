package insane96mcp.iguanatweaksexpanded.module.mining.keego;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.farming.hoes.IHoeCooldownModifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class KeegoHoeItem extends HoeItem implements IHoeCooldownModifier {
    public KeegoHoeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult interactionResult = super.useOn(context);
        if (interactionResult.consumesAction() && context.getPlayer() != null) {
            int amplifier = 0;
            if (context.getPlayer().hasEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()))
                //noinspection DataFlowIssue
                amplifier = context.getPlayer().getEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()).getAmplifier() + 1;

            context.getPlayer().addEffect(new MobEffectInstance(NewEnchantmentsFeature.ATTACK_MOMENTUM.get(), 40, Math.min(amplifier, 7), false, false, true));
        }
        return interactionResult;
    }

    @Override
    public int getCooldownOnUse(int baseCooldown, Player player, Level level) {
        if (!player.hasEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()))
            return baseCooldown;

        return (int) (baseCooldown - ((player.getEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()).getAmplifier() + 1) * 0.66667f));
    }
}
