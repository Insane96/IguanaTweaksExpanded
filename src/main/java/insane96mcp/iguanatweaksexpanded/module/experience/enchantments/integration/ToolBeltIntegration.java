package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.integration;

import dev.gigaherz.toolbelt.ToolBelt;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import dev.gigaherz.toolbelt.slot.BeltExtensionSlot;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.Soulbound;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public class ToolBeltIntegration {
    public static void onSoulBound(Player player, ListTag soulboundItems) {
        LazyOptional<BeltExtensionSlot> oBeltExtensionSlot = BeltExtensionSlot.get(player);
        oBeltExtensionSlot.ifPresent(beltExtensionSlot ->
                beltExtensionSlot.getSlots().forEach(extensionSlot -> {
                    ItemStack belt = extensionSlot.getContents();
                    if (belt.is(ToolBelt.BELT.get())) {
                        belt.getCapability(ToolBeltItem.ITEM_HANDLER).ifPresent(cap -> {
                            int slots = cap.getSlots();
                            for (int i = 0; i < slots; i++) {
                                ItemStack stackInSlot = cap.getStackInSlot(i);
                                if (stackInSlot.getEnchantmentLevel(NewEnchantmentsFeature.SOULBOUND.get()) > 0) {
                                    CompoundTag compoundTag = Soulbound.saveWithSlot(stackInSlot, (byte) -1);
                                    if (compoundTag != null) {
                                        cap.extractItem(i, 99, false);
                                        soulboundItems.add(compoundTag);
                                    }
                                }
                            }
                        });
                    }
                })
        );
    }
}
