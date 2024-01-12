package com.hechu.mindustry.mixin;

import com.hechu.mindustry.kiwi.MobEffectModule;
import com.hechu.mindustry.world.effect.*;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements Attackable, net.minecraftforge.common.extensions.IForgeLivingEntity {
    @Shadow
    public abstract boolean hasEffect(MobEffect pEffect);

    @Shadow
    @Nullable
    public abstract MobEffectInstance getEffect(MobEffect pEffect);

    @Shadow
    public abstract boolean removeEffect(MobEffect pEffect);

    @Shadow
    public abstract boolean addEffect(MobEffectInstance pEffectInstance);

    public MixinLivingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "getDamageAfterMagicAbsorb", at = @At("RETURN"), cancellable = true)
    protected void onGetDamageAfterMagicAbsorb(DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Float> cir) {
        float damage = cir.getReturnValue();
        if (!damageSource.is(DamageTypeTags.BYPASSES_EFFECTS)) {
            if (this.hasEffect(MobEffectModule.FREEZING.get())) {
                MobEffectInstance effect = this.getEffect(MobEffectModule.FREEZING.get());
                if (effect != null) {
                    damage /= (float) Math.pow(FreezingMobEffect.BASE_HEALTH_MULTIPLIER, effect.getAmplifier() + 1);
                }
            }
            if (this.hasEffect(MobEffectModule.MELTING.get())) {
                MobEffectInstance effect = this.getEffect(MobEffectModule.MELTING.get());
                if (effect != null) {
                    damage /= (float) Math.pow(MeltingMobEffect.BASE_HEALTH_MULTIPLIER, effect.getAmplifier() + 1);
                }
            }
            if (this.hasEffect(MobEffectModule.SAPPED.get())) {
                MobEffectInstance effect = this.getEffect(MobEffectModule.SAPPED.get());
                if (effect != null) {
                    damage /= (float) Math.pow(SappedMobEffect.BASE_HEALTH_MULTIPLIER, effect.getAmplifier() + 1);
                }
            }
            if (this.hasEffect(MobEffectModule.OVERDRIVE.get())) {
                MobEffectInstance effect = this.getEffect(MobEffectModule.OVERDRIVE.get());
                if (effect != null) {
                    damage /= (float) Math.pow(OverdriveMobEffect.BASE_HEALTH_MULTIPLIER, effect.getAmplifier() + 1);
                }
            }
            if (this.hasEffect(MobEffectModule.BOSS.get())) {
                MobEffectInstance effect = this.getEffect(MobEffectModule.BOSS.get());
                if (effect != null) {
                    damage /= (float) Math.pow(BossMobEffect.BASE_HEALTH_MULTIPLIER, effect.getAmplifier() + 1);
                }
            }
            cir.setReturnValue(damage);
        }
    }

    @Inject(method = "baseTick", at = @At("HEAD"), cancellable = true)
    public void onBaseTick(CallbackInfo ci) {
        if (this.isAlive() && this.isInWaterRainOrBubble()) {
            if (!this.hasEffect(MobEffectModule.WET.get())
                    ||
                    (this.hasEffect(MobEffectModule.WET.get())
                            && this.getEffect(MobEffectModule.WET.get()).getAmplifier() <= 0
                            && this.getEffect(MobEffectModule.WET.get()).getDuration() <= 15 * 20)) {
                this.removeEffect(MobEffectModule.WET.get());
                this.addEffect(new MobEffectInstance(MobEffectModule.WET.get(), 15 * 20 + 1, 0));
            }
        }
    }
}
