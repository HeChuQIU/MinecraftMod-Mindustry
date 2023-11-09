package com.hechu.mindustry.datagen.block;

import com.hechu.mindustry.MindustryModule;
import com.hechu.mindustry.annotation.Block;
import com.hechu.mindustry.world.level.block.multiblock.MultiblockCoreBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TurretBlockStateGenerator extends BlockStateProvider {
    public TurretBlockStateGenerator(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        MindustryModule.getBlocks().stream()
                .filter(block -> block instanceof MultiblockCoreBlock)
                .map(block -> (MultiblockCoreBlock)block)
                .forEach(block -> {
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
