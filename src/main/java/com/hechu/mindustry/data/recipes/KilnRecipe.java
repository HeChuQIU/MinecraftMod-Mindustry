package com.hechu.mindustry.data.recipes;

import com.hechu.mindustry.world.level.block.entity.multiblock.KilnBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class KilnRecipe extends MindustryProcessingRecipe<KilnBlockEntity> {
    public KilnRecipe(ResourceLocation id, String group, int processTick, ItemStack result, NonNullList<MindustryProcessingIngredient> ingredients) {
        super(id, group, processTick, result, ingredients);
    }
}
