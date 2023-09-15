package com.hechu.mindustry.world.level.block.model;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.world.level.block.entity.DrillBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public abstract class DrillModel<TDrillBlockEntity extends DrillBlockEntity> extends DefaultedBlockGeoModel<TDrillBlockEntity> {
    public DrillModel(String name) {
        super(new ResourceLocation(Mindustry.MODID, name));
    }

    public abstract String getModelPath();
    public abstract String getTexturePath();
    public abstract String getAnimationPath();

    @Override
    public ResourceLocation getModelResource(TDrillBlockEntity object) {
        return new ResourceLocation(Mindustry.MODID, getModelPath());
    }

    @Override
    public ResourceLocation getTextureResource(TDrillBlockEntity object){
        return new ResourceLocation(Mindustry.MODID, getTexturePath());
    }

    @Override
    public ResourceLocation getAnimationResource(TDrillBlockEntity animatable){
        return new ResourceLocation(Mindustry.MODID, getAnimationPath());
    }
}
