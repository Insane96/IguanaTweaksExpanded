package insane96mcp.iguanatweaksexpanded.mixin.integration.allurement;

import com.teamabnormals.allurement.common.enchantment.LaunchEnchantment;
import insane96mcp.iguanatweaksexpanded.module.experience.enchanting.integration.Allurement;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LaunchEnchantment.class)
public class LaunchEnchantmentMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;<init>(Lnet/minecraft/world/item/enchantment/Enchantment$Rarity;Lnet/minecraft/world/item/enchantment/EnchantmentCategory;[Lnet/minecraft/world/entity/EquipmentSlot;)V"))
    private static EnchantmentCategory onEnchantmentCategory(EnchantmentCategory enchantmentCategory) {
        return Allurement.LAUNCH_CATEGORY;
    }
}
