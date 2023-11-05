package com.hechu.mindustry.world.level.block.entity.multiblock;

import com.hechu.mindustry.world.level.block.multiblock.MultiblockCoreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class MultiblockEntity extends BlockEntity {

    public MultiblockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if (masterBlockPos != null)
            tag.putIntArray("masterBlockPos", new int[]{masterBlockPos.getX(), masterBlockPos.getY(), masterBlockPos.getZ()});
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("masterBlockPos")) {
            int[] masterBlockPos = tag.getIntArray("masterBlockPos");
            this.masterBlockPos = new BlockPos(masterBlockPos[0], masterBlockPos[1], masterBlockPos[2]);
        }
    }

    public Vec3i getSize() {
        return getBlockState().getBlock() instanceof MultiblockCoreBlock ? ((MultiblockCoreBlock) getBlockState().getBlock()).getSize() : Vec3i.ZERO;
    }

    public boolean isMaster() {
        return getBlockPos().equals(getMasterBlockPos());
    }

    protected BlockPos masterBlockPos;

    public BlockPos getMasterBlockPos() {
        return masterBlockPos;
    }

    public void setMasterBlockPos(BlockPos pos) {
        masterBlockPos = pos;
    }

    public Vec3 getCenter() {
        return getMasterBlockPos().getCenter().subtract(0.5,0.5, 0.5).add(getSize().getX(), getSize().getY(), getSize().getZ());
    }
}
