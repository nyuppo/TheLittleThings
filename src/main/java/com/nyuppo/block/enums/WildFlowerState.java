package com.nyuppo.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum WildFlowerState implements StringIdentifiable {
    DEFAULT("default"),
    SPREADING("spreading"),
    BLOOMING("blooming"),
    GERMINATING("germinating");

    private final String name;

    WildFlowerState(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
