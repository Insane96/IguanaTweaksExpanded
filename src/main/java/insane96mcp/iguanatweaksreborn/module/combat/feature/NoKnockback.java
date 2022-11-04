package insane96mcp.iguanatweaksreborn.module.combat.feature;

import com.google.common.collect.Multimap;
import insane96mcp.iguanatweaksreborn.IguanaTweaksReborn;
import insane96mcp.iguanatweaksreborn.module.Modules;
import insane96mcp.iguanatweaksreborn.setup.Strings;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Label(name = "No Knockback", description = "Player will deal no knockback if attacking with a non-weapon or spamming.")
@LoadFeature(module = Modules.Ids.COMBAT)
public class NoKnockback extends Feature {

	public static final ResourceLocation NO_KNOCKBACK_TAG = new ResourceLocation(IguanaTweaksReborn.RESOURCE_PREFIX + "no_knockback");

	@Config
	@Label(name = "No Item No Knockback", description = "If true the player will deal no knockback when not using an item that doesn't have the attack damage attribute.")
	public static Boolean noItemNoKnockback = true;
	@Config(min = 0d, max = 1d)
	@Label(name = "Attack Cooldown No Knockback", description = "When the attack cooldown is below this percentage the player will deal no knockback. (Between 0 and 1, where 1 is the attack fully charged)")
	public static Double attackCooldownNoKnockback = 0.925d;

	public NoKnockback(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	@SubscribeEvent
	public void onPlayerAttackEvent(AttackEntityEvent event) {
		if (!this.isEnabled())
			return;
		Player player = event.getEntity();
		if (player.getAbilities().instabuild)
			return;
		player.getPersistentData().putInt(Strings.Tags.TIME_SINCE_LAST_SWING, player.attackStrengthTicker);
	}

	@SubscribeEvent
	public void onKnockback(LivingKnockBackEvent event) {
		if (!this.isEnabled())
			return;
		LivingEntity attacker = event.getEntity().getKillCredit();
		if (!(attacker instanceof Player player))
			return;
		if (player.getAbilities().instabuild)
			return;
		CombatEntry combatEntry = event.getEntity().getCombatTracker().getLastEntry();
		if (combatEntry == null || !(combatEntry.getSource().getDirectEntity() instanceof Player))
			return;
		ItemStack itemStack = player.getMainHandItem();

		TagKey<Item> tagKey = TagKey.create(Registry.ITEM_REGISTRY, NO_KNOCKBACK_TAG);
		boolean isInTag = ForgeRegistries.ITEMS.tags().getTag(tagKey).contains(itemStack.getItem());

		boolean preventKnockback = false;
		Multimap<Attribute, AttributeModifier> attributeModifiers = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);
		if ((!attributeModifiers.containsKey(Attributes.ATTACK_DAMAGE) && noItemNoKnockback) || isInTag) {
			preventKnockback = true;
		}
		int ticksSinceLastSwing = player.getPersistentData().getInt(Strings.Tags.TIME_SINCE_LAST_SWING);
		float cooldown = Mth.clamp((ticksSinceLastSwing + 0.5f) / player.getCurrentItemAttackStrengthDelay(), 0.0F, 1.0F);
		if (cooldown <= attackCooldownNoKnockback)
			preventKnockback = true;
		if (preventKnockback)
			event.setCanceled(true);
	}
}