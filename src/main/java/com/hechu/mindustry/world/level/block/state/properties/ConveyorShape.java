package com.hechu.mindustry.world.level.block.state.properties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

//TODO: 传送带纹理与实际方向不匹配

/**
 * 表示传送带的形状。第一个方向是传送带的输出方向，其余方向是传送带的输入方向。
 */
public enum ConveyorShape implements StringRepresentable {
    NORTH_SOUTH("north_south"), SOUTH_NORTH("south_north"), WEST_EAST("west_east"), EAST_WEST("east_west"), NORTH_WEST("north_west"), NORTH_EAST("north_east"), SOUTH_WEST("south_west"), SOUTH_EAST("south_east"), WEST_NORTH("west_north"), WEST_SOUTH("west_south"), EAST_NORTH("east_north"), EAST_SOUTH("east_south"), NORTH_WEST_SOUTH("north_west_south"), NORTH_EAST_SOUTH("north_east_south"), NORTH_WEST_EAST("north_west_east"), SOUTH_WEST_NORTH("south_west_north"), SOUTH_EAST_NORTH("south_east_north"), SOUTH_WEST_EAST("south_west_east"), WEST_NORTH_EAST("west_north_east"), WEST_SOUTH_EAST("west_south_east"), WEST_NORTH_SOUTH("west_north_south"), EAST_NORTH_WEST("east_north_west"), EAST_SOUTH_WEST("east_south_west"), EAST_NORTH_SOUTH("east_north_south"), NORTH_ALL("north_all"), SOUTH_ALL("south_all"), WEST_ALL("west_all"), EAST_ALL("east_all"), ASCENDING_NORTH("ascending_north"), ASCENDING_SOUTH("ascending_south"), ASCENDING_WEST("ascending_west"), ASCENDING_EAST("ascending_east"), DESCENDING_NORTH("descending_north"), DESCENDING_SOUTH("descending_south"), DESCENDING_WEST("descending_west"), DESCENDING_EAST("descending_east");
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

    public Direction getOutputDirection() {
        switch (this) {
            case NORTH_SOUTH, NORTH_WEST, NORTH_ALL, NORTH_WEST_EAST, NORTH_EAST_SOUTH, NORTH_WEST_SOUTH, NORTH_EAST,
                 ASCENDING_NORTH, DESCENDING_NORTH -> {
                return Direction.NORTH;
            }
            case SOUTH_NORTH, SOUTH_ALL, SOUTH_WEST_EAST, SOUTH_EAST_NORTH, SOUTH_WEST_NORTH, SOUTH_EAST, SOUTH_WEST,
                 ASCENDING_SOUTH, DESCENDING_SOUTH -> {
                return Direction.SOUTH;
            }
            case WEST_EAST, WEST_ALL, WEST_NORTH_SOUTH, WEST_SOUTH_EAST, WEST_NORTH_EAST, WEST_SOUTH, WEST_NORTH,
                 ASCENDING_WEST, DESCENDING_WEST -> {
                return Direction.WEST;
            }
            case EAST_WEST, EAST_ALL, EAST_NORTH_SOUTH, EAST_SOUTH_WEST, EAST_NORTH_WEST, EAST_SOUTH, EAST_NORTH,
                 DESCENDING_EAST, ASCENDING_EAST -> {
                return Direction.EAST;
            }
        }
        return Direction.NORTH;
    }

    public Set<Direction> getInputDirections() {
        return switch (this) {
            case DESCENDING_SOUTH, ASCENDING_SOUTH, SOUTH_NORTH, WEST_NORTH, EAST_NORTH -> Set.of(Direction.NORTH);
            case DESCENDING_NORTH, ASCENDING_NORTH, NORTH_SOUTH, WEST_SOUTH, EAST_SOUTH -> Set.of(Direction.SOUTH);
            case DESCENDING_WEST, ASCENDING_WEST, NORTH_WEST, SOUTH_WEST, EAST_WEST -> Set.of(Direction.WEST);
            case DESCENDING_EAST, ASCENDING_EAST, NORTH_EAST, SOUTH_EAST, WEST_EAST -> Set.of(Direction.EAST);
            case NORTH_WEST_SOUTH, EAST_SOUTH_WEST -> Set.of(Direction.WEST, Direction.SOUTH);
            case NORTH_EAST_SOUTH, WEST_SOUTH_EAST -> Set.of(Direction.SOUTH, Direction.EAST);
            case WEST_NORTH_EAST, SOUTH_EAST_NORTH -> Set.of(Direction.EAST, Direction.NORTH);
            case WEST_NORTH_SOUTH, EAST_NORTH_SOUTH -> Set.of(Direction.NORTH, Direction.SOUTH);
            case NORTH_WEST_EAST, SOUTH_WEST_EAST -> Set.of(Direction.WEST, Direction.EAST);
            case SOUTH_WEST_NORTH, EAST_NORTH_WEST -> Set.of(Direction.NORTH, Direction.WEST);
            case NORTH_ALL -> Set.of(Direction.SOUTH, Direction.WEST, Direction.EAST);
            case SOUTH_ALL -> Set.of(Direction.NORTH, Direction.WEST, Direction.EAST);
            case WEST_ALL -> Set.of(Direction.NORTH, Direction.SOUTH, Direction.EAST);
            case EAST_ALL -> Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST);
        };
    }

    public Optional<Direction> getMainInputDirection() {
        return switch (this) {
            case DESCENDING_SOUTH, ASCENDING_SOUTH, SOUTH_NORTH, WEST_NORTH, EAST_NORTH, SOUTH_EAST_NORTH,
                 SOUTH_WEST_NORTH, SOUTH_ALL -> Optional.of(Direction.NORTH);
            case DESCENDING_NORTH, ASCENDING_NORTH, NORTH_SOUTH, WEST_SOUTH, EAST_SOUTH, NORTH_WEST_SOUTH,
                 NORTH_EAST_SOUTH, NORTH_ALL -> Optional.of(Direction.SOUTH);
            case DESCENDING_WEST, ASCENDING_WEST, NORTH_WEST, SOUTH_WEST, EAST_WEST, EAST_SOUTH_WEST, EAST_NORTH_WEST,
                 EAST_ALL -> Optional.of(Direction.WEST);
            case DESCENDING_EAST, ASCENDING_EAST, NORTH_EAST, SOUTH_EAST, WEST_EAST, WEST_SOUTH_EAST, WEST_NORTH_EAST,
                 WEST_ALL -> Optional.of(Direction.EAST);
            default -> Optional.empty();
        };
    }

    public BlockPos getOutputBlockPos(BlockPos origin) {
        switch (this) {
            case NORTH_SOUTH, NORTH_WEST, NORTH_ALL, NORTH_WEST_EAST, NORTH_EAST_SOUTH, NORTH_WEST_SOUTH, NORTH_EAST,
                 DESCENDING_NORTH -> {
                return origin.north();
            }
            case SOUTH_NORTH, SOUTH_ALL, SOUTH_WEST_EAST, SOUTH_EAST_NORTH, SOUTH_WEST_NORTH, SOUTH_EAST, SOUTH_WEST,
                 DESCENDING_SOUTH -> {
                return origin.south();
            }
            case WEST_EAST, WEST_ALL, WEST_NORTH_SOUTH, WEST_SOUTH_EAST, WEST_NORTH_EAST, WEST_SOUTH, WEST_NORTH,
                 DESCENDING_WEST -> {
                return origin.west();
            }
            case EAST_WEST, EAST_ALL, EAST_NORTH_SOUTH, EAST_SOUTH_WEST, EAST_NORTH_WEST, EAST_SOUTH, EAST_NORTH,
                 DESCENDING_EAST -> {
                return origin.east();
            }
            case ASCENDING_NORTH -> {
                return origin.north().above();
            }
            case ASCENDING_SOUTH -> {
                return origin.south().above();
            }
            case ASCENDING_WEST -> {
                return origin.west().above();
            }
            case ASCENDING_EAST -> {
                return origin.east().above();
            }
        }
        return origin;
    }

    public BlockPos[] getInputBlockPos(BlockPos origin) {
        switch (this) {
            case NORTH_SOUTH, EAST_SOUTH, WEST_SOUTH -> {
                return new BlockPos[]{origin.south()};
            }
            case SOUTH_NORTH, EAST_NORTH, WEST_NORTH -> {
                return new BlockPos[]{origin.north()};
            }
            case WEST_EAST, SOUTH_EAST, NORTH_EAST -> {
                return new BlockPos[]{origin.east()};
            }
            case EAST_WEST, SOUTH_WEST, NORTH_WEST -> {
                return new BlockPos[]{origin.west()};
            }
            case NORTH_WEST_SOUTH -> {
                return new BlockPos[]{origin.west(), origin.south()};
            }
            case NORTH_EAST_SOUTH -> {
                return new BlockPos[]{origin.east(), origin.south()};
            }
            case NORTH_WEST_EAST, SOUTH_WEST_EAST -> {
                return new BlockPos[]{origin.west(), origin.east()};
            }
            case SOUTH_WEST_NORTH -> {
                return new BlockPos[]{origin.west(), origin.north()};
            }
            case SOUTH_EAST_NORTH -> {
                return new BlockPos[]{origin.east(), origin.north()};
            }
            case WEST_NORTH_EAST -> {
                return new BlockPos[]{origin.north(), origin.east()};
            }
            case WEST_SOUTH_EAST -> {
                return new BlockPos[]{origin.south(), origin.east()};
            }
            case WEST_NORTH_SOUTH -> {
                return new BlockPos[]{origin.north(), origin.south()};
            }
            case EAST_NORTH_WEST -> {
                return new BlockPos[]{origin.north(), origin.west()};
            }
            case EAST_SOUTH_WEST -> {
                return new BlockPos[]{origin.south(), origin.west()};
            }
            case EAST_NORTH_SOUTH -> {
                return new BlockPos[]{origin.north(), origin.south()};
            }
            case NORTH_ALL -> {
                return new BlockPos[]{origin.south(), origin.west(), origin.east()};
            }
            case SOUTH_ALL -> {
                return new BlockPos[]{origin.north(), origin.west(), origin.east()};
            }
            case WEST_ALL -> {
                return new BlockPos[]{origin.north(), origin.south(), origin.east()};
            }
            case EAST_ALL -> {
                return new BlockPos[]{origin.north(), origin.south(), origin.west()};
            }
            case ASCENDING_NORTH -> {
                return new BlockPos[]{origin.south(), origin.south().below()};
            }
            case ASCENDING_SOUTH -> {
                return new BlockPos[]{origin.north(), origin.north().below()};
            }
            case ASCENDING_WEST -> {
                return new BlockPos[]{origin.east(), origin.east().below()};
            }
            case ASCENDING_EAST -> {
                return new BlockPos[]{origin.west(), origin.west().below()};
            }
            case DESCENDING_NORTH -> {
                return new BlockPos[]{origin.south(), origin.south().above()};
            }
            case DESCENDING_SOUTH -> {
                return new BlockPos[]{origin.north(), origin.north().above()};
            }
            case DESCENDING_WEST -> {
                return new BlockPos[]{origin.east(), origin.east().above()};
            }
            case DESCENDING_EAST -> {
                return new BlockPos[]{origin.west(), origin.west().above()};
            }
        }
        return new BlockPos[]{};
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
