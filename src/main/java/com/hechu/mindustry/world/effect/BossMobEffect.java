package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BossMobEffect extends MobEffect {
    protected BossMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 15750228);
    }

    public static BossMobEffect create() {
        return new BossMobEffect();
    }

    public static final float BASE_ATTACK_DAMAGE_MULTIPLIER = 1.3f;// TODO: 等待使用Mixin实现
    public static final float BASE_HEALTH_MULTIPLIER = 1.5f;// TODO: 等待使用Mixin实现
}
