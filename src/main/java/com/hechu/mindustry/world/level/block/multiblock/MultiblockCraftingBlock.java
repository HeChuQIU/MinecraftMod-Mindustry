package com.hechu.mindustry.world.level.block.multiblock;

import com.hechu.mindustry.world.level.block.entity.multiblock.MultiblockCraftingBlockEntity;
import com.hechu.mindustry.world.level.block.entity.multiblock.MultiblockEntity;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MultiblockCraftingBlock<TBlockEntity extends MultiblockCraftingBlockEntity> extends MultiblockEntityBlock<TBlockEntity> {

    protected MultiblockCraftingBlock(Properties properties, Class<TBlockEntity> tBlockEntityClass) {
        super(properties, tBlockEntityClass);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof MultiblockCraftingBlockEntity && ((MultiblockCraftingBlockEntity) pBlockEntity).isMaster()) {
                if (!pLevel1.isClientSide) {
                    ((MultiblockCraftingBlockEntity) pBlockEntity).serverTick(pLevel1, pPos, pState1);
                }
            }
        };
    }
}
