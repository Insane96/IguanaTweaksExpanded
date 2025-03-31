package insane96mcp.iguanatweaksexpanded.module.mining.durium;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.state.BlockState;

public class DuriumShears extends ShearsItem {
    public DuriumShears(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return Math.max(1f, super.getDestroySpeed(pStack, pState) * 0.5f);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (player.getCooldowns().isOnCooldown(stack.getItem()))
            return InteractionResult.PASS;
        InteractionResult interactionResult = super.interactLivingEntity(stack, player, entity, hand);
        if (interactionResult != InteractionResult.PASS)
            player.getCooldowns().addCooldown(stack.getItem(), 60);
        return interactionResult;
    }
}
