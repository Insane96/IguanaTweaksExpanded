package insane96mcp.iguanatweaksexpanded.data.criterion;

import net.minecraft.advancements.CriteriaTriggers;

public class ITETriggers {
    public static ActivateRespawnObeliskTrigger ACTIVATE_RESPAWN_OBELISK = CriteriaTriggers.register(new ActivateRespawnObeliskTrigger());
    public static OverweightCrateCarryTrigger OVERWEIGHT_CREATE_CARRY = CriteriaTriggers.register(new OverweightCrateCarryTrigger());

    public static void init() {
        //Do nothing, it's just to trigger the registration of CriteriaTriggers
    }
}
