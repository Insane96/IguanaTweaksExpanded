package insane96mcp.iguanatweaksexpanded.module.items.copper;

import insane96mcp.iguanatweaksexpanded.InsaneSurvivalExtra;
import insane96mcp.iguanatweaksexpanded.data.generator.ISEDamageTypeTagsProvider;
import insane96mcp.iguanatweaksexpanded.integration.ShieldsPlusRegistration;
import insane96mcp.iguanatweaksexpanded.item.ISEArmorMaterial;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ISEDataPacks;
import insane96mcp.iguanatweaksexpanded.network.message.ElectrocutionParticleMessage;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksreborn.utils.MCUtils;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.event.HurtItemStackEvent;
import insane96mcp.insanelib.item.ILItemTier;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.Util;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

@Label(name = "Copper Tools & Armor Expansion", description = "Two new set of tools and a new Armor Set. Disabling this will prevent copper tools from being faster and more durable the deeper are used and will prevent the electrocution effect of coated copper items. Disabling this will disable ore generation and items in the creative inventory.")
@LoadFeature(module = Modules.Ids.ITEMS)
public class CopperExpansion extends Feature {
	public static final TagKey<Item> COPPER_TOOLS_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "equipment/hand/tools/copper"));
	public static final TagKey<Item> COPPER_UNBREAKING_BONUS = TagKey.create(Registries.ITEM, new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "copper_unbreaking_bonus"));
	public static final TagKey<Item> COATED_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "equipment/coated_copper"));

	public static final ILItemTier COPPER_ITEM_TIER = new ILItemTier(0, 65, 8f, 1.0f, 11, () -> Ingredient.of(Items.COPPER_INGOT));

	public static final RegistryObject<Item> COPPER_SWORD = ISERegistries.ITEMS.register("copper_sword", () -> new SwordItem(COPPER_ITEM_TIER, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> COPPER_SHOVEL = ISERegistries.ITEMS.register("copper_shovel", () -> new ShovelItem(COPPER_ITEM_TIER, 1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> COPPER_PICKAXE = ISERegistries.ITEMS.register("copper_pickaxe", () -> new PickaxeItem(COPPER_ITEM_TIER, 1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> COPPER_AXE = ISERegistries.ITEMS.register("copper_axe", () -> new AxeItem(COPPER_ITEM_TIER, 7.0F, -3.1F, new Item.Properties()));
	public static final RegistryObject<Item> COPPER_HOE = ISERegistries.ITEMS.register("copper_hoe", () -> new HoeItem(COPPER_ITEM_TIER, -1, -2.0F, new Item.Properties()));

	public static final ILItemTier COATED_ITEM_TIER = new ILItemTier(3, 170, 7f, 1.5f, 5, () -> Ingredient.of(Items.OBSIDIAN));
	public static final RegistryObject<Item> COATED_SWORD = ISERegistries.ITEMS.register("coated_copper_sword", () -> new SwordItem(COATED_ITEM_TIER, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> COATED_SHOVEL = ISERegistries.ITEMS.register("coated_copper_shovel", () -> new ShovelItem(COATED_ITEM_TIER, 1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> COATED_PICKAXE = ISERegistries.ITEMS.register("coated_copper_pickaxe", () -> new PickaxeItem(COATED_ITEM_TIER, 1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> COATED_AXE = ISERegistries.ITEMS.register("coated_copper_axe", () -> new AxeItem(COATED_ITEM_TIER, 7.0F, -3.1F, new Item.Properties()));
	public static final RegistryObject<Item> COATED_HOE = ISERegistries.ITEMS.register("coated_copper_hoe", () -> new HoeItem(COATED_ITEM_TIER, -1, -2.0F, new Item.Properties()));

    public static final RegistryObject<SimpleParticleType> ELECTROCUTION_SPARKS = ISERegistries.PARTICLE_TYPES.register("electrocution_sparks", () -> new SimpleParticleType(true));
	public static final RegistryObject<SoundEvent> ELECTROCUTION = ISERegistries.SOUND_EVENTS.register("electrocution", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "electrocution")));
	public static final TagKey<DamageType> DOESNT_TRIGGER_ELECTROCUTION = ISEDamageTypeTagsProvider.create("doesnt_trigger_electrocution");


	private static final ISEArmorMaterial CHAINED_COPPER = new ISEArmorMaterial(InsaneSurvivalExtra.RESOURCE_PREFIX + "chained_copper", 10, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 1);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 3);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 4);
		p_266652_.put(ArmorItem.Type.HELMET, 1);
	}), 13, SoundEvents.ARMOR_EQUIP_CHAIN, 0f, 0f, () -> Ingredient.of(Items.COPPER_INGOT));

	public static final RegistryObject<Item> BOOTS = ISERegistries.ITEMS.register("chained_copper_boots", () -> new ArmorItem(CHAINED_COPPER, ArmorItem.Type.BOOTS, new Item.Properties()));
	public static final RegistryObject<Item> LEGGINGS = ISERegistries.ITEMS.register("chained_copper_leggings", () -> new ArmorItem(CHAINED_COPPER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> CHESTPLATE = ISERegistries.ITEMS.register("chained_copper_chestplate", () -> new ArmorItem(CHAINED_COPPER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> HELMET = ISERegistries.ITEMS.register("chained_copper_helmet", () -> new ArmorItem(CHAINED_COPPER, ArmorItem.Type.HELMET, new Item.Properties()));

	@Config
	@Label(name = "Deep Bonus Sea Level", description = "The sea level + this at which the deep bonus starts to apply")
	public static Integer deepBonusSeaLevelRelative = 16;

	public CopperExpansion(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		InsaneSurvivalExtra.addServerPack("copper_expansion", "Insane's Survival Extra Copper Expansion", () -> this.isEnabled() && !ISEDataPacks.disableAllDataPacks);
	}

	public static final String COATED_TIMES_HIT = InsaneSurvivalExtra.RESOURCE_PREFIX + "coated_times_hit";
	public static ResourceKey<DamageType> ELECTROCUTION_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(InsaneSurvivalExtra.MOD_ID, "electrocution_attack"));

	@SubscribeEvent
	public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		if (!this.isEnabled()
				|| !event.getEntity().getMainHandItem().is(COPPER_TOOLS_EQUIPMENT)
				|| !event.getEntity().getMainHandItem().isCorrectToolForDrops(event.getState()))
			return;

		Level level = event.getEntity().level();
		if (!level.dimension().equals(Level.OVERWORLD))
			return;

		int y = getNormalizedY(event.getEntity().getBlockY(), level);
		if (y < 0)
			return;
		event.setNewSpeed((event.getNewSpeed() + getBonusMiningSpeed(y)));
	}

	public static float getBonusMiningSpeed(int normalizedY) {
		return (float) (0.24f * Math.pow(normalizedY, 0.655f));
	}

	@SubscribeEvent
	public void onHurtItemStack(HurtItemStackEvent event) {
		if (!this.isEnabled()
				|| (!event.getStack().is(COPPER_UNBREAKING_BONUS))
				|| event.getPlayer() == null)
			return;

		Level level = event.getEntity().level();
		if (!level.dimension().equals(Level.OVERWORLD))
			return;

		int amount = event.getAmount();
		int newAmount = 0;
		int y = getNormalizedY(event.getEntity().getBlockY(), level);
		if (y < 0)
			return;
		double chance = 1 - 1 / (1 + getBonusUnbreakability(y));
		for (int i = 0; i < amount; i++) {
			if (event.getRandom().nextFloat() >= chance)
				++newAmount;
		}
		event.setAmount(newAmount);
	}

	public static float getBonusUnbreakability(int normalizedY) {
		return (float) (0.37f * Math.pow(normalizedY, 0.67f));
	}

	public static int getNormalizedY(int y, Level level) {
		int startingY = level.getSeaLevel() + deepBonusSeaLevelRelative;
		if (y > startingY)
			return -1;
		//Normalize y to go from 0 at sea level + deepBonusSeaLevelRelative
		return (y - startingY) * -1;
	}

	@SubscribeEvent
	public void onAttack(LivingHurtEvent event) {
		if (!this.isEnabled()
				|| event.getSource().is(DOESNT_TRIGGER_ELECTROCUTION)
				|| !(event.getSource().getDirectEntity() instanceof Player player)
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
			electrocute(player, event.getEntity(), (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
			if (event.getEntity().isDeadOrDying())
				event.setCanceled(true);
		}
		tag.putInt(COATED_TIMES_HIT, hits);
	}

	@SubscribeEvent
	public void onParry(ShieldBlockEvent event) {
		if (!ModList.get().isLoaded("shieldplus")
				|| !this.isEnabled()
				|| !(event.getEntity() instanceof Player player)
				|| !(event.getDamageSource().getDirectEntity() instanceof LivingEntity attacker)
				|| !(player.getUseItem().getItem() instanceof SPShieldItem spShieldItem)
				|| !player.getUseItem().is(ShieldsPlusIntegration.COATED_SHIELD.get()))
			return;

		CompoundTag tag = player.getUseItem().getOrCreateTag();
		if (!tag.contains(COATED_TIMES_HIT))
			tag.putInt(COATED_TIMES_HIT, 0);

		int hits = tag.getInt(COATED_TIMES_HIT);
		if (++hits >= 4) {
			hits = 0;
			electrocute(player, attacker, spShieldItem.getBlockedDamage(player.getUseItem(), attacker, player.level()));
		}
		tag.putInt(COATED_TIMES_HIT, hits);
	}

	private void electrocute(Player electrocuter, LivingEntity attacked, float damage) {
		DamageSource damageSource = electrocuter.damageSources().source(ELECTROCUTION_ATTACK, electrocuter);
		double range = 4.5d;
		ItemStack useItem = electrocuter.getUseItem();
		int hitEntities = 0;
		IntList listIdsOfHitEntities = new IntArrayList();
		List<LivingEntity> listOfHitEntities = new ArrayList<>();
		//Add the player to the list, so it doesn't get targeted
		listOfHitEntities.add(electrocuter);
		Entity lastEntityHit = attacked;
		do {
			List<LivingEntity> entitiesOfClass = electrocuter.level().getEntitiesOfClass(LivingEntity.class, lastEntityHit.getBoundingBox().inflate(range),
					livingEntity -> electrocuter.canAttack(livingEntity) && !livingEntity.isDeadOrDying() && !electrocuter.isAlliedTo(livingEntity) && (!(livingEntity instanceof ArmorStand) || !((ArmorStand)livingEntity).isMarker()));
			LivingEntity target = MCUtils.getNearestEntity(entitiesOfClass, listOfHitEntities, attacked.position());
			if (target == null)
				break;
			listOfHitEntities.add(target);
			MCUtils.attackEntityIgnoreInvFrames(damageSource, damage, target, target, true);
			listIdsOfHitEntities.add(target.getId());
			target.playSound(ELECTROCUTION.get(), 0.4f, 1.0f);
			lastEntityHit = target;
			hitEntities++;
		} while (hitEntities < 4);

		if (electrocuter.level() instanceof ServerLevel serverLevel)
			ElectrocutionParticleMessage.sync(serverLevel, listIdsOfHitEntities);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onTooltip(ItemTooltipEvent event) {
		if (!this.isEnabled()
				|| event.getEntity() == null)
			return;

		if (event.getItemStack().is(COPPER_TOOLS_EQUIPMENT)) {
			float bonus = getBonusMiningSpeed(getNormalizedY(event.getEntity().getBlockY(), event.getEntity().level()));
			if (bonus > 0)
				event.getToolTip().add(Component.translatable("iguanatweaksexpanded.copper.depth_mining_speed", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(bonus)).withStyle(Style.EMPTY.withColor(0xD1890D)));
		}
		if (event.getItemStack().is(COPPER_UNBREAKING_BONUS)) {
			float bonus = Math.round(getBonusUnbreakability(getNormalizedY(event.getEntity().getBlockY(), event.getEntity().level())) * 10f) / 10f;
			if (bonus > 0)
				event.getToolTip().add(Component.translatable("iguanatweaksexpanded.copper.depth_unbreaking", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(1f + bonus)).withStyle(Style.EMPTY.withColor(0xD1890D)));
		}
		if (event.getItemStack().is(COATED_EQUIPMENT)) {
			int hits = event.getItemStack().getOrCreateTag().getInt(COATED_TIMES_HIT);
			event.getToolTip().add(Component.translatable("iguanatweaksexpanded.electrocution.charge", Math.round(hits / 3f * 100f)).withStyle(Style.EMPTY.withColor(0x4624a6)));
		}
	}

	public static class ShieldsPlusIntegration {
		public static final RegistryObject<SPShieldItem> COPPER_SHIELD = CopperShield.registerShield("copper_shield");

		public static final SPShieldMaterial COATED_SHIELD_MATERIAL = new SPShieldMaterial("coated_copper", 184, () -> Items.OBSIDIAN, 5, Rarity.COMMON);
		public static final RegistryObject<SPShieldItem> COATED_SHIELD = ShieldsPlusRegistration.registerShield("coated_copper_shield", COATED_SHIELD_MATERIAL);

		public static void init() {

		}
	}
}