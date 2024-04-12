package insane96mcp.iguanatweaksexpanded.module.world.coalfire;

import insane96mcp.iguanatweaksexpanded.module.Modules;
import insane96mcp.iguanatweaksexpanded.module.misc.ITEDataPacks;
import insane96mcp.iguanatweaksexpanded.setup.ITERegistries;
import insane96mcp.iguanatweaksexpanded.setup.IntegratedPack;
import insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem;
import insane96mcp.iguanatweaksreborn.data.lootmodifier.ReplaceLootModifier;
import insane96mcp.iguanatweaksreborn.module.world.coalfire.PilableLayerBlock;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.event.BlockBurntEvent;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

@Label(name = "Coal & Charcoal", description = "If this feature is enabled, 'Unlit campfire' from IguanaTweaks Reborn is set to true")
@LoadFeature(module = Modules.Ids.WORLD)
public class CoalCharcoal extends Feature {

    public static final SimpleBlockWithItem CHARCOAL_LAYER = SimpleBlockWithItem.register("charcoal_layer", () -> new PilableLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).strength(0.4F).sound(SoundType.MOSS_CARPET).isViewBlocking((state, blockGetter, pos) -> state.getValue(PilableLayerBlock.LAYERS) >= 8), Items.CHARCOAL));

    public static final RegistryObject<Item> FIRESTARTER = ITERegistries.ITEMS.register("firestarter", () -> new FirestarterItem(new Item.Properties().stacksTo(1).defaultDurability(11)));

    public static final SimpleBlockWithItem SOUL_SAND_HELLISH_COAL_ORE = SimpleBlockWithItem.register("soul_sand_hellish_coal_ore", () -> new HellishCoalOreBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).strength(2f).sound(SoundType.SOUL_SAND), UniformInt.of(1, 3)));
    public static final SimpleBlockWithItem SOUL_SOIL_HELLISH_COAL_ORE = SimpleBlockWithItem.register("soul_soil_hellish_coal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).strength(2f).sound(SoundType.SOUL_SOIL), UniformInt.of(1, 3)));
    public static final RegistryObject<Item> HELLISH_COAL = ITERegistries.ITEMS.register("hellish_coal", () -> new SimpleFuelItem(new Item.Properties().fireResistant(), 2400));

    @Config(min = 0d, max = 1d)
    @Label(name = "Charcoal from burnt logs chance", description = "Chance for logs to release charcoal layer when burnt")
    public static Double charcoalFromBurntLogsChance = 0.8d;

    @Config
    @Label(name = "No charcoal smelting and iron coal", description = "If enabled, a data pack will be enabled that removes the Charcoal recipe from smelting and makes Coal Ore require an Iron Pickaxe or better to mine.")
    public static Boolean noCharcoalSmeltingAndIronCoal = true;

    @Config
    @Label(name = "Two flint fire starter.Enabled", description = "If true, two flints (on per hand) can start a fire")
    public static Boolean twoFlintFireStarter = true;
    @Config(min = 0d, max = 1d)
    @Label(name = "Two flint fire starter.Ignite Chance", description = "Chance to ignite a block when using two flints")
    public static Double twoFlintFireStarterIgniteChance = 0.35d;
    @Config(min = 0d, max = 1d)
    @Label(name = "Two flint fire starter.Break Chance", description = "Chance for the flint to break when using two flints")
    public static Double twoFlintFireStarterBreakChance = 0.3d;

    public CoalCharcoal(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
        IntegratedPack.addPack(new IntegratedPack(PackType.SERVER_DATA, "charcoal_smelting_iron_coal", Component.literal("IguanaTweaks Expanded No Charcoal Smelting and Iron Coal"), () -> this.isEnabled() && !ITEDataPacks.disableAllDataPacks && noCharcoalSmeltingAndIronCoal));
    }

    @SubscribeEvent
    public void onBlockBurnt(BlockBurntEvent event) {
        if (!this.isEnabled()
                || charcoalFromBurntLogsChance == 0d)
            return;

        if (event.getLevel().getRandom().nextDouble() < charcoalFromBurntLogsChance
                && event.getState().is(BlockTags.LOGS_THAT_BURN)) {
            PilableFallingLayerEntity.fall((Level) event.getLevel(), event.getPos(), CHARCOAL_LAYER.block().get().defaultBlockState());
        }
    }

    @SubscribeEvent
    public void onBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
        if (!this.isEnabled()
                || !twoFlintFireStarter
                || event.getHand() != InteractionHand.MAIN_HAND
                || !event.getItemStack().is(Items.FLINT)
                || !event.getEntity().getOffhandItem().is(Items.FLINT)
                || event.getEntity().getCooldowns().isOnCooldown(Items.FLINT)
                || event.getLevel().isClientSide)
            return;

        double breakMain = event.getEntity().getRandom().nextDouble();
        double breakOff = event.getEntity().getRandom().nextDouble();
        double ignite = event.getEntity().getRandom().nextDouble();

        event.getEntity().swing(event.getHand(), true);
        event.setCanceled(true);
        event.getEntity().getCooldowns().addCooldown(event.getItemStack().getItem(), 15);

        if (ignite < twoFlintFireStarterIgniteChance) {
            UseOnContext context = new UseOnContext(event.getEntity(), event.getHand(), new BlockHitResult(event.getHitVec().getLocation(), event.getHitVec().getDirection(), event.getPos(), event.getHitVec().isInside()));
            //Yes, I copy-pasted FlintAndSteelItem#use
            Player player = context.getPlayer();
            Level level = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!CampfireBlock.canLight(blockstate) && !CandleBlock.canLight(blockstate) && !CandleCakeBlock.canLight(blockstate)) {
                BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
                if (BaseFireBlock.canBePlacedAt(level, blockpos1, context.getHorizontalDirection())) {
                    BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
                    level.setBlock(blockpos1, blockstate1, 11);
                    level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
                }
            }
            else {
                level.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.TRUE), 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockpos);
            }
            event.getLevel().playSound(null, event.getPos(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, event.getLevel().getRandom().nextFloat() * 0.4F + 0.8F);
        }
        //On fail, play a high-pitched sound
        else {
            event.getLevel().playSound(null, event.getPos(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, event.getLevel().getRandom().nextFloat() * 0.4F + 1.5F);
        }

        if (breakMain < twoFlintFireStarterBreakChance) {
            event.getItemStack().shrink(1);
            event.getEntity().broadcastBreakEvent(event.getHand());
            net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(event.getEntity(), event.getItemStack(), event.getHand());
        }
        if (breakOff < twoFlintFireStarterBreakChance) {
            event.getEntity().getOffhandItem().shrink(1);
            event.getEntity().broadcastBreakEvent(InteractionHand.OFF_HAND);
            net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(event.getEntity(), event.getEntity().getOffhandItem(), InteractionHand.OFF_HAND);
        }
    }

    private static final String path = "coal_fire/";

    public static void addGlobalLoot(GlobalLootModifierProvider provider) {
        provider.add(path + "replace_flint_and_steel", new ReplaceLootModifier.Builder(new LootItemCondition[] {
                    LocationCheck.checkLocation(new LocationPredicate.Builder().setDimension(Level.OVERWORLD)).build()
                }, Items.FLINT_AND_STEEL, FIRESTARTER.get())
                .applyToChestsOnly()
                .keepDurability()
                .build()
        );
    }
}