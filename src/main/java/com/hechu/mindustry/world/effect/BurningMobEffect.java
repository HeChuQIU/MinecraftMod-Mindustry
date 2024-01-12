package com.hechu.mindustry.world.effect;

import com.hechu.mindustry.kiwi.MobEffectModule;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class BurningMobEffect extends MindustryMobEffect {
    public BurningMobEffect() {
        super(MobEffectCategory.HARMFUL, 16761940);
    }

    public static BurningMobEffect create() {
        return (BurningMobEffect) new BurningMobEffect()
                .reactive(MobEffectModule.WET.get(), params -> {
                    MobEffectInstance wetEffect = params.livingEntity.getEffect(MobEffectModule.WET.get());
                    MobEffectInstance burningEffect = params.livingEntity.getEffect(MobEffectModule.BURNING.get());
                    if (wetEffect != null && burningEffect != null) {
                        int t = burningEffect.getDuration() - wetEffect.getDuration();
                        // 不知道怎么缩减时间，所以这么处理
                        params.livingEntity.removeEffect(MobEffectModule.BURNING.get());
                        params.livingEntity.removeEffect(MobEffectModule.WET.get());
                        if (t > 0) {
                            params.livingEntity.addEffect(new MobEffectInstance(MobEffectModule.BURNING.get(), t, burningEffect.getAmplifier()));
                        } else {
                            params.livingEntity.addEffect(new MobEffectInstance(MobEffectModule.WET.get(), -t, wetEffect.getAmplifier()));
                        }
                    }
                });
    }

    public static final int BASE_DURATION = 20;
    public static final float BASE_DAMAGE = 1f;

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        super.applyEffectTick(livingEntity, amplifier);
        if (livingEntity instanceof Player player && player.isCreative())
            return;
        float j = (float) (BASE_DURATION / Math.pow(2d, amplifier));

        if (livingEntity.hasEffect(MobEffectModule.TARRED.get()))
            j += 4.0F;

        livingEntity.setRemainingFireTicks(j >= 1 ? (int) j : 1);
        livingEntity.hurt(livingEntity.damageSources().onFire(), j < 1f ? (BASE_DAMAGE / j) : BASE_DAMAGE);
    }

    /**
     * Checks whether the effect is ready to be applied this tick.
     *
     * @param duration
     * @param amplifier
     */
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = (int) (BASE_DURATION / Math.pow(2d, amplifier));
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }
}
