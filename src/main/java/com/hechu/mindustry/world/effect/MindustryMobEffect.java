package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public abstract class MindustryMobEffect extends MobEffect {
    private final Map<MobEffect, Consumer<ApplyEffectParams>> affinities = new java.util.HashMap<>();

    public MindustryMobEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public MindustryMobEffect reactive(MobEffect effect, Consumer<ApplyEffectParams> consumer) {
        affinities.put(effect, consumer);
        return this;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        ApplyEffectParams effectParams = new ApplyEffectParams(livingEntity, amplifier);
        affinities.keySet().stream().filter(livingEntity::hasEffect).map(affinities::get).forEach(consumer -> consumer.accept(effectParams));
    }

    public static class ApplyEffectParams {
        public ApplyEffectParams(@NotNull LivingEntity livingEntity, int amplifier) {
            this.livingEntity = livingEntity;
            this.amplifier = amplifier;
        }

        public @NotNull LivingEntity livingEntity;
        public int amplifier;
    }
}
