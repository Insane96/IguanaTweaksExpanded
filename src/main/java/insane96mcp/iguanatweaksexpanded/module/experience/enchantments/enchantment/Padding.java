package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.data.generator.ITRItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class Padding extends Enchantment {
    public static final String SHOULD_APPLY = IguanaTweaksExpanded.RESOURCE_PREFIX + "should_padding_apply";

    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ITRItemTagsProvider.create("enchanting/accepts_padding");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("reach", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public Padding() {
        super(Rarity.RARE, CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 20;
    }

    @Override
    public int getMaxCost(int level) {
        return 40;
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
}
