package net.hechuqiu.mindustry.common.item

import net.hechuqiu.mindustry.common.block.interfaces.IMultiblock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

open class MultiblockItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    override fun canPlace(context: BlockPlaceContext, state: BlockState): Boolean {
        return (state.block as? IMultiblock)?.getBoundingHandler()?.handle(
            context.level, context.clickedPos, state, context.player?.direction?.opposite, null
        ) { level, blockPos, _ ->
            level.getBlockState(blockPos).canBeReplaced()
        } ?: super.canPlace(context, state)
    }

    override fun updatePlacementContext(context: BlockPlaceContext): BlockPlaceContext? {
        val block = this.block as? IMultiblock ?: return super.updatePlacementContext(context)
        val placePos = context.clickedPos
        val yRot = context.player?.yRot ?: 0.0
        val hoverDirection = when (yRot) {
            in 0..90 -> Direction.EAST
            in 90..180 -> Direction.SOUTH
            in 180..270 -> Direction.WEST
            else -> Direction.NORTH
        }
        val offset = block.getBoundingHandler().mainBlockOffset
        val ox = offset.x
        val oz = offset.z
        val size = block.getBoundingHandler().multiblockSize
        val sx = size.x
        val sz = size.z
        val newPos = placePos.offset(
            when (hoverDirection) {
                Direction.EAST -> BlockPos(oz, 0, -ox).offset(-sx, 0, 0)
                Direction.SOUTH -> BlockPos(ox, 0, oz).offset(-sx, 0, -sz)
                Direction.WEST -> BlockPos(-oz, 0, ox).offset(0, 0, -sz)
                else -> BlockPos(-ox, 0, -oz).offset(0, 0, 0)
            }
        ).offset(offset)
        return BlockPlaceContext.at(context, newPos, hoverDirection)
    }
}