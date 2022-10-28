package insane96mcp.iguanatweaksreborn.module.farming.feature;

import insane96mcp.iguanatweaksreborn.module.Modules;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.util.IdTagMatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

@Label(name = "Harder Crops", description = """
		Crops are no longer insta-minable. This applies only to blocks that are instances of net.minecraft.world.level.block.CropBlock.
		Crops hardness is still affected by the Hardness module.
		Changing anything requires a minecraft restart.""")
@LoadFeature(module = Modules.Ids.FARMING)
public class HarderCrops extends Feature {

	@Config(min = 0d, max = 128d)
	@Label(name = "Hardness", description = "How hard to break are plants? For comparison, dirt has an hardness of 0.5")
	public static Double hardness = 1.0d;
	@Config
	@Label(name = "Other affected blocks", description = "Block ids or tags that will have the hardness and hoe efficiency applied. Each entry has a block or tag. This still only applies to blocks that have 0 hardness.")
	public ArrayList<IdTagMatcher> moreBlocksList;
	@Config
	@Label(name = "Only fully grown", description = "If the hardness should be applied to mature crops only.")
	public static Boolean onlyFullyGrown = true;

	public HarderCrops(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	@Override
	public void readConfig(final ModConfigEvent event) {
		super.readConfig(event);
		applyHardness();
	}

	private boolean hardnessApplied = false;

	private void applyHardness() {
		if (!this.isEnabled()
				|| hardnessApplied) return;
		hardnessApplied = true;

		for (Block block : ForgeRegistries.BLOCKS.getValues()) {
			boolean isInWhitelist = false;
			for (IdTagMatcher blacklistEntry : this.moreBlocksList) {
				if (blacklistEntry.matchesBlock(block, null)) {
					isInWhitelist = true;
					break;
				}
			}
			if (!(block instanceof CropBlock) && !isInWhitelist)
				continue;
			if (onlyFullyGrown) {
				//I have doubts that this always takes the fully grown crop
				block.getStateDefinition().getPossibleStates().get(block.getStateDefinition().getPossibleStates().size() - 1).destroySpeed = hardness.floatValue();
			}
			else {
				block.getStateDefinition().getPossibleStates().forEach(blockState -> {
					if (blockState.destroySpeed == 0f)
						blockState.destroySpeed = hardness.floatValue();
				});
			}
		}
	}

	@SubscribeEvent
	public void onCropBreaking(PlayerEvent.BreakSpeed event) {
		if (!this.isEnabled()
				|| hardness == 0d) return;
		ItemStack heldStack = event.getEntity().getMainHandItem();
		if (!(heldStack.getItem() instanceof TieredItem heldItem))
			return;
		if (!heldItem.canPerformAction(heldStack, ToolActions.HOE_DIG) && !heldItem.canPerformAction(heldStack, ToolActions.AXE_DIG))
			return;
		Block block = event.getState().getBlock();
		boolean isInWhitelist = false;
		for (IdTagMatcher blacklistEntry : this.moreBlocksList) {
			if (blacklistEntry.matchesBlock(block, null)) {
				isInWhitelist = true;
				break;
			}
		}
		if (!(block instanceof CropBlock) && !isInWhitelist)
			return;
		float efficiency = heldItem.getTier().getSpeed();
		if (efficiency > 1.0F) {
			int efficiencyLevel = EnchantmentHelper.getBlockEfficiency(event.getEntity());
			ItemStack itemstack = event.getEntity().getMainHandItem();
			if (efficiencyLevel > 0 && !itemstack.isEmpty()) {
				efficiency += (float) (efficiencyLevel * efficiencyLevel + 1);
			}
		}
		if (heldItem.canPerformAction(heldStack, ToolActions.HOE_DIG))
			event.setNewSpeed(event.getNewSpeed() * efficiency);
		else
			event.setNewSpeed(event.getNewSpeed() / efficiency);

	}
}