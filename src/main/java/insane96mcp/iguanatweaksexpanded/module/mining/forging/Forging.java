package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import insane96mcp.iguanatweaksexpanded.InsaneSE;
import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ISEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ISERegistries;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.event.ISOLivingAttackEvent;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.LoadFeature;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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

@LoadFeature(module = Modules.Ids.MINING)
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

	public static final RegistryObject<ForgeHammerItem> HAMMER = ISERegistries.ITEMS.register("forge_hammer", () -> new ForgeHammerItem(Tiers.IRON, 20, new Item.Properties()));

	@Config(description = "Enchanted items can't be forged")
	public static Boolean unforgableEnchantedItems = true;

	@Config(description = """
			Enables the following changes to vanilla data pack:
			* All metal gear requires a forge to be made
			* Diamond Gear requires Gold gear to be forged
			* Gold Gear requires Flint / Leather gear to be forged
			* Iron Gear requires Stone / Chained Copper gear to be forged
			* Buckets, Flint and Steel and Shears require a forge to be made""")
	public static Boolean forgingEquipmentCraftingDataPack = true;

	public void init(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super.init(module, enabledByDefault, canBeDisabled);
		InsaneSE.addServerPack("forging_equipment", "IguanaTweaks Expanded Forging Equipment", () -> this.isEnabled() && !ISEDataPacks.disableAllDataPacks && forgingEquipmentCraftingDataPack);
	}

	@SubscribeEvent
	public void onHammerDamage(ISOLivingAttackEvent event) {
		if (!(event.getSource().getEntity() instanceof LivingEntity attacker)
				|| !attacker.getMainHandItem().is(HAMMER.get())
				|| event.getEntity().level().isClientSide)
			return;

		event.getEntity().getPersistentData().putBoolean(InsaneSE.MOD_ID + "cancel_knockback", true);

		float attackStrengthScale = 1f;
		if (attacker instanceof Player player)
			attackStrengthScale = player.getAttackStrengthScale(0.5f);

		float range = 3.5F;
		float rangeSqr = range * range;

		for (LivingEntity livingEntity : event.getEntity().level().getEntitiesOfClass(LivingEntity.class, event.getEntity().getBoundingBox().inflate(range, range / 2f, range))) {
			if (livingEntity != attacker
                    && livingEntity.onGround()
					&& !livingEntity.isAlliedTo(attacker)
					&& (!(livingEntity instanceof ArmorStand armorStand) || !armorStand.isMarker())
					&& event.getEntity().distanceToSqr(livingEntity) < rangeSqr) {
				livingEntity.push(0, (0.9f + (getKnockbackBonus(attacker))) * (1.0D - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)) * attackStrengthScale * attackStrengthScale, 0);
				if (livingEntity instanceof Player player)
					player.hurtMarked = true;
			}
		}

		event.getEntity().playSound(SoundEvents.ANVIL_PLACE, 0.6f * attackStrengthScale * attackStrengthScale, 1.1f);
		((ServerLevel) event.getEntity().level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.ANVIL.defaultBlockState), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), 200, range / 2f, range / 4f, range / 2f, 1f);
	}

	private static float getKnockbackBonus(LivingEntity entity) {
		return Math.max(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, entity), EnchantmentHelper.getEnchantmentLevel(EnchantmentsFeature.KNOCKBACK.get(), entity)) * 0.15f;
	}

	@SubscribeEvent
	public void cancelKnockback(LivingKnockBackEvent event) {
		if (event.getEntity().getPersistentData().getBoolean(InsaneSE.MOD_ID + "cancel_knockback")) {
			event.setCanceled(true);
			event.getEntity().getPersistentData().remove(InsaneSE.MOD_ID + "cancel_knockback");
		}
	}
}
