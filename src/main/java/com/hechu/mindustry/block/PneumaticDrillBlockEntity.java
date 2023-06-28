package com.hechu.mindustry.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import static com.hechu.mindustry.block.BlockEntityRegister.PNEUMATIC_DRILL_BLOCK_ENTITY;

public class PneumaticDrillBlockEntity extends DrillBlockEntity {
    public static final String NAME = "pneumatic_drill";

    public PneumaticDrillBlockEntity(BlockPos pos, BlockState state) {
        super(PNEUMATIC_DRILL_BLOCK_ENTITY.get(),pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> state.setAndContinue(ROTATION_ANIMS)));
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation ROTATION_ANIMS = RawAnimation.begin().thenLoop("2x2drill_template.rotate");

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private float progress;

    @Override
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
    }

    @Override
    public float getProgress() {
        return progress;
    }
}
