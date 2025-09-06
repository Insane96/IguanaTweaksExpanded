package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import insane96mcp.iguanatweaksexpanded.utils.LogHelper;
import insane96mcp.iguanatweaksreborn.module.experience.enchantments.EnchantmentsFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ISEEnsorcellerBlockEntity extends BlockEntity {
    public static final int ENOUGH_CHARGES = 5;

    private int charges;
    private int ensorcellingTicks;
    private ResourceLocation pendingEnchantId;
    private int pendingLevel;

    public ISEEnsorcellerBlockEntity(BlockPos pos, BlockState state) {
        super(EnchantingFeature.ENSORCELLER_BLOCK_ENTITY.get(), pos, state);
    }

    public void addCharges(int added) {
        charges += added;
        setChanged();
    }

    public boolean hasPendingEnchantment() {
        return pendingEnchantId != null && pendingLevel > 0;
    }

    public ItemStack createPendingBook(RandomSource random) {
        Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(pendingEnchantId);
        if (enchantment == null) {
            clearPending();
            LogHelper.warn("Invalid enchantment pending found. Discarding");
            return ItemStack.EMPTY;
        }
        ItemStack out = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, pendingLevel));
        clearPending();
        setChanged();
        return out;
    }

    private void clearPending() {
        pendingEnchantId = null;
        pendingLevel = 0;
    }

    private void tryRollEnchantment(Level level, BlockPos pos, BlockState state) {
        List<Enchantment> eligibleEnchantments = new ArrayList<>();
        for (Enchantment ench : ForgeRegistries.ENCHANTMENTS.getValues()) {
            if (ench.isCurse()
                    || !ench.isAllowedOnBooks()
                    || !ench.isDiscoverable()
                    || EnchantmentsFeature.isEnchantmentDisabled(ench))
                continue;
            eligibleEnchantments.add(ench);
        }
        if (eligibleEnchantments.isEmpty())
            return;
        Enchantment enchantment = eligibleEnchantments.get(level.random.nextInt(eligibleEnchantments.size()));
        int cost = EnchantingFeature.getCost(enchantment, 1);
        if (charges >= cost) {
            charges -= cost;
            pendingEnchantId = ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
            pendingLevel = 1;
            setChanged();
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ISEEnsorcellerBlockEntity be) {
        if (level.isClientSide)
            return;
        ServerLevel serverLevel = (ServerLevel) level;
        if (!be.hasPendingEnchantment() && be.charges >= ENOUGH_CHARGES) {
            be.ensorcellingTicks++;
            if (be.ensorcellingTicks >= EnchantingFeature.ensorceller$timeToGenerate) {
                be.ensorcellingTicks = 0;
                be.tryRollEnchantment(level, pos, state);
                serverLevel.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 2, 0.5f);
            }
        } else {
            be.ensorcellingTicks = 0;
        }

        double x = pos.getX() + 0.5f;
        double y = pos.getY() + 0.5f;
        double z = pos.getZ() + 0.5f;
        if (be.hasPendingEnchantment())
            serverLevel.sendParticles(ParticleTypes.ENCHANT, x, y, z, 10, 1, 1, 1, 1);
        else if (be.charges >= ENOUGH_CHARGES)
            serverLevel.sendParticles(ParticleTypes.CRIT, x, y, z, be.charges / 2, 1, 1, 1, 0.1);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Charges", charges);
        tag.putInt("EnsorcellingTicks", ensorcellingTicks);
        if (hasPendingEnchantment()) {
            tag.putString("PendingId", pendingEnchantId.toString());
            tag.putInt("PendingLvl", pendingLevel);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        charges = tag.getInt("Charges");
        ensorcellingTicks = tag.getInt("EnsorcellingTicks");
        if (tag.contains("PendingId")) {
            pendingEnchantId = ResourceLocation.parse(tag.getString("PendingId"));
            pendingLevel = tag.getInt("PendingLvl");
        } else {
            pendingEnchantId = null;
            pendingLevel = 0;
        }
    }
}
