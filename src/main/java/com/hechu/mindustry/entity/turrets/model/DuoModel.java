package com.hechu.mindustry.entity.turrets.model;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.entity.turrets.Duo;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class DuoModel extends DefaultedEntityGeoModel<Duo> {

    public DuoModel(String name) {
        super(new ResourceLocation(Mindustry.MODID, name));
    }

    public String getModelPath() {
        return "geo/entity/turrets/duo.geo.json";
    }

    public String getTexturePath() {
        return "textures/entity/turrets/duo.png";
    }

    public String getAnimationPath() {
        return "animations/entity/turrets/duo.animation.json";
    }

    @Override
    public ResourceLocation getModelResource(Duo object) {
        return new ResourceLocation(Mindustry.MODID, getModelPath());
    }

    @Override
    public ResourceLocation getTextureResource(Duo object) {
        return new ResourceLocation(Mindustry.MODID, getTexturePath());
    }

    @Override
    public ResourceLocation getAnimationResource(Duo animatable) {
        return new ResourceLocation(Mindustry.MODID, getAnimationPath());
    }
}
