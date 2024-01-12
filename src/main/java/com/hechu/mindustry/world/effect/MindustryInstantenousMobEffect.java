package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

public abstract class MindustryInstantenousMobEffect extends MindustryMobEffect {
    public MindustryInstantenousMobEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    /**
     * Returns {@code true} if the potion has an instant effect instead of a continuous one (e.g. Harming)
     */
    @Override
    public final boolean isInstantenous() {
        return true;
    }

    /**
     * Checks whether the effect is ready to be applied this tick.
     *
     * @param pDuration
     * @param pAmplifier
     */
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

}
