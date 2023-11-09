package com.hechu.mindustry.world.level.block.multiblock;

import com.hechu.mindustry.utils.Utils;
import com.hechu.mindustry.world.level.block.entity.multiblock.MultiblockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
        boolean[] faceData = Utils.checkDirection(entity);
//        List<BlockPos> posList = Utils.checkPlayerFace(pos, size, faceData);
        BlockPos masterPos = pos
                .east(faceData[0] ? 0 : -(size.getX() - 1))
                .above(!faceData[1] ? 0 : -(size.getY() - 1))
                .south(faceData[2] ? 0 : -(size.getZ() - 1));
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
