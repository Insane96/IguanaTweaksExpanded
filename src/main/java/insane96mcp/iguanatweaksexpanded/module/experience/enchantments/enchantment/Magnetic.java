package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.enchantment.IEnchantmentTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Magnetic extends Enchantment implements IEnchantmentTooltip {
    public Magnetic() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return 15 + (level - 1) * 20;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 30;
    }

    public static void tryPullItems(LivingEntity entity) {
        int lvl = entity.getItemBySlot(EquipmentSlot.LEGS).getEnchantmentLevel(NewEnchantmentsFeature.MAGNETIC.get());
        if (lvl == 0)
            return;

        List<ItemEntity> itemsInRange = entity.level().getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(lvl + 2));
        for (ItemEntity itemEntity : itemsInRange) {
            Vec3 vecToEntity = new Vec3(entity.getX() - itemEntity.getX(), entity.getY() + (double)entity.getEyeHeight() / 2.0D - itemEntity.getY(), entity.getZ() - itemEntity.getZ());
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(vecToEntity.normalize().scale(0.02d + lvl * 0.01d)));
        }
    }

    @Override
    public Component getTooltip(ItemStack stack, int lvl) {
        return Component.translatable(this.getDescriptionId() + ".tooltip", lvl + 2).withStyle(ChatFormatting.DARK_PURPLE);
    }
}
