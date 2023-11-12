package com.hechu.mindustry.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hechu.mindustry.kiwi.RecipeModule;
import com.hechu.mindustry.world.level.block.entity.multiblock.MultiblockCraftingBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class MindustryProcessingRecipe implements Recipe<MultiblockCraftingBlockEntity> {
    private final ResourceLocation id;
    private final String group;
    private final CraftingBookCategory category;
    private final ItemStack result;
    private final NonNullList<MindustryProcessingIngredient> ingredients;

    public MindustryProcessingRecipe(ResourceLocation id, String group, CraftingBookCategory category, ItemStack result, NonNullList<MindustryProcessingIngredient> ingredients) {
        this.id = id;
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param blockEntity
     * @param level
     */
    @Override
    public boolean matches(@NotNull MultiblockCraftingBlockEntity blockEntity, @NotNull Level level) {
        IItemHandler itemHandler = blockEntity.getItemHandler();
        ItemStack stack = itemHandler.getStackInSlot(0);
        MindustryProcessingIngredient ingredient = ingredients.stream().filter(ing -> ing.test(stack)).findFirst().orElse(null);
        if (ingredient == null)
            return false;

        //TODO: 此处需修改
        itemHandler.extractItem(0, 1, false);
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MultiblockCraftingBlockEntity pContainer, @NotNull RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     *
     * @param pWidth
     * @param pHeight
     */
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeModule.MINDUSTRY_PROCESSING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeModule.MINDUSTRY_PROCESSING_RECIPE.get();
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    public static class Serializer implements RecipeSerializer<MindustryProcessingRecipe> {
        public Serializer() {
        }

        public @NotNull MindustryProcessingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject o) {
            String s = GsonHelper.getAsString(o, "group", "");
            NonNullList<MindustryProcessingIngredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(o, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else {
                CraftingBookCategory category = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(o, "category", (String) null), CraftingBookCategory.MISC);
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(o, "result"));
                return new MindustryProcessingRecipe(id, s, category, itemstack, nonnulllist);
            }
        }

        private static NonNullList<MindustryProcessingIngredient> itemsFromJson(JsonArray a) {
            NonNullList<MindustryProcessingIngredient> nonnulllist = NonNullList.create();

            for (int i = 0; i < a.size(); ++i) {
                MindustryProcessingIngredient ingredient = MindustryProcessingIngredient.fromJson(a.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        public MindustryProcessingRecipe fromNetwork(@NotNull ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            CraftingBookCategory craftingbookcategory = buffer.readEnum(CraftingBookCategory.class);
            int i = buffer.readVarInt();
            NonNullList<MindustryProcessingIngredient> nonnulllist = NonNullList.withSize(i, (MindustryProcessingIngredient) MindustryProcessingIngredient.EMPTY);

            for (int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, (MindustryProcessingIngredient) MindustryProcessingIngredient.fromNetwork(buffer));
            }

            ItemStack itemstack = buffer.readItem();
            return new MindustryProcessingRecipe(recipeId, s, craftingbookcategory, itemstack, nonnulllist);
        }

        public void toNetwork(FriendlyByteBuf buffer, MindustryProcessingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            buffer.writeVarInt(recipe.ingredients.size());

            for (MindustryProcessingIngredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.result);
        }
    }
}
