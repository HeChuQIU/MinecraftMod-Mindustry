package com.hechu.mindustry.item;

import com.hechu.mindustry.Mindustry;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MechanicalDrillModel extends GeoModel<MechanicalDrill> {
    private static final ResourceLocation modelResource = new ResourceLocation(Mindustry.MODID, "geo/mechanical_drill.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(Mindustry.MODID, "textures/block/mechanical_drill.png");
    private static final ResourceLocation animationResource = new ResourceLocation(Mindustry.MODID, "animations/mechanical_drill.animation.json");

    @Override
    public ResourceLocation getModelResource(MechanicalDrill object) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(MechanicalDrill object) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(MechanicalDrill animatable) {
        return animationResource;
    }
}
