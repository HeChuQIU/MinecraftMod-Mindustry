package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SappedMobEffect extends MobEffect {
    protected SappedMobEffect() {
        super(MobEffectCategory.HARMFUL, 6642589);
    }

    public static SappedMobEffect create() {
        return (SappedMobEffect) new SappedMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "0412E7B6-7A9B-4206-8291-F8911C9D4F67", BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float BASE_SPEED_MULTIPLIER = 0.8f;
    public static final float BASE_HEALTH_MULTIPLIER = 0.7f;//TODO: 等待使用Mixin实现
}
