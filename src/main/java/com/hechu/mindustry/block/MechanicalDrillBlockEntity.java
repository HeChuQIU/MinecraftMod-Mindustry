package com.hechu.mindustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Arrays;

import static com.hechu.mindustry.block.BlockEntityRegister.MECHANICAL_DRILL_BLOCK_ENTITY;

public class MechanicalDrillBlockEntity extends DrillBlockEntity {
    public static final String NAME = "mechanical_drill";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation ROTATION_ANIMS = RawAnimation.begin().thenLoop("2x2drill_template.rotate");

    public MechanicalDrillBlockEntity(BlockPos pos, BlockState state) {
        super(MECHANICAL_DRILL_BLOCK_ENTITY.get(), pos, state,
                Arrays.stream(new BlockPos[]{pos.below(),pos.below().east(),pos.below().south(),pos.below().east().south()}).toList(),
                state1 -> state1.is(BlockTags.SAND) ||
                        state1.is(BlockTags.COAL_ORES) ||
                        state1.is(BlockTags.COPPER_ORES), 0.4f);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> state.setAndContinue(ROTATION_ANIMS)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}
