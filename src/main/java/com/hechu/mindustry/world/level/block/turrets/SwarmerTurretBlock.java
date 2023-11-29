package com.hechu.mindustry.world.level.block.turrets;

import com.hechu.mindustry.utils.capabilities.MindustryCapabilities;
import com.hechu.mindustry.world.level.block.entity.turrets.SwarmerTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwarmerTurretBlock extends TurretBlockBase {
    public SwarmerTurretBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        SwarmerTurretBlockEntity swarmerTurretBlockEntity = new SwarmerTurretBlockEntity(pPos, pState);
        swarmerTurretBlockEntity.getCapability(MindustryCapabilities.HEALTH_HANDLER, null).ifPresent(healthHandler -> {
            healthHandler.setMaxHealth(100);
            healthHandler.setHealth(100);
        });
        return swarmerTurretBlockEntity;
    }

}
