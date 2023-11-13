package com.hechu.mindustry.jade;

import com.hechu.mindustry.data.recipes.MindustryProcessingIngredient;
import com.hechu.mindustry.data.recipes.MindustryProcessingRecipe;
import com.hechu.mindustry.world.level.block.entity.multiblock.MultiblockCraftingBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.impl.ui.ProgressStyle;

public enum CraftingBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity instanceof MultiblockCraftingBlockEntity<?> craftingBlockEntity) {
            IElementHelper elements = tooltip.getElementHelper();

            tooltip.add(Component.empty());

            String recipeId = accessor.getServerData().getString("recipe");
            if (!recipeId.isEmpty()) {
                MindustryProcessingRecipe<?> recipe = (MindustryProcessingRecipe<?>) craftingBlockEntity.getLevel().getRecipeManager().byKey(new ResourceLocation(recipeId)).orElse(null);
                if (recipe != null) {
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        MindustryProcessingIngredient mindustryProcessingIngredient = (MindustryProcessingIngredient) ingredient;
                        IElement translate = elements.item(mindustryProcessingIngredient.getItemStack(), 0.8f)
                                .size(new Vec2(16, 16)).translate(new Vec2(0, -2));
                        tooltip.append(translate);
                    }

                    int craftTicks = recipe.getProcessTick();
                    int currentCraftTicks = accessor.getServerData().getInt("currentCraftTicks");

                    ProgressStyle progressStyle = new ProgressStyle();
                    progressStyle.color = 0xffffff | 0xff << 24;
//            progressStyle.color2 = 0x000000 | 0x88 << 24;
                    progressStyle.glowText = true;

                    BoxStyle boxStyle = new BoxStyle();
                    IElement progress = elements.progress((float) currentCraftTicks / craftTicks, Component.literal("          "), progressStyle, boxStyle, true);
                    tooltip.append(progress);

                    IElement icon = elements.item(recipe.getResult(), 0.8f)
                            .size(new Vec2(16, 16)).translate(new Vec2(0, -2));
                    tooltip.append(icon);
                }
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (accessor.getBlockEntity() instanceof MultiblockCraftingBlockEntity<?> blockEntity) {
            blockEntity = (MultiblockCraftingBlockEntity<?>) blockEntity.getMasterBlockEntity();
            data.putInt("currentCraftTicks", blockEntity.getCurrentProcessTick());
            MindustryProcessingRecipe<?> recipe = blockEntity.getRecipeUsing();
            data.putString("recipe", recipe == null ? "" : recipe.getId().toString());
        }
    }

    @Override
    public ResourceLocation getUid() {
        return MindustryPlugin.CraftingBlock;
    }
}
