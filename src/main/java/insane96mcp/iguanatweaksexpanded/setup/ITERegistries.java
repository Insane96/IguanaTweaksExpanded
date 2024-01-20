package insane96mcp.iguanatweaksexpanded.setup;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.data.condition.LootItemCurrentSeasonCondition;
import insane96mcp.iguanatweaksexpanded.module.items.solarium.SoliumBoulderFeature;
import insane96mcp.iguanatweaksexpanded.module.world.oregeneration.BeegOreVeinFeature;
import insane96mcp.iguanatweaksexpanded.module.world.oregeneration.OreWithRandomPatchConfiguration;
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
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ITERegistries {
    public static final List<DeferredRegister<?>> REGISTRIES = new ArrayList<>();

    public static final DeferredRegister<Block> BLOCKS = createRegistry(ForgeRegistries.BLOCKS);
    public static final DeferredRegister<Item> ITEMS = createRegistry(ForgeRegistries.ITEMS);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = createRegistry(ForgeRegistries.ENTITY_TYPES);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = createRegistry(ForgeRegistries.BLOCK_ENTITY_TYPES);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = createRegistry(ForgeRegistries.ENCHANTMENTS);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = createRegistry(ForgeRegistries.MOB_EFFECTS);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = createRegistry(ForgeRegistries.SOUND_EVENTS);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = createRegistry(ForgeRegistries.MENU_TYPES);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = createRegistry(ForgeRegistries.PARTICLE_TYPES);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = createRegistry(ForgeRegistries.RECIPE_SERIALIZERS);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = createRegistry(ForgeRegistries.RECIPE_TYPES);
    public static final DeferredRegister<Feature<?>> FEATURES = createRegistry(ForgeRegistries.FEATURES);
    public static final RegistryObject<SoliumBoulderFeature> SOLIUM_BOUDLER_FEATURE = FEATURES.register("solium_boulder_feature", () -> new SoliumBoulderFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<BeegOreVeinFeature> ORE_WITH_SURFACE_FEATURE = FEATURES.register("ore_with_surface_feature", () -> new BeegOreVeinFeature(OreWithRandomPatchConfiguration.CODEC));
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = createRegistry(Registries.LOOT_CONDITION_TYPE.location());

    public static final RegistryObject<LootItemConditionType> CURRENT_SEASON = LOOT_CONDITION_TYPES.register("current_season", () -> new LootItemConditionType(new LootItemCurrentSeasonCondition.Serializer()));


    static <R> DeferredRegister<R> createRegistry(ResourceKey<? extends Registry<R>> key) {
        DeferredRegister<R> register = DeferredRegister.create(key, IguanaTweaksExpanded.MOD_ID);
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
        return registerShield(id, material, false);
    }

    public static RegistryObject<SPShieldItem> registerShield(String id, SPShieldMaterial material, boolean fireResistant) {
        Item.Properties properties = new Item.Properties().durability(material.durability).rarity(material.rarity);
        RegistryObject<SPShieldItem> shield = ITERegistries.ITEMS.register(id, () -> new SPShieldItem(material, properties));
        SPItems.SHIELDS.add(shield);
        return shield;
    }
}
