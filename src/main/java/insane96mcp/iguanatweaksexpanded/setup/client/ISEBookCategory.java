package insane96mcp.iguanatweaksexpanded.setup.client;

import net.minecraft.util.StringRepresentable;

public enum ISEBookCategory implements StringRepresentable {
    FORGE_MISC("forge_misc"),
    BLAST_FURNACE_MISC("blast_furnace_misc"),
    SOUL_BLAST_FURNACE_MISC("soul_blast_furnace_misc"),
    UNKNOWN("unknown");

    public static final StringRepresentable.EnumCodec<ISEBookCategory> CODEC = StringRepresentable.fromEnum(ISEBookCategory::values);
    private final String name;

    ISEBookCategory(String name) {
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
