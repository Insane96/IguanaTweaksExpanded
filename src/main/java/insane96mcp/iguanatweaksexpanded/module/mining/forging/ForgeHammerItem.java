package insane96mcp.iguanatweaksexpanded.module.mining.forging;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.insanelib.InsaneLib;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ForgeHammerItem extends TieredItem implements Vanishable {
    protected static final UUID ENTITY_REACH_UUID = UUID.fromString("cdec6524-49a5-465a-a61c-f53c2e637c48");

    public static final String FORGE_COOLDOWN_LANG = IguanaTweaksExpanded.MOD_ID + ".hammer_cooldown";
    public static final String FORGE_DURABILITY_LANG = IguanaTweaksExpanded.MOD_ID + ".hammer_durability";

    final int useCooldown;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ForgeHammerItem(Tier tier, int useCooldown, Properties pProperties) {
        super(tier, pProperties);
        this.useCooldown = useCooldown;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 3.5 + getTier().getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -(4d - 0.35), AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ENTITY_REACH_UUID, "Tool modifier", -1d, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public int getUseCooldown(@Nullable LivingEntity entity, ItemStack stack) {
        int cooldown = this.useCooldown;
        int efficiency = stack.getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY);
        if (efficiency <= 0)
            return cooldown;
        //Each efficiency removed 2 ticks from cooldown
        return cooldown - (efficiency * 2);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.maxDamage / 3;
    }

    public int getSmashesOnHit(ItemStack stack, RandomSource random) {
        return 1;
    }

    public void onUse(Player player, ItemStack stack) {
        player.getCooldowns().addCooldown(this, Math.max(this.getUseCooldown(player, stack), 10));
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    /**
     * Called when a {@link net.minecraft.world.level.block.Block} is destroyed using this Item. Return {@code true} to
     * trigger the "Use Item" statistic.
     */
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
            pStack.hurtAndBreak(1, pEntityLiving, (p_43276_) -> {
                p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.BLOCK_EFFICIENCY || enchantment.category == EnchantmentCategory.BREAKABLE || enchantment.category == EnchantmentCategory.VANISHABLE;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(CommonComponents.space().append(Component.translatable(FORGE_COOLDOWN_LANG, InsaneLib.ONE_DECIMAL_FORMATTER.format(Math.max(this.getUseCooldown(null, pStack) / 20f, 0.5f))).withStyle(ChatFormatting.DARK_GREEN)));
        //pTooltipComponents.add(CommonComponents.space().append(Component.translatable(FORGE_DURABILITY_LANG, this.useDamageTaken).withStyle(ChatFormatting.DARK_GREEN)));
    }
}
