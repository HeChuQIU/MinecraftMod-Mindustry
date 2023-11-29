package com.hechu.mindustry.world.level.block.turrets;

import com.hechu.mindustry.world.level.block.entity.turrets.SwarmerTurretBlockEntity;
import com.hechu.mindustry.world.level.block.entity.turrets.TurretBlockEntityBase;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.kiwi.block.IKiwiBlock;

public abstract class TurretBlockBase extends BaseEntityBlock implements IKiwiBlock {
    public TurretBlockBase(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof TurretBlockEntityBase turretBlockEntityBase) {
                if (pLevel1.isClientSide) {
                    turretBlockEntityBase.clientTick();
                } else {
                    turretBlockEntityBase.serverTick();
                }
            }
        };
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
