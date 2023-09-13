package com.hechu.mindustry.block;

import com.hechu.mindustry.utils.capabilities.MindustryCapabilities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HealthTestBlock extends BaseEntityBlock {
    public static final String NAME = "health_test";

    public HealthTestBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        HealthTestBlockEntity healthTestBlockEntity = new HealthTestBlockEntity(pPos, pState);
        healthTestBlockEntity.getCapability(MindustryCapabilities.HEALTH_HANDLER, null).ifPresent(healthHandler -> {
            healthHandler.setHealth(500);
        });
        return healthTestBlockEntity;
    }

    @Override
    public void onProjectileHit(Level pLevel, @NotNull BlockState pState, BlockHitResult pHit, @NotNull Projectile pProjectile) {
        HealthTestBlockEntity blockEntity = (HealthTestBlockEntity) pLevel.getBlockEntity(pHit.getBlockPos());
        if (blockEntity == null)
            return;
        blockEntity.getCapability(MindustryCapabilities.HEALTH_HANDLER, null).ifPresent(healthHandler -> {
            healthHandler.setHealth(healthHandler.getHealth() - 1);
            if (healthHandler.getHealth() <= 0) {
                pLevel.destroyBlock(pHit.getBlockPos(), true, pProjectile);
            }
        });
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
