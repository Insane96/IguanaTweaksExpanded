package insane96mcp.survivalreimagined.mixin;

import insane96mcp.insanelib.base.Feature;
import insane96mcp.survivalreimagined.module.experience.feature.Anvils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = AnvilMenu.class, priority = 1001)
public class AnvilMenuMixin extends ItemCombinerMenu {

	@Shadow
	@Final
	private static int COST_RENAME;

	@Shadow
	@Final
	private DataSlot cost;

	@Shadow
	public int repairItemCountCost;

	@Shadow
	private String itemName;

	public AnvilMenuMixin(@Nullable MenuType<?> p_39773_, int p_39774_, Inventory p_39775_, ContainerLevelAccess p_39776_) {
		super(p_39773_, p_39774_, p_39775_, p_39776_);
	}

	@Inject(
			method = "mayPickup",
			at = @At("HEAD"),
			cancellable = true
	)
	private void mayPickUp(Player player, boolean unused, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue((player.getAbilities().instabuild || player.experienceLevel >= this.cost.get()));
	}

	@Inject(
			at = @At("HEAD"),
			method = "createResult",
			cancellable = true
	)
	public void createResult(CallbackInfo ci) {
		if (!Feature.isEnabled(Anvils.class))
			return;

		ItemStack left = this.inputSlots.getItem(0);
		this.cost.set(1);
		int mergeCost = 0;
		int baseCost = 0;
		boolean isRenaming = false;

		if (left.isEmpty()) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
			this.cost.set(0);
			return;
		}

		ItemStack resultStack = left.copy();
		ItemStack right = this.inputSlots.getItem(1);
		Map<Enchantment, Integer> leftEnchantments = EnchantmentHelper.getEnchantments(resultStack);
		baseCost += left.getBaseRepairCost() + (right.isEmpty() ? 0 : right.getBaseRepairCost());
		this.repairItemCountCost = 0;
		boolean isEnchantedBook = false;

		if (!right.isEmpty()) {
			if (!net.minecraftforge.common.ForgeHooks.onAnvilChange((AnvilMenu) (Object) this, left, right, resultSlots, itemName, baseCost, this.player)) return;
			isEnchantedBook = right.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(right).isEmpty();
			boolean isPartialRepairItem = Anvils.isPartialRepairItem(resultStack, right);
			if (resultStack.isDamageableItem() && (resultStack.getItem().isValidRepairItem(left, right) || isPartialRepairItem)) {
				int repairItemCountCost;
				if (isPartialRepairItem) {
					int maxPartialRepairDurLeft = (int) (resultStack.getMaxDamage() * 0.4f);
					int repairSteps = Math.min(resultStack.getDamageValue(), resultStack.getMaxDamage() / 4);
					if (repairSteps <= 0) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
						return;
					}

					for(repairItemCountCost = 0; repairSteps > 0 && repairItemCountCost < right.getCount() && resultStack.getDamageValue() > maxPartialRepairDurLeft; ++repairItemCountCost) {
						int dmgAfterRepair = resultStack.getDamageValue() - repairSteps;
						resultStack.setDamageValue(Math.max(maxPartialRepairDurLeft, dmgAfterRepair));
						++mergeCost;
						repairSteps = Math.min(resultStack.getDamageValue(), resultStack.getMaxDamage() / 4);
					}
				}
				//Vanilla behaviour
				else {
					int repairSteps = Math.min(resultStack.getDamageValue(), resultStack.getMaxDamage() / 4);
					if (repairSteps <= 0) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
						return;
					}

					for(repairItemCountCost = 0; repairSteps > 0 && repairItemCountCost < right.getCount(); ++repairItemCountCost) {
						int dmgAfterRepair = resultStack.getDamageValue() - repairSteps;
						resultStack.setDamageValue(dmgAfterRepair);
						++mergeCost;
						repairSteps = Math.min(resultStack.getDamageValue(), resultStack.getMaxDamage() / 4);
					}
				}

				this.repairItemCountCost = repairItemCountCost;
			}
			else {
				if (!isEnchantedBook && (!resultStack.is(right.getItem()) || !resultStack.isDamageableItem())) {
					this.resultSlots.setItem(0, ItemStack.EMPTY);
					this.cost.set(0);
					return;
				}

				if (resultStack.isDamageableItem() && !isEnchantedBook) {
					int leftDurabilityLeft = left.getMaxDamage() - left.getDamageValue();
					int rightDurabilityLeft = right.getMaxDamage() - right.getDamageValue();
					int j1 = rightDurabilityLeft + resultStack.getMaxDamage() * 12 / 100;
					int k1 = leftDurabilityLeft + j1;
					int l1 = resultStack.getMaxDamage() - k1;
					if (l1 < 0) {
						l1 = 0;
					}

					if (l1 < resultStack.getDamageValue()) {
						resultStack.setDamageValue(l1);
						mergeCost += 2;
					}
				}

				Map<Enchantment, Integer> rightEnchantments = EnchantmentHelper.getEnchantments(right);
				boolean canEnchant = false;
				boolean cannotEnchant = false;

				for (Enchantment rightEnchantment : rightEnchantments.keySet()) {
					if (rightEnchantment != null) {
						int leftLvl = leftEnchantments.getOrDefault(rightEnchantment, 0);
						int rightLvl = rightEnchantments.get(rightEnchantment);
						rightLvl = leftLvl == rightLvl ? rightLvl + 1 : Math.max(rightLvl, leftLvl);
						boolean canEnchant2 = rightEnchantment.canEnchant(left);
						if (this.player.getAbilities().instabuild || left.is(Items.ENCHANTED_BOOK)) {
							canEnchant2 = true;
						}

						for(Enchantment enchantment : leftEnchantments.keySet()) {
							if (enchantment != rightEnchantment && !rightEnchantment.isCompatibleWith(enchantment)) {
								canEnchant2 = false;
								++mergeCost;
							}
						}

						if (!canEnchant2) {
							cannotEnchant = true;
						}
						else {
							canEnchant = true;
							if (rightLvl > rightEnchantment.getMaxLevel() && leftLvl == rightLvl /*Added to allow over max level enchantment books to be applied to items*/) {
								rightLvl = rightEnchantment.getMaxLevel();
							}

							leftEnchantments.put(rightEnchantment, rightLvl);
							int enchantmentRarityCost = switch (rightEnchantment.getRarity()) {
								case COMMON -> 1;
								case UNCOMMON -> 2;
								case RARE -> 4;
								case VERY_RARE -> 8;
							};

							if (isEnchantedBook) {
								enchantmentRarityCost = Math.max(1, enchantmentRarityCost / 2);
							}

							mergeCost += enchantmentRarityCost * rightLvl;
							if (left.getCount() > 1) {
								mergeCost = 40;
							}
						}
					}
				}

				if (cannotEnchant && !canEnchant) {
					this.resultSlots.setItem(0, ItemStack.EMPTY);
					this.cost.set(0);
					return;
				}
			}
		}

		if (StringUtils.isBlank(this.itemName)) {
			if (left.hasCustomHoverName()) {
				isRenaming = true;
				resultStack.resetHoverName();
			}
		}
		else if (!this.itemName.equals(left.getHoverName().getString())) {
			isRenaming = true;
			resultStack.setHoverName(Component.literal(this.itemName));
		}
		if (isEnchantedBook && !resultStack.isBookEnchantable(right))
			resultStack = ItemStack.EMPTY;

		this.cost.set(baseCost + mergeCost);
		if (isRenaming && !Anvils.isFreeRenaming())
			this.cost.set(this.cost.get() + COST_RENAME);
		if (mergeCost <= 0 && !isRenaming)
			resultStack = ItemStack.EMPTY;

		if (isRenaming && Anvils.isFreeRenaming() && mergeCost <= 0)
			this.cost.set(0);

		//Set Too Expensive cap
		if (this.cost.get() >= Anvils.anvilRepairCap && !this.player.getAbilities().instabuild)
			resultStack = ItemStack.EMPTY;

		if (!resultStack.isEmpty()) {
			int toolRepairCost = resultStack.getBaseRepairCost();
			if (!right.isEmpty() && toolRepairCost < right.getBaseRepairCost())
				toolRepairCost = right.getBaseRepairCost();

			if (mergeCost >= 1)
				toolRepairCost = AnvilMenu.calculateIncreasedRepairCost(toolRepairCost);

			if (!Anvils.isNoRepairCostIncrease())
				resultStack.setRepairCost(toolRepairCost);
			EnchantmentHelper.setEnchantments(leftEnchantments, resultStack);
		}

		this.resultSlots.setItem(0, resultStack);
		this.broadcastChanges();

		ci.cancel();
	}

	@Shadow
	protected boolean mayPickup(@NotNull Player p_39798_, boolean p_39799_) {
		return false;
	}

	@Shadow
	protected void onTake(@NotNull Player p_150601_, @NotNull ItemStack p_150602_) {
	}

	@Shadow
	protected boolean isValidBlock(@NotNull BlockState p_39788_) {
		return false;
	}

	@Shadow
	public void createResult() {
	}

	@Shadow
	protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
		return null;
	}
}
