package com.hechu.mindustry.world.level.block.entity.multiblock;

import com.hechu.mindustry.MindustryModule;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KilnBlockEntity extends MultiblockCraftingBlockEntity {
    public KilnBlockEntity(BlockPos pos, BlockState blockState) {
        super(MindustryModule.KILN_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static final String NAME = "kiln_block_entity";

    public static final List<ItemStack> INPUTS = List.of(new ItemStack[]{new ItemStack(Items.SAND, 8)});

    public static final List<ItemStack> OUTPUTS = List.of(new ItemStack[]{new ItemStack(Items.GLASS, 8)});

    @Override
    public List<ItemStack> getInputs() {
        return INPUTS;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return OUTPUTS;
    }

    @Override
    public int getCraftTicks() {
        return 80;
    }
}
