package com.hechu.mindustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class DrillBlockEntity extends BlockEntity implements GeoBlockEntity {

    protected float progress = 0;
    protected List<BlockPos> miningBlocksPos = Collections.emptyList();
    protected Predicate<BlockState> isMinable = state -> false;
    protected float baseMiningSpeed = 0;
    protected BlockState miningBlockState = null;

    public DrillBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    public DrillBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, List<BlockPos> miningBlocksPos, Predicate<BlockState> isMinable, float baseMiningSpeed) {
        super(blockEntityType, pos, state);
        this.miningBlocksPos = miningBlocksPos;
        this.isMinable = isMinable;
        this.baseMiningSpeed = baseMiningSpeed;
    }

    @Override
    public abstract void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar);

    @Override
    public abstract AnimatableInstanceCache getAnimatableInstanceCache();

    public void tick() {
        if (level == null)
            return;

        // 创建字典用于存储映射结果
        Map<BlockState, List<BlockPos>> blockStateToPositions = new HashMap<>();
        List<BlockState> miningBlockStates = new ArrayList<>();
        for (BlockPos pos : miningBlocksPos) {
            BlockState state = level.getBlockState(pos);
            if (isMinable.test(state)) {
                miningBlockStates.add(state);
                if (!blockStateToPositions.containsKey(state)) {
                    blockStateToPositions.put(state, new ArrayList<>());
                }
                blockStateToPositions.get(state).add(pos);
            }
        }

        // 将集合的内容映射到字典，并按值降序排列
        Map<BlockState, Integer> minableBlockCount = new HashMap<>();
        for (BlockState state : miningBlockStates) {
            minableBlockCount.put(state, minableBlockCount.getOrDefault(state, 0) + 1);
        }
        minableBlockCount = minableBlockCount.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        boolean isMining = !minableBlockCount.isEmpty();
        int miningBlockCount = 0;
        for (Map.Entry<BlockState, Integer> kv : minableBlockCount.entrySet()) {
            miningBlockState = kv.getKey();
            miningBlockCount = kv.getValue();
            break;
        }
        if (level.isClientSide) {
//            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (isMining) {
                progress += baseMiningSpeed * miningBlockCount / 20;
                /*if (localPlayer != null)
                    level.destroyBlockProgress(localPlayer.getId(), miningBlockPos, (int) (progress * 10));*/
                if (progress >= 1) {
                    if (miningBlockState != null)
                        for (BlockPos pos : miningBlocksPos) {
                            level.addDestroyBlockEffect(pos, miningBlockState);
                        }
                    progress = 0;
                }
            } else {
                progress = 0;
            }
            /*else {
                if (localPlayer != null)
                    level.destroyBlockProgress(localPlayer.getId(), miningBlockPos, 10);
            }*/
            return;
        }
        // 服务器端
        if (isMining) {
            progress += baseMiningSpeed * miningBlockCount / 20;
            if (progress >= 1) {
                for (ItemStack drop : Block.getDrops(miningBlockState, (ServerLevel) level, miningBlocksPos.get(0), null)) {
                    Block.popResource(level, getBlockPos().above(), drop);
                }
                level.playSound(null, getBlockPos(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1f, 1f);
                progress = 0;
            }
        } else {
            progress = 0;
        }
    }

    public float getProgress() {
        return progress;
    }

    public BlockState getMiningBlockState() {
        return miningBlockState;
    }
}
