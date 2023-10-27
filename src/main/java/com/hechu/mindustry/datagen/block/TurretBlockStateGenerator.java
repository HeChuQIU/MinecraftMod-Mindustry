package com.hechu.mindustry.datagen.block;

import com.hechu.mindustry.annotation.Block;
import com.hechu.mindustry.annotation.Multiblock;
import com.hechu.mindustry.world.level.block.BlockRegister;
import com.hechu.mindustry.world.level.block.multiblock.MultiblockCoreBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class TurretBlockStateGenerator extends BlockStateProvider {
    public TurretBlockStateGenerator(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockRegister.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .filter(block -> block.getClass().isAnnotationPresent(Multiblock.class) && block.getClass().isAnnotationPresent(Block.class))
                .forEach(block -> {
                    Multiblock multiblock = block.getClass().getAnnotation(Multiblock.class);
                    String name = block.getClass().getAnnotation(Block.class).name();

                    this.getVariantBuilder(block)
                            .forAllStates(state -> {
                                IntegerProperty partProperty = ((MultiblockCoreBlock) block).getPartProperty();
                                int part = state.getValue(partProperty);
                                return ConfiguredModel.builder()
                                        .modelFile(this.models().getExistingFile(this.modLoc("block/" + name + "/" + name + "_" + part)))
                                        .build();
                            });
                });
    }
}