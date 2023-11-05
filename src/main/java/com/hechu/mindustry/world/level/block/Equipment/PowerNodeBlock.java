package com.hechu.mindustry.world.level.block.Equipment;

import com.hechu.mindustry.utils.capabilities.MindustryCapabilities;
import com.hechu.mindustry.world.level.block.entity.BlockEntityRegister;
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

/**
 * @author luobochuanqi
 */
public class PowerNodeBlock extends BaseEntityBlock {

    public static final String NAME = "power_node";

    public PowerNodeBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        PowerNodeBlockEntity powerNodeBlockEntity = new PowerNodeBlockEntity(pPos, pState);
        powerNodeBlockEntity.getCapability(MindustryCapabilities.HEALTH_HANDLER, null).ifPresent(healthHandler -> {
            healthHandler.setMaxHealth(40);
            healthHandler.setHealth(40);
        });
        return powerNodeBlockEntity;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, BlockEntityRegister.POWER_NODE_BLOCK_ENTITY.get(), PowerNodeBlockEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
