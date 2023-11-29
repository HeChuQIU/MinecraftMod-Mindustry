package com.hechu.mindustry.world.entity.projectile;

import com.hechu.mindustry.kiwi.EntityModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;

public class MissileBullet extends BulletBase {
    public MissileBullet(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MissileBullet(Level pLevel) {
        super(EntityModule.MISSILE_BULLET.get(), pLevel);
    }

    public MissileBullet(double pX, double pY, double pZ, Level pLevel) {
        super(EntityModule.MISSILE_BULLET.get(), pX, pY, pZ, 0, 0, 0, pLevel);
    }

    public MissileBullet(LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(EntityModule.MISSILE_BULLET.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    private boolean isCanSeeEntity(LivingEntity e) {
        return e instanceof Enemy
                && (this.level().clip(new ClipContext(position(), e.position(), ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE, null)).getType() == HitResult.Type.MISS);
    }

    public LivingEntity target = null;
    public Vec3 targetPos;
    public double acceleration = 0.1;
    public float maxVelocity = 1.0f;

    @Nullable
    LivingEntity getNearestEnemy(Vec3 range) {
        Vec3 pos = position();
        return level().getNearestEntity(LivingEntity.class, TargetingConditions.forCombat().selector(this::isCanSeeEntity), null,
                pos.x, pos.y, pos.z, new AABB(BlockPos.containing(pos)).inflate(range.x, range.y, range.z));
    }

    @Nullable
    LivingEntity getTarget() {
        target = getNearestEnemy(new Vec3(64, 64, 64));
//        if (target == null || !target.isAlive()) {
//            target = getNearestEnemy(new Vec3(64, 8, 64));
//        }
        return target;
    }

    @Override
    public boolean alwaysAccepts() {
        return super.alwaysAccepts();
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     *
     * @param pX
     * @param pY
     * @param pZ
     * @param pVelocity
     * @param pInaccuracy
     */
    @Override
    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy);
//        maxVelocity = pVelocity;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level().isClientSide || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25D, d1 - vec3.y * 0.25D, d2 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
                }

                f = 0.8F;
            }

            if (this.getTarget() != null) {
                targetPos = new Vec3(target.position().x, target.position().y + target.getEyeHeight(), target.position().z);
                Vec3 power = new Vec3(targetPos.x - d0, targetPos.y - d1, targetPos.z - d2).normalize().scale(acceleration);
                Vec3 deltaMovement = vec3.add(power).scale((double) f).normalize().scale(maxVelocity);
                this.setDeltaMovement(deltaMovement);
            }

            this.level().addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
            this.setPos(d0, d1, d2);
        } else {
            this.discard();
        }
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */
    @Override
    protected float getInertia() {
        return 1.0F;
    }

    @Override
    protected boolean shouldBurn() {
        return true;
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     *
     * @param pResult
     */
    @Override
    protected void onHit(HitResult pResult) {
        HitResult.Type hitresult$type = pResult.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            if (((EntityHitResult) pResult).getEntity() instanceof MissileBullet)
                return;
            this.onHitEntity((EntityHitResult) pResult);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, pResult.getLocation(), GameEvent.Context.of(this, (BlockState) null));
            level().explode(this, position().x, position().y, position().z, 1.5f, Level.ExplosionInteraction.NONE);
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult) pResult;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
            level().explode(this, position().x, position().y, position().z, 1.5f, true, Level.ExplosionInteraction.NONE);
        }
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.FLAME;
    }
}
