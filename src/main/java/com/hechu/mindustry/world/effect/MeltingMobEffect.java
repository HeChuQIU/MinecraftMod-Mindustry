package com.hechu.mindustry.world.effect;

import com.hechu.mindustry.kiwi.MobEffectModule;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class MeltingMobEffect extends MindustryMobEffect {
    public static final float BASE_SPEED_MULTIPLIER = 0.8f;
    public static final float BASE_HEALTH_MULTIPLIER = 0.8f;
    public static final float BASE_DAMAGE = 1.8f;

    protected MeltingMobEffect() {
        super(MobEffectCategory.HARMFUL, 16752742, 20);
    }

    public static MeltingMobEffect create() {
        return (MeltingMobEffect) new MeltingMobEffect()
                .reactive(MobEffectModule.WET, params ->
                        reactiveHandler(params, MobEffectModule.MELTING.get(), MobEffectModule.WET.get()))
                .reactive(MobEffectModule.FREEZING, params ->
                        reactiveHandler(params, MobEffectModule.FREEZING.get(), MobEffectModule.BURNING.get()))
                .addAttributeModifier(Attributes.MOVEMENT_SPEED, "09C9E884-72C0-448B-944D-B19B3EA34FEB",
                        BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        super.applyEffectTick(livingEntity, amplifier);
        if (livingEntity instanceof Player player && player.isCreative())
            return;
        float j = (float) (applyTimeInterval / Math.pow(2d, amplifier));
        if (livingEntity.hasEffect(MobEffectModule.TARRED.get()))
            j += 8;
        livingEntity.setRemainingFireTicks(j >= 1 ? (int) j : 1);
        livingEntity.hurt(livingEntity.damageSources().onFire(), j < 1f ? (BASE_DAMAGE / j) : BASE_DAMAGE);
    }
}
