package com.hechu.mindustry.world.level.block;

import com.hechu.mindustry.utils.Utils;
import com.hechu.mindustry.world.item.drill.Drill;
import com.hechu.mindustry.world.level.block.entity.DrillBlockEntity;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import snownee.kiwi.block.ModBlock;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class DrillBlock<TBlockEntity extends DrillBlockEntity> extends ModBlock implements EntityBlock {
    public static final Logger LOGGER = LogUtils.getLogger();
    protected Class<TBlockEntity> blockEntityClass;
    public static final EnumProperty<DrillPart> PART = EnumProperty.create("part", DrillPart.class);

    protected DrillBlock(Properties properties, Class<TBlockEntity> blockEntityClass) {
        super(properties);
        this.blockEntityClass = blockEntityClass;
        this.stateDefinition.any().setValue(PART, DrillPart.SLAVE);
    }

    protected DrillBlock(Properties properties, Class<TBlockEntity> blockEntityClass, DrillPart part) {
        super(properties);
        this.blockEntityClass = blockEntityClass;
        this.stateDefinition.any().setValue(PART, part);
    }

    public abstract Vec3i getSize();

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Block.box(0d, 0d, 0d, 16d, 16d, 16d);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART);
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        try {
            Constructor<TBlockEntity> constructor = blockEntityClass.getConstructor(BlockPos.class, BlockState.class);
            TBlockEntity blockEntity = constructor.newInstance(pos, state);
            if (state.getValue(PART).equals(DrillPart.MASTER)) {
                blockEntity.masterPos = pos;
            }
            return blockEntity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

/*    @Override
    public RenderShape getRenderShape(BlockState state) {
//        if (state.getValue(PART) != DrillPart.MASTER) {
//            return RenderShape.INVISIBLE;
//        } else {
//            return RenderShape.ENTITYBLOCK_ANIMATED;
//        }
        return RenderShape.INVISIBLE;
    }*/

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
//        if (state.getValue(PART) != DrillPart.MASTER) {
//            return null;
//        }
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof DrillBlockEntity drillBlockEntity) {
                drillBlockEntity.tick();
            }
        };
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity entity, @NotNull ItemStack stack) {
        if (entity == null)
            return;
        Vec3i size = getSize();
        List<BlockPos> posList = Utils.checkPlayerFace(pos, size, entity);
        posList.sort((pos1, pos2) -> {
            int c1 = Integer.compare(pos1.getX(), pos2.getX());
            if (c1 != 0)
                return c1;
            int c2 = Integer.compare(pos1.getY(), pos2.getY());
            if (c2 != 0)
                return c1;
            return Integer.compare(pos1.getZ(), pos2.getZ());
        });
        BlockPos masterPos = posList.get(0);
        for (BlockPos blockPos : posList) {
            if (blockPos.equals(masterPos))
                level.setBlock(blockPos, state.setValue(PART, DrillPart.MASTER), 3);
            else
                level.setBlock(blockPos, state.setValue(PART, DrillPart.SLAVE), 3);
            ((DrillBlockEntity) Objects.requireNonNull(level.getBlockEntity(blockPos))).masterPos = masterPos;
        }
    }

    @Override
    public void onRemove(@NotNull BlockState stateNow, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState stateBefore, boolean b) {
        BlockPos masterPos = ((DrillBlockEntity) Objects.requireNonNull(level.getBlockEntity(pos))).masterPos;
        if (!Objects.equals(masterPos, pos))
            if (masterPos != null) {
                level.destroyBlock(masterPos, false);
            }
        super.onRemove(stateNow, level, pos, stateBefore, b);
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
