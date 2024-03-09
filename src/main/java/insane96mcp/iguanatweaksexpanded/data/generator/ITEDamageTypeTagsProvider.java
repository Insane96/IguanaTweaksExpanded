package insane96mcp.iguanatweaksexpanded.data.generator;

import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.module.experience.enchantments.enchantment.CurseOfExperience;
import insane96mcp.iguanatweaksexpanded.module.items.copper.CopperToolsExpansion;
import insane96mcp.iguanatweaksreborn.module.combat.PiercingPickaxes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ITEDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public ITEDamageTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(PiercingPickaxes.DOESNT_TRIGGER_PIERCING).add(CopperToolsExpansion.ELECTROCUTION_ATTACK);

        tag(CopperToolsExpansion.DOESNT_TRIGGER_ELECTROCUTION).addOptionalTag(PiercingPickaxes.PIERCING_DAMAGE_TYPE.location()).add(CopperToolsExpansion.ELECTROCUTION_ATTACK).add(CurseOfExperience.DAMAGE_TYPE);

        tag(DamageTypeTags.BYPASSES_ARMOR).add(CurseOfExperience.DAMAGE_TYPE);
    }

    public static TagKey<DamageType> create(String tagName) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(IguanaTweaksExpanded.MOD_ID, tagName));
    }
}
