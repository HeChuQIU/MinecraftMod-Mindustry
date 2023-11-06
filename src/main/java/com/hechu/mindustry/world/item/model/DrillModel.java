package com.hechu.mindustry.world.item.model;

import com.hechu.mindustry.Static;
import com.hechu.mindustry.world.item.drill.Drill;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public abstract class DrillModel<TDrill extends Drill> extends GeoModel<TDrill> {

    public final String modelPath;
    public final String texturePath;
    public final String animationPath;

    public DrillModel(String modelPath, String texturePath, String animationPath) {
        this.modelPath = modelPath;
        this.texturePath = texturePath;
        this.animationPath = animationPath;
    }

    @Override
    public ResourceLocation getModelResource(TDrill object) {
        return new ResourceLocation(Static.MOD_ID, modelPath);
    }

    @Override
    public ResourceLocation getTextureResource(TDrill object){
        return new ResourceLocation(Static.MOD_ID, texturePath);
    }

    @Override
    public ResourceLocation getAnimationResource(TDrill animatable){
        return new ResourceLocation(Static.MOD_ID, animationPath);
    }
}
