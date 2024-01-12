package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TarredMobEffect extends MobEffect {
    protected TarredMobEffect() {
        super(MobEffectCategory.NEUTRAL, 3223857);
    }

    public static TarredMobEffect create() {
        return (TarredMobEffect) new TarredMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "876CE14E-6630-461C-87A6-F7C0458581B2", BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float BASE_SPEED_MULTIPLIER = 0.6f;
}
