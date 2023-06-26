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

    public float progress = 0;
    private long tickCounter = 0;

    public void tick() {
        if (level == null)
            return;
        tickCounter++;
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
            /*PlayerList playerList = level.getServer() == null ? null : level.getServer().getPlayerList();
            if (playerList != null && playerList.getPlayerCount() > 0) {
                for (ServerPlayer player : playerList.getPlayers()) {
                    level.destroyBlockProgress(player.getId(), miningBlockPos, (int) Math.ceil(counter * 10));
                }
            }*/
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
        /*if (counter++ % 100 == 0) {
            BlockState belowBlockState = level.getBlockState(getBlockPos().below());
            if (belowBlockState.getTags().anyMatch(tag -> tag.equals(BlockTags.COPPER_ORES))) {
                level.removeBlock(getBlockPos().below(), true);
                for (ItemStack drop : Block.getDrops(belowBlockState, (ServerLevel) level, getBlockPos().below(), null)) {
                    Block.popResource(level, getBlockPos().below(), drop);
                }
            }
        }*/
    }

    public float getProgress() {
        return progress;
    }
}
