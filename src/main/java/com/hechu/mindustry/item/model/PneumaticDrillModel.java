package com.hechu.mindustry.item.model;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.item.PneumaticDrill;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PneumaticDrillModel extends GeoModel<PneumaticDrill> {
    private static final ResourceLocation modelResource = new ResourceLocation(Mindustry.MODID, "geo/2x2drill_template.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(Mindustry.MODID, "textures/block/2x2drill_template.png");
    private static final ResourceLocation animationResource = new ResourceLocation(Mindustry.MODID, "animations/2x2drill_template.animation.json");

    @Override
    public ResourceLocation getModelResource(PneumaticDrill object) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(PneumaticDrill object) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(PneumaticDrill animatable) {
        return animationResource;
    }
}
