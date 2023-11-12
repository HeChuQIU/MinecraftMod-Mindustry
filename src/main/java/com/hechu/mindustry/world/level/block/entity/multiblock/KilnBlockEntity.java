package com.hechu.mindustry.world.level.block.entity.multiblock;

import com.hechu.mindustry.data.recipes.KilnRecipe;
import com.hechu.mindustry.kiwi.BlockEntityModule;
import com.hechu.mindustry.kiwi.RecipeModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class KilnBlockEntity extends MultiblockCraftingBlockEntity<KilnBlockEntity> {
    public KilnBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityModule.KILN_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static final String NAME = "kiln_block_entity";

    @Override
    protected void initQuickCheck() {
        quickCheck = RecipeManager.createCheck(RecipeModule.KILN_RECIPE.get());
    }

    @Override
    public int getInputSlotCount() {
        return 1;
    }

    @Override
    public int getOutputSlotCount() {
        return 1;
    }

    @Override
    public int getCraftTicks() {
        return 80;
    }
}
