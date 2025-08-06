package insane96mcp.iguanatweaksexpanded.module.mining.keego;

import insane96mcp.iguanatweaksexpanded.InsaneSE;
import insane96mcp.iguanatweaksexpanded.item.ISEArmorMaterial;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksexpanded.module.misc.ISEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.item.ILItemTier;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicInteger;

@LoadFeature(module = Modules.Ids.MINING, description = "Add a new Nether gem which makes lets you go fast (KEEp GOing). Disabling this will disable ore generation and items in the creative inventory.", enabledByDefault = false)
public class Keego extends Feature {

	public static final TagKey<Item> KEEGO_TOOL_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(InsaneSE.MOD_ID, "equipment/hand/tools/keego"));
	public static final TagKey<Item> KEEGO_HAND_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(InsaneSE.MOD_ID, "equipment/hand/keego"));
	public static final TagKey<Item> KEEGO_ARMOR_EQUIPMENT = TagKey.create(Registries.ITEM, new ResourceLocation(InsaneSE.MOD_ID, "equipment/armor/keego"));

    public static final SimpleBlockWithItem ORE = SimpleBlockWithItem.register("keego_ore", () -> new KeegoOreBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).strength(-1f, 10f), UniformInt.of(10, 15)));

	public static final SimpleBlockWithItem BLOCK = SimpleBlockWithItem.register("keego_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 7.0F).sound(SoundType.METAL)));

	public static final RegistryObject<Item> GEM = ISERegistries.ITEMS.register("keego", () -> new Item(new Item.Properties()));

	public static final ILItemTier ITEM_TIER = new ILItemTier(2, 937, 6.5f, 2.5f, 8, () -> Ingredient.of(GEM.get()));

	public static final RegistryObject<Item> SWORD = ISERegistries.ITEMS.register("keego_sword", () -> new SwordItem(ITEM_TIER, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> SHOVEL = ISERegistries.ITEMS.register("keego_shovel", () -> new ShovelItem(ITEM_TIER, 1.5F, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> PICKAXE = ISERegistries.ITEMS.register("keego_pickaxe", () -> new PickaxeItem(ITEM_TIER, 1, -2.8F, new Item.Properties()));
	public static final RegistryObject<Item> AXE = ISERegistries.ITEMS.register("keego_axe", () -> new AxeItem(ITEM_TIER, 6.0F, -3.2F, new Item.Properties()));
	public static final RegistryObject<Item> HOE = ISERegistries.ITEMS.register("keego_hoe", () -> new KeegoHoeItem(ITEM_TIER, -2, -1.1F, new Item.Properties()));

	private static final ISEArmorMaterial ARMOR_MATERIAL = new ISEArmorMaterial(InsaneSE.RESOURCE_PREFIX + "keego", 22, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266652_) -> {
		p_266652_.put(ArmorItem.Type.BOOTS, 4);
		p_266652_.put(ArmorItem.Type.LEGGINGS, 5);
		p_266652_.put(ArmorItem.Type.CHESTPLATE, 6);
		p_266652_.put(ArmorItem.Type.HELMET, 3);
	}), 6, SoundEvents.ARMOR_EQUIP_IRON, 0f, 0f, () -> Ingredient.of(GEM.get()));

	public static final RegistryObject<Item> HELMET = ISERegistries.ITEMS.register("keego_helmet", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> CHESTPLATE = ISERegistries.ITEMS.register("keego_chestplate", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> LEGGINGS = ISERegistries.ITEMS.register("keego_leggings", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> BOOTS = ISERegistries.ITEMS.register("keego_boots", () -> new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

	public Keego(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		InsaneSE.addServerPack("keego", "Insane's Survival Extra Keego", () -> this.isEnabled() && !ISEDataPacks.disableAllDataPacks);
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if (!this.isEnabled()
				|| !event.getPlayer().getMainHandItem().is(KEEGO_TOOL_EQUIPMENT))
			return;

		int amplifier = 0;
		if (event.getPlayer().hasEffect(NewEnchantmentsFeature.MINING_MOMENTUM.get()))
			//noinspection DataFlowIssue
			amplifier = event.getPlayer().getEffect(NewEnchantmentsFeature.MINING_MOMENTUM.get()).getAmplifier() + 1;

		int duration = (int) (1f / event.getState().getDestroyProgress(event.getPlayer(), event.getLevel(), event.getPos()) + 5) * 3 + 1;
		event.getPlayer().addEffect(new MobEffectInstance(NewEnchantmentsFeature.MINING_MOMENTUM.get(), Math.max(duration, 20), Math.min(amplifier, 23), false, false, true));
	}

	@SubscribeEvent
	public void onMoving(TickEvent.PlayerTickEvent event) {
		if (!this.isEnabled()
				|| event.player.level().isClientSide
				|| event.player.isCrouching()
				|| event.phase == TickEvent.Phase.END)
			return;

		AtomicInteger pieces = new AtomicInteger(0);
		event.player.getInventory().armor.forEach(stack -> {
			if (stack.is(KEEGO_ARMOR_EQUIPMENT))
				pieces.addAndGet(1);
		});
		if (pieces.get() == 0)
			return;
		if (event.player.walkDist % (10 - pieces.get() * 2) < event.player.walkDistO % (10 - pieces.get() * 2)) {
			int amplifier = 0;
			if (event.player.hasEffect(NewEnchantmentsFeature.MOVEMENT_MOMENTUM.get()))
				//noinspection DataFlowIssue
				amplifier = event.player.getEffect(NewEnchantmentsFeature.MOVEMENT_MOMENTUM.get()).getAmplifier() + 1;

			event.player.addEffect(new MobEffectInstance(NewEnchantmentsFeature.MOVEMENT_MOMENTUM.get(), 100, Math.min(amplifier, 7), false, false, true));
		}

	}

	@SubscribeEvent
	public void shieldParryEvent(ShieldBlockEvent event) {
		if (!ModList.get().isLoaded("shieldplus")
				|| !this.isEnabled()
				|| event.getEntity().level().isClientSide
				|| !event.getEntity().getUseItem().is(ShieldsPlusIntegration.SHIELD.get()))
			return;

		int amplifier = 0;
		if (event.getEntity().hasEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()))
			//noinspection DataFlowIssue
			amplifier = event.getEntity().getEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()).getAmplifier() + 1;

		event.getEntity().addEffect(new MobEffectInstance(NewEnchantmentsFeature.ATTACK_MOMENTUM.get(), 100, Math.min(amplifier, 7), false, false, true));
	}

	@SubscribeEvent
	public void onAttack(LivingHurtEvent event) {
		if (!this.isEnabled()
				|| !(event.getSource().getEntity() instanceof ServerPlayer serverPlayer)
				|| serverPlayer.getAttackStrengthScale(0.5f) <= 0.9f
				|| !serverPlayer.getMainHandItem().is(KEEGO_HAND_EQUIPMENT))
			return;

		int amplifier = 0;
		if (serverPlayer.hasEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()))
			//noinspection DataFlowIssue
			amplifier = serverPlayer.getEffect(NewEnchantmentsFeature.ATTACK_MOMENTUM.get()).getAmplifier() + 1;

		double duration = ((4 - serverPlayer.getAttribute(Attributes.ATTACK_SPEED).getValue()) * 20d);
		serverPlayer.addEffect(new MobEffectInstance(NewEnchantmentsFeature.ATTACK_MOMENTUM.get(), (int) Math.max(duration, 10), Math.min(amplifier, 7), false, false, true));
	}

	public static class ShieldsPlusIntegration {
		public static final RegistryObject<SPShieldItem> SHIELD = KeegoShield.registerShield("keego_shield");

		public static void init() {

		}
	}
}