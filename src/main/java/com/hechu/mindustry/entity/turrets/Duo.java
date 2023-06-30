package com.hechu.mindustry.entity.turrets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Duo extends Mob implements RangedAttackMob, GeoEntity {
    public Duo(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.ATTACK_DAMAGE, 15.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 0, 20, 10.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false, (p_29932_) -> {
            return p_29932_ instanceof Enemy;
        }));
    }



    @Override
    public void performRangedAttack(LivingEntity entity, float p_33318_) {
        Arrow snowball = new Arrow(this.level, this);
        double d0 = entity.getEyeY() - 1.6f;
        double d1 = entity.getX() - this.getX();
        double d2 = d0 - snowball.getY();
        double d3 = entity.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double) 0.2F;
        snowball.shoot(d1, d2 + d4, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(snowball);
        fireCountMod2++;
        fireCountMod2 %= 2;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "Fire", 5, this::animController));
    }

    protected static final RawAnimation LEFT_FIRE_ANIM = RawAnimation.begin().thenPlay("duo.left_fire");
    protected static final RawAnimation RIGHT_FIRE_ANIM = RawAnimation.begin().thenPlay("duo.right_fire");

    protected int fireCountMod2 = 0;

    protected <E extends Duo> PlayState animController(final AnimationState<E> event) {
        if (fireCountMod2 == 0) {
            return event.setAndContinue(RIGHT_FIRE_ANIM);
        }
        return event.setAndContinue(LEFT_FIRE_ANIM);
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
