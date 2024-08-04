package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.integration.ToolBeltIntegration;
import insane96mcp.iguanatweaksreborn.utils.MCUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Soulbound extends Enchantment {
    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_soulbound");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("soulbound_enchantment",
            (item) -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT) || EnchantmentCategory.VANISHABLE.canEnchant(item));
    public Soulbound() {
        super(Rarity.RARE, CATEGORY, EquipmentSlot.values());
    }

    public int getMinCost(int pEnchantmentLevel) {
        return pEnchantmentLevel * 20;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return this.getMinCost(pEnchantmentLevel) + 40;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
                || player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY))
            return;

        CompoundTag persistedTag = MCUtils.getOrCreatePersistedData(player);
        ListTag list = new ListTag();
        for (int i = 0; i < player.getInventory().items.size(); ++i) {
            CompoundTag compoundTag = saveWithSlot(player.getInventory().items.get(i), (byte) i);
            if (compoundTag != null) {
                player.getInventory().removeItem(player.getInventory().items.get(i));
                list.add(compoundTag);
            }
        }
        for (int j = 0; j < player.getInventory().armor.size(); ++j) {
            CompoundTag compoundTag = saveWithSlot(player.getInventory().armor.get(j), (byte) (j + 100));
            if (compoundTag != null) {
                player.getInventory().removeItem(player.getInventory().armor.get(j));
                list.add(compoundTag);
            }
        }
        for (int k = 0; k < player.getInventory().offhand.size(); ++k) {
            CompoundTag compoundTag = saveWithSlot(player.getInventory().offhand.get(k), (byte) (k + 150));
            if (compoundTag != null) {
                player.getInventory().removeItem(player.getInventory().offhand.get(k));
                list.add(compoundTag);
            }
        }
        if (ModList.get().isLoaded("toolbelt"))
            ToolBeltIntegration.onSoulBound(player, list);
        persistedTag.put(IguanaTweaksExpanded.RESOURCE_PREFIX + "soulbound_items", list);
    }

    @Nullable
    public static CompoundTag saveWithSlot(ItemStack stack, byte slot) {
        if (stack.getEnchantmentLevel(NewEnchantmentsFeature.SOULBOUND.get()) <= 0)
            return null;

        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putByte("Slot", slot);
        stack.save(compoundTag);
        return compoundTag;
    }

    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        if (player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY))
            return;

        List<ItemStack> lateStacks = new ArrayList<>();
        CompoundTag persistedTag = MCUtils.getOrCreatePersistedData(player);
        ListTag list = persistedTag.getList(IguanaTweaksExpanded.RESOURCE_PREFIX + "soulbound_items", CompoundTag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            ItemStack stack = ItemStack.of(list.getCompound(i));
            int slot = list.getCompound(i).getByte("Slot");
            //Stacks with slot -1 have no slot so will be given later
            if (slot == -1) {
                lateStacks.add(stack);
                continue;
            }
            slot &= 255;
            if (slot < player.getInventory().items.size())
                player.getInventory().items.set(slot, stack);
            else if (slot >= 100 && slot < player.getInventory().armor.size() + 100)
                player.getInventory().armor.set(slot - 100, stack);
            else if (slot >= 150 && slot < player.getInventory().offhand.size() + 150)
                player.getInventory().offhand.set(slot - 150, stack);
        }
        for (ItemStack stack : lateStacks) {
            if (!player.getInventory().add(stack)) {
                ItemEntity itementity = player.drop(stack, false);
                if (itementity != null) {
                    itementity.setNoPickUpDelay();
                    itementity.setTarget(player.getUUID());
                }
            }
        }
        persistedTag.remove(IguanaTweaksExpanded.RESOURCE_PREFIX + "soulbound_items");
    }
}
