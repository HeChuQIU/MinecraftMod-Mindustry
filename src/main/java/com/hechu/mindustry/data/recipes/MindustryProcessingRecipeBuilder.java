package com.hechu.mindustry.data.recipes;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class MindustryProcessingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final List<MindustryProcessingIngredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final RecipeSerializer<?> recipeSerializerType;
    @Nullable
    private String group;
    private int processTick;

    public MindustryProcessingRecipeBuilder(RecipeSerializer<?> recipeSerializerType, RecipeCategory pCategory, ItemLike pResult, int pCount) {
        this.recipeSerializerType = recipeSerializerType;
        this.category = pCategory;
        this.result = pResult.asItem();
        this.count = pCount;
    }

    /**
     * Adds an ingredient that can be any item in the given tag.
     */
    public MindustryProcessingRecipeBuilder requires(TagKey<Item> pTag) {
        return this.requires(MindustryProcessingIngredient.of(pTag));
    }

    /**
     * Adds an ingredient of the given item.
     */
    public MindustryProcessingRecipeBuilder requires(ItemLike pItem) {
        return this.requires(pItem, 1);
    }

    /**
     * Adds the given ingredient multiple times.
     */
    public MindustryProcessingRecipeBuilder requires(ItemLike item, int count) {
        this.requires(MindustryProcessingIngredient.of(item, count));
        return this;
    }

    /**
     * Adds an ingredient.
     */
    public MindustryProcessingRecipeBuilder requires(MindustryProcessingIngredient ingredient) {
        return this.requires(ingredient, 1);
    }

    /**
     * Adds an ingredient multiple times.
     */
    public MindustryProcessingRecipeBuilder requires(MindustryProcessingIngredient ingredient, int count) {
        for (int i = 0; i < count; ++i) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public @NotNull MindustryProcessingRecipeBuilder unlockedBy(@NotNull String criterionName, @NotNull CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    public @NotNull MindustryProcessingRecipeBuilder group(@Nullable String groupname) {
        this.group = groupname;
        return this;
    }

    public @NotNull MindustryProcessingRecipeBuilder processTick(int processTick) {
        this.processTick = processTick;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, @NotNull ResourceLocation recipeId) {
//        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe",
                RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new MindustryProcessingRecipeBuilder.Result(recipeSerializerType,recipeId, this.result, this.count,
                this.group == null ? "" : this.group, processTick, this.ingredients,
                this.advancement, recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    /**
     * Makes sure that this recipe is valid and obtainable.
     */
    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final int processTick;
        private final List<MindustryProcessingIngredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(RecipeSerializer<?> recipeSerializerType, ResourceLocation pId, Item pResult, int pCount, String pGroup, int processTick, List<MindustryProcessingIngredient> pIngredients, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.recipeSerializerType = recipeSerializerType;
            this.id = pId;
            this.result = pResult;
            this.count = pCount;
            this.group = pGroup;
            this.processTick = processTick;
            this.ingredients = pIngredients;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        public void serializeRecipeData(@NotNull JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }
            pJson.addProperty("processTick", this.processTick);

            JsonArray jsonarray = new JsonArray();

            for(MindustryProcessingIngredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }

            pJson.add("ingredients", jsonarray);
            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                resultJson.addProperty("count", this.count);
            }

            pJson.add("result", resultJson);
        }

        /**
         * Gets the ID for the recipe.
         */
        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        private RecipeSerializer<?> recipeSerializerType;
        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return recipeSerializerType;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}