package insane96mcp.iguanatweaksexpanded.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class EnchantmentBlockBreakEvent extends Event {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState state;
    private final ServerPlayer player;
    private int exp;

    public EnchantmentBlockBreakEvent(Level level, BlockPos pos, BlockState state, ServerPlayer player) {
        this.pos = pos;
        this.level = level;
        this.state = state;
        this.player = player;

        if (state == null || !ForgeHooks.isCorrectToolForDrops(state, player)) // Handle empty block or player unable to break block scenario
        {
            this.exp = 0;
        }
        else
        {
            int fortuneLevel = player.getMainHandItem().getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
            int silkTouchLevel = player.getMainHandItem().getEnchantmentLevel(Enchantments.SILK_TOUCH);
            this.exp = state.getExpDrop(level, level.random, pos, fortuneLevel, silkTouchLevel);
        }
    }

    public LevelAccessor getLevel()
    {
        return level;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public BlockState getState()
    {
        return state;
    }

    public ServerPlayer getPlayer() {
        return this.player;
    }

    /**
     * Get the experience dropped by the block after the event has processed
     *
     * @return The experience to drop or 0 if the event was canceled
     */
    public int getExpToDrop()
    {
        return this.isCanceled() ? 0 : exp;
    }

    /**
     * Set the amount of experience dropped by the block after the event has processed
     *
     * @param exp 1 or higher to drop experience, else nothing will drop
     */
    public void setExpToDrop(int exp)
    {
        this.exp = exp;
    }
}
