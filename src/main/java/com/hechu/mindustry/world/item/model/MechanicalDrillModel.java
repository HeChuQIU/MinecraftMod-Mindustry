package com.hechu.mindustry.world.item.model;

import com.hechu.mindustry.world.item.drill.MechanicalDrill;

public class MechanicalDrillModel extends DrillModel<MechanicalDrill> {
    public MechanicalDrillModel() {
        super("geo/2x2drill_template.geo.json",
                "textures/block/mechanical_drill.png",
                "animations/2x2drill_template.animation.json");
    }
}
