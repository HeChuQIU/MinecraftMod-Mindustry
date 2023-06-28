package com.hechu.mindustry.block;

import net.minecraft.util.StringRepresentable;

public enum DrillPart implements StringRepresentable {
    MASTER("master"),
    SLAVE("slave");

    private final String name;

    private DrillPart(String s) {
        this.name = s;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
