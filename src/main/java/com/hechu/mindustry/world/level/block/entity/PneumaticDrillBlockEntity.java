package com.hechu.mindustry.world.level.block.entity;

import com.hechu.mindustry.MindustryModule;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.Arrays;

public class PneumaticDrillBlockEntity extends DrillBlockEntity {
    public static final String NAME = "pneumatic_drill";

    public PneumaticDrillBlockEntity(BlockPos pos, BlockState state) {
        super(MindustryModule.PNEUMATIC_DRILL_BLOCK_ENTITY.get(),pos, state,
                Arrays.stream(new BlockPos[]{pos.below(),pos.below().east(),pos.below().south(),pos.below().east().south()}).toList(),
                state1 -> state1.is(BlockTags.SAND) ||
                        state1.is(BlockTags.COAL_ORES) ||
                        state1.is(BlockTags.COPPER_ORES), 0.6f);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> state.setAndContinue(ROTATION_ANIMS)));
    }
    private static final RawAnimation ROTATION_ANIMS = RawAnimation.begin().thenLoop("2x2drill_template.rotate");

/*    @Override
    public void tick() {
        if (level == null)
            return;
        BlockPos miningBlockPos = getBlockPos().below();
        BlockState miningBlockState = level.getBlockState(miningBlockPos);
        boolean isMining = miningBlockState.getTags().anyMatch(tag ->
                tag.equals(BlockTags.COPPER_ORES) ||
                        tag.equals(BlockTags.COAL_ORES) ||
                        tag.equals(BlockTags.SAND));
        if (level.isClientSide) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (isMining) {
                progress += 0.4 / 20;
                if (localPlayer != null)
                    level.destroyBlockProgress(localPlayer.getId(), miningBlockPos, (int) (progress * 10));
                if (progress >= 1) {
                    level.addDestroyBlockEffect(miningBlockPos, miningBlockState);
                    progress = 0;
                }
            } else {
                if (localPlayer != null)
                    level.destroyBlockProgress(localPlayer.getId(), miningBlockPos, 10);
            }
            return;
        }
        if (isMining) {
            progress += 0.4 / 20;
            if (progress >= 1) {
                for (ItemStack drop : Block.getDrops(miningBlockState, (ServerLevel) level, miningBlockPos, null)) {
                    Block.popResource(level, getBlockPos().above(), drop);
                }
                level.playSound(null, getBlockPos(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1f, 1f);
                progress = 0;
            }
        } else {
            progress = 0;
        }
    }*/
}
