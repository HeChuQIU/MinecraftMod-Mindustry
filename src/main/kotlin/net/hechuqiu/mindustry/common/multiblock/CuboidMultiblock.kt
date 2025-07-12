package net.hechuqiu.mindustry.common.multiblock

import net.hechuqiu.mindustry.common.block.content.MindustryBlock
import net.hechuqiu.mindustry.common.block.content.TestMultiblock0.Companion.FACING
import net.hechuqiu.mindustry.common.block.interfaces.IMultiblock
import net.minecraft.core.Direction

open class CuboidMultiblock : MindustryBlock, IMultiblock {
    constructor(properties: Properties) : super(properties)

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        )
    }

    override fun getBoundingHandler(): BoundingBlockHandler {
        TODO("Not yet implemented")
    }


}