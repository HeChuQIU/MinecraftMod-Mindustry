package com.hechu.mindustry.block;

import com.hechu.mindustry.utils.AdaptedItemHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.hechu.mindustry.block.DrillBlock.PART;
import static net.minecraft.world.level.material.Fluids.WATER;

public abstract class DrillBlockEntity extends BlockEntity implements GeoBlockEntity {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final int SLOT_OUTPUT_COUNT = 1;
    private static final String ITEMS_OUTPUT_TAG = "item_output_tag";
    private static final String MASTER_POS = "master_pos";

    protected float progress = 0;
    protected List<BlockPos> miningBlocksPos = Collections.emptyList();
    protected Predicate<BlockState> isMinable = state -> false;
    protected float baseMiningSpeed = 0;
    protected BlockState miningBlockState = null;
    protected float miningSpeed;
    public BlockPos masterPos;
    private final ItemStackHandler outputItems = createItemHandler(SLOT_OUTPUT_COUNT);
    private final LazyOptional<IItemHandler> outputItemHandler = LazyOptional.of(() -> new AdaptedItemHandler(outputItems) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack;
        }
    });
    private final FluidTank fluidTank = new FluidTank(1000 * 32);
    private final LazyOptional<IFluidTank> fluidHandler = LazyOptional.of(() -> fluidTank);

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        outputItemHandler.invalidate();
    }

    public ItemStackHandler getOutputItems() {
        return outputItems;
    }

    @Nonnull
    private ItemStackHandler createItemHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS_OUTPUT_TAG, outputItems.serializeNBT());
        tag.putLong(MASTER_POS, masterPos.asLong());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS_OUTPUT_TAG))
            outputItems.deserializeNBT(tag.getCompound(ITEMS_OUTPUT_TAG));
        if (tag.contains(MASTER_POS))
            masterPos = BlockPos.of(tag.getLong(MASTER_POS));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
            if (side != null && side != Direction.DOWN)
                return outputItemHandler.cast();

        if (cap == ForgeCapabilities.FLUID_HANDLER)
            if (side != null && side != Direction.DOWN)
                return fluidHandler.cast();
        return super.getCapability(cap, side);
    }

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

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void tick() {
        if (level == null)
            return;
//        if (!level.isClientSide
//                && !(level.getBlockEntity(masterPos) instanceof DrillBlockEntity
//                || (level.getBlockState(masterPos).getValue(PART).equals(DrillBlock.DrillPart.MASTER)))) {
//            level.removeBlock(getBlockPos(), false);
//        }
//        level.removeBlock(getBlockPos(), false);
        if (masterPos == null || !level.getBlockState(masterPos).hasProperty(PART) || !DrillBlock.DrillPart.MASTER.equals(level.getBlockState(masterPos).getValue(PART)))
            level.destroyBlock(getBlockPos(), false);
        if (!getBlockState().getValue(PART).equals(DrillBlock.DrillPart.MASTER))
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
        miningSpeed = baseMiningSpeed / miningBlocksPos.size() * miningBlockCount;
        FluidStack fluidStack = fluidTank.drain(6, IFluidHandler.FluidAction.EXECUTE);
        if (!fluidStack.isEmpty())
            miningSpeed *= 2.56F;
        if (level.isClientSide) {
//            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (isMining) {
                progress += miningSpeed / 20;
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
            progress += miningSpeed / 20;
            if (progress >= 1) {
                for (ItemStack drop : Block.getDrops(miningBlockState, (ServerLevel) level, miningBlocksPos.get(0), null)) {
                    //Block.popResource(level, getBlockPos().above(), drop);
                    outputItems.insertItem(0, drop, false);
                }
                level.playSound(null, getBlockPos(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1f, 1f);
                progress = 0;
            }
        } else {
            progress = 0;
        }
    }

    public float getProgress() {
        if (getBlockState().getValue(PART).equals(DrillBlock.DrillPart.MASTER))
            return progress;
        else if (level != null) {
            return ((DrillBlockEntity) Objects.requireNonNull(level.getBlockEntity(masterPos))).getProgress();
        }
        return 0;
    }

    public BlockState getMiningBlockState() {
        if (getBlockState().getValue(PART).equals(DrillBlock.DrillPart.MASTER))
            return miningBlockState;
        else if (level != null) {
            return ((DrillBlockEntity) Objects.requireNonNull(level.getBlockEntity(masterPos))).getMiningBlockState();
        }
        return null;
    }

    public float getMiningSpeed() {
        if (getBlockState().getValue(PART).equals(DrillBlock.DrillPart.MASTER))
            return miningSpeed;
        else if (level != null) {
            return ((DrillBlockEntity) Objects.requireNonNull(level.getBlockEntity(masterPos))).getMiningSpeed();
        }
        return 0;
    }
}
