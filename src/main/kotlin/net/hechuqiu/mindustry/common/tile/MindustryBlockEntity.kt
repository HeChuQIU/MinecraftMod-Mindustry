package net.hechuqiu.mindustry.common.tile

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class MindustryBlockEntity : BlockEntity {
    constructor(type: BlockEntityType<*>, pos: BlockPos, blockState: BlockState) : super(type, pos, blockState)

    fun isClientSide(): Boolean {
        return getLevel()!!.isClientSide()
    }

    abstract fun tick(level: Level, pos: BlockPos, state: BlockState)
    abstract fun clientTick(level: Level, pos: BlockPos, state: BlockState)
    abstract fun serverTick(level: Level, pos: BlockPos, state: BlockState)
}