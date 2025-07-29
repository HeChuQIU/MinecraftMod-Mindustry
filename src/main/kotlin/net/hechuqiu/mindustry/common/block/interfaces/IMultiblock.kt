package net.hechuqiu.mindustry.common.block.interfaces

import net.hechuqiu.mindustry.common.multiblock.MultiblockHandler
import net.minecraft.core.BlockPos
import net.minecraft.core.Vec3i

interface IMultiblock {
    fun getBoundingHandler(): MultiblockHandler
}