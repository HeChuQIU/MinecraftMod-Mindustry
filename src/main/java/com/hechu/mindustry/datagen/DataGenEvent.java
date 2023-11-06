package com.hechu.mindustry.datagen;

import com.hechu.mindustry.Static;
import com.hechu.mindustry.annotation.Block;
import com.hechu.mindustry.world.level.block.BlockRegister;
import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlock;
import com.hechu.mindustry.world.level.block.multiblock.MultiblockCoreBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvent {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(), new MindustrySimpleBlockModelProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustrySimpleBlockStateProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustryItemModelProvider(packOutput, existingFileHelper));
    }

    public static class MindustryItemModelProvider extends ItemModelProvider {
        public MindustryItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
            super(packOutput, Static.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            this.singleTexture("copper", new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Static.MOD_ID, "item/" + "copper"));
            this.withExistingParent(PowerNodeBlock.NAME, new ResourceLocation(Static.MOD_ID, "block/power_node"));
        }
    }

    public static class MindustrySimpleBlockModelProvider extends BlockModelProvider {
        public MindustrySimpleBlockModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
            super(packOutput, Static.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            this.cubeAll(PowerNodeBlock.NAME, new ResourceLocation(Static.MOD_ID, "block/power_node"));
            registerMutilationModels();
        }

        void registerMutilationModels() {
            BlockRegister.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .filter(block -> block instanceof MultiblockCoreBlock)
                    .map(block -> (MultiblockCoreBlock) block)
                    .forEach(block -> {
                        Vec3i size = block.getSize();
                        String name = block.getBlockName();
                        int sizeX = size.getX();
                        int sizeZ = size.getZ();
                        int sizeY = size.getY();
                        for (int x = 0; x < sizeX; x++) {
                            for (int z = 0; z < sizeZ; z++) {
                                for (int y = 0; y < sizeY; y++) {
                                    int i = x + z * sizeX + y * sizeX * sizeZ;
                                    int finalX = x;
                                    int finalZ = z;
                                    int finalY = y;
                                    var elementBuilder = block.isSingleTexture() ?
                                            this.getBuilder("block/" + name + "/" + name + "_" + i)
                                                    .texture("0", "block/" + name + "/" + name)
                                                    .texture("particle", new ResourceLocation("minecraft", "block/stone"))
                                                    .element().from(0, 0, 0).to(16, 16, 16)
                                            :
                                            this.getBuilder("block/" + name + "/" + name + "_" + i)
                                                    .texture("west", "block/" + name + "/" + name + "_west")
                                                    .texture("east", "block/" + name + "/" + name + "_east")
                                                    .texture("north", "block/" + name + "/" + name + "_north")
                                                    .texture("south", "block/" + name + "/" + name + "_south")
                                                    .texture("up", "block/" + name + "/" + name + "_up")
                                                    .texture("down", "block/" + name + "/" + name + "_down")
                                                    .texture("particle", new ResourceLocation("minecraft", "block/stone"))
                                                    .element().from(0, 0, 0).to(16, 16, 16);
                                    if (finalX == 0) {
                                        elementBuilder.face(Direction.WEST);
                                    }
                                    if (finalX == sizeX - 1) {
                                        elementBuilder.face(Direction.EAST);
                                    }
                                    if (finalZ == 0) {
                                        elementBuilder.face(Direction.NORTH);
                                    }
                                    if (finalZ == sizeZ - 1) {
                                        elementBuilder.face(Direction.SOUTH);
                                    }
                                    if (finalY == 0) {
                                        elementBuilder.face(Direction.DOWN);
                                    }
                                    if (finalY == sizeY - 1) {
                                        elementBuilder.face(Direction.UP);
                                    }
                                    elementBuilder
                                            .faces((direction, faceBuilder) -> {
                                                switch (direction) {
                                                    case WEST -> {
                                                        if (finalX == 0) {
                                                            float u1 = (16f * (finalZ)) / sizeZ;
                                                            float v1 = 16 - (16f * (finalY + 1)) / sizeY;
                                                            float u2 = u1 + 16f / sizeZ;
                                                            float v2 = v1 + 16f / sizeY;
//                                                        float u1 = (16f * finalZ) / sizeZ;
//                                                        float v1 = 16 - (16f * finalY) / sizeY;
//                                                        float u2 = u1 + 16f / sizeZ;
//                                                        float v2 = v1 - 16f / sizeY;
                                                            faceBuilder.uvs(u1, v1, u2, v2).texture(block.isSingleTexture() ? "#0" : "#west");
                                                        }
                                                    }
                                                    case EAST -> {
                                                        if (finalX == sizeX - 1) {
                                                            float u1 = 16 - (16f * (finalZ + 1)) / sizeZ;
                                                            float v1 = 16 - (16f * (finalY + 1)) / sizeY;
                                                            float u2 = u1 + 16f / sizeZ;
                                                            float v2 = v1 + 16f / sizeY;
//                                                        float u1 = 16 - (16f * finalZ) / sizeZ;
//                                                        float v1 = 16 - (16f * finalY) / sizeY;
//                                                        float u2 = u1 - 16f / sizeZ;
//                                                        float v2 = v1 - 16f / sizeY;
                                                            faceBuilder.uvs(u1, v1, u2, v2).texture(block.isSingleTexture() ? "#0" : "#east");
                                                        }
                                                    }
                                                    case NORTH -> {
                                                        if (finalZ == 0) {
                                                            float u1 = 16 - (16f * (finalX + 1)) / sizeX;
                                                            float v1 = 16 - (16f * (finalY + 1)) / sizeY;
                                                            float u2 = u1 + 16f / sizeX;
                                                            float v2 = v1 + 16f / sizeY;
//                                                        float u1 = 16 - (16f * finalX) / sizeX;
//                                                        float v1 = 16 - (16f * finalY) / sizeY;
//                                                        float u2 = u1 - 16f / sizeX;
//                                                        float v2 = v1 - 16f / sizeY;
                                                            faceBuilder.uvs(u1, v1, u2, v2).texture(block.isSingleTexture() ? "#0" : "#north");
                                                        }
                                                    }
                                                    case SOUTH -> {
                                                        if (finalZ == sizeZ - 1) {
                                                            float u1 = (16f * finalX) / sizeX;
                                                            float v1 = 16 - (16f * (finalY + 1)) / sizeY;
                                                            float u2 = u1 + 16f / sizeX;
                                                            float v2 = v1 + 16f / sizeY;
//                                                        float u1 = (16f * finalX) / sizeX;
//                                                        float v1 = 16 - (16f * finalY) / sizeY;
//                                                        float u2 = u1 + 16f / sizeX;
//                                                        float v2 = v1 - 16f / sizeY;
                                                            faceBuilder.uvs(u1, v1, u2, v2).texture(block.isSingleTexture() ? "#0" : "#south");
                                                        }
                                                    }
                                                    case DOWN -> {
                                                        if (finalY == 0) {
                                                            float u1 = 16 - (16f * finalX) / sizeX;
                                                            float v1 = 16 + (16f * (finalZ - 1)) / sizeZ;
                                                            float u2 = u1 - 16f / sizeX;
                                                            float v2 = v1 - 16f / sizeZ;
//                                                        float u1 = 16 - (16f * finalX) / sizeX;
//                                                        float v1 = (16f * finalZ) / sizeZ;
//                                                        float u2 = u1 - 16f / sizeX;
//                                                        float v2 = v1 + 16f / sizeZ;
                                                            faceBuilder.uvs(u1, v1, u2, v2).texture(block.isSingleTexture() ? "#0" : "#down");
                                                        }
                                                    }
                                                    case UP -> {
                                                        if (finalY == sizeY - 1) {
                                                            float u1 = 16 - (16f * finalX) / sizeX;
                                                            float v1 = 16 - (16f * finalZ) / sizeZ;
                                                            float u2 = u1 - 16f / sizeX;
                                                            float v2 = v1 - 16f / sizeZ;
                                                            faceBuilder.uvs(u1, v1, u2, v2).texture(block.isSingleTexture() ? "#0" : "#up");
                                                        }
                                                    }
                                                }
                                            })
                                            .end();
                                }
                            }
                        }
                    });
        }
    }

    public static class MindustrySimpleBlockStateProvider extends BlockStateProvider {
        public MindustrySimpleBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
            super(output, Static.MOD_ID, exFileHelper);
        }

        @Override
        protected void registerStatesAndModels() {
            this.simpleBlock(BlockRegister.POWER_NODE.get());
            registerMultiblockStatesAndModels();
        }

        void registerMultiblockStatesAndModels() {
            BlockRegister.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .filter(block -> block instanceof MultiblockCoreBlock)
                    .map(block -> (MultiblockCoreBlock) block)
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
}
