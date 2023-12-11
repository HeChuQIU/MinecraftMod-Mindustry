package com.hechu.mindustry.datagen.provider;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.data.recipes.MindustryProcessingRecipeBuilder;
import com.hechu.mindustry.kiwi.ItemModule;
import com.hechu.mindustry.kiwi.RecipeModule;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MindustryRecipeProvider extends RecipeProvider {

    public MindustryRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        new MindustryProcessingRecipeBuilder(RecipeModule.KILN_RECIPE_SERIALIZER.get(), RecipeCategory.MISC, Items.GLASS, 8)
                .requires(Items.SAND, 8)
                .processTick(80)
                .save(writer, new ResourceLocation(MindustryConstants.MOD_ID, "kiln_sand_to_glass"));
        new MindustryProcessingRecipeBuilder(RecipeModule.KILN_RECIPE_SERIALIZER.get(), RecipeCategory.MISC, ItemModule.SURGE_ALLOY.get(), 2)
                .requires(ItemModule.COPPER.get(),32)
                .processTick(10)
                .save(writer, new ResourceLocation(MindustryConstants.MOD_ID, "kiln_copper_to_surge_alloy"));
        new MindustryProcessingRecipeBuilder(RecipeModule.KILN_RECIPE_SERIALIZER.get(), RecipeCategory.MISC, Items.DIAMOND, 1)
                .requires(ItemModule.COAL.get(), 8)
                .processTick(10)
                .save(writer, new ResourceLocation(MindustryConstants.MOD_ID, "kiln_coal_to_diamond"));
    }
}