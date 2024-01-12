package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class OverclockMobEffect extends MobEffect {
    public static final float BASE_ATTACK_DAMAGE_MULTIPLIER = 1.15f;
    public static final float BASE_SPEED_MULTIPLIER = 1.15f;
    public static final float BASE_RELOAD_MULTIPLIER = 1.25f;

    protected OverclockMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 16765566);
    }

    public static OverclockMobEffect create() {
        return (OverclockMobEffect) new OverclockMobEffect()
                .addAttributeModifier(Attributes.MOVEMENT_SPEED, "5F2966A3-777D-4531-A663-CC80F9DBFB86",
                        BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL)
                .addAttributeModifier(Attributes.ATTACK_DAMAGE, "CEDC5617-712E-4DEA-A237-7C2B32BD85A9",
                        BASE_ATTACK_DAMAGE_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL)
                .addAttributeModifier(Attributes.ATTACK_SPEED, "1B3287F2-CFE6-4B57-B549-0E601BCAB4DA",
                        BASE_RELOAD_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
