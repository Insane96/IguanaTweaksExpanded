package insane96mcp.iguanatweaksreborn.module.stacksize.feature;

import com.google.common.collect.Lists;
import insane96mcp.iguanatweaksreborn.module.stacksize.utils.ItemStackSize;
import insane96mcp.iguanatweaksreborn.setup.Config;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Label(name = "Custom Stack Size Feature", description = "Change stack sizes as you please")
public class CustomStackSize extends Feature {

    private final ForgeConfigSpec.ConfigValue<List<? extends String>> customStackListConfig;
    public List<ItemStackSize> customStackList;

    public CustomStackSize(Module module) {
        super(Config.builder, module);
        Config.builder.comment(this.getDescription()).push(this.getName());
        customStackListConfig = Config.builder
                .comment("Define custom item stack sizes, one string = one item/tag. Those items are not affected by other changes such as 'Food Stack Reduction'.\nThe format is modid:itemid,stack_size or #modid:tagid,stack_size\nE.g. 'minecraft:stone,16' will make stone stack up to 16.\nE.g. '#forge:stone,16' will make all the stone types stack up to 16.\nValues over 64 or lower than 1 will not work.")
                .defineList("Custom Stack Sizes", Lists.newArrayList(), o -> o instanceof String);
        Config.builder.pop();
    }

    @Override
    public void loadConfig() {
        super.loadConfig();

        customStackList = ItemStackSize.parseStringList(customStackListConfig.get());
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        processCustomStackSizes();
    }

    private boolean processed = false;

    public void processCustomStackSizes() {
        if (!this.isEnabled())
            return;
        if (customStackList.isEmpty())
            return;
        if (processed)
            return;

        processed = true;

        for (ItemStackSize customStackSize : customStackList) {
            if (customStackSize.tag != null) {
                Tag<Item> tag = ItemTags.getAllTags().getTag(customStackSize.tag);
                if (tag == null)
                    continue;
                tag.getValues().forEach(item -> item.maxStackSize = Mth.clamp(customStackSize.stackSize, 1, 64));
            }
            else {
                Item item = ForgeRegistries.ITEMS.getValue(customStackSize.id);
                item.maxStackSize = Mth.clamp(customStackSize.stackSize, 1, 64);
            }
        }
    }
}