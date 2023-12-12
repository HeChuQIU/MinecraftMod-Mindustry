package com.hechu.mindustry.datagen.provider;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.kiwi.BlockModule;
import com.hechu.mindustry.kiwi.ItemModule;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MindustryItemModelProvider extends ItemModelProvider {
    public MindustryItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, MindustryConstants.MOD_ID, existingFileHelper);
    }
    @Override
    protected void registerModels() {
        ItemModule.getRegisterName().forEach(this::registerItem);
        BlockModule.getRegisterName().forEach(this::registerBlockItem);
    }
    private void registerItem(String name) {
        this.singleTexture(name, new ResourceLocation("item/generated"), "layer0", new ResourceLocation(MindustryConstants.MOD_ID, "item/%s".formatted(name)));
    }
    private void registerBlockItem(String name) {
        this.withExistingParent(name, new ResourceLocation(MindustryConstants.MOD_ID, "block/%s".formatted(name)));
    }
}
