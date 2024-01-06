package insane96mcp.iguanatweaksexpanded.data.criterion;

import com.google.gson.JsonObject;
import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class OverweightCrateCarryTrigger extends SimpleCriterionTrigger<OverweightCrateCarryTrigger.TriggerInstance> {
	static final ResourceLocation ID = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "overweight_crate_carry");

	@Override
	protected TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate pPredicate, DeserializationContext context) {
		return new TriggerInstance(pPredicate);
	}

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	public void trigger(ServerPlayer player) {
		this.trigger(player, (TriggerInstance::matches));
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		public TriggerInstance(ContextAwarePredicate pPredicate) {
			super(ID, pPredicate);
		}

		public JsonObject serializeToJson(SerializationContext context) {
			return super.serializeToJson(context);
		}

		public boolean matches() {
			return true;
		}
	}
}
