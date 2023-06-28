package com.hechu.mindustry.block.model;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.block.MechanicalDrill;
import com.hechu.mindustry.block.PneumaticDrillBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class PneumaticDrillModel extends DefaultedBlockGeoModel<PneumaticDrillBlockEntity> {
    private static final ResourceLocation modelResource = new ResourceLocation(Mindustry.MODID, "geo/2x2drill_template.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(Mindustry.MODID, "textures/block/2x2drill_template.png");
    private static final ResourceLocation animationResource = new ResourceLocation(Mindustry.MODID, "animations/2x2drill_template.animation.json");

    public PneumaticDrillModel() {
        super(new ResourceLocation(Mindustry.MODID, MechanicalDrill.NAME));
    }

    @Override
    public ResourceLocation getModelResource(PneumaticDrillBlockEntity object) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(PneumaticDrillBlockEntity object) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(PneumaticDrillBlockEntity animatable) {
        return animationResource;
    }
}
