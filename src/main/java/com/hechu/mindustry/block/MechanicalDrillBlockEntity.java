package com.hechu.mindustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import static com.hechu.mindustry.Mindustry.MECHANICAL_DRILL_BLOCK_ENTITY;

public class MechanicalDrillBlockEntity extends BlockEntity implements GeoBlockEntity {
    public static final String NAME = "mechanical_drill";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // We statically instantiate our RawAnimations for efficiency, consistency, and error-proofing
    private static final RawAnimation ROTATION_ANIMS = RawAnimation.begin().thenLoop("mechanical_drill.rotation");

    public MechanicalDrillBlockEntity(BlockPos pos, BlockState state) {
        super(MECHANICAL_DRILL_BLOCK_ENTITY.get(), pos, state);
    }

    // Let's set our animations up
    // For this one, we want it to play the "Fertilizer" animation set if it's raining,
    // or switch to a botarium if it's not.
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> state.setAndContinue(ROTATION_ANIMS)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
