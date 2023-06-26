package com.hechu.mindustry.block;

import com.hechu.mindustry.model.MechanicalDrillModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MechanicalDrillBlockEntityRenderer extends GeoBlockRenderer<MechanicalDrillBlockEntity> {

    public MechanicalDrillBlockEntityRenderer() {
        super(new MechanicalDrillModel());
    }
}
