package insane96mcp.iguanatweaksexpanded.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;

public class DestroyBlockPostEvent extends BlockEvent {
    private final Player player;
    public DestroyBlockPostEvent(LevelAccessor level, BlockPos pos, BlockState state, Player player) {
        super(level, pos, state);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
