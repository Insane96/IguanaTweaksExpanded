package insane96mcp.iguanatweaksexpanded.module.mining.durium;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DuriumShears extends ShearsItem {
    public DuriumShears(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        if (!pState.is(Blocks.COBWEB) && !pState.is(BlockTags.LEAVES)) {
            if (pState.is(BlockTags.WOOL)) {
                return 3f;
            }
            else {
                return 1f;
            }
        }
        else {
            return 9f;
        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (player.getCooldowns().isOnCooldown(stack.getItem()))
            return InteractionResult.PASS;
        InteractionResult interactionResult = super.interactLivingEntity(stack, player, entity, hand);
        if (interactionResult != InteractionResult.PASS)
            player.getCooldowns().addCooldown(stack.getItem(), 80);
        return interactionResult;
    }
}
