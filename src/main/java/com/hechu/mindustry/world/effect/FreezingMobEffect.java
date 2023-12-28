package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class FreezingMobEffect extends MobEffect {
    protected FreezingMobEffect() {
        super(MobEffectCategory.HARMFUL, 7063782);
    }

    public static FreezingMobEffect create() {
        return (FreezingMobEffect) new FreezingMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "6069378B-E74B-4712-8BDC-401451175BC2", BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float BASE_SPEED_MULTIPLIER = 0.6f;
    public static final float BASE_HEALTH_MULTIPLIER = 0.8f;//TODO: 等待使用Mixin实现
}
