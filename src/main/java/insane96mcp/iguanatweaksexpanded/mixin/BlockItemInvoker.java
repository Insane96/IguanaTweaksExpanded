package insane96mcp.iguanatweaksexpanded.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(BlockItem.class)
public interface BlockItemInvoker {
    @Invoker("getPlaceSound")
    SoundEvent invokeGetPlaceSound(BlockState state, Level world, BlockPos pos, Player entity);

    @Invoker("getPlacementState")
    BlockState invokeGetPlacementState(BlockPlaceContext pContext);
    @Invoker("placeBlock")
    boolean invokePlaceBlock(BlockPlaceContext pContext, BlockState pState);
    @Invoker("updateBlockStateFromTag")
    BlockState invokeUpdateBlockStateFromTag(BlockPos pPos, Level pLevel, ItemStack pStack, BlockState pState);
    @Invoker("updateCustomBlockEntityTag")
    boolean invokeUpdateCustomBlockEntityTag(BlockPos pPos, Level pLevel, @Nullable Player pPlayer, ItemStack pStack, BlockState pState);
}
