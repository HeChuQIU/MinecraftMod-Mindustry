package net.hechuqiu.mindustry.common.block.content

import net.hechuqiu.mindustry.common.tile.MindustryBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class MindustryEntityBlock : MindustryBlock, EntityBlock {
    constructor(properties: Properties) : super(properties)

    abstract override fun newBlockEntity(
        pos: BlockPos,
        state: BlockState
    ): BlockEntity?

    @Suppress("UNCHECKED_CAST")
    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return getFullTicker(level, state, blockEntityType as BlockEntityType<MindustryBlockEntity>) as BlockEntityTicker<T>
    }

    fun  <T : MindustryBlockEntity> getFullTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T> =
        BlockEntityTicker { level, pos, state, blockEntity ->
            blockEntity.tick(level, pos, state)
            if (blockEntity.isClientSide()) {
                blockEntity.clientTick(level, pos, state)
            } else {
                blockEntity.serverTick(level, pos, state)
            }
        }

    fun <T : MindustryBlockEntity> getClientTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T> =
        BlockEntityTicker { level, pos, state, blockEntity -> blockEntity.clientTick(level, pos, state) }

    fun <T : MindustryBlockEntity> getServerTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T> =
        BlockEntityTicker { level, pos, state, blockEntity -> blockEntity.serverTick(level, pos, state) }
}
