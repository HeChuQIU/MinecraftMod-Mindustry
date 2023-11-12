package com.hechu.mindustry.world.level.block.entity.multiblock;

import com.hechu.mindustry.data.recipes.MindustryProcessingRecipe;
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
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class MultiblockCraftingBlockEntity<C extends MultiblockCraftingBlockEntity<?>> extends MultiblockEntity implements Container, RecipeHolder {

    public MultiblockCraftingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        initQuickCheck();
    }

    protected RecipeManager.CachedCheck<C, ? extends MindustryProcessingRecipe<C>> quickCheck;

    protected abstract void initQuickCheck();

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) {
            return;
        }

        MindustryProcessingRecipe<?> recipe = quickCheck.getRecipeFor((C)this, level).orElse(null);

        if (recipe == null||recipe.equals(getRecipeUsed())) {
            currentProcessTick = 0;
        }

        setRecipeUsed(recipe);

        currentProcessTick++;

        if (currentProcessTick >= getCraftTicks()) {
            currentProcessTick = 0;
            IItemHandlerModifiable itemHandler = getItemHandler();
            for (int i = 0; i < getInputSlotCount(); i++) {
                int itemCost = recipe.getItemCostAtSlots(this)[i];
                itemHandler.extractItem(i, itemCost, false);
            }
            itemHandler.insertItem(getInputSlotCount(), recipe.getResultItem(null).copy(), false);
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

    public abstract int getInputSlotCount();
    public abstract int getOutputSlotCount();

    LazyOptional<Capability<IItemHandlerModifiable>> itemHandler = LazyOptional.of(() -> new ItemStackHandler(getInputSlotCount()+ getOutputSlotCount()) {

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

    public IItemHandlerModifiable getItemHandler() {
        return (IItemHandlerModifiable) itemHandler.cast().orElseThrow(NullPointerException::new);
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

    public abstract int getCraftTicks();

    protected int currentProcessTick = 0;

    public int getCurrentProcessTick() {
        if (!getBlockPos().equals(getMasterBlockPos()))
            if (level != null && getMasterBlockPos() != null) {
                BlockEntity blockEntity = level.getBlockEntity(getMasterBlockPos());
                if (blockEntity instanceof MultiblockCraftingBlockEntity)
                    return ((MultiblockCraftingBlockEntity) blockEntity).getCurrentProcessTick();
            }
        return currentProcessTick;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getContainerSize() {
        return getItemHandler().getSlots();
    }

    @Override
    public boolean isEmpty() {
        IItemHandler itemHandler = getItemHandler();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
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
        if (slot < getInputSlotCount() + getOutputSlotCount()) {
            return getItemHandler().getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param slot
     * @param amount
     */
    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        if (slot < getInputSlotCount() + getOutputSlotCount()) {
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
        if (slot < getInputSlotCount() + getOutputSlotCount()) {
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
        if (slot < getInputSlotCount() + getOutputSlotCount()) {
            getItemHandler().setStackInSlot(slot, stack);
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
        IItemHandlerModifiable itemHandler = getItemHandler();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    protected MindustryProcessingRecipe<?> recipeUsed;

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        recipeUsed = (MindustryProcessingRecipe<?>) recipe;
    }

    @Nullable
    @Override
    public MindustryProcessingRecipe<?> getRecipeUsed() {
        return recipeUsed;
    }
}
