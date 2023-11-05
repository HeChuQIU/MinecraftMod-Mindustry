package com.hechu.mindustry.datagen.block;

import com.hechu.mindustry.annotation.Block;
import com.hechu.mindustry.annotation.Multiblock;
import com.hechu.mindustry.world.level.block.BlockRegister;
import com.hechu.mindustry.world.level.block.multiblock.MultiblockCoreBlock;
import com.hechu.mindustry.world.level.block.multiblock.TestMultiblockCoreBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class TurretBlockModelGenerator extends BlockModelProvider {
    public TurretBlockModelGenerator(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
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
