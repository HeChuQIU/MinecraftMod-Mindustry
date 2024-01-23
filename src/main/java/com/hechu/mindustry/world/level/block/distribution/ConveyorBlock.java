package com.hechu.mindustry.world.level.block.distribution;

import com.hechu.mindustry.world.level.block.entity.distribution.ConveyorBlockEntity;
import com.hechu.mindustry.world.level.block.entity.turrets.TurretBlockEntityBase;
import com.hechu.mindustry.world.level.block.state.properties.ConveyorShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConveyorBlock extends BaseEntityBlock {
    public static final EnumProperty<ConveyorShape> SHAPE = EnumProperty.create("shape", ConveyorShape.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape FLAT_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    protected static final VoxelShape HALF_BLOCK_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public ConveyorBlock() {
        super(Properties.of().strength(0.7F).sound(SoundType.METAL));
        this.registerDefaultState(this.stateDefinition.any().setValue(SHAPE, ConveyorShape.NORTH_SOUTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(SHAPE)) {
            case DESCENDING_EAST, DESCENDING_NORTH, DESCENDING_SOUTH, DESCENDING_WEST, ASCENDING_EAST, ASCENDING_NORTH, ASCENDING_SOUTH, ASCENDING_WEST ->
                    HALF_BLOCK_AABB;
            default -> FLAT_AABB;
        };
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof ConveyorBlockEntity conveyorBlockEntity) {
                if (pLevel1.isClientSide) {
                    conveyorBlockEntity.clientTick();
                } else {
                    conveyorBlockEntity.serverTick();
                }
            }
        };
    }

    protected void updateState(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock) {
//        if (pBlock.defaultBlockState().isSignalSource() && (new RailState(pLevel, pPos, pState)).getConnections() == 3) {
//            this.updateDir(pLevel, pPos, pState, false);
//        }
    }

//    protected BlockState updateDir(Level pLevel, BlockPos pPos, BlockState pState, boolean pAlwaysPlace) {
//        if (pLevel.isClientSide) {
//            return pState;
//        } else {
//            RailShape railshape = pState.getValue(SHAPE);
//            return (new RailState(pLevel, pPos, pState)).place(pLevel.hasNeighborSignal(pPos), pAlwaysPlace, railshape).getState();
//        }
//    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide && pLevel.getBlockState(pPos).is(this)) {
            var shape = pState.getValue(SHAPE);
            if (shouldBeRemoved(pPos, pLevel, shape)) {
                dropResources(pState, pLevel, pPos);
                pLevel.removeBlock(pPos, pIsMoving);
            } else {
                this.updateState(pState, pLevel, pPos, pBlock);
            }
        }
    }

    private static boolean shouldBeRemoved(BlockPos pPos, Level pLevel, ConveyorShape pShape) {
        return false;
//        if (!canSupportRigidBlock(pLevel, pPos.below())) {
//            return true;
//        } else {
//            switch (pShape) {
//                case ASCENDING_EAST:
//                    return !canSupportRigidBlock(pLevel, pPos.east());
//                case ASCENDING_WEST:
//                    return !canSupportRigidBlock(pLevel, pPos.west());
//                case ASCENDING_NORTH:
//                    return !canSupportRigidBlock(pLevel, pPos.north());
//                case ASCENDING_SOUTH:
//                    return !canSupportRigidBlock(pLevel, pPos.south());
//                default:
//                    return false;
//            }
//        }
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
//            if (pState.getValue(SHAPE).isAscending()) {
//                pLevel.updateNeighborsAt(pPos.above(), this);
//            }
//            if (this.isStraight) {
//                pLevel.updateNeighborsAt(pPos, this);
//                pLevel.updateNeighborsAt(pPos.below(), this);
//            }
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        BlockState blockstate = super.defaultBlockState();
        Direction direction = pContext.getHorizontalDirection();
        boolean flag1 = direction == Direction.EAST || direction == Direction.WEST;
        return blockstate.setValue(SHAPE, flag1 ? ConveyorShape.EAST_WEST : ConveyorShape.NORTH_SOUTH).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pDirection,
                                           @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel,
                                           @NotNull BlockPos pNeighborPos, @NotNull BlockPos pCurrentPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ConveyorBlockEntity(pPos, pState);
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     *
     * @param state
     * @deprecated call via {@link BlockStateBase#getRenderShape}
     * whenever possible. Implementing/overriding is fine.
     */
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
