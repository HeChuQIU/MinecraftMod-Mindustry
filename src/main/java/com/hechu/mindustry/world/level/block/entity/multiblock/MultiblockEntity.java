package com.hechu.mindustry.world.level.block.entity.multiblock;

import com.hechu.mindustry.world.level.block.multiblock.MultiblockCoreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import snownee.kiwi.block.entity.ModBlockEntity;

public abstract class MultiblockEntity extends ModBlockEntity {

    public MultiblockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag data) {
        readPacketData(data);
        super.load(data);
        if (data.contains("masterBlockPos")) {
            int[] masterBlockPos = data.getIntArray("masterBlockPos");
            this.masterBlockPos = new BlockPos(masterBlockPos[0], masterBlockPos[1], masterBlockPos[2]);
        }
    }

    @Override
    public void saveAdditional(CompoundTag data) {
        if (masterBlockPos != null)
            data.putIntArray("masterBlockPos", new int[]{masterBlockPos.getX(), masterBlockPos.getY(), masterBlockPos.getZ()});
        writePacketData(data);
        super.saveAdditional(data);
    }

    @Override
    protected void readPacketData(CompoundTag compoundTag) {

    }

    @NotNull
    @Override
    protected CompoundTag writePacketData(CompoundTag compoundTag) {
        return compoundTag;
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
