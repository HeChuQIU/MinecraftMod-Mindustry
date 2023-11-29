package com.hechu.mindustry.world.level.block.entity.turrets;

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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.kiwi.block.entity.ModBlockEntity;

public abstract class TurretBlockEntityBase extends ModBlockEntity {
    private final LazyOptional<IHealthHandler> healthHandler = LazyOptional.of(HealthHandler::new);
    public int time;
    public float rot;
    public float oRot;
    public float tRot;
    public LivingEntity target = null;
    public Vec3 targetPos = Vec3.ZERO;
    /**
     * 是否正在发射
     */
    public boolean isShooting = false;

    public TurretBlockEntityBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    private boolean isCanSeeEntity(LivingEntity e) {
        return e instanceof Enemy
                && (this.level.clip(new ClipContext(getBlockPos().above().above().getCenter(), e.position(), ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE, null)).getType() == HitResult.Type.MISS);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == MindustryCapabilities.HEALTH_HANDLER) {
            return healthHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Nullable
    LivingEntity getNearestEnemy(Vec3 range) {
        BlockPos blockPos = this.getBlockPos();
        return level.getNearestEntity(LivingEntity.class, TargetingConditions.forCombat().selector(this::isCanSeeEntity), null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(), new AABB(blockPos).inflate(range.x, range.y, range.z));
    }

    @Nullable
    LivingEntity getTarget() {
        target = getNearestEnemy(new Vec3(64, 8, 64));
//        if (target == null || !target.isAlive()) {
//            target = getNearestEnemy(new Vec3(64, 8, 64));
//        }
        return target;
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
        tag.putDouble("targetPosX", targetPos.x);
        tag.putDouble("targetPosY", targetPos.y);
        tag.putDouble("targetPosZ", targetPos.z);
        return tag;
    }

    @Override
    protected void readPacketData(CompoundTag compoundTag) {

    }

    @NotNull
    @Override
    protected CompoundTag writePacketData(CompoundTag compoundTag) {
        return compoundTag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        targetPos = new Vec3(tag.getDouble("targetPosX"), tag.getDouble("targetPosY"), tag.getDouble("targetPosZ"));
    }

    public abstract void tick();

    public void serverTick() {
        tick();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void clientTick() {
        tick();
        BlockPos blockPos = getBlockPos();
        oRot = rot;
        if (target != null) {
        double d0 = targetPos.x - ((double) blockPos.getX() + 0.5D);
        double d1 = targetPos.z - ((double) blockPos.getZ() + 0.5D);
        tRot = (float) Mth.atan2(d1, d0);
    } else

    {
        tRot += 0.02F;
    }

        while(rot >=(float)Math.PI)

    {
        rot -= ((float) Math.PI * 2F);
    }

        while(rot< -(float)Math.PI)

    {
        rot += ((float) Math.PI * 2F);
    }

        while(tRot >=(float)Math.PI)

    {
        tRot -= ((float) Math.PI * 2F);
    }

        while(tRot< -(float)Math.PI)

    {
        tRot += ((float) Math.PI * 2F);
    }

    float f2;
    f2 =tRot -rot;
        while(f2 >=(float)Math.PI)

    {
        f2 -= ((float) Math.PI * 2F);
    }

        while(f2< -(float)Math.PI)

    {
        f2 += ((float) Math.PI * 2F);
    }

    rot +=f2 *0.4F;
    float f3 = 0.2F;
}
}
