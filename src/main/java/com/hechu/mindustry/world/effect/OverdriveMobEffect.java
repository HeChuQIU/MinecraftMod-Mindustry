package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class OverdriveMobEffect extends MobEffect {
    protected OverdriveMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 16765566);
    }

    public static OverdriveMobEffect create() {
        return (OverdriveMobEffect) new OverdriveMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "F06556B6-259F-470E-8E09-6FE6C902C786", BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float BASE_SPEED_MULTIPLIER = 1.15f;
    public static final float BASE_HEALTH_MULTIPLIER = 0.95f;// TODO: 等待使用Mixin实现
    public static final float BASE_ATTACK_DAMAGE_MULTIPLIER = 1.4f;// TODO: 等待使用Mixin实现
}
