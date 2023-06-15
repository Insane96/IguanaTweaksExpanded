package insane96mcp.survivalreimagined.data.generator;

import insane96mcp.survivalreimagined.SurvivalReimagined;
import insane96mcp.survivalreimagined.module.combat.feature.Fletching;
import insane96mcp.survivalreimagined.module.combat.feature.Knockback;
import insane96mcp.survivalreimagined.module.farming.feature.BoneMeal;
import insane96mcp.survivalreimagined.module.farming.feature.Hoes;
import insane96mcp.survivalreimagined.module.hungerhealth.feature.FoodDrinks;
import insane96mcp.survivalreimagined.module.hungerhealth.feature.NoHunger;
import insane96mcp.survivalreimagined.module.items.feature.CopperTools;
import insane96mcp.survivalreimagined.module.items.feature.FlintExpansion;
import insane96mcp.survivalreimagined.module.items.feature.ItemStats;
import insane96mcp.survivalreimagined.module.items.feature.StackSizes;
import insane96mcp.survivalreimagined.module.mining.feature.Durium;
import insane96mcp.survivalreimagined.module.mining.feature.Keego;
import insane96mcp.survivalreimagined.module.mining.feature.Solarium;
import insane96mcp.survivalreimagined.module.mining.feature.SoulSteel;
import insane96mcp.survivalreimagined.module.sleeprespawn.feature.Tiredness;
import insane96mcp.survivalreimagined.module.world.feature.Spawners;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SRItemTagsProvider extends ItemTagsProvider {
    public SRItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, tagLookupCompletableFuture, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //Vanilla
        tag(ItemTags.PICKAXES).add(FlintExpansion.PICKAXE.get(), Solarium.PICKAXE.get(), Durium.PICKAXE.get(), CopperTools.PICKAXE.get(), SoulSteel.PICKAXE.get(), Keego.PICKAXE.get());
        tag(ItemTags.AXES).add(FlintExpansion.AXE.get(), Solarium.AXE.get(), Durium.AXE.get(), CopperTools.AXE.get(), SoulSteel.AXE.get(), Keego.AXE.get());
        tag(ItemTags.SHOVELS).add(FlintExpansion.SHOVEL.get(), Solarium.SHOVEL.get(), Durium.SHOVEL.get(), CopperTools.SHOVEL.get(), SoulSteel.SHOVEL.get(), Keego.SHOVEL.get());
        tag(ItemTags.SWORDS).add(FlintExpansion.SWORD.get(), Solarium.SWORD.get(), Durium.SWORD.get(), CopperTools.SWORD.get(), SoulSteel.SWORD.get(), Keego.SWORD.get());
        tag(ItemTags.HOES).add(FlintExpansion.HOE.get(), Solarium.HOE.get(), Durium.HOE.get(), CopperTools.HOE.get(), SoulSteel.HOE.get(), Keego.HOE.get());

        tag(ItemTags.ARROWS).add(Fletching.QUARTZ_ARROW_ITEM.get(), Fletching.DIAMOND_ARROW_ITEM.get(), Fletching.EXPLOSIVE_ARROW_ITEM.get(), Fletching.TORCH_ARROW_ITEM.get());
        //Mod's
        tag(Hoes.DISABLED_HOES)
                .add(Items.WOODEN_HOE);

        tag(Tiredness.ENERGY_BOOST_ITEM_TAG)
                .add(Items.COOKIE)
                .addOptional(new ResourceLocation("farmersdelight:chocolate_pie_slice")).addOptional(new ResourceLocation("create:bar_of_chocolate")).addOptional(new ResourceLocation("create:chocolate_glazed_berries"));

        tag(ItemStats.NO_DAMAGE);
        tag(ItemStats.NO_EFFICIENCY);
        tag(Knockback.REDUCED_KNOCKBACK);

        tag(Spawners.SPAWNER_REACTIVATOR)
                .add(Items.ECHO_SHARD);

        tag(NoHunger.RAW_FOOD)
                .add(Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PORKCHOP, Items.MUTTON, Items.BEEF, Items.CHICKEN, Items.RABBIT, Items.ROTTEN_FLESH, Items.GOLDEN_CARROT);

        tag(StackSizes.NO_STACK_SIZE_CHANGES)
                .add(Items.ROTTEN_FLESH, Items.SPIDER_EYE);

        tag(FoodDrinks.FOOD_BLACKLIST);
        tag(BoneMeal.ITEM_BLACKLIST);
    }

    public static TagKey<Item> create(String tagName) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(SurvivalReimagined.MOD_ID, tagName));
    }
}
