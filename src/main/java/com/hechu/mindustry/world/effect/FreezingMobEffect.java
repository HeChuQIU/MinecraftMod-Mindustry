package com.hechu.mindustry.world.effect;

import com.hechu.mindustry.kiwi.MobEffectModule;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FreezingMobEffect extends MindustryMobEffect {
    public static final float BASE_SPEED_MULTIPLIER = 0.6f;
    public static final float BASE_HEALTH_MULTIPLIER = 0.8f;

    protected FreezingMobEffect() {
        super(MobEffectCategory.HARMFUL, 7063782);
    }

    public static FreezingMobEffect create() {
        return (FreezingMobEffect) new FreezingMobEffect()
                .reactive(MobEffectModule.BURNING, params ->
                        reactiveHandler(params, MobEffectModule.FREEZING.get(), MobEffectModule.BURNING.get()))
                .reactive(MobEffectModule.MELTING, params ->
                        reactiveHandler(params, MobEffectModule.FREEZING.get(), MobEffectModule.MELTING.get()))
                .addAttributeModifier(Attributes.MOVEMENT_SPEED, "6069378B-E74B-4712-8BDC-401451175BC2",
                        BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    //TODO: 等待使用Mixin实现
}
