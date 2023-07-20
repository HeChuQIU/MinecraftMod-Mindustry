package com.hechu.mindustry.block.model;

import com.hechu.mindustry.block.PneumaticDrillBlockEntity;

public class PneumaticDrillModel extends DrillModel<PneumaticDrillBlockEntity> {

    public PneumaticDrillModel() {
        super(PneumaticDrillBlockEntity.NAME);
    }

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
