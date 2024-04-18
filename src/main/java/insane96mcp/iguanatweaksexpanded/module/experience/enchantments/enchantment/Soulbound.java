package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.data.generator.ITRItemTagsProvider;
import insane96mcp.iguanatweaksreborn.utils.MCUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;

public class Soulbound extends Enchantment {
    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITRItemTagsProvider.create("enchanting/accepts_soulbound_enchantments");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("soulbound_enchantment", (item) -> {
        return item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT) || EnchantmentCategory.VANISHABLE.canEnchant(item);
    });
    public Soulbound() {
        super(Rarity.RARE, CATEGORY, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMinCost(int level) {
        return 2 + 10 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
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
                player.getInventory().setItem(i, ItemStack.EMPTY);
                list.add(compoundTag);
            }
        }
        for (int j = 0; j < player.getInventory().armor.size(); ++j) {
            CompoundTag compoundTag = saveWithSlot(player.getInventory().armor.get(j), (byte) j);
            if (compoundTag != null) {
                player.getInventory().setItem(j, ItemStack.EMPTY);
                list.add(compoundTag);
            }
        }
        for (int k = 0; k < player.getInventory().offhand.size(); ++k) {
            CompoundTag compoundTag = saveWithSlot(player.getInventory().offhand.get(k), (byte) k);
            if (compoundTag != null) {
                player.getInventory().setItem(k, ItemStack.EMPTY);
                list.add(compoundTag);
            }
        }
        persistedTag.put(IguanaTweaksExpanded.RESOURCE_PREFIX + "soulbound_items", list);
    }

    @Nullable
    private static CompoundTag saveWithSlot(ItemStack stack, byte slot) {
        if (stack.getEnchantmentLevel(NewEnchantmentsFeature.SOULBOUND.get()) <= 0)
            return null;

        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putByte("Slot", slot);
        stack.save(compoundTag);
        return compoundTag;
    }

    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity().level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY))
            return;

        CompoundTag persistedTag = MCUtils.getOrCreatePersistedData(event.getEntity());
        ListTag list = persistedTag.getList(IguanaTweaksExpanded.RESOURCE_PREFIX + "soulbound_items", CompoundTag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            ItemStack stack = ItemStack.of(list.getCompound(i));
            byte slot = list.getCompound(i).getByte("Slot");
            event.getEntity().getInventory().setItem(slot, stack);
        }
        persistedTag.remove(IguanaTweaksExpanded.RESOURCE_PREFIX + "soulbound_items");
    }
}
