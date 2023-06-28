package com.hechu.mindustry.block.model;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.block.MechanicalDrill;
import com.hechu.mindustry.block.MechanicalDrillBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class MechanicalDrillModel extends DefaultedBlockGeoModel<MechanicalDrillBlockEntity> {
    private static final ResourceLocation modelResource = new ResourceLocation(Mindustry.MODID, "geo/mechanical_drill.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(Mindustry.MODID, "textures/block/mechanical_drill.png");
    private static final ResourceLocation animationResource = new ResourceLocation(Mindustry.MODID, "animations/mechanical_drill.animation.json");

    public MechanicalDrillModel() {
        super(new ResourceLocation(Mindustry.MODID, MechanicalDrill.NAME));
    }

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
