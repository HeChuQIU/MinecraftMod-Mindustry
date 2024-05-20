package com.hechu.mindustry.world.level.block.distribution;

import com.hechu.mindustry.world.level.block.entity.distribution.ConveyorBlockEntity;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return switch (pState.getValue(SHAPE)) {
            case DESCENDING_EAST, DESCENDING_NORTH, DESCENDING_SOUTH, DESCENDING_WEST, ASCENDING_EAST, ASCENDING_NORTH,
                 ASCENDING_SOUTH, ASCENDING_WEST -> HALF_BLOCK_AABB;
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

    protected void updateState(BlockState state, Level level, BlockPos pos, Block block) {
        ConveyorShape shape = state.getValue(SHAPE);
        BlockState blockState = this.updateDir(level, pos, state, false);
        if (blockState.getValue(SHAPE) != shape) {
            level.setBlock(pos, blockState, 3);
            level.neighborChanged(pos, block, pos);
        }
    }

    protected BlockState updateDir(Level level, BlockPos pos, BlockState state, boolean alwaysplace) {
        if (level.isClientSide) {
            return state;
        } else {
            BlockState blockstate = super.defaultBlockState();
            ConveyorShape shape = getConveyorShape(level, state.getValue(SHAPE).getOutputDirection(), pos);
            blockstate = blockstate.setValue(SHAPE, shape);
            return blockstate;
        }
    }

    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pBlock, @NotNull BlockPos pFromPos, boolean pIsMoving) {
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

    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
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

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = super.defaultBlockState();
        ConveyorShape shape = getConveyorShape(context.getLevel(), context.getHorizontalDirection(), context.getClickedPos());
        blockstate = blockstate.setValue(SHAPE, shape);
        return blockstate;
    }

    private static @NotNull ConveyorShape getConveyorShape(Level level, Direction outputDirection, BlockPos pos) {
        BlockPos outputPos = pos.relative(outputDirection);
        ConveyorShape shape;
        boolean output = Stream.of(level.getBlockState(outputPos))
                .filter(s -> s.getValues().containsKey(SHAPE))
                .flatMap(s -> Arrays.stream(s.getValue(SHAPE).getInputBlockPos(outputPos)))
                .anyMatch(pos::equals);
        boolean outputUp = Stream.of(level.getBlockState(outputPos.above()))
                .filter(s -> s.getValues().containsKey(SHAPE))
                .map(s -> s.getValue(SHAPE).getOutputBlockPos(outputPos.above()))
                .anyMatch(p -> !pos.above().equals(p));
        if (!output && outputUp) {
            shape = switch (outputDirection) {
                case NORTH -> ConveyorShape.ASCENDING_NORTH;
                case SOUTH -> ConveyorShape.ASCENDING_SOUTH;
                case WEST -> ConveyorShape.ASCENDING_WEST;
                case EAST -> ConveyorShape.ASCENDING_EAST;
                default -> ConveyorShape.NORTH_SOUTH;
            };
        } else {
            Direction inputDirection = outputDirection.getOpposite();
            boolean input = Stream.of(level.getBlockState(pos.relative(inputDirection)))
                    .filter(s -> s.getValues().containsKey(SHAPE))
                    .map(s -> s.getValue(SHAPE).getOutputBlockPos(pos.relative(inputDirection)))
                    .anyMatch(pos::equals);
            boolean inputUp = Stream.of(level.getBlockState(pos.relative(inputDirection).above()))
                    .filter(s -> s.getValues().containsKey(SHAPE))
                    .map(s -> s.getValue(SHAPE).getOutputBlockPos(pos.relative(inputDirection).above()))
                    .anyMatch(pos.above()::equals);
            if (!input && inputUp) {
                shape = switch (outputDirection) {
                    case NORTH -> ConveyorShape.DESCENDING_NORTH;
                    case SOUTH -> ConveyorShape.DESCENDING_SOUTH;
                    case WEST -> ConveyorShape.DESCENDING_WEST;
                    case EAST -> ConveyorShape.DESCENDING_EAST;
                    default -> ConveyorShape.NORTH_SOUTH;
                };
            } else {
                Set<Direction> inputDirections = Arrays.stream(Direction.values())
                        .filter(d -> d != outputDirection && d != Direction.UP && d != Direction.DOWN)
                        .filter(d -> Stream.of(pos.relative(d))
                                .filter(p -> level.getBlockState(p).getValues().containsKey(SHAPE))
                                .anyMatch(p -> level.getBlockState(p).getValue(SHAPE).getOutputBlockPos(p).equals(pos))
                        )
                        .collect(Collectors.toSet());
                switch (outputDirection) {
                    case NORTH -> {
                        if (inputDirections.containsAll(Set.of(Direction.SOUTH, Direction.WEST, Direction.EAST)))
                            shape = ConveyorShape.NORTH_ALL;
                        else if (inputDirections.containsAll(Set.of(Direction.SOUTH, Direction.WEST)))
                            shape = ConveyorShape.NORTH_WEST_SOUTH;
                        else if (inputDirections.containsAll(Set.of(Direction.SOUTH, Direction.EAST)))
                            shape = ConveyorShape.NORTH_EAST_SOUTH;
                        else if (inputDirections.containsAll(Set.of(Direction.WEST, Direction.EAST)))
                            shape = ConveyorShape.NORTH_WEST_EAST;
                        else if (inputDirections.contains(Direction.SOUTH))
                            shape = ConveyorShape.NORTH_SOUTH;
                        else if (inputDirections.contains(Direction.WEST))
                            shape = ConveyorShape.NORTH_WEST;
                        else if (inputDirections.contains(Direction.EAST))
                            shape = ConveyorShape.NORTH_EAST;
                        else
                            shape = ConveyorShape.NORTH_SOUTH;
                    }
                    case SOUTH -> {
                        if (inputDirections.containsAll(Set.of(Direction.NORTH, Direction.WEST, Direction.EAST)))
                            shape = ConveyorShape.SOUTH_ALL;
                        else if (inputDirections.containsAll(Set.of(Direction.NORTH, Direction.WEST)))
                            shape = ConveyorShape.SOUTH_WEST_NORTH;
                        else if (inputDirections.containsAll(Set.of(Direction.NORTH, Direction.EAST)))
                            shape = ConveyorShape.SOUTH_EAST_NORTH;
                        else if (inputDirections.containsAll(Set.of(Direction.WEST, Direction.EAST)))
                            shape = ConveyorShape.SOUTH_WEST_EAST;
                        else if (inputDirections.contains(Direction.NORTH))
                            shape = ConveyorShape.SOUTH_NORTH;
                        else if (inputDirections.contains(Direction.WEST))
                            shape = ConveyorShape.SOUTH_WEST;
                        else if (inputDirections.contains(Direction.EAST))
                            shape = ConveyorShape.SOUTH_EAST;
                        else
                            shape = ConveyorShape.SOUTH_NORTH;
                    }
                    case WEST -> {
                        if (inputDirections.containsAll(Set.of(Direction.NORTH, Direction.SOUTH, Direction.EAST)))
                            shape = ConveyorShape.WEST_ALL;
                        else if (inputDirections.containsAll(Set.of(Direction.NORTH, Direction.SOUTH)))
                            shape = ConveyorShape.WEST_NORTH_SOUTH;
                        else if (inputDirections.containsAll(Set.of(Direction.NORTH, Direction.EAST)))
                            shape = ConveyorShape.WEST_NORTH_EAST;
                        else if (inputDirections.containsAll(Set.of(Direction.SOUTH, Direction.EAST)))
                            shape = ConveyorShape.WEST_SOUTH_EAST;
                        else if (inputDirections.contains(Direction.NORTH))
                            shape = ConveyorShape.WEST_NORTH;
                        else if (inputDirections.contains(Direction.SOUTH))
                            shape = ConveyorShape.WEST_SOUTH;
                        else if (inputDirections.contains(Direction.EAST))
                            shape = ConveyorShape.WEST_EAST;
                        else
                            shape = ConveyorShape.WEST_EAST;
                    }
                    case EAST -> {
                        if (inputDirections.containsAll(Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST)))
                            shape = ConveyorShape.EAST_ALL;
                        else if (inputDirections.containsAll(Set.of(Direction.NORTH, Direction.SOUTH)))
                            shape = ConveyorShape.EAST_NORTH_SOUTH;
                        else if (inputDirections.containsAll(Set.of(Direction.NORTH, Direction.WEST)))
                            shape = ConveyorShape.EAST_NORTH_WEST;
                        else if (inputDirections.containsAll(Set.of(Direction.SOUTH, Direction.WEST)))
                            shape = ConveyorShape.EAST_SOUTH_WEST;
                        else if (inputDirections.contains(Direction.NORTH))
                            shape = ConveyorShape.EAST_NORTH;
                        else if (inputDirections.contains(Direction.SOUTH))
                            shape = ConveyorShape.EAST_SOUTH;
                        else if (inputDirections.contains(Direction.WEST))
                            shape = ConveyorShape.EAST_WEST;
                        else
                            shape = ConveyorShape.EAST_WEST;
                    }
                    default -> shape = ConveyorShape.NORTH_SOUTH;
                }
            }
        }
        return shape;
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
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
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
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
