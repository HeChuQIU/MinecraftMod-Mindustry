package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BlastedMobEffect extends MobEffect {
    protected BlastedMobEffect() {
        super(MobEffectCategory.HARMFUL, 16742494);
    }

    public static BlastedMobEffect create() {
        return new BlastedMobEffect();
    }

    /**
     * Returns {@code true} if the potion has an instant effect instead of a continuous one (e.g. Harming)
     */
    @Override
    public boolean isInstantenous() {
        return true;
    }

    // TODO: 等待实现
}
