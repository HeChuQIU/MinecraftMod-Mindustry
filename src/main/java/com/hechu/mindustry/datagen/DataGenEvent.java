package com.hechu.mindustry.datagen;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.annotation.Block;
import com.hechu.mindustry.data.recipes.MindustryProcessingRecipeBuilder;
import com.hechu.mindustry.kiwi.BlockModule;
import com.hechu.mindustry.kiwi.ItemModule;
import com.hechu.mindustry.kiwi.MutilBlockModule;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvent {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        gen.addProvider(event.includeClient(), new MindustryBlockModelProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustryBlockStateProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustryBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustryItemModelProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeServer(), new MindustryRecipeProvider(packOutput));
    }
    // TODO 方块战利品生成
    public static class MindustryBlockTagsProvider extends BlockTagsProvider {
        public MindustryBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MindustryConstants.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            tag(Tags.Blocks.ORES_COAL).add(BlockModule.COAL_ORE_BLOCK.get());
            tag(Tags.Blocks.ORES_COPPER).add(BlockModule.COPPER_ORE_BLOCK.get());
        }
    }

    public static class MindustryItemModelProvider extends ItemModelProvider {
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

    public static class MindustryBlockModelProvider extends BlockModelProvider {
        public MindustryBlockModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
            super(packOutput, MindustryConstants.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            BlockModule.getRegisterName().forEach(this::registerBlockModel);
            registerMutilationModels();
        }

        private void registerBlockModel(String name) {
            this.cubeAll(name, new ResourceLocation(MindustryConstants.MOD_ID, "block/%s".formatted(name)));
        }

        void registerMutilationModels() {
            MutilBlockModule.getBlocks().forEach(block -> {
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

    public static class MindustryBlockStateProvider extends BlockStateProvider {
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

    public static class MindustryRecipeProvider extends RecipeProvider {

        public MindustryRecipeProvider(PackOutput output) {
            super(output);
        }

        @Override
        protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
            MindustryProcessingRecipeBuilder builder = new MindustryProcessingRecipeBuilder(RecipeCategory.MISC, Items.GLASS, 8);
            builder.requires(Items.SAND,8).unlockedBy("has_item", has(Items.SAND))
                    .save(writer, new ResourceLocation(MindustryConstants.MOD_ID, "kiln"));
        }
    }
}
