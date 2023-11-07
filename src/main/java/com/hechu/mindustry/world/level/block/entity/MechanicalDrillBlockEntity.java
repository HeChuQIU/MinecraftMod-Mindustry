package com.hechu.mindustry.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.Arrays;

public class MechanicalDrillBlockEntity extends DrillBlockEntity {
    public static final String NAME = "mechanical_drill";
    private static final RawAnimation ROTATION_ANIMS = RawAnimation.begin().thenLoop("mechanical_drill.rotate");

    public MechanicalDrillBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.MECHANICAL_DRILL_BLOCK_ENTITY.get(), pos, state,
                Arrays.stream(new BlockPos[]{pos.below(),pos.below().east(),pos.below().south(),pos.below().east().south()}).toList(),
                state1 -> state1.is(Tags.Blocks.SAND) ||
                        state1.is(Tags.Blocks.ORES_COAL) ||
                        state1.is(Tags.Blocks.ORES_COPPER), 0.4f);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> state.setAndContinue(ROTATION_ANIMS)));
    }
}
