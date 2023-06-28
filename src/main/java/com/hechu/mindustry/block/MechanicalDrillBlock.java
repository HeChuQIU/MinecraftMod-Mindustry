package com.hechu.mindustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MechanicalDrillBlock extends DrillBlock<MechanicalDrillBlockEntity> {
    public static final String NAME = "mechanical_drill";

    public MechanicalDrillBlock() {
        super(Properties.of(Material.STONE).noOcclusion().destroyTime(3).strength(3.0F, 3.0F), MechanicalDrillBlockEntity.class);
    }
}
