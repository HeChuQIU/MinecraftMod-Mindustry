package com.hechu.mindustry.world.level.block.entity.multiblock;

import com.hechu.mindustry.kiwi.RecipeModule;
import com.hechu.mindustry.recipe.MindustryProcessingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
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

public abstract class MultiblockCraftingBlockEntity extends MultiblockEntity implements Container, RecipeHolder {

    public MultiblockCraftingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        this.quickCheck = RecipeManager.createCheck(RecipeModule.MINDUSTRY_PROCESSING_RECIPE.get());
    }

    protected final RecipeManager.CachedCheck<MultiblockCraftingBlockEntity, ? extends MindustryProcessingRecipe> quickCheck;

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) {
            return;
        }

        Recipe<?> recipe = quickCheck.getRecipeFor(this, level).orElse(null);

        if (recipe == null) {
            currentCraftTicks = 0;
            return;
        }

        if (currentCraftTicks < getCraftTicks()) {
            currentCraftTicks += 1;
        } else {
            currentCraftTicks = 0;
            IItemHandler itemHandler = getItemHandler();
            itemHandler.insertItem(1, recipe.getResultItem(null).copy(), false);
        }


//            List<ItemStack> inputs = getInputs();
//            List<ItemStack> outputs = getOutputs();
//            ItemStackHandler itemHandler = (ItemStackHandler) getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(NullPointerException::new);
//            for (int i = 0; i < inputs.size(); i++) {
//                ItemStack input = inputs.get(i);
//                ItemStack stackInSlot = itemHandler.getStackInSlot(i);
//                if (stackInSlot.getCount() < input.getCount()) {
//                    return;
//                }
//            }
//            for (int i = 0; i < inputs.size(); i++) {
//                ItemStack input = inputs.get(i);
//                ItemStack stackInSlot = itemHandler.getStackInSlot(i);
//                stackInSlot.shrink(input.getCount());
//            }
//            for (int i = 0; i < outputs.size(); i++) {
//                ItemStack output = outputs.get(i);
//                ItemStack stackInSlot = itemHandler.getStackInSlot(i + inputs.size());
//                if (stackInSlot.isEmpty()) {
//                    itemHandler.setStackInSlot(i + inputs.size(), output.copy());
//                } else {
//                    stackInSlot.grow(output.getCount());
//                }
//            }
    }

    LazyOptional<Capability<IItemHandler>> itemHandler = LazyOptional.of(() -> new ItemStackHandler(getInputs().size() + getOutputs().size()) {

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
//            if (slot < getInputs().size()) {
//                return ItemStack.EMPTY;
//            }
            return super.extractItem(slot, amount, simulate);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot < getSlots()) {
                return getStackInSlot(slot).getItem() == Items.AIR ||
                        getStackInSlot(slot).getItem() == stack.getItem()
                                && getStackInSlot(slot).getCount() + stack.getCount() <= getStackInSlot(slot).getMaxStackSize();
            }
            return false;
        }
    }).cast();

    public IItemHandler getItemHandler() {
        return (IItemHandler) itemHandler.cast().orElseThrow(NullPointerException::new);
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
            return itemHandler.cast();
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

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getContainerSize() {
        return getInputs().size() + getOutputs().size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : getInputs()) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        for (ItemStack itemStack : getOutputs()) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param slot
     */
    @Override
    public @NotNull ItemStack getItem(int slot) {
        if (slot < getInputs().size()) {
            return getInputs().get(slot);
        }
        return getOutputs().get(slot - getInputs().size());
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param slot
     * @param amount
     */
    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        if (slot < getInputs().size() + getOutputs().size()) {
            return getItemHandler().extractItem(slot, amount, false);
        }
        return ItemStack.EMPTY;
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param slot
     */
    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        if (slot < getInputs().size() + getOutputs().size()) {
            IItemHandler itemHandler = getItemHandler();
            ItemStack itemStack = itemHandler.getStackInSlot(slot).copy();
            itemHandler.extractItem(slot, itemStack.getCount(), false);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param slot
     * @param stack
     */
    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        if (slot < getInputs().size() + getOutputs().size()) {
            getItemHandler().insertItem(slot, stack, false);
        }
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     *
     * @param player
     */
    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        IItemHandler itemHandler = getItemHandler();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.extractItem(i, itemHandler.getStackInSlot(i).getCount(), false);
        }
    }

    protected Recipe<?> recipeUsed;

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        recipeUsed = recipe;
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return recipeUsed;
    }
}
