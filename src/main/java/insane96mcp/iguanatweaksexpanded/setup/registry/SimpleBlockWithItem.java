package insane96mcp.iguanatweaksexpanded.setup.registry;

import insane96mcp.iguanatweaksexpanded.setup.SRRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public record SimpleBlockWithItem(RegistryObject<Block> block, RegistryObject<BlockItem> item) {
    public SimpleBlockWithItem(RegistryObject<Block> block, RegistryObject<BlockItem> item) {
        this.block = block;
        this.item = item;
    }

    public static insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem register(String id, Supplier<Block> blockSupplier) {
        return register(id, blockSupplier, new Item.Properties());
    }

    public static insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem register(String id, Supplier<Block> blockSupplier, Item.Properties itemProperties) {
        RegistryObject<Block> block = SRRegistries.BLOCKS.register(id, blockSupplier);
        RegistryObject<BlockItem> item = SRRegistries.ITEMS.register(id, () -> new BlockItem(block.get(), itemProperties));
        return new insane96mcp.iguanatweaksexpanded.setup.registry.SimpleBlockWithItem(block, item);
    }

    public RegistryObject<Block> block() {
        return this.block;
    }

    public RegistryObject<BlockItem> item() {
        return this.item;
    }
}