package insane96mcp.iguanatweaksexpanded.mixin;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(PoiTypes.class)
public abstract class PoiTypesMixin {
    @Shadow
    private static PoiType register(Registry<PoiType> pKey, ResourceKey<PoiType> pValue, Set<BlockState> pMatchingStates, int pMaxTickets, int pValidRange) {
        return null;
    }

    @Shadow
    private static Set<BlockState> getBlockStates(Block pBlock) {
        return null;
    }

    @Inject(method = "bootstrap", at = @At("HEAD"))
    private static void onRegister(Registry<PoiType> pRegistry, CallbackInfoReturnable<PoiType> cir) {
        //register(pRegistry, PoiTypes.ARMORER, getBlockStates(MultiBlockFurnaces.BLAST_FURNACE.block().get()), 1, 1);
        //register(pRegistry, PoiTypes.FLETCHER, getBlockStates(Fletching.FLETCHING_TABLE.block().get()), 1, 1);
    }
}
