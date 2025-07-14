package net.hechuqiu.mindustry.common.block.interfaces

import net.hechuqiu.mindustry.common.boundingblock.BoundingBlockHandler

interface IBoundingBlock {
    fun getBoundingHandler(): BoundingBlockHandler
}