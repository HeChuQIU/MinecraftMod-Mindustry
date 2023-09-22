package com.hechu.mindustry.world.level.block.entity.turrets;

import com.hechu.mindustry.utils.capabilities.HealthHandler;
import com.hechu.mindustry.utils.capabilities.IHealthHandler;
import com.hechu.mindustry.utils.capabilities.MindustryCapabilities;
import com.hechu.mindustry.world.level.block.entity.BlockEntityRegister;
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
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TurretBlockEntity extends BlockEntity {
    public static final String NAME = "turret";

    public int time;
    public float rot;
    public float oRot;
    public float tRot;

    public TurretBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.TURRET_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    private final LazyOptional<IHealthHandler> healthHandler = LazyOptional.of(HealthHandler::new);

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

    public LivingEntity target = null;
    public double targetPosX;
    public double targetPosY;
    public double targetPosZ;

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
        tag.putDouble("targetPosX", targetPosX);
        tag.putDouble("targetPosY", targetPosY);
        tag.putDouble("targetPosZ", targetPosZ);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        targetPosX = tag.getDouble("targetPosX");
        targetPosY = tag.getDouble("targetPosY");
        targetPosZ = tag.getDouble("targetPosZ");
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TurretBlockEntity
            blockEntity) {
        blockEntity.time++;
        if (blockEntity.time % 5 != 0)
            return;

        Vec3 pos = blockPos.getCenter();
        pos = pos.add(0, 1, 0);
        LivingEntity target = blockEntity.getTarget();
        if (target != null) {
            Arrow bullet = new Arrow(level, pos.x, pos.y, pos.z);
            Vec3 blockPosCenter = blockPos.getCenter();
            double d0 = target.getEyeY();
            double d1 = target.getX() - blockPosCenter.x;
            double d2 = d0 - bullet.getY() - target.getBoundingBox().getYsize();
            double d3 = target.getZ() - blockPosCenter.z;
            double d4 = Math.pow(Math.sqrt(d1 * d1 + d3 * d3), 1.55) * (double) 0.02F;

            bullet.shoot(d1, d2 + d4, d3, 3.2F, 2.56F);
//            bullet.noPhysics = true;
//            bullet.shoot(d1, 0, d3, 3.2F, 2.56F);
//        this.playSound(SoundEvents.ARROW_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            level.addFreshEntity(bullet);
        }

        if (target != null) {
            blockEntity.targetPosX = target.getX();
            blockEntity.targetPosY = target.getY();
            blockEntity.targetPosZ = target.getZ();
        } else {
            blockEntity.targetPosX = 0;
            blockEntity.targetPosY = 0;
            blockEntity.targetPosZ = 0;
        }
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, TurretBlockEntity
            blockEntity) {
        tick(level, blockPos, blockState, blockEntity);
        level.sendBlockUpdated(blockPos, blockState, blockState, 3);
    }

    public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, TurretBlockEntity
            blockEntity) {
        tick(level, blockPos, blockState, blockEntity);

        blockEntity.oRot = blockEntity.rot;
        if (blockEntity.targetPosX != 0 || blockEntity.targetPosY != 0 || blockEntity.targetPosZ != 0) {
            double d0 = blockEntity.targetPosX - ((double) blockPos.getX() + 0.5D);
            double d1 = blockEntity.targetPosZ - ((double) blockPos.getZ() + 0.5D);
            blockEntity.tRot = (float) Mth.atan2(d1, d0);
        } else {
            blockEntity.tRot += 0.02F;
        }

        while (blockEntity.rot >= (float) Math.PI) {
            blockEntity.rot -= ((float) Math.PI * 2F);
        }

        while (blockEntity.rot < -(float) Math.PI) {
            blockEntity.rot += ((float) Math.PI * 2F);
        }

        while (blockEntity.tRot >= (float) Math.PI) {
            blockEntity.tRot -= ((float) Math.PI * 2F);
        }

        while (blockEntity.tRot < -(float) Math.PI) {
            blockEntity.tRot += ((float) Math.PI * 2F);
        }

        float f2;
        f2 = blockEntity.tRot - blockEntity.rot;
        while (f2 >= (float) Math.PI) {
            f2 -= ((float) Math.PI * 2F);
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        blockEntity.rot += f2 * 0.4F;
        float f3 = 0.2F;
    }
}
