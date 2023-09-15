package com.hechu.mindustry.world.level.block;

import com.hechu.mindustry.world.level.block.entity.PneumaticDrillBlockEntity;
import net.minecraft.core.Vec3i;

public class PneumaticDrillBlock extends DrillBlock<PneumaticDrillBlockEntity> {
    public static final String NAME = "pneumatic_drill";

    public PneumaticDrillBlock() {
        super(Properties.of().noOcclusion().destroyTime(3).strength(3.0F, 3.0F), PneumaticDrillBlockEntity.class);
    }

    @Override
    public Vec3i getSize() {
        return new Vec3i(2, 1, 2);
    }
}
