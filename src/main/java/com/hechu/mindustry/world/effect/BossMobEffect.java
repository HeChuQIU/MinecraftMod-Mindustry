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
        return (BossMobEffect) new BossMobEffect()
                .addAttributeModifier(Attributes.ATTACK_DAMAGE, "125CA66E-C7B3-4E3C-8A60-80D974AFAEE6",
                        BASE_ATTACK_DAMAGE_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float BASE_ATTACK_DAMAGE_MULTIPLIER = 1.3f;
    public static final float BASE_HEALTH_MULTIPLIER = 1.5f;
}
