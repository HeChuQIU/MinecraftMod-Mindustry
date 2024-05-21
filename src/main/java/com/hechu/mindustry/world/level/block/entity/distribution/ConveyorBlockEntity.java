package com.hechu.mindustry.world.level.block.entity.distribution;

import com.hechu.mindustry.distribution.Conveyor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConveyorBlockEntity extends ModBlockEntity {
    public static final int MAX_ITEMS = 3;
    public static final int MAX_ITEMS_STACK_LIMIT = 1;

    public ConveyorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityModule.CONVEYOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public void tick() {

    }

    public void serverTick() {
//        if (level != null && level.getGameTime() % 20 == 0) {
//            for (Direction direction : getInputDirections()) {
//                BlockPos pos = worldPosition.relative(direction);
//                BlockEntity blockEntity = level.getBlockEntity(pos);
//                if (blockEntity != null && blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction.getOpposite()).isPresent()) {
//                    IItemHandler itemHandler = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction.getOpposite()).orElseThrow(NullPointerException::new);
//                    for (int i = 0; i < itemHandler.getSlots(); i++) {
//                        ItemStack stack = itemHandler.getStackInSlot(i);
//                        if (!stack.isEmpty()) {
//                            ItemStack insertItem = getItemHandler().insertItem(0, stack, false);
//                            itemHandler.extractItem(i, stack.getCount() - insertItem.getCount(), false);
//                            break;
//                        }
//                    }
//                }
//            }
//            for (int i = 0; i < MAX_ITEMS; i++) {
//                ItemStack stack = getItemHandler().getStackInSlot(i);
//                if (stack.isEmpty()) {
//                    for (int j = i + 1; j < MAX_ITEMS; j++) {
//                        ItemStack stack1 = getItemHandler().getStackInSlot(j);
//                        getItemHandler().setStackInSlot(j - 1, stack1);
//                        getItemHandler().setStackInSlot(j, ItemStack.EMPTY);
//                        break;
//                    }
//                }
//            }
//        }
        if (Conveyor.getTailConveyors().contains(this)) {
            if (!this.isTail())
                Conveyor.getTailConveyors().remove(this);
        } else if (this.isTail())
            Conveyor.getTailConveyors().add(this);

        tick();
    }

    public void clientTick() {
        tick();
    }

    LazyOptional<Capability<IItemHandlerModifiable>> itemHandler = LazyOptional.of(ItemHandler::new).cast();

    public void moveItems() {
        Conveyor.getComputingConveyors().add(this);
        ItemHandler items = getItemHandler();
        Optional<ItemHandler> outputItemHandler = getOutputConveyor().filter(c -> !Conveyor.getComputingConveyors().contains(c)).map(ConveyorBlockEntity::getItemHandler);
        if (outputItemHandler.isPresent()) {
            for (int i = 0; i < MAX_ITEMS; i++) {
                ItemStack stack = items.getStackInSlot(i);
                if (stack.isEmpty())
                    continue;

                ItemStack insertItem = outputItemHandler.get().insertItem(0, stack, false);
                items.extractItem(i, stack.getCount() - insertItem.getCount(), false);
            }
        }
        Conveyor.getComputingConveyors().remove(this);
        if (IntStream.range(0, MAX_ITEMS).mapToObj(items::getStackInSlot).anyMatch(i -> i.getCount() < MAX_ITEMS_STACK_LIMIT)) {
            getMainInputConveyor().ifPresent(ConveyorBlockEntity::moveItems);
        }
    }

    public Direction getOutputDirection() {
        return getBlockState().getValue(ConveyorBlock.SHAPE).getOutputDirection();
    }

    public Set<Direction> getInputDirections() {
        return getBlockState().getValue(ConveyorBlock.SHAPE).getInputDirections();
    }

    public Optional<Direction> getMainInputDirection() {
        return getBlockState().getValue(ConveyorBlock.SHAPE).getMainInputDirection();
    }

    public Optional<ConveyorBlockEntity> getOutputConveyor() {
        return Optional.ofNullable(level)
                .map(l -> l.getBlockEntity(worldPosition.relative(getOutputDirection())))
                .filter(b -> b instanceof ConveyorBlockEntity)
                .map(b -> (ConveyorBlockEntity) b)
//                .filter(c -> c.getMainInputDirection().filter(d -> d == getOutputDirection().getOpposite()).isPresent())
                ;
    }

    public Stream<ConveyorBlockEntity> getInputConveyors() {
        return Optional.ofNullable(level)
                .map(l -> getInputDirections().stream()
                        .map(d -> l.getBlockEntity(worldPosition.relative(d)))
                        .filter(b -> b instanceof ConveyorBlockEntity)
                        .map(b -> (ConveyorBlockEntity) b))
                .orElseGet(Stream::empty);
    }

    public Optional<ConveyorBlockEntity> getMainInputConveyor() {
        return getInputConveyors().filter(c -> c.getOutputDirection()
                == getBlockState().getValue(ConveyorBlock.SHAPE).getMainInputDirection().map(Direction::getOpposite).orElseGet(null)).findFirst();
    }

    /**
     * @return 这个传送带是否是尾部（终点）
     */
    public boolean isTail() {
        return getOutputConveyor().isEmpty();
    }

    public ConveyorBlockEntity getTail() {
        ConveyorBlockEntity tail = this;
        while (!tail.isTail()) {
            tail = tail.getOutputConveyor().orElseThrow(NullPointerException::new);
        }
        return tail;
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
