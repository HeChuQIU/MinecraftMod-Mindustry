package net.hechuqiu.mindustry.common.multiblock

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Vec3i
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object MultiblockHelper {
    fun handler(size: Vec3i): MultiblockHandler {
        return object : MultiblockHandler() {
            override fun <DATA> handle(
                level: Level,
                pos: BlockPos,
                state: BlockState,
                direction: Direction?,
                data: DATA,
                predicate: (Level, BlockPos, DATA) -> Boolean
            ): Boolean {
                for (x in 0 until size.x) {
                    for (z in 0 until size.z) {
                        for (y in 0 until size.y) {
                            if (x == 0 && z == 0 && y == 0) {
                                continue
                            }
                            val p = pos.offset(x, y, z)
                            if (!predicate(level, p, data)) {
                                return false
                            }
                        }
                    }
                }
                return true
            }

            override val multiblockSize: Vec3i
                get() = size
            override val mainBlockOffset: BlockPos
                get() = BlockPos(
                    (size.x - 1) / 2,
                    (size.y - 1) / 2,
                    (size.z - 1) / 2
                )
        }
    }

    fun handlerDirectional(size: Vec3i): MultiblockHandler {
        return object : MultiblockHandler() {
            override fun <DATA> handle(
                level: Level,
                pos: BlockPos,
                state: BlockState,
                direction: Direction?,
                data: DATA,
                predicate: (Level, BlockPos, DATA) -> Boolean
            ): Boolean {
                for (x in 0 until size.x) {
                    for (z in 0 until size.z) {
                        for (y in 0 until size.y) {
                            if (x == 0 && z == 0 && y == 0) {
                                continue
                            }
                            val p = when (direction) {
                                Direction.UP, Direction.DOWN -> throw IllegalArgumentException("Direction 不应该是 UP 或 DOWN，但实际却是 $direction")
                                Direction.NORTH, null -> pos.offset(x, y, z)
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

            override val multiblockSize: Vec3i
                get() = size
            override val mainBlockOffset: BlockPos
                get() = BlockPos(
                    (size.x - 1) / 2,
                    (size.y - 1) / 2,
                    (size.z - 1) / 2
                )
        }
    }
}