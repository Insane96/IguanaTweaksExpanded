package insane96mcp.survivalreimagined.module.farming;

import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.util.IdTagMatcher;
import insane96mcp.survivalreimagined.base.SRFeature;
import insane96mcp.survivalreimagined.data.IdTagValue;
import insane96mcp.survivalreimagined.data.generator.SRBlockTagsProvider;
import insane96mcp.survivalreimagined.module.Modules;
import insane96mcp.survivalreimagined.network.message.JsonConfigSyncMessage;
import insane96mcp.survivalreimagined.utils.Utils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Label(name = "Harder Crops", description = "Crops are no longer insta-minable. Break speed is still affected by the Hardness module. Requires a minecraft restart.")
@LoadFeature(module = Modules.Ids.FARMING)
public class HarderCrops extends SRFeature {
	public static final TagKey<Block> HARDER_CROPS_TAG = SRBlockTagsProvider.create("harder_crops");
	public static final ArrayList<IdTagValue> CROPS_HARDNESS_DEFAULT = new ArrayList<>(List.of(
			new IdTagValue(IdTagMatcher.Type.TAG, HARDER_CROPS_TAG.location().toString(), 1d)
	));
	public static final ArrayList<IdTagValue> cropsHardness = new ArrayList<>();
	@Config
	@Label(name = "Only fully grown", description = "If the hardness should be applied to mature crops only.")
	public static Boolean onlyFullyGrown = true;

	public HarderCrops(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		JSON_CONFIGS.add(new JsonConfig<>("crops_hardness.json", cropsHardness, CROPS_HARDNESS_DEFAULT, IdTagValue.LIST_TYPE, HarderCrops::applyHardness, true, JsonConfigSyncMessage.ConfigType.HARDER_CROPS));
	}

	public static void applyHardness(List<IdTagValue> list, boolean isClientSide) {
		for (IdTagValue hardnesses : list) {
			getAllBlocks(hardnesses, isClientSide).forEach(block -> {
				if (onlyFullyGrown) {
					//I have doubts that this always takes the fully grown modded crops
					BlockState state = block.getStateDefinition().getPossibleStates().get(block.getStateDefinition().getPossibleStates().size() - 1);
					if (state.destroySpeed == 0f)
						state.destroySpeed = (float) hardnesses.value;
				}
				else {
					block.getStateDefinition().getPossibleStates().forEach(blockState -> {
						if (blockState.destroySpeed == 0f)
							blockState.destroySpeed = (float) hardnesses.value;
					});
				}
			});
		}
	}

	public static void handleSyncPacket(String json) {
		loadAndReadJson(json, cropsHardness, CROPS_HARDNESS_DEFAULT, IdTagValue.LIST_TYPE);
	}

	@SubscribeEvent
	public void onCropBreaking(PlayerEvent.BreakSpeed event) {
		if (!this.isEnabled()
				|| event.getState().destroySpeed == 0f)
			return;
		ItemStack heldStack = event.getEntity().getMainHandItem();
		if (!(heldStack.getItem() instanceof TieredItem heldItem))
			return;
		if (!heldItem.canPerformAction(heldStack, ToolActions.HOE_DIG) && !heldItem.canPerformAction(heldStack, ToolActions.AXE_DIG))
			return;
		Block block = event.getState().getBlock();
		boolean isInTag = Utils.isBlockInTag(block, HARDER_CROPS_TAG);
		if (!(block instanceof CropBlock) && !isInTag)
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