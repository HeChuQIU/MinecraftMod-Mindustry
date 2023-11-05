package com.hechu.mindustry.world.level.block.multiblock;

import org.apache.commons.lang3.ClassUtils;

import java.util.Arrays;

public class MultiblockBlockUtil {
    static {
        Arrays.stream(MultiblockCoreBlock.class.getPermittedSubclasses()).forEach(clazz -> {
            if (ClassUtils.isAssignable(clazz, MultiblockCoreBlock.class)) {

            }
        });
    }
}
