package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ShockedMobEffect extends MobEffect {
    protected ShockedMobEffect() {
        super(MobEffectCategory.HARMFUL, 11065599);
    }

    public static ShockedMobEffect create() {
        return new ShockedMobEffect();
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
