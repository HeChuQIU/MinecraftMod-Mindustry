package com.hechu.mindustry.world.level.block;

import com.hechu.mindustry.world.level.block.entity.MechanicalDrillBlockEntity;
import net.minecraft.core.Vec3i;

public class MechanicalDrillBlock extends DrillBlock<MechanicalDrillBlockEntity> {
    public static final String NAME = "mechanical_drill";

    public MechanicalDrillBlock() {
        super(Properties.of().noOcclusion().destroyTime(3).strength(3.0F, 3.0F), MechanicalDrillBlockEntity.class);
    }

    @Override
    public Vec3i getSize() {
        return  new Vec3i(2,1,2);
    }
}
