package com.hechu.mindustry.world.level.block.multiblock;

import com.hechu.mindustry.world.level.block.DrillBlock;
import com.hechu.mindustry.world.level.block.entity.DrillBlockEntity;
import com.hechu.mindustry.world.level.block.entity.multiblock.MultiblockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class MultiblockEntityBlock<TBlockEntity extends MultiblockEntity> extends MultiblockCoreBlock implements EntityBlock {

    protected MultiblockEntityBlock(Properties properties, Class<TBlockEntity> blockEntityClass) {
        super(properties);
        this.blockEntityClass = blockEntityClass;
    }

    protected Class<TBlockEntity> blockEntityClass;

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        TBlockEntity blockEntity = null;
        try {
            Constructor<TBlockEntity> constructor = blockEntityClass.getConstructor(BlockPos.class, BlockState.class);
            blockEntity = constructor.newInstance(pos, state);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return blockEntity;
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity entity, @NotNull ItemStack stack) {
        if (entity == null)
            return;
        Vec3i size = getSize();
        List<BlockPos> posList = new ArrayList<>();
        boolean isEast = Direction.getFacingAxis(entity, Direction.Axis.X).equals(Direction.EAST);
        boolean isUp = Direction.getFacingAxis(entity, Direction.Axis.Y).equals(Direction.UP);
        boolean isSouth = Direction.getFacingAxis(entity, Direction.Axis.Z).equals(Direction.SOUTH);
        BlockPos masterPos = pos
                .east(isEast ? 0 : -(size.getX() - 1))
                .above(!isUp ? 0 : -(size.getY() - 1))
                .south(isSouth ? 0 : -(size.getZ() - 1));
        for (int x = 0; x < size.getX(); x++) {
            for (int z = 0; z < size.getZ(); z++) {
                for (int y = 0; y < size.getY(); y++) {
                    int part = x + z * size.getX() + y * size.getX() * size.getZ();
                    BlockPos blockPos = masterPos.east(x).above(y).south(z);
                    level.setBlock(blockPos, state.setValue(getPartProperty(), part), 3);
                    if (level.getBlockEntity(blockPos) instanceof MultiblockEntity multiblockEntity)
                        multiblockEntity.setMasterBlockPos(masterPos);
//                    if (blockPos.equals(masterPos))
//                        level.setBlock(blockPos, state.setValue(PART, DrillBlock.DrillPart.MASTER), 3);
//                    else
//                        level.setBlock(blockPos, state.setValue(PART, DrillBlock.DrillPart.SLAVE), 3);
                }
            }
        }
    }
    
    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide()) {
            BlockPos masterPos = level.getBlockEntity(pos) instanceof MultiblockEntity multiblockEntity ? multiblockEntity.getMasterBlockPos() : null;
            if (masterPos != null && state.getBlock() != newState.getBlock()) {
                if (!pos.equals(masterPos))
                    level.destroyBlock(masterPos, false);
                Vec3i size = getSize();
                for (int x = 0; x < size.getX(); x++) {
                    for (int z = 0; z < size.getZ(); z++) {
                        for (int y = 0; y < size.getY(); y++) {
                            BlockPos blockPos = masterPos.east(x).above(y).south(z);
                            if (blockPos.equals(pos))
                                continue;
                            level.destroyBlock(blockPos, false);
                        }
                    }
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
