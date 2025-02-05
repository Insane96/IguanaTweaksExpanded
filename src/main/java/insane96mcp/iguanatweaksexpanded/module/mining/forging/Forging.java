package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import insane96mcp.iguanatweaksexpanded.InsaneSurvivalExtra;
import insane96mcp.iguanatweaksexpanded.data.generator.ISEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.items.copper.CopperExpansion;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.Solarium;
import insane96mcp.iguanatweaksexpanded.module.mining.SoulSteel;
import insane96mcp.iguanatweaksexpanded.module.mining.durium.Durium;
import insane96mcp.iguanatweaksexpanded.module.mining.keego.Keego;
import insane96mcp.iguanatweaksexpanded.module.mining.quaron.Quaron;
import insane96mcp.iguanatweaksexpanded.module.misc.ISEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.event.ISOLivingAttackEvent;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.items.flintexpansion.FlintExpansion;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Forging")
@LoadFeature(module = Modules.Ids.MINING, canBeDisabled = false)
public class Forging extends Feature {
	public static final SimpleBlockWithItem FORGE = SimpleBlockWithItem.register("forge", () -> new ForgeBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL)));
	public static final RegistryObject<BlockEntityType<ForgeBlockEntity>> FORGE_BLOCK_ENTITY_TYPE = ISERegistries.BLOCK_ENTITY_TYPES.register("forge", () -> BlockEntityType.Builder.of(ForgeBlockEntity::new, FORGE.block().get()).build(null));

	public static final RegistryObject<RecipeType<ForgeRecipe>> FORGE_RECIPE_TYPE = ISERegistries.RECIPE_TYPES.register("forging", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return "forging";
		}
	});
	public static final RegistryObject<ForgeRecipe.ForgeRecipeSerializer> FORGE_RECIPE_SERIALIZER = ISERegistries.RECIPE_SERIALIZERS.register("forging", ForgeRecipe.ForgeRecipeSerializer::new);
	public static final RegistryObject<MenuType<ForgeMenu>> FORGE_MENU_TYPE = ISERegistries.MENU_TYPES.register("forge", () -> new MenuType<>(ForgeMenu::new, FeatureFlags.VANILLA_SET));

	public static final RegistryObject<ForgeHammerItem> WOODEN_HAMMER = ISERegistries.ITEMS.register("wooden_hammer", () -> new ForgeHammerItem(Tiers.WOOD, 35, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> STONE_HAMMER = ISERegistries.ITEMS.register("stone_hammer", () -> new ForgeHammerItem(Tiers.STONE, 30, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> FLINT_HAMMER = ISERegistries.ITEMS.register("flint_hammer", () -> new ForgeHammerItem(FlintExpansion.ITEM_TIER, 30, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> COPPER_HAMMER = ISERegistries.ITEMS.register("copper_hammer", () -> new ForgeHammerItem(CopperExpansion.COPPER_ITEM_TIER, 20, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> GOLDEN_HAMMER = ISERegistries.ITEMS.register("golden_hammer", () -> new ForgeHammerItem(Tiers.GOLD, 8, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> IRON_HAMMER = ISERegistries.ITEMS.register("iron_hammer", () -> new ForgeHammerItem(Tiers.IRON, 25, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> SOLARIUM_HAMMER = ISERegistries.ITEMS.register("solarium_hammer", () -> new SolariumForgeHammerItem(Solarium.ITEM_TIER, 30, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> DURIUM_HAMMER = ISERegistries.ITEMS.register("durium_hammer", () -> new ForgeHammerItem(Durium.ITEM_TIER, 30, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> COATED_COPPER_HAMMER = ISERegistries.ITEMS.register("coated_copper_hammer", () -> new ForgeHammerItem(CopperExpansion.COATED_ITEM_TIER, 25, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> QUARON_HAMMER = ISERegistries.ITEMS.register("quaron_hammer", () -> new ForgeHammerItem(Quaron.ITEM_TIER, 20, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> KEEGO_HAMMER = ISERegistries.ITEMS.register("keego_hammer", () -> new KeegoForgeHammerItem(Keego.ITEM_TIER, 18, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> DIAMOND_HAMMER = ISERegistries.ITEMS.register("diamond_hammer", () -> new ForgeHammerItem(Tiers.DIAMOND, 15, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> SOUL_STEEL_HAMMER = ISERegistries.ITEMS.register("soul_steel_hammer", () -> new ForgeHammerItem(SoulSteel.ITEM_TIER, 20, new Item.Properties()));
	public static final RegistryObject<ForgeHammerItem> NETHERITE_HAMMER = ISERegistries.ITEMS.register("netherite_hammer", () -> new ForgeHammerItem(Tiers.NETHERITE, 15, new Item.Properties()));

	@Config
	@Label(name = "Unforgable enchanted items", description = "Enchanted items can't be forged")
	public static Boolean unforgableEnchantedItems = true;

	@Config
	@Label(name = "Forging Equipment Crafting Data Pack", description = """
			Enables the following changes to vanilla data pack:
			* All metal gear requires a forge to be made
			* Diamond Gear requires Gold gear to be forged
			* Gold Gear requires Flint / Leather gear to be forged
			* Iron Gear requires Stone / Chained Copper gear to be forged
			* Buckets, Flint and Steel and Shears require a forge to be made""")
	public static Boolean forgingEquipment = true;

	public Forging(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "forging_equipment", Component.literal("IguanaTweaks Expanded Forging Equipment"), () -> this.isEnabled() && !ISEDataPacks.disableAllDataPacks && forgingEquipment));
	}

	@SubscribeEvent
	public void onHammerDamage(ISOLivingAttackEvent event) {
		if (!(event.getSource().getEntity() instanceof LivingEntity attacker)
				|| !attacker.getMainHandItem().is(ISEItemTagsProvider.FORGE_HAMMERS)
				|| event.getEntity().level().isClientSide)
			return;

		event.getEntity().getPersistentData().putBoolean(InsaneSurvivalExtra.MOD_ID + "cancel_knockback", true);

		float attackStrengthScale = 1f;
		if (attacker instanceof Player player)
			attackStrengthScale = player.getAttackStrengthScale(0.5f);

		float range = 3F;
		float rangeSqr = range * range;

		for (LivingEntity livingEntity : event.getEntity().level().getEntitiesOfClass(LivingEntity.class, event.getEntity().getBoundingBox().inflate(range, range / 2f, range))) {
			if (livingEntity != attacker
					&& !livingEntity.isAlliedTo(attacker)
					&& (!(livingEntity instanceof ArmorStand armorStand) || !armorStand.isMarker())
					&& event.getEntity().distanceToSqr(livingEntity) < rangeSqr) {
				livingEntity.push(0, (0.9f + (getKnockbackBonus(attacker))) * (1.0D - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)) * attackStrengthScale, 0);
			}
		}

		event.getEntity().playSound(SoundEvents.ANVIL_PLACE, 0.6f * attackStrengthScale, 1.1f);
		((ServerLevel) event.getEntity().level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.ANVIL.defaultBlockState), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), 200, range / 2f, range / 4f, range / 2f, 1f);
	}

	private static float getKnockbackBonus(LivingEntity entity) {
		return Math.max(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, entity), EnchantmentHelper.getEnchantmentLevel(EnchantmentsFeature.KNOCKBACK.get(), entity)) * 0.2f;
	}

	@SubscribeEvent
	public void cancelKnockback(LivingKnockBackEvent event) {
		if (event.getEntity().getPersistentData().getBoolean(InsaneSurvivalExtra.MOD_ID + "cancel_knockback")) {
			event.setCanceled(true);
			event.getEntity().getPersistentData().remove(InsaneSurvivalExtra.MOD_ID + "cancel_knockback");
		}
	}
}
