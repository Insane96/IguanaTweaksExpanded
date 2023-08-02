package insane96mcp.survivalreimagined.module.items.feature;

import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.item.ILItemTier;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import insane96mcp.survivalreimagined.SurvivalReimagined;
import insane96mcp.survivalreimagined.module.Modules;
import insane96mcp.survivalreimagined.module.combat.feature.PiercingPickaxes;
import insane96mcp.survivalreimagined.network.ElectrocutionParticleMessage;
import insane96mcp.survivalreimagined.network.NetworkHandler;
import insane96mcp.survivalreimagined.setup.SRItems;
import insane96mcp.survivalreimagined.setup.SRSoundEvents;
import insane96mcp.survivalreimagined.utils.MCUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

@Label(name = "Copper Tools Extension", description = "Two new set of tools")
@LoadFeature(module = Modules.Ids.ITEMS)
public class CopperToolsExtension extends Feature {
	public static final TagKey<Item> COATED_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(SurvivalReimagined.MOD_ID, "equipment/coated_copper"));

	public static final ILItemTier COPPER_ITEM_TIER = new ILItemTier(1, 143, 8f, 1.0f, 9, () -> Ingredient.of(Items.COPPER_INGOT));

	public static final RegistryObject<Item> COPPER_SWORD = SRItems.REGISTRY.register("copper_sword", () -> new SwordItem(COPPER_ITEM_TIER, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> COPPER_SHOVEL = SRItems.REGISTRY.register("copper_shovel", () -> new ShovelItem(COPPER_ITEM_TIER, 1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> COPPER_PICKAXE = SRItems.REGISTRY.register("copper_pickaxe", () -> new PickaxeItem(COPPER_ITEM_TIER, 1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> COPPER_AXE = SRItems.REGISTRY.register("copper_axe", () -> new AxeItem(COPPER_ITEM_TIER, 7.0F, -3.1F, new Item.Properties()));
	public static final RegistryObject<Item> COPPER_HOE = SRItems.REGISTRY.register("copper_hoe", () -> new HoeItem(COPPER_ITEM_TIER, -1, -2.0F, new Item.Properties()));

	public static final ILItemTier COATED_ITEM_TIER = new ILItemTier(3, 321, 9f, 1.5f, 5, () -> Ingredient.of(Items.OBSIDIAN));
	public static final RegistryObject<Item> COATED_SWORD = SRItems.REGISTRY.register("coated_copper_sword", () -> new SwordItem(COATED_ITEM_TIER, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> COATED_SHOVEL = SRItems.REGISTRY.register("coated_copper_shovel", () -> new ShovelItem(COATED_ITEM_TIER, 1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> COATED_PICKAXE = SRItems.REGISTRY.register("coated_copper_pickaxe", () -> new PickaxeItem(COATED_ITEM_TIER, 1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> COATED_AXE = SRItems.REGISTRY.register("coated_copper_axe", () -> new AxeItem(COATED_ITEM_TIER, 7.0F, -3.1F, new Item.Properties()));
	public static final RegistryObject<Item> COATED_HOE = SRItems.REGISTRY.register("coated_copper_hoe", () -> new HoeItem(COATED_ITEM_TIER, -1, -2.0F, new Item.Properties()));

	public static final SPShieldMaterial COATED_SHIELD_MATERIAL = new SPShieldMaterial("coated_copper", 5.5d, 184, () -> Items.OBSIDIAN, 5, Rarity.COMMON);

	public static final RegistryObject<SPShieldItem> COATED_SHIELD = SRItems.registerShield("coated_copper_shield", COATED_SHIELD_MATERIAL);

	public CopperToolsExtension(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	public static final String COATED_TIMES_HIT = SurvivalReimagined.RESOURCE_PREFIX + "coated_times_hit";
	public static ResourceKey<DamageType> ELECTROCUTION_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(SurvivalReimagined.MOD_ID, "electrocution_attack"));

	@SubscribeEvent
	public void onAttack(LivingDamageEvent event) {
		if (!this.isEnabled()
				|| event.getSource().is(ELECTROCUTION_ATTACK)
				|| event.getSource().is(PiercingPickaxes.PIERCING_MOB_ATTACK)
				|| event.getSource().is(PiercingPickaxes.PIERCING_PLAYER_ATTACK)
				|| !(event.getSource().getEntity() instanceof Player player)
				|| !(event.getSource().getDirectEntity() instanceof Player)
				|| player.getAttackStrengthScale(1f) < 0.9f
				|| !(player.getMainHandItem().getItem() instanceof TieredItem tieredItem)
				|| tieredItem.getTier() != COATED_ITEM_TIER)
			return;

		CompoundTag tag = player.getMainHandItem().getOrCreateTag();
		if (!tag.contains(COATED_TIMES_HIT))
			tag.putInt(COATED_TIMES_HIT, 0);

		int hits = tag.getInt(COATED_TIMES_HIT);
		if (++hits >= 4) {
			hits = 0;
			electrocute(player, event.getEntity());
		}
		tag.putInt(COATED_TIMES_HIT, hits);
	}

	private void electrocute(Player attacker, LivingEntity attacked) {
		DamageSource damageSource = attacker.damageSources().source(ELECTROCUTION_ATTACK, attacker);
		double range = 4.5d;
		float secondaryDamage = (float) (1.5f * attacker.getAttributeValue(Attributes.ATTACK_DAMAGE));
		int hitEntities = 0;
		IntList listIdsOfHitEntities = new IntArrayList();
		List<LivingEntity> listOfHitEntities = new ArrayList<>();
		//Add the player to the list, so it doesn't get targeted
		listOfHitEntities.add(attacker);
		Entity lastEntityHit = attacked;
		do {
			List<LivingEntity> entitiesOfClass = attacker.level().getEntitiesOfClass(LivingEntity.class, lastEntityHit.getBoundingBox().inflate(range),
					livingEntity -> (attacker.canAttack(livingEntity)
									|| (livingEntity instanceof Player && attacker.canHarmPlayer((Player) livingEntity))) && !livingEntity.isDeadOrDying());
			LivingEntity target = MCUtils.getNearestEntity(entitiesOfClass, listOfHitEntities, attacked.position());
			if (target == null)
				break;
			listOfHitEntities.add(target);
			MCUtils.attackEntityIgnoreInvFrames(damageSource, secondaryDamage, target, attacker, true);
			listIdsOfHitEntities.add(target.getId());
			target.playSound(SRSoundEvents.ELECTROCUTION.get(), 0.4f, 1.0f);
			lastEntityHit = target;
			hitEntities++;
		} while (hitEntities < 4);

		Object msg = new ElectrocutionParticleMessage(listIdsOfHitEntities);
		for (Player levelPlayer : attacker.level().players()) {
			NetworkHandler.CHANNEL.sendTo(msg, ((ServerPlayer) levelPlayer).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onTooltip(ItemTooltipEvent event) {
		if (!this.isEnabled()
				|| !event.getItemStack().is(COATED_EQUIPMENT)
				|| event.getEntity() == null)
			return;

		int hits = event.getItemStack().getOrCreateTag().getInt(COATED_TIMES_HIT);
		//event.getToolTip().add(Component.translatable("survivalreimagined.electrocution.damage", event.getEntity().getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5f).withStyle(ChatFormatting.DARK_GREEN));
		event.getToolTip().add(Component.translatable("survivalreimagined.electrocution.charge", Math.round(hits / 3f * 100f)).withStyle(ChatFormatting.DARK_GRAY));
	}
}