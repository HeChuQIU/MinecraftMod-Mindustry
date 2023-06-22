package com.hechu.mindustry.model;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.block.MechanicalDrillBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MechanicalDrillModel extends AnimatedGeoModel<MechanicalDrillBlockEntity> {
    private static final ResourceLocation modelResource = new ResourceLocation(Mindustry.MODID, "geo/mechanical_drill.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(Mindustry.MODID, "textures/block/mechanical_drill.png");
    private static final ResourceLocation animationResource = new ResourceLocation(Mindustry.MODID, "animations/mechanical_drill.animation.json");

    @Override
    public ResourceLocation getModelResource(MechanicalDrillBlockEntity object) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(MechanicalDrillBlockEntity object) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(MechanicalDrillBlockEntity animatable) {
        return animationResource;
    }
}
