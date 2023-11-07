package com.hechu.mindustry.world.level.block.model;

import com.hechu.mindustry.world.level.block.entity.PneumaticDrillBlockEntity;

public class PneumaticDrillModel extends DrillModel<PneumaticDrillBlockEntity> {

    public PneumaticDrillModel() {
        super(PneumaticDrillBlockEntity.NAME);
    }

    @Override
    public String getModelPath() {
        return "geo/pneumatic_drill.geo.json";
    }

    @Override
    public String getTexturePath() {
        return "textures/block/pneumatic_drill.png";
    }

    @Override
    public String getAnimationPath() {
        return "animations/pneumatic_drill.animation.json";
    }
}
