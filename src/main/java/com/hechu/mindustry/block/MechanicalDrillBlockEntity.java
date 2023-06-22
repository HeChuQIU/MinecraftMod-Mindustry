package com.hechu.mindustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static com.hechu.mindustry.Mindustry.MECHANICAL_DRILL_BLOCK_ENTITY;

public class MechanicalDrillBlockEntity extends BlockEntity implements IAnimatable {
    public static final String NAME = "mechanical_drill";

    protected static final AnimationBuilder DEPLOY = new AnimationBuilder().addAnimation("mechanical_drill.rotation", () -> true);

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public MechanicalDrillBlockEntity(BlockPos pos, BlockState blockState) {
        super(MECHANICAL_DRILL_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public void registerControllers(final AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "Deployment", 0, this::deployAnimController));
    }

    protected <E extends MechanicalDrillBlockEntity> PlayState deployAnimController(final AnimationEvent<E> event) {
        event.getController().setAnimation(DEPLOY);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
