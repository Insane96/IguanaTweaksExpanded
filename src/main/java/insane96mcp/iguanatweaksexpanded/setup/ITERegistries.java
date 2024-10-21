package insane96mcp.iguanatweaksexpanded.setup;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.data.function.EnchantWithTreasureFunction;
import insane96mcp.iguanatweaksexpanded.module.combat.fletching.Fletching;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.SoliumBoulderFeature;
import insane96mcp.iguanatweaksexpanded.module.mining.multiblockfurnaces.MultiBlockFurnaces;
import insane96mcp.iguanatweaksexpanded.module.mining.oregeneration.BeegOreVeinFeature;
import insane96mcp.iguanatweaksexpanded.module.mining.oregeneration.OreWithRandomPatchConfiguration;
import insane96mcp.shieldsplus.setup.SPItems;
import insane96mcp.shieldsplus.world.item.SPShieldItem;
import insane96mcp.shieldsplus.world.item.SPShieldMaterial;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ITERegistries {
    public static final List<DeferredRegister<?>> REGISTRIES = new ArrayList<>();

    public static final DeferredRegister<Block> BLOCKS = createRegistry(ForgeRegistries.BLOCKS);
    public static final DeferredRegister<Item> ITEMS = createRegistry(ForgeRegistries.ITEMS);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = createRegistry(ForgeRegistries.ENTITY_TYPES);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = createRegistry(ForgeRegistries.BLOCK_ENTITY_TYPES);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = createRegistry(ForgeRegistries.ENCHANTMENTS);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = createRegistry(ForgeRegistries.MOB_EFFECTS);
    public static final DeferredRegister<Potion> POTION = createRegistry(ForgeRegistries.POTIONS);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = createRegistry(ForgeRegistries.SOUND_EVENTS);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = createRegistry(ForgeRegistries.MENU_TYPES);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = createRegistry(ForgeRegistries.PARTICLE_TYPES);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = createRegistry(ForgeRegistries.RECIPE_SERIALIZERS);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = createRegistry(ForgeRegistries.RECIPE_TYPES);

    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTIONS = createRegistry(Registries.LOOT_FUNCTION_TYPE.location());
    public static final RegistryObject<LootItemFunctionType> ENCHANT_WITH_TREASURE = LOOT_FUNCTIONS.register("enchant_with_treasure", () -> new LootItemFunctionType(new EnchantWithTreasureFunction.Serializer()));

    public static final DeferredRegister<Feature<?>> FEATURES = createRegistry(ForgeRegistries.FEATURES);
    public static final RegistryObject<SoliumBoulderFeature> SOLIUM_BOUDLER_FEATURE = FEATURES.register("solium_boulder_feature", () -> new SoliumBoulderFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<BeegOreVeinFeature> ORE_WITH_SURFACE_FEATURE = FEATURES.register("ore_with_surface_feature", () -> new BeegOreVeinFeature(OreWithRandomPatchConfiguration.CODEC));

    public static DeferredRegister<PoiType> POI_TYPES = createRegistry("minecraft", ForgeRegistries.POI_TYPES);
    public static RegistryObject<PoiType> ARMORER = POI_TYPES.register("armorer", () -> {
        HashSet<BlockState> states = new HashSet<>(ForgeRegistries.POI_TYPES.getDelegateOrThrow(PoiTypes.ARMORER).get().matchingStates());
        RegistryObject<Block>[] barrelRegistryObjects = new RegistryObject[]{
                MultiBlockFurnaces.BLAST_FURNACE.block()
        };
        for (RegistryObject<Block> barrelRegistryObject : barrelRegistryObjects) {
            states.addAll(barrelRegistryObject.get().getStateDefinition().getPossibleStates());
        }
        return new PoiType(states, 1, 1);
    });
    public static RegistryObject<PoiType> FLETCHER = POI_TYPES.register("fletcher", () -> {
        HashSet<BlockState> states = new HashSet<>(ForgeRegistries.POI_TYPES.getDelegateOrThrow(PoiTypes.FLETCHER).get().matchingStates());
        RegistryObject<Block>[] barrelRegistryObjects = new RegistryObject[]{
                Fletching.FLETCHING_TABLE.block()
        };
        for (RegistryObject<Block> barrelRegistryObject : barrelRegistryObjects) {
            states.addAll(barrelRegistryObject.get().getStateDefinition().getPossibleStates());
        }
        return new PoiType(states, 1, 1);
    });


    static <R> DeferredRegister<R> createRegistry(ResourceKey<? extends Registry<R>> key) {
        DeferredRegister<R> register = DeferredRegister.create(key, IguanaTweaksExpanded.MOD_ID);
        REGISTRIES.add(register);
        return register;
    }

    static <R> DeferredRegister<R> createRegistry(String modId, IForgeRegistry<R> reg) {
        DeferredRegister<R> register = DeferredRegister.create(reg, modId);
        REGISTRIES.add(register);
        return register;
    }

    static <R> DeferredRegister<R> createRegistry(IForgeRegistry<R> reg) {
        DeferredRegister<R> register = DeferredRegister.create(reg, IguanaTweaksExpanded.MOD_ID);
        REGISTRIES.add(register);
        return register;
    }

    static <R> DeferredRegister<R> createRegistry(ResourceLocation registryName) {
        DeferredRegister<R> register = DeferredRegister.create(registryName, IguanaTweaksExpanded.MOD_ID);
        REGISTRIES.add(register);
        return register;
    }

    public static RegistryObject<SPShieldItem> registerShield(String id, SPShieldMaterial material) {
        Item.Properties properties = new Item.Properties().durability(material.durability).rarity(material.rarity);
        RegistryObject<SPShieldItem> shield = ITERegistries.ITEMS.register(id, () -> new SPShieldItem(material, properties));
        SPItems.SHIELDS.add(shield);
        return shield;
    }
}
