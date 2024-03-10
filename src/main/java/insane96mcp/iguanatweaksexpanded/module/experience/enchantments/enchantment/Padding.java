package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class Padding extends Enchantment implements IEnchantmentTooltip {
    public static final String SHOULD_APPLY = IguanaTweaksExpanded.RESOURCE_PREFIX + "should_padding_apply";

    public Padding() {
        super(Rarity.RARE, EnchantmentsFeature.WEAPONS_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 20;
    }

    @Override
    public int getMaxCost(int level) {
        return 40;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return super.canApplyAtEnchantingTable(stack) || stack.is(BonusDamageEnchantment.ACCEPTS_DAMAGE_ENCHANTMENTS);
    }

    public static void shouldApply(LivingHurtEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof LivingEntity livingEntity)) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity)
                livingEntity.getPersistentData().remove(SHOULD_APPLY);
        }
        else {
            //I have to apply it twice since knockback is dealt twice when the player has the attack_knockback attribute, if is sprinting or if has the knockback enchantment
            livingEntity.getPersistentData().putByte(SHOULD_APPLY, (byte) 2);
        }
    }

    public static void reduceKnockback(LivingKnockBackEvent event) {
        LivingEntity lastHurtByMob = event.getEntity().getLastHurtByMob();
        if (lastHurtByMob == null)
            return;
        byte shouldApply = lastHurtByMob.getPersistentData().getByte(SHOULD_APPLY);
        if (shouldApply == 0)
            return;
        int lvl = lastHurtByMob.getMainHandItem().getEnchantmentLevel(NewEnchantmentsFeature.PADDING.get());
        if (lvl == 0)
            return;

        event.setStrength(event.getStrength() * 0.5f);
        lastHurtByMob.getPersistentData().putByte(SHOULD_APPLY, --shouldApply);
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.DARK_PURPLE);
    }
}
