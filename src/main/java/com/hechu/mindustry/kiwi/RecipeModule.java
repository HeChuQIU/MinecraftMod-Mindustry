package com.hechu.mindustry.kiwi;

import com.hechu.mindustry.recipe.MindustryProcessingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

@KiwiModule("recipe")
public class RecipeModule extends AbstractModule {
    @KiwiModule.Name("mindustry_processing_recipe")
    public static final KiwiGO<RecipeType<MindustryProcessingRecipe>> MINDUSTRY_PROCESSING_RECIPE = go(()->new RecipeType<>() { });
    @KiwiModule.Name("mindustry_processing_recipe")
    public static final KiwiGO<RecipeSerializer<MindustryProcessingRecipe>> MINDUSTRY_PROCESSING_SERIALIZER =  go(MindustryProcessingRecipe.Serializer::new);

}
