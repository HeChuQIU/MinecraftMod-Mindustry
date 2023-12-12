package com.hechu.mindustry.datagen.provider;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.annotation.Block;
import com.hechu.mindustry.kiwi.BlockModule;
import com.hechu.mindustry.kiwi.MutilBlockModule;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MindustryBlockStateProvider extends BlockStateProvider {
    public MindustryBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MindustryConstants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockModule.getBlocks().forEach(this::registerBlockState);
        registerMultiblockStatesAndModels();
    }

    private void registerBlockState(net.minecraft.world.level.block.Block block) {
        this.simpleBlock(block);
    }

    void registerMultiblockStatesAndModels() {
        MutilBlockModule.getBlocks().forEach(block -> {
            String name = block.getClass().getAnnotation(Block.class).name();
            this.getVariantBuilder(block)
                    .forAllStates(state -> {
                        IntegerProperty partProperty = block.getPartProperty();
                        int part = state.getValue(partProperty);
                        return ConfiguredModel.builder()
                                .modelFile(this.models().getExistingFile(this.modLoc("block/" + name + "/" + name + "_" + part)))
                                .build();
                    });
        });
    }
}