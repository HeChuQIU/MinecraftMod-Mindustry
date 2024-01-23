package com.hechu.mindustry.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

//TODO: 传送带纹理与实际方向不匹配

/**
 * 表示传送带的形状。第一个方向是传送带的输出方向，其余方向是传送带的输入方向。
 */
public enum ConveyorShape implements StringRepresentable {
    NORTH_SOUTH("north_south"),
    SOUTH_NORTH("south_north"),
    WEST_EAST("west_east"),
    EAST_WEST("east_west"),
    NORTH_WEST("north_west"),
    NORTH_EAST("north_east"),
    SOUTH_WEST("south_west"),
    SOUTH_EAST("south_east"),
    WEST_NORTH("west_north"),
    WEST_SOUTH("west_south"),
    EAST_NORTH("east_north"),
    EAST_SOUTH("east_south"),
    NORTH_WEST_SOUTH("north_west_south"),
    NORTH_EAST_SOUTH("north_east_south"),
    NORTH_WEST_EAST("north_west_east"),
    SOUTH_WEST_NORTH("south_west_north"),
    SOUTH_EAST_NORTH("south_east_north"),
    SOUTH_WEST_EAST("south_west_east"),
    WEST_NORTH_EAST("west_north_east"),
    WEST_SOUTH_EAST("west_south_east"),
    WEST_NORTH_SOUTH("west_north_south"),
    EAST_NORTH_WEST("east_north_west"),
    EAST_SOUTH_WEST("east_south_west"),
    EAST_NORTH_SOUTH("east_north_south"),
    NORTH_ALL("north_all"),
    SOUTH_ALL("south_all"),
    WEST_ALL("west_all"),
    EAST_ALL("east_all"),
    ASCENDING_NORTH("ascending_north"),
    ASCENDING_SOUTH("ascending_south"),
    ASCENDING_WEST("ascending_west"),
    ASCENDING_EAST("ascending_east"),
    DESCENDING_NORTH("descending_north"),
    DESCENDING_SOUTH("descending_south"),
    DESCENDING_WEST("descending_west"),
    DESCENDING_EAST("descending_east");
    private final String name;

    ConveyorShape(String pName) {
        this.name = pName;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
