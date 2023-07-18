package com.hechu.mindustry.block;

public class PneumaticDrillBlock extends DrillBlock<PneumaticDrillBlockEntity> {
    public static final String NAME = "pneumatic_drill";

    public PneumaticDrillBlock() {
        super(Properties.of().noOcclusion().destroyTime(3).strength(3.0F, 3.0F), PneumaticDrillBlockEntity.class);
    }
}
