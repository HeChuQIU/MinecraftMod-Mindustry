package com.hechu.mindustry.world.effect;

import com.hechu.mindustry.kiwi.MobEffectModule;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class MeltingMobEffect extends MindustryMobEffect {
    protected MeltingMobEffect() {
        super(MobEffectCategory.HARMFUL, 16752742);
    }

    public static MeltingMobEffect create() {
        return (MeltingMobEffect) new MeltingMobEffect()
                .reactive(MobEffectModule.WET.get(), params -> {
                    MobEffectInstance wetEffect = params.livingEntity.getEffect(MobEffectModule.WET.get());
                    MobEffectInstance meltingEffect = params.livingEntity.getEffect(MobEffectModule.MELTING.get());
                    if (wetEffect != null && meltingEffect != null) {
                        int t = meltingEffect.getDuration() - wetEffect.getDuration();
                        // 不知道怎么缩减时间，所以这么处理
                        params.livingEntity.removeEffect(MobEffectModule.MELTING.get());
                        params.livingEntity.removeEffect(MobEffectModule.WET.get());
                        if (t > 0) {
                            params.livingEntity.addEffect(new MobEffectInstance(MobEffectModule.MELTING.get(), t, meltingEffect.getAmplifier()));
                        } else {
                            params.livingEntity.addEffect(new MobEffectInstance(MobEffectModule.WET.get(), -t, wetEffect.getAmplifier()));
                        }
                    }
                })
                .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "09C9E884-72C0-448B-944D-B19B3EA34FEB", BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float BASE_SPEED_MULTIPLIER = 0.8f;
    public static final float BASE_HEALTH_MULTIPLIER = 0.8f;

    public static final int BASE_DURATION = 20;
    public static final float BASE_DAMAGE = 1.8f;

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        super.applyEffectTick(livingEntity, amplifier);
        if (livingEntity instanceof Player player && player.isCreative())
            return;
        float j = (float) (BASE_DURATION / Math.pow(2d, amplifier));
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
