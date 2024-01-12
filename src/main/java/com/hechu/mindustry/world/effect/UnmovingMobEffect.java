package com.hechu.mindustry.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class UnmovingMobEffect extends MobEffect {
    protected UnmovingMobEffect() {
        super(MobEffectCategory.HARMFUL, 4539717);
    }

    public static UnmovingMobEffect create() {
        return (UnmovingMobEffect) new UnmovingMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED,
                        "DBFFFCE7-5055-4E1F-ABD3-196EE8F94DED", -1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                //.addAttributeModifier(Attributes.JUMP_STRENGTH,
                //        "B0E07E41-FFF9-4D50-89F7-82482CFA7618", -1f, AttributeModifier.Operation.MULTIPLY_TOTAL);//TODO: 等待使用Mixin实现
    }
}
