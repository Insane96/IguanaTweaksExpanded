package insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces;

import com.mojang.blaze3d.vertex.PoseStack;
import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block.MultiBlockBlastFurnaceBlock;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block.MultiBlockBlastFurnaceBlockEntity;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block.MultiBlockSoulBlastFurnaceBlock;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.block.MultiBlockSoulBlastFurnaceBlockEntity;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.MultiItemBlastingRecipe;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.crafting.MultiItemSoulBlastingRecipe;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.data.MultiItemBlastingSerializer;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.data.MultiItemSoulBlastingSerializer;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.inventory.MultiBlockBlastFurnaceMenu;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.inventory.MultiBlockSoulBlastFurnaceMenu;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksexpanded.utils.ClientUtils;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Label(name = "Multi Block Furnaces", description = "Add new multi block furnaces")
@LoadFeature(module = Modules.Ids.MINING, canBeDisabled = false)
public class MultiBlockFurnaces extends Feature {
	public static final String INVALID_FURNACE_LANG = IguanaTweaksExpanded.MOD_ID + ".invalid_blast_furnace";

	public static final SimpleBlockWithItem BLAST_FURNACE = SimpleBlockWithItem.register("blast_furnace", () -> new MultiBlockBlastFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.BLAST_FURNACE)));
	public static final RegistryObject<BlockEntityType<MultiBlockBlastFurnaceBlockEntity>> BLAST_FURNACE_BLOCK_ENTITY_TYPE = ITERegistries.BLOCK_ENTITY_TYPES.register("blast_furnace", () -> BlockEntityType.Builder.of(MultiBlockBlastFurnaceBlockEntity::new, BLAST_FURNACE.block().get()).build(null));

	public static final RegistryObject<RecipeType<MultiItemBlastingRecipe>> BLASTING_RECIPE_TYPE = ITERegistries.RECIPE_TYPES.register("multi_item_blasting", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return "multi_item_blasting";
		}
	});
	public static final RegistryObject<MultiItemBlastingSerializer> BLASTING_RECIPE_SERIALIZER = ITERegistries.RECIPE_SERIALIZERS.register("multi_item_blasting", MultiItemBlastingSerializer::new);
	public static final RegistryObject<MenuType<MultiBlockBlastFurnaceMenu>> BLAST_FURNACE_MENU_TYPE = ITERegistries.MENU_TYPES.register("blast_furnace", () -> new MenuType<>(MultiBlockBlastFurnaceMenu::new, FeatureFlags.VANILLA_SET));

	public static final SimpleBlockWithItem SOUL_BLAST_FURNACE = SimpleBlockWithItem.register("soul_blast_furnace", () -> new MultiBlockSoulBlastFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.BLAST_FURNACE)));
	public static final RegistryObject<BlockEntityType<MultiBlockSoulBlastFurnaceBlockEntity>> SOUL_BLAST_FURNACE_BLOCK_ENTITY_TYPE = ITERegistries.BLOCK_ENTITY_TYPES.register("soul_blast_furnace", () -> BlockEntityType.Builder.of(MultiBlockSoulBlastFurnaceBlockEntity::new, SOUL_BLAST_FURNACE.block().get()).build(null));

	public static final RegistryObject<RecipeType<MultiItemSoulBlastingRecipe>> SOUL_BLASTING_RECIPE_TYPE = ITERegistries.RECIPE_TYPES.register("multi_item_soul_blasting", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return "multi_item_soul_blasting";
		}
	});
	public static final RegistryObject<MultiItemSoulBlastingSerializer> SOUL_BLASTING_RECIPE_SERIALIZER = ITERegistries.RECIPE_SERIALIZERS.register("multi_item_soul_blasting", MultiItemSoulBlastingSerializer::new);
	public static final RegistryObject<MenuType<MultiBlockSoulBlastFurnaceMenu>> SOUL_BLAST_FURNACE_MENU_TYPE = ITERegistries.MENU_TYPES.register("soul_blast_furnace", () -> new MenuType<>(MultiBlockSoulBlastFurnaceMenu::new, FeatureFlags.VANILLA_SET));

	@Config
	@Label(name = "Blast Furnace Data pack", description = "Enables a data pack that changes the vanilla Blast Furnace recipe to give the multi block blast furnace.")
	public static Boolean blastFurnaceDataPack = true;

	public MultiBlockFurnaces(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "multi_block_blast_furnace", Component.literal("IguanaTweaks Expanded Multi Block Blast Furnace"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && blastFurnaceDataPack));
	}

	@SubscribeEvent
	public void onRightClickBlastFurnace(PlayerInteractEvent.RightClickBlock event) {
		if (!this.isEnabled()
				|| !event.getLevel().getBlockState(event.getHitVec().getBlockPos()).is(Blocks.BLAST_FURNACE))
			return;

		event.getEntity().sendSystemMessage(Component.translatable(INVALID_FURNACE_LANG));
		event.setCanceled(true);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onRenderLevelStage(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
			return;

		for (GhostBlocksData ghostBlocksData : GHOST_BLOCKS_DATA) {
			ghostBlocksData.render(event.getPoseStack());
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START)
			return;

		List<GhostBlocksData> toRemove = new ArrayList<>();
		for (GhostBlocksData ghostBlocksData : GHOST_BLOCKS_DATA) {
			ghostBlocksData.tick();
			if (ghostBlocksData.shouldBeRemoved())
				toRemove.add(ghostBlocksData);
		}
		toRemove.forEach(GHOST_BLOCKS_DATA::remove);
	}

	public static final List<GhostBlocksData> GHOST_BLOCKS_DATA = new ArrayList<>();

	public static class GhostBlocksData {
		public int ticksRendered;
		public BlockPos furnacePos;
		public Map<BlockPos, BlockState> posAndStates = new HashMap<>();

		public GhostBlocksData(BlockPos furnacePos) {
			this(furnacePos, 200);
		}

		public GhostBlocksData(BlockPos furnacePos, int ticksRendered) {
			this.ticksRendered = ticksRendered;
			this.furnacePos = furnacePos;
		}

		public void tick() {
			this.ticksRendered--;
		}

		public boolean shouldBeRemoved() {
			return this.ticksRendered <= 0;
		}

		public void render(PoseStack poseStack) {
			for (Map.Entry<BlockPos, BlockState> posAndState : posAndStates.entrySet()) {
				ClientUtils.renderGhostBlock(poseStack, posAndState.getValue(), posAndState.getKey());
			}
		}
	}
}
