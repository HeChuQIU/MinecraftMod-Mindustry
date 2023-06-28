package com.hechu.mindustry.item.model;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.item.Drill;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public abstract class DrillModel<TDrill extends Drill> extends GeoModel<TDrill> {
    public abstract String getModelPath();
    public abstract String getTexturePath();
    public abstract String getAnimationPath();

    @Override
    public ResourceLocation getModelResource(TDrill object) {
        return new ResourceLocation(Mindustry.MODID, getModelPath());
    }

    @Override
    public ResourceLocation getTextureResource(TDrill object){
        return new ResourceLocation(Mindustry.MODID, getTexturePath());
    }

    @Override
    public ResourceLocation getAnimationResource(TDrill animatable){
        return new ResourceLocation(Mindustry.MODID, getAnimationPath());
    }
}
