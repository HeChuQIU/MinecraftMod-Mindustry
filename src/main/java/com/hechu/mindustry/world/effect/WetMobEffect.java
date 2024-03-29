package com.hechu.mindustry.world.effect;

import com.hechu.mindustry.kiwi.MobEffectModule;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class WetMobEffect extends MindustryMobEffect {
    public static final float BASE_SPEED_MULTIPLIER = 0.94f;

    protected WetMobEffect() {
        super(MobEffectCategory.NEUTRAL, 4286688);
    }

    public static WetMobEffect create() {
        return (WetMobEffect) new WetMobEffect()
                .reactive(MobEffectModule.BURNING, params ->
                        reactiveHandler(params, MobEffectModule.BURNING.get(), MobEffectModule.WET.get()))
                .reactive(MobEffectModule.MELTING, params ->
                        reactiveHandler(params, MobEffectModule.MELTING.get(), MobEffectModule.WET.get()))
                .addAttributeModifier(Attributes.MOVEMENT_SPEED, "D59732C5-E09A-4952-8505-ED83BCF77402",
                        BASE_SPEED_MULTIPLIER - 1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
