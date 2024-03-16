package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.damage.BonusDamageEnchantment;
import insane96mcp.insanelib.world.enchantments.IEnchantmentTooltip;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WaterCoolant extends BonusDamageEnchantment implements IEnchantmentTooltip {
    public static final TagKey<EntityType<?>> AFFECTED_BY_WATER_COOLANT = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "enchantments/water_coolant"));
    public WaterCoolant() {
        super(Rarity.UNCOMMON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public float getDamageBonus(LivingEntity attacker, LivingEntity target, ItemStack stack, int lvl) {
        if (!target.getType().is(AFFECTED_BY_WATER_COOLANT))
            return 0f;
        return this.getDamageBonus(stack, lvl);
    }

    @Override
    public boolean isAffectedByEnchantment(LivingEntity target) {
        return target.getType().is(AFFECTED_BY_WATER_COOLANT);
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity entity, int lvl) {
        if (!(entity instanceof LivingEntity livingEntity)
                || !livingEntity.getType().is(AFFECTED_BY_WATER_COOLANT))
            return;

        if (lvl > 0) {
            livingEntity.setTicksFrozen(Entity.BASE_TICKS_REQUIRED_TO_FREEZE + 10 + (Entity.FREEZE_HURT_FREQUENCY * 2 * lvl));
            if (attacker instanceof ServerPlayer player)
                player.magicCrit(entity);
        }
    }
}
