package com.hechu.mindustry.world.level.block.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MultiblockCoreBlock extends Block {
    protected MultiblockCoreBlock(Properties properties) {
        super(properties);
    }

    public abstract IntegerProperty getPartProperty();
}
