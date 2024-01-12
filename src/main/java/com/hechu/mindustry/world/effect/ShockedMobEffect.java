package com.hechu.mindustry.world.effect;

import com.hechu.mindustry.kiwi.MobEffectModule;
import net.minecraft.world.effect.MobEffectCategory;

public class ShockedMobEffect extends MindustryInstantenousMobEffect {
    protected ShockedMobEffect() {
        super(MobEffectCategory.HARMFUL, 11065599);
    }

    public static ShockedMobEffect create() {
        return (ShockedMobEffect) new ShockedMobEffect()
                .reactive(MobEffectModule.WET, params -> {
                    float j = (float) (7.0F * Math.pow(2, params.amplifier));
                    params.livingEntity.hurt(params.livingEntity.damageSources().magic(), j);
                });
    }
}
