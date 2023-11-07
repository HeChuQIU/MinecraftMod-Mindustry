package com.hechu.mindustry.world.item.model;

import com.hechu.mindustry.world.item.drill.MechanicalDrill;

public class MechanicalDrillModel extends DrillModel<MechanicalDrill> {
    public MechanicalDrillModel() {
        super("geo/mechanical_drill.geo.json",
                "textures/block/mechanical_drill.png",
                "animations/mechanical_drill.animation.json");
    }
}
