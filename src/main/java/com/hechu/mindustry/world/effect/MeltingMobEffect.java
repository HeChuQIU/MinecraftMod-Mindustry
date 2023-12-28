package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class MeltingMobEffect extends MobEffect {
    protected MeltingMobEffect() {
        super(MobEffectCategory.HARMFUL, 16752742);
    }

    public static MeltingMobEffect create() {
        return (MeltingMobEffect) new MeltingMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "09C9E884-72C0-448B-944D-B19B3EA34FEB", BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float BASE_SPEED_MULTIPLIER = 0.8f;
    public static final float BASE_HEALTH_MULTIPLIER = 0.8f;//TODO: 等待使用Mixin实现

    public static final int BASE_DURATION = 20;
    public static final float BASE_DAMAGE = 1.8f;

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
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
