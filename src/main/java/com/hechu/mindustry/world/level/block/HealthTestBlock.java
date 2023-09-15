package com.hechu.mindustry.world.level.block;

import com.hechu.mindustry.utils.capabilities.MindustryCapabilities;
import com.hechu.mindustry.world.level.block.entity.HealthTestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
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
            healthHandler.setHealth(100);
        });
        return healthTestBlockEntity;
    }

    @Override
    public void onProjectileHit(Level pLevel, @NotNull BlockState pState, BlockHitResult pHit, @NotNull Projectile pProjectile) {
        super.onProjectileHit(pLevel, pState, pHit, pProjectile);
        if(pLevel.isClientSide) return;

        HealthTestBlockEntity blockEntity = (HealthTestBlockEntity) pLevel.getBlockEntity(pHit.getBlockPos());
        if (blockEntity == null) return;

        float damage;
        if(pProjectile instanceof AbstractArrow arrow) {
            float f = (float) arrow.getDeltaMovement().length();
            int i = Mth.ceil(Mth.clamp((double) f * arrow.getBaseDamage(), 0.0D, (double) Integer.MAX_VALUE));
            damage = i;
        }
        else {
            damage = 1;
        }
        blockEntity.getCapability(MindustryCapabilities.HEALTH_HANDLER, null).ifPresent(healthHandler -> {
            healthHandler.setHealth(healthHandler.getHealth() - damage);
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
