package insane96mcp.iguanatweaksexpanded.data.generator;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.BloodPact;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.MagicProtection;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.curse.CurseOfExperience;
import insane96mcp.iguanatweaksexpanded.module.items.copper.CopperExpansion;
import insane96mcp.iguanatweaksreborn.module.combat.PiercingDamage;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ITEDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public ITEDamageTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(PiercingDamage.DOESNT_TRIGGER_PIERCING).add(CopperExpansion.ELECTROCUTION_ATTACK).add(CurseOfExperience.DAMAGE_TYPE);

        tag(CopperExpansion.DOESNT_TRIGGER_ELECTROCUTION).addOptionalTag(PiercingDamage.PIERCING_DAMAGE_TYPE.location()).add(CopperExpansion.ELECTROCUTION_ATTACK).add(CurseOfExperience.DAMAGE_TYPE);

        tag(DamageTypeTags.BYPASSES_ARMOR).add(CurseOfExperience.DAMAGE_TYPE, BloodPact.DAMAGE_TYPE);
        tag(DamageTypeTags.BYPASSES_COOLDOWN).add(BloodPact.DAMAGE_TYPE);

        tag(MagicProtection.SOURCES_REDUCED).add(DamageTypes.MAGIC, DamageTypes.INDIRECT_MAGIC);
    }

    public static TagKey<DamageType> create(String tagName) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, tagName));
    }
}
