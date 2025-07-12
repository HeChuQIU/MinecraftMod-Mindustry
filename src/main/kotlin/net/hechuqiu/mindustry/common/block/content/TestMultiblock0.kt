package net.hechuqiu.mindustry.common.block.content

import com.mojang.logging.LogUtils
import net.hechuqiu.mindustry.common.block.interfaces.IMultiblock
import net.hechuqiu.mindustry.common.multiblock.BoundingBlockHandler
import net.hechuqiu.mindustry.common.multiblock.CuboidMultiblock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import org.slf4j.Logger


class TestMultiblock0(properties: Properties) : MindustryBlock(properties), IMultiblock {
    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        )
    }

    companion object {
        val LOGGER: Logger = LogUtils.getLogger()
        val FACING: EnumProperty<Direction> = BlockStateProperties.FACING
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val facing = context.horizontalDirection.opposite
        return defaultBlockState().setValue(FACING, facing)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        val facing = state.getValue(FACING)
        return when (facing) {
            Direction.NORTH, Direction.UP, Direction.DOWN, null -> Shapes.box(0.0, 0.0, 0.0, 2.0, 2.0, 2.0)
            Direction.SOUTH -> Shapes.box(-1.0, 0.0, -1.0, 1.0, 2.0, 1.0)
            Direction.WEST -> Shapes.box(0.0, 0.0, -1.0, 2.0, 2.0, 1.0)
            Direction.EAST -> Shapes.box(-1.0, 0.0, 0.0, 1.0, 2.0, 2.0)
        }
    }

    override fun getBoundingHandler(): BoundingBlockHandler {
        return object : BoundingBlockHandler() {
            override fun <DATA> handle(
                level: Level,
                pos: BlockPos,
                state: BlockState,
                direction: Direction?,
                data: DATA,
                predicate: (Level, BlockPos, DATA) -> Boolean
            ): Boolean {
                for (x in 0..1) {
                    for (z in 0..1) {
                        for (y in 0..1) {
                            if (x == 0 && z == 0 && y == 0) {
                                continue
                            }
                            val p = when (direction) {
                                Direction.NORTH, null, Direction.UP, Direction.DOWN -> {
                                    if (direction == Direction.UP || direction == Direction.DOWN) {
                                        LOGGER.warn("Direction 不应该是 UP 或 DOWN，但实际却是 $direction")
                                    }
                                    pos.offset(x, y, z)
                                }

                                Direction.SOUTH -> pos.offset(-x, y, -z)
                                Direction.WEST -> pos.offset(z, y, -x)
                                Direction.EAST -> pos.offset(-z, y, x)
                            }

                            if (!predicate(level, p, data)) {
                                return false
                            }
                        }
                    }
                }
                return true
            }
        }
    }

}