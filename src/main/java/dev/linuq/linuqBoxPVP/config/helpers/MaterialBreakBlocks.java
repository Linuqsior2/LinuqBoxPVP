package dev.linuq.linuqBoxPVP.config.helpers;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import org.bukkit.Material;

import java.util.List;

public class MaterialBreakBlocks extends OkaeriConfig {
    @Comment("Nazwa materialu")
    @CustomKey("material")
    private List<Material> material;

    public MaterialBreakBlocks(List<Material> material) {
        this.material = material;
    }

    public List<Material> getMaterial() {
        return material;
    }
}

