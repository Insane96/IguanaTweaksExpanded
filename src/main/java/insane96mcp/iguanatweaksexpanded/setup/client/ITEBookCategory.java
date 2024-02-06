package insane96mcp.iguanatweaksexpanded.setup.client;

import net.minecraft.util.StringRepresentable;

public enum ITEBookCategory implements StringRepresentable {
    FORGE_MISC("forge_misc"),
    BLAST_FURNACE_MISC("blast_furnace_misc"),
    SOUL_BLAST_FURNACE_MISC("soul_blast_furnace_misc"),
    FLETCHING_MISC("fletching_misc"),
    UNKNOWN("unknown");

    public static final StringRepresentable.EnumCodec<ITEBookCategory> CODEC = StringRepresentable.fromEnum(ITEBookCategory::values);
    private final String name;

    ITEBookCategory(String name) {
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
