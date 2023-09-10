package insane96mcp.survivalreimagined.module.items.solarium;

import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.item.ILItemTier;
import insane96mcp.insanelib.util.MCUtils;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.survivalreimagined.SurvivalReimagined;
import insane96mcp.survivalreimagined.base.SimpleBlockWithItem;
import insane96mcp.survivalreimagined.event.HurtItemStackEvent;
import insane96mcp.survivalreimagined.module.Modules;
import insane96mcp.survivalreimagined.module.items.solarium.item.*;
import insane96mcp.survivalreimagined.module.sleeprespawn.death.integration.ToolBelt;
import insane96mcp.survivalreimagined.setup.SRRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

@Label(name = "Solarium", description = "Add Solarium, a new metal made by alloying Overgrown solium moss ball (found in hot biomes) and can be used to upgrade Iron Equipment")
@LoadFeature(module = Modules.Ids.ITEMS)
public class Solarium extends Feature {
	public static final UUID[] MOVEMENT_SPEED_MODIFIER_UUIDS = new UUID[] {
			UUID.fromString("c9c18638-6505-4544-9871-6397916fd0b7"),
			UUID.fromString("64ccd03d-2fd8-4cd7-b395-e334bd1c9f5d"),
			UUID.fromString("6ae004b1-404e-495b-bfae-85d7a3aefea6"),
			UUID.fromString("7f5e7b07-e1b4-452d-82b9-105ada7d3238")
	};

	public static final TagKey<Item> SOLARIUM_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(SurvivalReimagined.MOD_ID, "equipment/solarium"));

	public static final SimpleBlockWithItem SOLIUM_MOSS = SimpleBlockWithItem.register("solium_moss", () -> new SoliumMossBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).pushReaction(PushReaction.DESTROY).noCollission().strength(0.4F).sound(SoundType.GLOW_LICHEN).lightLevel(GlowLichenBlock.emission(9)).randomTicks()));
	public static final RegistryObject<Item> SOLARIUM_BALL = SRRegistries.ITEMS.register("solarium_ball", () -> new Item(new Item.Properties()));

	public static final ILItemTier ITEM_TIER = new ILItemTier(2, 207, 5.5f, 1.5f, 12, () -> Ingredient.of(SOLARIUM_BALL.get()));

	public static final RegistryObject<Item> SWORD = SRRegistries.ITEMS.register("solarium_sword", () -> new SolariumSwordItem(3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> SHOVEL = SRRegistries.ITEMS.register("solarium_shovel", () -> new SolariumShovelItem(1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> PICKAXE = SRRegistries.ITEMS.register("solarium_pickaxe", () -> new SolariumPickaxeItem(1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> AXE = SRRegistries.ITEMS.register("solarium_axe", () -> new SolariumAxeItem(5.5F, -3.2F, new Item.Properties()));
	public static final RegistryObject<Item> HOE = SRRegistries.ITEMS.register("solarium_hoe", () -> new SolariumHoeItem(-2, -1.0F, new Item.Properties()));

	public static final RegistryObject<Item> HELMET = SRRegistries.ITEMS.register("solarium_helmet", () -> new SolariumArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> CHESTPLATE = SRRegistries.ITEMS.register("solarium_chestplate", () -> new SolariumArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> LEGGINGS = SRRegistries.ITEMS.register("solarium_leggings", () -> new SolariumArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> BOOTS = SRRegistries.ITEMS.register("solarium_boots", () -> new SolariumArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final RegistryObject<SPShieldItem> SHIELD = SolariumShield.registerShield("solarium_shield");

	public Solarium(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
	}

	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent event) {
		if (!this.isEnabled()
				|| !event.getItemStack().is(SOLARIUM_EQUIPMENT))
			return;

		event.getToolTip().add(Component.empty());
		event.getToolTip().add(Component.translatable("survivalreimagined:innate_solarium").withStyle(ChatFormatting.GREEN));
	}

	@SubscribeEvent
	public void onHurtItemStack(HurtItemStackEvent event) {
		if (!this.isEnabled()
				|| !event.getStack().is(SOLARIUM_EQUIPMENT)
				|| event.getPlayer() == null)
			return;

		int amount = event.getAmount();
		int newAmount = 0;
		float calculatedSkyLight = getCalculatedSkyLight(event.getPlayer());
		if (calculatedSkyLight <= 0f)
			return;
		for (int i = 0; i < amount; i++) {
			//5% per light level (75% at 15)
			if (event.getRandom().nextFloat() >= calculatedSkyLight * 0.05f)
				++newAmount;
		}
		event.setAmount(newAmount);
	}

	public static void healGear(ItemStack stack, Entity entity, Level level) {
		if (level.isClientSide
				|| entity.tickCount % 200 != 22)
			return;

		float chance = getCalculatedSkyLight(entity) * 0.10f;
		if (chance <= 0f)
			return;
		if (level.random.nextFloat() >= chance)
			return;
		stack.setDamageValue(stack.getDamageValue() - 1);
	}

	@SubscribeEvent
	public void onLivingTick(LivingEvent.LivingTickEvent event) {
		boostSpeed(event);

		//TODO Move if any other item needs toolbelt ticking
		if (ModList.get().isLoaded("toolbelt"))
			ToolBelt.tryTickItemsIn(event.getEntity());
	}

	public static void boostSpeed(LivingEvent.LivingTickEvent event) {
		if (event.getEntity().tickCount % 2 != 1)
			return;

		float calculatedSkyLight = getCalculatedSkyLightRatio(event.getEntity());
		if (calculatedSkyLight <= 0f)
			return;
		for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
			if (!equipmentSlot.isArmor())
				continue;
			ItemStack stack = event.getEntity().getItemBySlot(equipmentSlot);
			AttributeInstance movSpeed = event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED);
			if (stack.is(SOLARIUM_EQUIPMENT))
				MCUtils.applyModifier(event.getEntity(), Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER_UUIDS[equipmentSlot.getIndex()], "Solarium movement speed boost", 0.1f * calculatedSkyLight, AttributeModifier.Operation.MULTIPLY_BASE, false);
			else if (movSpeed != null && movSpeed.getModifier(MOVEMENT_SPEED_MODIFIER_UUIDS[equipmentSlot.getIndex()]) != null)
				movSpeed.removeModifier(MOVEMENT_SPEED_MODIFIER_UUIDS[equipmentSlot.getIndex()]);
		}
	}

	@SubscribeEvent
	public void boostAD(LivingHurtEvent event) {
		if (!(event.getSource().getEntity() instanceof LivingEntity entity)
				|| !entity.getMainHandItem().is(SOLARIUM_EQUIPMENT))
			return;
		float calculatedSkyLight = getCalculatedSkyLightRatio(event.getEntity());
		if (calculatedSkyLight <= 0f)
			return;
		event.setAmount(event.getAmount() * (1 + 0.25f * calculatedSkyLight));
	}

	@SubscribeEvent
	public void boostMiningSpeed(PlayerEvent.BreakSpeed event) {
		if (!event.getEntity().getMainHandItem().is(SOLARIUM_EQUIPMENT)
				|| !event.getEntity().getMainHandItem().isCorrectToolForDrops(event.getState()))
			return;
		float calculatedSkyLight = getCalculatedSkyLightRatio(event.getEntity());
		if (calculatedSkyLight <= 0f)
			return;
		event.setNewSpeed(event.getOriginalSpeed() * (1 + 0.75f * calculatedSkyLight));
	}

	public static float getCalculatedSkyLight(Entity entity) {
		Level level = entity.level();
		if (level.getDayTime() % 24000 > 12542
				|| level.isThundering())
			return 0f;
		float skyLight = level.getBrightness(LightLayer.SKY, entity.blockPosition());
		if (level.isRaining())
			skyLight /= 3f;
		return skyLight;
	}

	public static float getCalculatedSkyLightRatio(Entity entity) {
		return getCalculatedSkyLight(entity) / 15f;
	}

	@SubscribeEvent
	public void onBlockBreak(PlayerEvent.BreakSpeed event) {
		if (!this.isEnabled()
				|| !event.getState().is(SOLIUM_MOSS.block().get())
				|| !(event.getEntity().getMainHandItem().getItem() instanceof ShearsItem))
			return;

		event.setNewSpeed(event.getOriginalSpeed() * 5f);
	}
}