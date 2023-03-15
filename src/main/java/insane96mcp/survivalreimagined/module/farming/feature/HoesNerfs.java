package insane96mcp.survivalreimagined.module.farming.feature;

import com.google.gson.reflect.TypeToken;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.util.IdTagMatcher;
import insane96mcp.survivalreimagined.SurvivalReimagined;
import insane96mcp.survivalreimagined.base.SRFeature;
import insane96mcp.survivalreimagined.module.Modules;
import insane96mcp.survivalreimagined.module.farming.utils.HoeStat;
import insane96mcp.survivalreimagined.setup.Strings;
import insane96mcp.survivalreimagined.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

@Label(name = "Hoes Nerfs", description = "Slower Hoes and more fragile. Hoes Properties are controlled via json in this feature's folder")
@LoadFeature(module = Modules.Ids.FARMING)
public class HoesNerfs extends SRFeature {

	private static final ResourceLocation DISABLED_HOES = new ResourceLocation(SurvivalReimagined.MOD_ID, "disabled_hoes");

	public static final ArrayList<HoeStat> HOES_STATS_DEFAULT = new ArrayList<>(Arrays.asList(
			new HoeStat(IdTagMatcher.Type.ID, "minecraft:stone_hoe", 20, 3),
			new HoeStat(IdTagMatcher.Type.ID, "minecraft:iron_hoe", 15, 3),
			new HoeStat(IdTagMatcher.Type.ID, "minecraft:golden_hoe", 4),
			new HoeStat(IdTagMatcher.Type.ID, "minecraft:diamond_hoe", 10, 2),
			new HoeStat(IdTagMatcher.Type.ID, "minecraft:netherite_hoe", 6, 2),
			new HoeStat(IdTagMatcher.Type.ID, "minecraft:vulcanite_hoe", 15, 3)
	));

	public static final ArrayList<HoeStat> hoesStats = new ArrayList<>();

	public HoesNerfs(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	static final Type hoesStatsListType = new TypeToken<ArrayList<HoeStat>>(){}.getType();
	@Override
	public void loadJsonConfigs() {
		if (!this.isEnabled())
			return;
		super.loadJsonConfigs();
		this.loadAndReadFile("hoes_stats.json", hoesStats, HOES_STATS_DEFAULT, hoesStatsListType);
	}

	@SubscribeEvent
	public void onHoeUse(BlockEvent.BlockToolModificationEvent event) {
		if (!this.isEnabled()
				|| event.getPlayer() == null
				|| (event.getPlayer() != null && event.getPlayer().level.isClientSide)
				|| event.isSimulated()
				|| event.getToolAction() != ToolActions.HOE_TILL
				|| event.getState().getBlock().getToolModifiedState(event.getState(), event.getContext(), event.getToolAction(), true) == null)
			return;

		boolean isHoeDisabled = disabledHoes(event);
		if (!isHoeDisabled)
			harderTilling(event);
	}

	public boolean disabledHoes(BlockEvent.BlockToolModificationEvent event) {
		if (!isHoeDisabled(event.getHeldItemStack().getItem()))
			return false;

		//noinspection ConstantConditions getPlayer can't be null as it's called from onHoeUse that checks if player's null
		event.getPlayer().displayClientMessage(Component.translatable(Strings.Translatable.TOO_WEAK), true);
		event.setCanceled(true);
		return true;
	}

	public void harderTilling(BlockEvent.BlockToolModificationEvent event) {
		ItemStack hoe = event.getHeldItemStack();
		//noinspection ConstantConditions getPlayer can't be null as it's called from onHoeUse that checks if player's null
		if (event.getPlayer().getCooldowns().isOnCooldown(hoe.getItem()))
			return;
		for (HoeStat hoeStat : hoesStats) {
			if (hoeStat.matchesItem(hoe.getItem(), null)) {
				if (hoeStat.cooldown > 0) {
					event.getPlayer().getCooldowns().addCooldown(hoe.getItem(), hoeStat.cooldown);
				}
				if (hoeStat.damageOnTill > 1) {
					hoe.hurtAndBreak(hoeStat.damageOnTill - 1, event.getPlayer(), (player) -> player.broadcastBreakEvent(event.getPlayer().getUsedItemHand()));
				}
				break;
			}
		}
	}

	private static boolean isHoeDisabled(Item item) {
		return Utils.isItemInTag(item, DISABLED_HOES);
	}
}