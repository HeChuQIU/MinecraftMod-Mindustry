package com.hechu.mindustry.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.hechu.mindustry.MindustryConstants.logger;

public class Utils {
    public static boolean @NotNull [] checkDirection(Entity entity) {
        boolean[] data = new boolean[3];
        data[0] = Direction.getFacingAxis(entity, Direction.Axis.X).equals(Direction.EAST);
        data[1] = Direction.getFacingAxis(entity, Direction.Axis.Y).equals(Direction.UP);
        data[2] = Direction.getFacingAxis(entity, Direction.Axis.Z).equals(Direction.SOUTH);
        return data;
    }

    public static @NotNull List<BlockPos> checkPlayerFace(BlockPos pos, Vec3i size, Entity entity) {
        boolean[] faceData = checkDirection(entity);
        return checkPlayerFace(pos, size, faceData);
    }

    public static @NotNull List<BlockPos> checkPlayerFace(BlockPos pos, @NotNull Vec3i size, boolean[] faceData) {
        List<BlockPos> posList = new ArrayList<>();
        for (int i = 0; i < size.getX(); i++) {
            for (int j = 0; j < size.getY(); j++) {
                for (int k = 0; k < size.getZ(); k++) {
                    posList.add(pos.east(i * (faceData[0] ? 1 : -1))
                            .above(j * (faceData[1] ? 1 : -1))
                            .south(k * (faceData[2] ? 1 : -1)));
                }
            }
        }
        return posList;
    }

    public static void checkFolder(@NotNull Path folder) {
        if (!folder.toFile().isDirectory()) {
            try {
                Files.createDirectories(folder);
            } catch (IOException e) {
                logger.error("Fail to create config folder.", e);
            }
        }
    }
}
