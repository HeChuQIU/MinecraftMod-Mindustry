package com.hechu.mindustry.world.level.block.entity.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public abstract class MultiblockCraftingBlockEntity extends MultiblockEntity {

    public MultiblockCraftingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) {
            return;
        }
        if (currentCraftTicks < getCraftTicks()) {
            currentCraftTicks += 1;
        } else {
            currentCraftTicks = 0;
            List<ItemStack> inputs = getInputs();
            List<ItemStack> outputs = getOutputs();
            ItemStackHandler itemHandler = (ItemStackHandler) getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(NullPointerException::new);
            for (int i = 0; i < inputs.size(); i++) {
                ItemStack input = inputs.get(i);
                ItemStack stackInSlot = itemHandler.getStackInSlot(i);
                if (stackInSlot.getCount() < input.getCount()) {
                    return;
                }
            }
            for (int i = 0; i < inputs.size(); i++) {
                ItemStack input = inputs.get(i);
                ItemStack stackInSlot = itemHandler.getStackInSlot(i);
                stackInSlot.shrink(input.getCount());
            }
            for (int i = 0; i < outputs.size(); i++) {
                ItemStack output = outputs.get(i);
                ItemStack stackInSlot = itemHandler.getStackInSlot(i + inputs.size());
                if (stackInSlot.isEmpty()) {
                    itemHandler.setStackInSlot(i + inputs.size(), output.copy());
                } else {
                    stackInSlot.grow(output.getCount());
                }
            }
        }
    }

    LazyOptional<Capability<IItemHandler>> ItemHandler = LazyOptional.of(() -> new ItemStackHandler(getInputs().size() + getOutputs().size()) {

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot < getInputs().size()) {
                return ItemStack.EMPTY;
            }
            return super.extractItem(slot, amount, simulate);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot < getInputs().size()) {
                return getInputs().get(slot).getItem() == stack.getItem();
            }
            return false;
        }
    }).cast();

    public LazyOptional<Capability<IItemHandler>> getItemHandler() {
        return ItemHandler;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!getBlockPos().equals(getMasterBlockPos()))
            if (level != null && getMasterBlockPos() != null) {
                BlockEntity blockEntity = level.getBlockEntity(getMasterBlockPos());
                if (blockEntity != null)
                    return blockEntity.getCapability(cap, side);
            }
        if (/*side == Direction.UP && */cap == ForgeCapabilities.ITEM_HANDLER) {
            return getItemHandler().cast();
        }
        return LazyOptional.empty();
    }

    public abstract List<ItemStack> getInputs();

    public abstract List<ItemStack> getOutputs();

    public abstract int getCraftTicks();

    protected int currentCraftTicks = 0;

    public int getCurrentCraftTicks() {
        if (!getBlockPos().equals(getMasterBlockPos()))
            if (level != null && getMasterBlockPos() != null) {
                BlockEntity blockEntity = level.getBlockEntity(getMasterBlockPos());
                if (blockEntity instanceof MultiblockCraftingBlockEntity)
                    return ((MultiblockCraftingBlockEntity) blockEntity).getCurrentCraftTicks();
            }
        return currentCraftTicks;
    }
}
