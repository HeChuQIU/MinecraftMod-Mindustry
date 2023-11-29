package com.hechu.mindustry.world.level.block.turrets;

import com.hechu.mindustry.utils.capabilities.MindustryCapabilities;
import com.hechu.mindustry.world.level.block.entity.turrets.SpectreTurretBlockEntity;
import com.hechu.mindustry.world.level.block.entity.turrets.SwarmerTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SpectreTurretBlock extends TurretBlockBase {
    public SpectreTurretBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        SpectreTurretBlockEntity spectreTurretBlockEntity = new SpectreTurretBlockEntity(pPos, pState);
        spectreTurretBlockEntity.getCapability(MindustryCapabilities.HEALTH_HANDLER, null).ifPresent(healthHandler -> {
            healthHandler.setMaxHealth(100);
            healthHandler.setHealth(100);
        });
        return spectreTurretBlockEntity;
    }
}
