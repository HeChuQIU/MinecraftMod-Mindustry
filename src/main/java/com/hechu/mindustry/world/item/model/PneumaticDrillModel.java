package com.hechu.mindustry.world.item.model;

import com.hechu.mindustry.world.item.PneumaticDrill;

public class PneumaticDrillModel extends DrillModel<PneumaticDrill> {
    @Override
    public String getModelPath() {
        return "geo/2x2drill_template.geo.json";
    }

    @Override
    public String getTexturePath() {
        return "textures/block/2x2drill_template.png";
    }

    @Override
    public String getAnimationPath() {
        return "animations/2x2drill_template.animation.json";
    }
}
