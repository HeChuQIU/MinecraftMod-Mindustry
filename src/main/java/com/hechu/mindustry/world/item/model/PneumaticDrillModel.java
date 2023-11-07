package com.hechu.mindustry.world.item.model;

import com.hechu.mindustry.world.item.drill.PneumaticDrill;

public class PneumaticDrillModel extends DrillModel<PneumaticDrill> {
    public PneumaticDrillModel() {
        super("geo/pneumatic_drill.geo.json",
                "textures/block/pneumatic_drill.png",
                "animations/pneumatic_drill.animation.json");
    }
}
