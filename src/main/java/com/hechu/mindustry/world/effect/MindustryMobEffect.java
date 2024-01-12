package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import snownee.kiwi.KiwiGO;

import java.util.Map;
import java.util.function.Consumer;

import static com.hechu.mindustry.MindustryConstants.logger;

public abstract class MindustryMobEffect extends MobEffect {
    public final double applyTimeInterval;
    private final Map<KiwiGO<MobEffect>, Consumer<ApplyEffectParams>> affinities = new java.util.HashMap<>();

    public MindustryMobEffect(MobEffectCategory pCategory, int pColor) {
        this(pCategory, pColor, 0);
    }

    public MindustryMobEffect(MobEffectCategory pCategory, int pColor, int applyTimeInterval) {
        super(pCategory, pColor);
        this.applyTimeInterval = applyTimeInterval;
    }

    public MindustryMobEffect reactive(KiwiGO<MobEffect> effect, Consumer<ApplyEffectParams> consumer) {
        affinities.put(effect, consumer);
        return this;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        ApplyEffectParams effectParams = new ApplyEffectParams(livingEntity, amplifier);
        affinities.entrySet().stream()
                .filter(entry -> livingEntity.hasEffect(entry.getKey().get()))
                .forEach(entry -> entry.getValue().accept(effectParams));
    }

    public static class ApplyEffectParams {
        public ApplyEffectParams(@NotNull LivingEntity livingEntity, int amplifier) {
            this.livingEntity = livingEntity;
            this.amplifier = amplifier;
        }

        public @NotNull LivingEntity livingEntity;
        public int amplifier;
    }

    /**
     * Checks whether the effect is ready to be applied this tick.
     *
     * @param duration
     * @param amplifier
     */
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = (int) (applyTimeInterval / Math.pow(2d, amplifier));
        if (j > 0)
            return duration % j == 0;
        return true;
    }

    protected static synchronized void reactiveHandler(ApplyEffectParams params, MobEffect effect1, MobEffect effect2) {
        // TODO 修改时长可能会导致客户端无法立刻刷新持续时间,所以可能还是要改
        MobEffectInstance effectInstance1 = params.livingEntity.getEffect(effect1);
        MobEffectInstance effectInstance2 = params.livingEntity.getEffect(effect2);
        if (effectInstance1 == null || effectInstance2 == null)
            return;
        logger.debug("Effect1 %s(%d), Effect2 %s(%d)"
                .formatted(effect1.getDisplayName(), effectInstance1.getDuration(),
                        effect2.getDisplayName(), effectInstance2.getDuration()));
        int t = effectInstance1.getDuration() - effectInstance2.getDuration();
        if (t == 0) {
            params.livingEntity.removeEffect(effect1);
            params.livingEntity.removeEffect(effect2);
            return;
        }
        if (t > 0) {
            params.livingEntity.removeEffect(effect2);
            effectInstance1.duration = t;
            return;
        }
        params.livingEntity.removeEffect(effect1);
        effectInstance2.duration = -t;
    }
}
