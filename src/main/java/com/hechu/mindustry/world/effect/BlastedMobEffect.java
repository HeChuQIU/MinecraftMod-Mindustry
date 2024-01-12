package com.hechu.mindustry.world.effect;

import com.hechu.mindustry.kiwi.MobEffectModule;
import net.minecraft.world.effect.MobEffectCategory;

public class BlastedMobEffect extends MindustryInstantenousMobEffect {
    protected BlastedMobEffect() {
        super(MobEffectCategory.HARMFUL, 16742494);
    }

    public static BlastedMobEffect create() {
        return (BlastedMobEffect) new BlastedMobEffect()
                .reactive(MobEffectModule.FREEZING.get(), params -> {
                    float j = (float) (9.0F * Math.pow(2, params.amplifier));
                    params.livingEntity.hurt(params.livingEntity.damageSources().magic(), j);
                });
    }
}
