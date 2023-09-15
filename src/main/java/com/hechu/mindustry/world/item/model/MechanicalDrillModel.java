package com.hechu.mindustry.world.item.model;

import com.hechu.mindustry.world.item.MechanicalDrill;

public class MechanicalDrillModel extends DrillModel<MechanicalDrill> {


    @Override
    public String getModelPath() {
        return "geo/2x2drill_template.geo.json";
    }

    @Override
    public String getTexturePath() {
        return "textures/block/mechanical_drill.png";
    }

    @Override
    public String getAnimationPath() {
        return "animations/2x2drill_template.animation.json";
    }
}
