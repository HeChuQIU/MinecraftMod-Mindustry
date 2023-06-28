package com.hechu.mindustry.item.model;

import com.hechu.mindustry.item.MechanicalDrill;

public class MechanicalDrillModel extends DrillModel<MechanicalDrill> {


    @Override
    public String getModelPath() {
        return "geo/mechanical_drill.geo.json";
    }

    @Override
    public String getTexturePath() {
        return "textures/block/mechanical_drill.png";
    }

    @Override
    public String getAnimationPath() {
        return "animations/mechanical_drill.animation.json";
    }
}
