package net.hechuqiu.mindustry.common.multiblock

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Vec3i
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

abstract class MultiblockHandler {
    /**
     * 处理边界方块的逻辑
     *
     * @param DATA 传入的数据类型
     * @param level 当前的世界
     * @param pos 主方块的位置
     * @param state 主方块的状态
     * @param direction 主方块的朝向。默认面朝北方（-z轴），且主方块应该位于整个结构的西北角
     * @param data 传入的数据
     * @param predicate 处理的条件
     */
    abstract fun <DATA> handle(
        level: Level,
        pos: BlockPos,
        state: BlockState,
        direction: Direction?,
        data: DATA,
        predicate: (Level, BlockPos, DATA) -> Boolean
    ): Boolean

    abstract val multiblockSize: Vec3i

    /**
     * 获取主方块的相对整个方块西北下角（xyz最小）的偏移位置
     * @return 主方块的偏移位置
     */
    abstract val mainBlockOffset: BlockPos
}