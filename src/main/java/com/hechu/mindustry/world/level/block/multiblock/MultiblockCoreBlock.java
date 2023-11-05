package com.hechu.mindustry.world.level.block.multiblock;

import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public abstract class MultiblockCoreBlock extends Block {
    protected MultiblockCoreBlock(Properties properties) {
        super(properties);
        this.stateDefinition.any().setValue(getPartProperty(), 0);
    }

    protected MultiblockCoreBlock(Properties properties,int part) {
        super(properties);
        this.stateDefinition.any().setValue(getPartProperty(), part);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getPartProperty());
        super.createBlockStateDefinition(builder);
    }

    public abstract IntegerProperty getPartProperty();

    public abstract Vec3i getSize();

    public abstract String getBlockName();

    public abstract boolean isSingleTexture();
}
