package com.hechu.mindustry.world.entity.turrets.model;

import com.hechu.mindustry.Static;
import com.hechu.mindustry.world.entity.turrets.Duo;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class DuoModel extends DefaultedEntityGeoModel<Duo> {

    public DuoModel(String name) {
        super(new ResourceLocation(Static.MOD_ID, name));
    }

    public String getModelPath() {
        return "geo/entity/turrets/duo.geo.json";
    }

    public String getTexturePath() {
        return "textures/entity/turrets/turret.png";
    }

    public String getAnimationPath() {
        return "animations/entity/turrets/duo.animation.json";
    }

    @Override
    public ResourceLocation getModelResource(Duo object) {
        return new ResourceLocation(Static.MOD_ID, getModelPath());
    }

    @Override
    public ResourceLocation getTextureResource(Duo object) {
        return new ResourceLocation(Static.MOD_ID, getTexturePath());
    }

    @Override
    public ResourceLocation getAnimationResource(Duo animatable) {
        return new ResourceLocation(Static.MOD_ID, getAnimationPath());
    }
}
