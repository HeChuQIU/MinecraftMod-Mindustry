package com.hechu.mindustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.List;

public abstract class DrillBlock<TBlockEntity extends DrillBlockEntity> extends BaseEntityBlock {
    protected Class<TBlockEntity> blockEntityClass;
    public static final EnumProperty<DrillPart> PART = EnumProperty.create("part", DrillPart.class);
    protected @Nullable BlockPos masterPos;
    protected @Nullable List<BlockPos> slavesPos;

    protected DrillBlock(Properties properties, Class<TBlockEntity> blockEntityClass){
        super(properties);
        this.blockEntityClass = blockEntityClass;
        this.stateDefinition.any().setValue(PART, DrillPart.MASTER);
    }

    protected DrillBlock(Properties properties, Class<TBlockEntity> blockEntityClass, DrillPart part, @Nullable BlockPos masterPos, @Nullable List<BlockPos> slavesPos){
        super(properties);
        this.blockEntityClass = blockEntityClass;
        this.stateDefinition.any().setValue(PART, part);
        this.masterPos = masterPos;
        this.slavesPos = slavesPos;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART);
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        if (state.getValue(PART) != DrillPart.MASTER) {
            return null;
        }
        try {
            Constructor<TBlockEntity> constructor = blockEntityClass.getConstructor(BlockPos.class, BlockState.class);
            return constructor.newInstance(pos, state);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        if (state.getValue(PART) != DrillPart.MASTER) {
            return RenderShape.INVISIBLE;
        } else {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, BlockState state, @NotNull BlockEntityType<T> type){
        if(state.getValue(PART) != DrillPart.MASTER){
            return null;
        }
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof DrillBlockEntity drillBlockEntity) {
                drillBlockEntity.tick();
            }
        };
    }

    public enum DrillPart implements StringRepresentable {
        MASTER("master"),
        SLAVE("slave");

        private final String name;

        DrillPart(String s) {
            this.name = s;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
