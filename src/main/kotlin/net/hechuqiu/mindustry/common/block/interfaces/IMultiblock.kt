package net.hechuqiu.mindustry.common.block.interfaces

import net.hechuqiu.mindustry.common.multiblock.BoundingBlockHandler

interface IMultiblock {
    fun getBoundingHandler(): BoundingBlockHandler
}