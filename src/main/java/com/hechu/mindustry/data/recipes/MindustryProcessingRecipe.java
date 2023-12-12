package com.hechu.mindustry.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hechu.mindustry.kiwi.RecipeModule;
import com.hechu.mindustry.world.level.block.entity.multiblock.MultiblockCraftingBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class MindustryProcessingRecipe<T extends MultiblockCraftingBlockEntity<?>> implements Recipe<T> {
    private final ResourceLocation id;
    private final String group;
    private final int processTick;

    public int getProcessTick() {
        return processTick;
    }

    private final ItemStack result;

    public ItemStack getResult() {
        return result;
    }

    private final NonNullList<MindustryProcessingIngredient> ingredients;

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = net.minecraft.core.NonNullList.create();
        ingredients.stream().map(ingredient -> (Ingredient) ingredient).forEach(nonNullList::add);
        return nonNullList;
    }

    public MindustryProcessingRecipe(ResourceLocation id, String group, int processTick, ItemStack result, NonNullList<MindustryProcessingIngredient> ingredients) {
        this.id = id;
        this.group = group;
        this.processTick = processTick;
        this.result = result;
        this.ingredients = ingredients;
    }

    /**
     *
     * @param blockEntity
     * @return
     */
    public int[] getItemCostAtSlots(MultiblockCraftingBlockEntity<?> blockEntity) {
        IItemHandlerModifiable itemHandler = blockEntity.getItemHandler();
        int[] slots = new int[ingredients.size()];
        for (MindustryProcessingIngredient ingredient : ingredients) {
            for (int j = 0; j < blockEntity.getInputSlotCount(); j++) {
                ItemStack stack = itemHandler.getStackInSlot(j);
                if (ingredient.test(stack)) {
                    slots[j] = ingredient.getItemStack().getCount();
                    break;
                }
            }
        }
        return slots;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param blockEntity
     * @param level
     */
    @Override
    public boolean matches(@NotNull T blockEntity, @NotNull Level level) {
        IItemHandlerModifiable itemHandler = blockEntity.getItemHandler();
        return ingredients.stream().allMatch(ingredient -> {
            for (int i = 0; i < blockEntity.getInputSlotCount(); i++) {
                ItemStack stack = itemHandler.getStackInSlot(i);
                if (ingredient.test(stack)) {
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull T pContainer, @NotNull RegistryAccess pRegistryAccess) {
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
        return RecipeModule.KILN_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeModule.KILN_RECIPE.get();
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    public static class Serializer<T extends MindustryProcessingRecipe<?>> implements RecipeSerializer<T> {
        protected final Constructor<T> constructor;

        public Serializer(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        public @NotNull T fromJson(@NotNull ResourceLocation id, @NotNull JsonObject o) {
            String group = GsonHelper.getAsString(o, "group", "");
            int processTick = GsonHelper.getAsInt(o, "processTick", 1);
            NonNullList<MindustryProcessingIngredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(o, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(o, "result"));
                try {
                    return constructor.newInstance(id, group, processTick, itemstack, nonnulllist);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public JsonObject toJson(T recipe) {
            JsonObject jsonobject = new JsonObject();
            if (!recipe.getGroup().isEmpty()) {
                jsonobject.addProperty("group", recipe.getGroup());
            }

            JsonArray jsonarray = new JsonArray();

            recipe.getIngredients().stream().map(ingredient -> (MindustryProcessingIngredient) ingredient)
                    .map(MindustryProcessingIngredient::toJson).forEach(jsonarray::add);

            jsonobject.add("ingredients", jsonarray);
            JsonObject resultJson = new JsonObject();
            ItemStack recipeResult = recipe.getResult();
            resultJson.addProperty("item", ForgeRegistries.ITEMS.getKey(recipeResult.getItem()).toString());
            if (recipeResult.getCount() > 1) {
                resultJson.addProperty("count", recipeResult.getCount());
            }
            jsonobject.add("result", resultJson);
            jsonobject.addProperty("processTick", recipe.getProcessTick());
            return jsonobject;
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

        public T fromNetwork(@NotNull ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            int processTick = buffer.readInt();
            int i = buffer.readVarInt();
            NonNullList<MindustryProcessingIngredient> nonnulllist = NonNullList.withSize(i, (MindustryProcessingIngredient) MindustryProcessingIngredient.EMPTY);

            for (int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, (MindustryProcessingIngredient) MindustryProcessingIngredient.fromNetwork(buffer));
            }

            ItemStack itemstack = buffer.readItem();
            try {
                return constructor.newInstance(recipeId, s, processTick, itemstack, nonnulllist);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public void toNetwork(FriendlyByteBuf buffer, T recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeInt(recipe.getProcessTick());
            buffer.writeVarInt(recipe.getIngredients().size());

            recipe.getIngredients().stream().map(ingredient -> (MindustryProcessingIngredient) ingredient)
                    .forEach(ingredient -> ingredient.toNetwork(buffer));

            buffer.writeItem(recipe.getResult());
        }
    }
}
