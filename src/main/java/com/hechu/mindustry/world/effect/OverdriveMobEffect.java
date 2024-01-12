package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class OverdriveMobEffect extends MindustryMobEffect {
    public static final float BASE_SPEED_MULTIPLIER = 1.15f;
    public static final float BASE_HEALTH_MULTIPLIER = 0.95f;
    public static final float BASE_ATTACK_DAMAGE_MULTIPLIER = 1.4f;
    public static final float BASE_HEAL_PER_SECOND = 0.6f;

    protected OverdriveMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 16765566);
    }

    public static OverdriveMobEffect create() {
        return (OverdriveMobEffect) new OverdriveMobEffect()
                .addAttributeModifier(Attributes.MOVEMENT_SPEED, "F06556B6-259F-470E-8E09-6FE6C902C786",
                        BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL)
                .addAttributeModifier(Attributes.ATTACK_DAMAGE, "CEDC5617-712E-4DEA-A237-7C2B32BD85A9",
                        BASE_ATTACK_DAMAGE_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        super.applyEffectTick(livingEntity, amplifier);
        if (livingEntity instanceof Player player && player.isCreative())
            return;
        float j = (float) (applyTimeInterval / Math.pow(2d, amplifier));
        livingEntity.heal(j < 1f ? (BASE_HEAL_PER_SECOND / j) : BASE_HEAL_PER_SECOND);
    }
}
