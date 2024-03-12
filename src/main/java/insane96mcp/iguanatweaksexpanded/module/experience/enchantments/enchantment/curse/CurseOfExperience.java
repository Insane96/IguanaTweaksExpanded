package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.insanelib.event.HurtItemStackEvent;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CurseOfExperience extends Enchantment implements IEnchantmentTooltip {
    public static ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "curse_of_experience"));
    public CurseOfExperience() {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.values());
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 25;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return 50;
    }

    public static void consumePlayerExperience(HurtItemStackEvent event) {
        if (event.getPlayer() == null
                || event.getStack().getEnchantmentLevel(NewEnchantmentsFeature.CURSE_OF_EXPERIENCE.get()) == 0
                || event.getAmount() == 0)
            return;

        if (event.getPlayer().experienceLevel == 0 && event.getPlayer().experienceProgress == 0)
            event.getPlayer().hurt(event.getPlayer().damageSources().source(DAMAGE_TYPE), 2f);
        else
            event.getPlayer().giveExperiencePoints(-2);
    }

    @Override
    public Component getTooltip(ItemStack itemStack, int i) {
        return Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.RED);
    }
}
