package com.hechu.mindustry.world.level.block.model;

import com.hechu.mindustry.world.level.block.MechanicalDrillBlock;
import com.hechu.mindustry.world.level.block.entity.MechanicalDrillBlockEntity;

public class MechanicalDrillModel extends DrillModel<MechanicalDrillBlockEntity> {

    public MechanicalDrillModel() {
        super(MechanicalDrillBlock.NAME);
    }

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
