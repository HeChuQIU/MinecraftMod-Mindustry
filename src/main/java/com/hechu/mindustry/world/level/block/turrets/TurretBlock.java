package com.hechu.mindustry.world.level.block.turrets;

import com.hechu.mindustry.utils.capabilities.MindustryCapabilities;
import com.hechu.mindustry.world.level.block.entity.BlockEntityRegister;
import com.hechu.mindustry.world.level.block.entity.turrets.TurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TurretBlock extends BaseEntityBlock {

    public static final String NAME = "turret";

    public TurretBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        TurretBlockEntity turretBlockEntity = new TurretBlockEntity(pPos, pState);
        turretBlockEntity.getCapability(MindustryCapabilities.HEALTH_HANDLER, null).ifPresent(healthHandler -> {
            healthHandler.setMaxHealth(100);
            healthHandler.setHealth(100);
        });
        return turretBlockEntity;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return level.isClientSide?
                createTickerHelper(type, BlockEntityRegister.TURRET_BLOCK_ENTITY.get(), TurretBlockEntity::turretAnimationTick):
                createTickerHelper(type, BlockEntityRegister.TURRET_BLOCK_ENTITY.get(), TurretBlockEntity::serverTick);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
