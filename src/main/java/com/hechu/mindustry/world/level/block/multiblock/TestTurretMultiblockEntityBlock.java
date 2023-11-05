package com.hechu.mindustry.world.level.block.multiblock;

import com.hechu.mindustry.annotation.Block;
import com.hechu.mindustry.world.level.block.entity.multiblock.TestTurretMultiblockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

@Block(name = TestTurretMultiblockEntityBlock.NAME)
public class TestTurretMultiblockEntityBlock extends MultiblockEntityBlock<TestTurretMultiblockEntity> {
    public TestTurretMultiblockEntityBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F), TestTurretMultiblockEntity.class);
    }

    public static final String NAME = "test_turret_multiblock_entity";
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 17);

    private BlockPos masterBlockPos;

    @Override
    public IntegerProperty getPartProperty() {
        return PART;
    }

    @Override
    public Vec3i getSize() {
        return new Vec3i(3, 3, 2);
    }

    @Override
    public String getBlockName() {
        return NAME;
    }

    @Override
    public boolean isSingleTexture() {
        return true;
    }
}
