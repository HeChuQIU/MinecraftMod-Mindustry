package net.hechuqiu.mindustry.common.boundingblock

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

abstract class BoundingBlockHandler {
    /**
     * 处理边界方块的逻辑
     *
     * @param DATA 传入的数据类型
     * @param level 当前的世界
     * @param pos 主方块的位置
     * @param state 主方块的状态
     * @param direction 主方块的朝向
     * @param data 传入的数据
     * @param predicate 处理的条件
     */
    abstract fun <DATA> handle(
        level: Level,
        pos: BlockPos,
        state: BlockState,
        direction: Direction = Direction.NORTH,
        data: DATA,
        predicate: (Level, BlockPos, DATA) -> Boolean
    ): Boolean
}