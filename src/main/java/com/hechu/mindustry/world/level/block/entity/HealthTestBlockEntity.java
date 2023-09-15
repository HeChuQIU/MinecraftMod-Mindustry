package com.hechu.mindustry.world.level.block.entity;

import com.hechu.mindustry.utils.capabilities.HealthHandler;
import com.hechu.mindustry.utils.capabilities.IHealthHandler;
import com.hechu.mindustry.utils.capabilities.MindustryCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HealthTestBlockEntity extends BlockEntity {
    public static final String NAME = "health_test";
    private final LazyOptional<IHealthHandler> healthHandler = LazyOptional.of(HealthHandler::new);

    public HealthTestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.HEALTH_TEST_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == MindustryCapabilities.HEALTH_HANDLER) {
            return healthHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag tag) {
        setHealth(tag.getFloat("health"));
        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putFloat("health", getHealth());
        super.saveAdditional(tag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putFloat("health", health);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        health = tag.getFloat("health");
    }

    float health = 0;

    public float getHealth() {
        return healthHandler.orElseThrow(NullPointerException::new).getHealth();
    }

    public void setHealth(float health) {
        this.health = health;
        healthHandler.ifPresent(h -> h.setHealth(health));
    }
}
