package com.hechu.mindustry.datagen.provider;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.kiwi.BlockModule;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class MindustryBlockTagsProvider extends BlockTagsProvider {
    public MindustryBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MindustryConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        tag(Tags.Blocks.ORES_COAL).add(BlockModule.COAL_ORE_BLOCK.get());
        tag(Tags.Blocks.ORES_COPPER).add(BlockModule.COPPER_ORE_BLOCK.get());
    }
}

