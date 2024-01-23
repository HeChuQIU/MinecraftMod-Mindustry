package com.hechu.mindustry.world.level.block.entity.distribution;

import com.hechu.mindustry.kiwi.BlockEntityModule;
import com.hechu.mindustry.world.level.block.distribution.ConveyorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.kiwi.block.entity.ModBlockEntity;

import java.util.Set;

public class ConveyorBlockEntity extends ModBlockEntity {
    public static final int MAX_ITEMS = 3;
    public static final int MAX_ITEMS_STACK_LIMIT = 1;

    public ConveyorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityModule.CONVEYOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public void tick() {

    }

    public void serverTick() {
        if (level != null && level.getGameTime() % 20 == 0) {
            for (Direction direction : getInputDirections()) {
                BlockPos pos = worldPosition.relative(direction);
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity != null && blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction.getOpposite()).isPresent()) {
                    IItemHandler itemHandler = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction.getOpposite()).orElseThrow(NullPointerException::new);
                    for (int i = 0; i < itemHandler.getSlots(); i++) {
                        ItemStack stack = itemHandler.getStackInSlot(i);
                        if (!stack.isEmpty()) {
                            ItemStack insertItem = getItemHandler().insertItem(0, stack, false);
                            itemHandler.extractItem(i, stack.getCount() - insertItem.getCount(), false);
                            break;
                        }
                    }
                }
            }
            for (int i = 0; i < MAX_ITEMS; i++) {
                ItemStack stack = getItemHandler().getStackInSlot(i);
                if (stack.isEmpty()) {
                    for (int j = i + 1; j < MAX_ITEMS; j++) {
                        ItemStack stack1 = getItemHandler().getStackInSlot(j);
                        getItemHandler().setStackInSlot(j - 1, stack1);
                        getItemHandler().setStackInSlot(j, ItemStack.EMPTY);
                        break;
                    }
                }
            }
        }
        tick();
    }

    public void clientTick() {
        tick();
    }

    LazyOptional<Capability<IItemHandlerModifiable>> itemHandler = LazyOptional.of(ItemHandler::new).cast();

    public Direction getOutputDirection() {
        return switch (getBlockState().getValue(ConveyorBlock.SHAPE)) {
            case NORTH_ALL, NORTH_EAST, NORTH_WEST, NORTH_SOUTH, ASCENDING_NORTH, DESCENDING_NORTH,
                    NORTH_WEST_EAST, NORTH_EAST_SOUTH, NORTH_WEST_SOUTH -> Direction.NORTH;
            case SOUTH_ALL, SOUTH_EAST, SOUTH_WEST, SOUTH_NORTH, SOUTH_WEST_EAST, SOUTH_EAST_NORTH,
                    SOUTH_WEST_NORTH, ASCENDING_SOUTH, DESCENDING_SOUTH -> Direction.SOUTH;
            case WEST_ALL, WEST_NORTH, WEST_SOUTH, WEST_EAST, WEST_NORTH_EAST, WEST_SOUTH_EAST,
                    WEST_NORTH_SOUTH, ASCENDING_WEST, DESCENDING_WEST -> Direction.WEST;
            case EAST_ALL, EAST_NORTH, EAST_SOUTH, EAST_WEST, EAST_NORTH_WEST, EAST_SOUTH_WEST,
                    EAST_NORTH_SOUTH, ASCENDING_EAST, DESCENDING_EAST -> Direction.EAST;
        };
    }

    public Set<Direction> getInputDirections() {
        return switch (getBlockState().getValue(ConveyorBlock.SHAPE)) {
            case DESCENDING_SOUTH, ASCENDING_SOUTH, SOUTH_NORTH, WEST_NORTH, EAST_NORTH -> Set.of(Direction.NORTH);
            case DESCENDING_NORTH, ASCENDING_NORTH, NORTH_SOUTH, WEST_SOUTH, EAST_SOUTH -> Set.of(Direction.SOUTH);
            case DESCENDING_WEST, ASCENDING_WEST, NORTH_WEST, SOUTH_WEST, EAST_WEST -> Set.of(Direction.WEST);
            case DESCENDING_EAST, ASCENDING_EAST, NORTH_EAST, SOUTH_EAST, WEST_EAST -> Set.of(Direction.EAST);
            case NORTH_WEST_SOUTH, EAST_SOUTH_WEST -> Set.of(Direction.WEST, Direction.SOUTH);
            case NORTH_EAST_SOUTH, WEST_SOUTH_EAST -> Set.of(Direction.SOUTH, Direction.EAST);
            case WEST_NORTH_EAST, SOUTH_EAST_NORTH -> Set.of(Direction.EAST, Direction.NORTH);
            case WEST_NORTH_SOUTH, EAST_NORTH_SOUTH -> Set.of(Direction.NORTH, Direction.SOUTH);
            case NORTH_WEST_EAST, SOUTH_WEST_EAST -> Set.of(Direction.WEST, Direction.EAST);
            case SOUTH_WEST_NORTH, EAST_NORTH_WEST -> Set.of(Direction.NORTH, Direction.WEST);
            case NORTH_ALL -> Set.of(Direction.SOUTH, Direction.WEST, Direction.EAST);
            case SOUTH_ALL -> Set.of(Direction.NORTH, Direction.WEST, Direction.EAST);
            case WEST_ALL -> Set.of(Direction.NORTH, Direction.SOUTH, Direction.EAST);
            case EAST_ALL -> Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST);
        };
    }

    public ItemHandler getItemHandler() {
        return (ItemHandler) itemHandler.cast().orElseThrow(NullPointerException::new);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            switch (side) {
                case UP, DOWN -> {
                    return itemHandler.cast();
                }
                case NORTH, SOUTH, WEST, EAST -> {
                    if (getOutputDirection() == side || getInputDirections().contains(side)) {
                        return itemHandler.cast();
                    }
                }
            }
        }
        return LazyOptional.empty();
    }

    @Override
    public void load(CompoundTag tag) {
        getItemHandler().deserializeNBT(tag.getCompound("items"));
        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("items", getItemHandler().serializeNBT());
        super.saveAdditional(tag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.put("items", getItemHandler().serializeNBT());
        return tag;
    }

    @Override
    protected void readPacketData(CompoundTag compoundTag) {
        getItemHandler().deserializeNBT(compoundTag.getCompound("items"));
    }

    @NotNull
    @Override
    protected CompoundTag writePacketData(CompoundTag compoundTag) {
        compoundTag.put("items", getItemHandler().serializeNBT());
        return compoundTag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        getItemHandler().deserializeNBT(tag.getCompound("items"));
    }

    public class ItemHandler extends ItemStackHandler {

        public ItemHandler() {
            super(MAX_ITEMS);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            ItemStack itemStack = super.insertItem(slot, stack, simulate);
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            }
            return itemStack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack itemStack = super.extractItem(slot, amount, simulate);
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            }
            return itemStack;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot < getSlots()) {
                return getStackInSlot(slot).isEmpty();
            }
            return false;
        }

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return MAX_ITEMS_STACK_LIMIT;
        }
    }
}
