package com.hechu.mindustry.block;

import com.hechu.mindustry.block.model.MechanicalDrillModel;

public class MechanicalDrillBlockEntityRenderer extends DrillBlockEntityRenderer<MechanicalDrillBlockEntity> {

    public MechanicalDrillBlockEntityRenderer() {
        super(new MechanicalDrillModel());
    }
}
