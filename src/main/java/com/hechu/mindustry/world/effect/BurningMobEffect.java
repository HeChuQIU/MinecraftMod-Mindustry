package com.hechu.mindustry.world.effect;

import com.hechu.mindustry.kiwi.MobEffectModule;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class BurningMobEffect extends MindustryMobEffect {
    public static final double BASE_DAMAGE = 1f;

    public BurningMobEffect() {
        super(MobEffectCategory.HARMFUL, 16761940, 20);
    }

    public static BurningMobEffect create() {
        return (BurningMobEffect) new BurningMobEffect()
                .reactive(MobEffectModule.WET, params ->
                        reactiveHandler(params, MobEffectModule.BURNING.get(), MobEffectModule.WET.get()))
                .reactive(MobEffectModule.FREEZING, params ->
                        reactiveHandler(params, MobEffectModule.FREEZING.get(), MobEffectModule.MELTING.get()));
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        super.applyEffectTick(livingEntity, amplifier);
        if (livingEntity instanceof Player player && player.isCreative())
            return;
        double j = applyTimeInterval / Math.pow(2d, amplifier);

        if (livingEntity.hasEffect(MobEffectModule.TARRED.get()))
            j += 4.0f;

        livingEntity.setRemainingFireTicks(j >= 1 ? (int) j : 1);
        livingEntity.hurt(livingEntity.damageSources().onFire(), (float) (j < 1f ? (BASE_DAMAGE / j) : BASE_DAMAGE));
    }
}
