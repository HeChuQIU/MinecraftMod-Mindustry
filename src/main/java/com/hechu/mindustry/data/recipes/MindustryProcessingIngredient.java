package com.hechu.mindustry.data.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MindustryProcessingIngredient extends AbstractIngredient {

    protected MindustryProcessingIngredient() {
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public @NotNull IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("count", itemStack.getCount());
        json.addProperty("item", ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
        return json;
    }

    @Override
    public boolean test(@Nullable ItemStack itemStack) {
        return super.test(itemStack) && itemStack.getCount() >= this.itemStack.getCount();
    }

    public static @NotNull MindustryProcessingIngredient fromJson(JsonElement json) {
        ItemStack itemStack;
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has("item") && jsonObject.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
        } else if (jsonObject.has("item")) {
            itemStack = itemStackFromJson(jsonObject);
        } else if (jsonObject.has("tag")) {
            //TODO
            throw new JsonParseException("MindustryProcessingIngredient 暂不支持 Tag");
//            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
//            TagKey<Item> tagkey = TagKey.create(Registries.ITEM, resourcelocation);
//            return new TagValue(tagkey);
        } else {
            throw new JsonParseException("An MindustryProcessingIngredient entry needs either a tag or an item");
        }
        return MindustryProcessingIngredient.of(itemStack);
    }

    private ItemStack itemStack;

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public ItemStack @NotNull [] getItems() {
        return new ItemStack[]{itemStack};
    }

    public static MindustryProcessingIngredient of(ItemLike item) {
        return of(new ItemStack(item));
    }

    public static MindustryProcessingIngredient of(ItemLike item, int count) {
        return of(new ItemStack(item, count));
    }

    public static @NotNull MindustryProcessingIngredient of(TagKey<Item> tag) {
        throw new NotImplementedException("MindustryProcessingIngredient 暂不支持 Tag");
    }

    public static MindustryProcessingIngredient of(ItemStack itemStack) {
        MindustryProcessingIngredient ingredient = new MindustryProcessingIngredient();
        ingredient.itemStack = itemStack;
        return ingredient;
    }

    public static Item itemFromJson(JsonObject itemObject) {
        String s = GsonHelper.getAsString(itemObject, "item");
        Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(s));
        if (item == null) {
            throw new JsonSyntaxException("Unknown item '" + s + "'");
        }
        if (item == Items.AIR) {
            throw new JsonSyntaxException("Empty ingredient not allowed here");
        } else {
            return item;
        }
    }

    public static ItemStack itemStackFromJson(JsonObject itemStackObject) {
        Item item = itemFromJson(itemStackObject);
        int count = GsonHelper.getAsInt(itemStackObject, "count", 1);
        return new ItemStack(item, count);
    }

    public static class Serializer implements IIngredientSerializer<MindustryProcessingIngredient> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull MindustryProcessingIngredient parse(@NotNull FriendlyByteBuf buffer) {
            return MindustryProcessingIngredient.of(buffer.readItem());
        }

        @Override
        public @NotNull MindustryProcessingIngredient parse(@NotNull JsonObject json) {
            return fromJson(json);
        }

        @Override
        public void write(@NotNull FriendlyByteBuf buffer, @NotNull MindustryProcessingIngredient ingredient) {
            buffer.writeItemStack(ingredient.getItemStack(), true);
        }
    }
}
