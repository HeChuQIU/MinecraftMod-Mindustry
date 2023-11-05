package com.hechu.mindustry.world.level.block.multiblock;

import com.hechu.mindustry.annotation.Block;
import com.hechu.mindustry.annotation.Multiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

@Block(name = TestMultiblockCoreBlock.NAME)
public class TestMultiblockCoreBlock extends MultiblockCoreBlock {
    public static final String NAME = "test_multiblock_core";

    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 15);

    public TestMultiblockCoreBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }

    public TestMultiblockCoreBlock(int part) {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F), part);
    }

    @Override
    public IntegerProperty getPartProperty() {
        return PART;
    }

    @Override
    public Vec3i getSize() {
        return new Vec3i(4, 2, 2);
    }

    @Override
    public String getBlockName() {
        return NAME;
    }

    @Override
    public boolean isSingleTexture() {
        return false;
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
//        if (level.isClientSide) return;
//
//        BlockPos.withinManhattanStream(pos, 2, 2, 2).forEach(blockPos -> {
//            if (level.getBlockState(blockPos).getBlock() == Blocks.DIAMOND_BLOCK) {
//                level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
//            }
//        });
    }
}
