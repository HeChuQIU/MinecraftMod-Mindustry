package net.hechuqiu.mindustry.common.tile

import net.hechuqiu.mindustry.common.registries.MindustryTileEntity.BOUNDING_BLOCK
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class TileEntityBoundingBlock : MindustryBlockEntity {
    constructor(pos: BlockPos, blockState: BlockState) : super(BOUNDING_BLOCK.get(), pos, blockState)

    private var mainPos: BlockPos = BlockPos.ZERO
    private var receivedCoords: Boolean = false

    fun setMainLocation(pos: BlockPos?) {
        receivedCoords = pos != null
        if (!isRemote()) {
            mainPos = pos ?: BlockPos.ZERO
//            sendUpdatePacket()
        }
    }

    fun getMainPos(): BlockPos {
        return mainPos
    }

    fun onNeighborChange(block: Block?, neighborPos: BlockPos?) {
        if (!isRemote()) {
            val power = level!!.getBestNeighborSignal(blockPos)
//            if (currentRedstoneLevel != power) {
//                val main: IBoundingBlock = getMain()
//                if (main != null) {
//                    main.onBoundingBlockPowerChange(worldPosition, currentRedstoneLevel, power)
//                }
//                currentRedstoneLevel = power
//            }
        }
    }

}