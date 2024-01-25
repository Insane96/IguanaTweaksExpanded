package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.protection.ITRProtectionEnchantment;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class MeleeProtection extends ITRProtectionEnchantment {
    public static final UUID[] ATTACK_SPEED_MODIFIER_UUIDS = new UUID[] {
            UUID.fromString("2f42e9bd-0537-403b-96b1-2a1d67029729"),
            UUID.fromString("5455c8d5-2e83-4da2-a698-2aa4333e8347"),
            UUID.fromString("f6adf83d-cc4e-48db-83b1-b5b942214353"),
            UUID.fromString("ab9acb05-1838-473a-bc87-c0832503edaa")
    };
    public MeleeProtection() {
        super(Rarity.UNCOMMON);
    }

    public static void applyAttributeModifier(ItemAttributeModifierEvent event) {
        if (!(event.getItemStack().getItem() instanceof ArmorItem armorItem))
            return;
        if (event.getSlotType() != armorItem.getEquipmentSlot())
            return;
        int lvl = event.getItemStack().getEnchantmentLevel(NewEnchantmentsFeature.MELEE_PROTECTION.get());
        if (lvl == 0)
            return;

        event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER_UUIDS[event.getSlotType().getIndex()], "Melee protection enchantment", 0.02d * lvl, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    public int getMaxLevel() {
        return 4;
    }

    public int getBaseCost() {
        return 5;
    }

    public int getCostPerLevel() {
        return 8;
    }

    public float getDamageReductionPerLevel() {
        return 0.08F;
    }

    public boolean isSourceReduced(DamageSource source) {
        return source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO);

    }
}
