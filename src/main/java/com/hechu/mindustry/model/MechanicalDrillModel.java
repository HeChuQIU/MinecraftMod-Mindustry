package com.hechu.mindustry.block;

import com.hechu.mindustry.Mindustry;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MechanicalDrillModel extends AnimatedGeoModel<MechanicalDrillBlockEntity> {
    private static final ResourceLocation modelResource = new ResourceLocation(Mindustry.MODID, "geo/mechanical_drill.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(Mindustry.MODID, "textures/item/mechanical_drill.png");
    private static final ResourceLocation animationResource = new ResourceLocation(Mindustry.MODID, "animations/mechanical_drill.rotation.json");
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
