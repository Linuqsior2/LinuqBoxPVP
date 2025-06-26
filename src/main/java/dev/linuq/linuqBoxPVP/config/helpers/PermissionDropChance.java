package dev.linuq.linuqBoxPVP.config.helpers;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;

public class PermissionDropChance extends OkaeriConfig {
    @Comment("procenty")
    @CustomKey("chance")
    private double chance;

    public PermissionDropChance(double chance) {
        this.chance = chance;
    }

    public double getChance() {
        return chance;
    }
}

