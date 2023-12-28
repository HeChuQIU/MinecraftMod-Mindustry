package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ElectrifiedMobEffect extends MobEffect {
    protected ElectrifiedMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 9961127);
    }

    public static ElectrifiedMobEffect create() {
        return (ElectrifiedMobEffect) new ElectrifiedMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "1138CD6F-BFE8-4DE9-A4E2-269C953961E6", BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float BASE_SPEED_MULTIPLIER = 0.7f;
    public static final float BASE_RELOAD_MULTIPLIER = 0.6f;// TODO: 等待使用Mixin实现
}
