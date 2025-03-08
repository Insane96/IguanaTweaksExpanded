package insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment;

import insane96mcp.iguanatweaksexpanded.data.generator.ISEItemTagsProvider;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.NewEnchantmentsFeature;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class Knowledgeable extends Enchantment {
    public static final TagKey<Item> ACCEPTS_ENCHANTMENT = ISEItemTagsProvider.create("enchanting/accepts_knowledgeable");
    static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("knowledgeable_enchantment", item -> item.builtInRegistryHolder().is(ACCEPTS_ENCHANTMENT));
    public Knowledgeable() {
        super(Rarity.VERY_RARE, CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    private static float getChance() {
        return 0.35f;
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity entity, int lvl) {
        if (!(entity instanceof LivingEntity target)
                || !(attacker instanceof Player player)
                || player.getAttackStrengthScale(0.5f) < 0.9f)
            return;

        float chance = (float) (getChance() * (1f / attacker.getAttributeValue(Attributes.ATTACK_SPEED)));
        if (target.getRandom().nextFloat() <= chance)
            target.level().addFreshEntity(new ExperienceOrb(target.level(), target.getX(), target.getY() + target.getBbHeight() / 2f, target.getZ(), lvl));
    }

    public static int applyToBlockDrops(Player player, int expToDrop) {
        float chance = (float) (getChance() * (1f / player.getAttributeValue(Attributes.ATTACK_SPEED)));
        int lvl = EnchantmentHelper.getEnchantmentLevel(NewEnchantmentsFeature.KNOWLEDGEABLE.get(), player);
        return lvl > 0 && player.getRandom().nextFloat() <= chance
                ? expToDrop + lvl
                : expToDrop;
    }
}
