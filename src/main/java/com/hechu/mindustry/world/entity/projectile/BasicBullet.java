package com.hechu.mindustry.world.entity.projectile;

import com.hechu.mindustry.kiwi.EntityModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BasicBullet extends BulletBase {
    public BasicBullet(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BasicBullet(Level pLevel) {
        super(EntityModule.BASIC_BULLET.get(), pLevel);
    }

    public BasicBullet(double pX, double pY, double pZ, Level pLevel) {
        super(EntityModule.BASIC_BULLET.get(), pX, pY, pZ, 0, 0, 0, pLevel);
    }

    public BasicBullet(LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(EntityModule.BASIC_BULLET.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public float damage = 10.0F;
    public int pierceCap = 2;
    public boolean pierceBlock = true;
    public float knockback = 0.0F;

    @Override
    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy);
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

//            Vec3 deltaMovement = getDeltaMovement().add(vec3.add(xPower, yPower, xPower)).scale(f);
//            this.setDeltaMovement(deltaMovement);

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
            Entity entity = ((EntityHitResult) pResult).getEntity();
            if (entity instanceof BulletBase)
                return;
            this.onHitEntity((EntityHitResult) pResult);
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult) pResult;
            this.onHitBlock(blockhitresult);
        }
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
        }
    }

    //TODO: 一个实体或者方块疑似会被重复判定

    /**
     * Called when the arrow hits an entity
     *
     * @param pResult
     */
    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        if (entity instanceof LivingEntity) {
            entity.hurt(this.damageSources().mobProjectile(this, null), damage);
        }
        pierceCap--;
        if (pierceCap < 0) {
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, pResult.getLocation(), GameEvent.Context.of(this, (BlockState) null));
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        pierceCap--;
        if (pierceCap < 0 || !pierceBlock) {
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, pResult.getLocation(), GameEvent.Context.of(this, (BlockState) null));
            this.discard();
        }
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleTypes.POOF;
    }
}
