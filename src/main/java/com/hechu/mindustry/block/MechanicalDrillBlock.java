package com.hechu.mindustry.block;

public class MechanicalDrillBlock extends DrillBlock<MechanicalDrillBlockEntity> {
    public static final String NAME = "mechanical_drill";

    public MechanicalDrillBlock() {
        super(Properties.of().noOcclusion().destroyTime(3).strength(3.0F, 3.0F), MechanicalDrillBlockEntity.class);
    }
}
