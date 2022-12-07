package com.nyuppo.block.enums;

public enum WildFlowerColour {
    WHITE("white"),
    ORANGE("orange"),
    MAGENTA("magenta"),
    LIGHT_BLUE("light_blue"),
    YELLOW("yellow"),
    LIME("lime"),
    PINK("pink"),
    CYAN("cyan"),
    PURPLE("purple"),
    BLUE("blue"),
    GREEN("green"),
    RED("red"),
    BLACK("black"),
    FIRE("fire"),
    ICE("ice"),
    GOLDEN("golden"),
    WEED("weed");

    private String name;

    WildFlowerColour(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}