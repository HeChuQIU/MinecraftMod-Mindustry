package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SporeSlowedMobEffect extends MobEffect {
    protected SporeSlowedMobEffect() {
        super(MobEffectCategory.NEUTRAL, 7624654);
    }

    public static SporeSlowedMobEffect create() {
        return (SporeSlowedMobEffect) new SporeSlowedMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "6011DBE7-8DC8-4279-BAB2-99267A65B6C6", BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float BASE_SPEED_MULTIPLIER = 0.8f;
}
