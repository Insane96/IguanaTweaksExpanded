package insane96mcp.iguanatweaksexpanded.module.mining.miningcharge;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

@LoadFeature(module = Modules.Ids.MINING, description = "Adds mining charge")
public class MiningCharge extends Feature {

	public static final SimpleBlockWithItem MINING_CHARGE = SimpleBlockWithItem.register("mining_charge", () -> new MiningChargeBlock(BlockBehaviour.Properties.copy(Blocks.TNT)));

	public static final RegistryObject<EntityType<PrimedMiningCharge>> PRIMED_MINING_CHARGE = ISERegistries.ENTITY_TYPES.register("mining_charge", () -> EntityType.Builder.<PrimedMiningCharge>of(PrimedMiningCharge::new, MobCategory.MISC).fireImmune().sized(0.735F, 0.735F).clientTrackingRange(10).updateInterval(10).build("mining_charge"));

	public static final RegistryObject<SoundEvent> PRIMED_MINING_CHARGE_SOUND = ISERegistries.SOUND_EVENTS.register("primed_mining_charge", () -> SoundEvent.createFixedRangeEvent(new ResourceLocation("entity.tnt.primed"), 16f));

	public MiningCharge(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}
}
