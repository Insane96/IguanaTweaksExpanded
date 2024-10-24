package insane96mcp.iguanatweaksexpanded.module.items.solarium;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.integration.BuzzierBeesIntegration;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.item.*;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.module.combat.RegeneratingAbsorption;
import insane96mcp.iguanatweaksreborn.module.sleeprespawn.death.integration.ToolBelt;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.item.ILItemTier;
import insane96mcp.insanelib.util.MCUtils;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

@Label(name = "Solarium", description = "Add Solarium, a new material made by alloying Overgrown solium moss ball (found in hot biomes) and can be used to upgrade Iron Equipment")
@LoadFeature(module = Modules.Ids.ITEMS, canBeDisabled = false)
public class Solarium extends Feature {
	public static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("c9c18638-6505-4544-9871-6397916fd0b7");
	public static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("435317e9-0146-4f1b-bc21-67f466ee5f9c");

	public static final TagKey<Item> SOLARIUM_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "equipment/solarium"));
	public static final TagKey<Item> SOLARIUM_HAND_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "equipment/hand/solarium"));

	public static final SimpleBlockWithItem SOLIUM_MOSS = SimpleBlockWithItem.register("solium_moss", () -> new SoliumMossBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).pushReaction(PushReaction.DESTROY).noCollission().strength(0.4F).sound(SoundType.GLOW_LICHEN).lightLevel(GlowLichenBlock.emission(9)).randomTicks()));
	public static final RegistryObject<Item> SOLARIUM_BALL = ITERegistries.ITEMS.register("solarium_ball", () -> new Item(new Item.Properties()));

	public static final ILItemTier ITEM_TIER = new ILItemTier(2, 207, 5f, 1f, 11, () -> Ingredient.of(SOLARIUM_BALL.get()));

	public static final RegistryObject<Item> SWORD = ITERegistries.ITEMS.register("solarium_sword", () -> new SolariumSwordItem(3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> SHOVEL = ITERegistries.ITEMS.register("solarium_shovel", () -> new SolariumShovelItem(1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> PICKAXE = ITERegistries.ITEMS.register("solarium_pickaxe", () -> new SolariumPickaxeItem(1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> AXE = ITERegistries.ITEMS.register("solarium_axe", () -> new SolariumAxeItem(5.5F, -3.2F, new Item.Properties()));
	public static final RegistryObject<Item> HOE = ITERegistries.ITEMS.register("solarium_hoe", () -> new SolariumHoeItem(0, -1.0F, new Item.Properties()));

	public static final RegistryObject<Item> HELMET = ITERegistries.ITEMS.register("solarium_helmet", () -> new SolariumArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> CHESTPLATE = ITERegistries.ITEMS.register("solarium_chestplate", () -> new SolariumArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> LEGGINGS = ITERegistries.ITEMS.register("solarium_leggings", () -> new SolariumArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> BOOTS = ITERegistries.ITEMS.register("solarium_boots", () -> new SolariumArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

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
		event.getToolTip().add(Component.translatable(IguanaTweaksExpanded.RESOURCE_PREFIX + "innate_solarium").withStyle(ChatFormatting.GREEN));
	}

	public static void healGear(ItemStack stack, Entity entity, Level level) {
		if (level.isClientSide
				|| entity.tickCount % 60 != 22)
			return;

		float chance = getCalculatedSkyLightRatio(entity);
        if (chance <= 0f
				|| level.random.nextFloat() >= chance)
			return;
        stack.setDamageValue(stack.getDamageValue() - 1);
	}

	@SubscribeEvent
	public void onLivingTick(LivingEvent.LivingTickEvent event) {
		armorBoost(event);

		//Move if any other item needs toolbelt ticking
		if (ModList.get().isLoaded("toolbelt"))
			ToolBelt.tryTickItemsIn(event.getEntity());
	}

	public static void armorBoost(LivingEvent.LivingTickEvent event) {
		if (event.getEntity().tickCount % 2 != 1)
			return;

		Attribute attr = RegeneratingAbsorption.SPEED_ATTRIBUTE.get();
		/*boolean isRegenAbsorption = isEnabled(AbsorptionArmor.class);
		if (isRegenAbsorption)
			attr = RegeneratingAbsorption.SPEED_ATTRIBUTE.get();*/
		AttributeInstance attributeInstance = event.getEntity().getAttribute(attr);
		if (attributeInstance == null)
			return;

		float calculatedSkyLightRatio = getCalculatedSkyLightRatio(event.getEntity());
		float amount = 0f;
		for (ItemStack stack : event.getEntity().getArmorSlots()) {
			if (!stack.is(SOLARIUM_EQUIPMENT))
				continue;
			amount += 0.05f;
		}
		amount *= calculatedSkyLightRatio;
		AttributeModifier modifier = attributeInstance.getModifier(ARMOR_MODIFIER_UUID);
		if (modifier != null && modifier.getAmount() != amount)
			attributeInstance.removeModifier(ARMOR_MODIFIER_UUID);
		if (amount > 0f)
			MCUtils.applyModifier(event.getEntity(), attr, ARMOR_MODIFIER_UUID, "Solarium boost", amount, AttributeModifier.Operation.ADDITION, false);
	}

	@SubscribeEvent
	public void boostAD(LivingHurtEvent event) {
		if (!(event.getSource().getEntity() instanceof LivingEntity entity)
				|| !entity.getMainHandItem().is(SOLARIUM_EQUIPMENT))
			return;
		float calculatedSkyLightRatio = getCalculatedSkyLightRatio(event.getEntity());
		if (calculatedSkyLightRatio <= 0f)
			return;
		event.setAmount(event.getAmount() * (1 + 0.2f * calculatedSkyLightRatio));
	}

	@SubscribeEvent
	public void boostMiningSpeed(PlayerEvent.BreakSpeed event) {
		/*if (!event.getState().requiresCorrectToolForDrops())
			event.setNewSpeed(event.getNewSpeed() + EnchantmentsFeature.applyMiningSpeedModifiers(0.5f, true, event.getEntity()));*/
		if (!event.getEntity().getMainHandItem().is(SOLARIUM_EQUIPMENT)
				|| !event.getEntity().getMainHandItem().isCorrectToolForDrops(event.getState()))
			return;
		float calculatedSkyLightRatio = getCalculatedSkyLightRatio(event.getEntity());
		if (calculatedSkyLightRatio <= 0f)
			return;
		event.setNewSpeed(event.getNewSpeed() * (1 + (calculatedSkyLightRatio * 0.5f)));
	}

	public static float getCalculatedSkyLight(Entity entity) {
		float calculatedSkyLight = getCalculatedSkyLight(entity.level(), entity.blockPosition());
		if (ModList.get().isLoaded("buzzier_bees") && BuzzierBeesIntegration.hasSunny(entity))
			calculatedSkyLight = 15f;
		return calculatedSkyLight;
	}

	private static float getCalculatedSkyLight(Level level, BlockPos pos) {
		if (!level.isDay()
				|| level.isThundering())
			return 0f;
		float skyLight = level.getBrightness(LightLayer.SKY, pos) - level.getSkyDarken();
		if (level.isRaining())
			skyLight /= 3f;
		return skyLight;
	}

	/**
	 * Returns a value between 0 and 1 where 0 is total darkness and 1 is 15 light level
	 */
	public static float getCalculatedSkyLightRatio(Entity entity) {
        return Math.min(getCalculatedSkyLight(entity), 12f) / 12f;
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