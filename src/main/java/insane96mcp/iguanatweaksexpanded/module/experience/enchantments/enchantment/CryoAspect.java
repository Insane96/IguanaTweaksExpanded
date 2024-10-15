package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ITEItemTagsProvider;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.FireAspect;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;
import org.jetbrains.annotations.NotNull;

public class CryoAspect extends Enchantment {
    public static TagKey<Item> ACCEPTS_ENCHANTMENT = ITEItemTagsProvider.create("enchanting/accepts_cryo_aspect");
    public static EnchantmentCategory CATEGORY = EnchantmentCategory.create("accepts_cryo_aspect", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public CryoAspect() {
        super(Rarity.RARE, CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 10 + 20 * (pEnchantmentLevel - 1);
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    public int getMaxLevel() {
        return 2;
    }

    public boolean checkCompatibility(Enchantment other) {
        return !(other instanceof FireAspect) && !(other instanceof FireAspectEnchantment) && super.checkCompatibility(other);
    }

    public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int lvl) {
        int ticks = this.frozenTicksPerLevel(target) + (80 * lvl);
        if (attacker instanceof Player player)
            ticks = (int) (ticks * player.getAttackStrengthScale(0.5f));
        if (target.getTicksFrozen() < ticks)
            target.setTicksFrozen(ticks);
    }

    public int frozenTicksPerLevel(@NotNull Entity target) {
        return target.getTicksRequiredToFreeze();
    }
}
