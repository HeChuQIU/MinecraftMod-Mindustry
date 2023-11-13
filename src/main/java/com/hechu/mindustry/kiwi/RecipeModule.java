package com.hechu.mindustry.kiwi;

import com.hechu.mindustry.data.recipes.KilnRecipe;
import com.hechu.mindustry.data.recipes.MindustryProcessingIngredient;
import com.hechu.mindustry.data.recipes.MindustryProcessingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

@KiwiModule("recipe")
public class RecipeModule extends AbstractModule {
    @KiwiModule.Name("mindustry_kiln_recipe")
    public static final KiwiGO<RecipeType<KilnRecipe>> KILN_RECIPE = go(()->new RecipeType<>() { });
    @KiwiModule.Name("mindustry_kiln_recipe")
    public static final KiwiGO<RecipeSerializer<KilnRecipe>> KILN_RECIPE_SERIALIZER =
            go(() -> {
                try {
                    return new MindustryProcessingRecipe.Serializer<>
                            (KilnRecipe.class.getConstructor(ResourceLocation.class, String.class, int.class, ItemStack.class, NonNullList.class));
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });

}
