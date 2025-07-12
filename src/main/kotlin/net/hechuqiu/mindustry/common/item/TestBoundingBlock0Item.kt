package net.hechuqiu.mindustry.common.item

import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3

class TestBoundingBlock0Item(block: Block, properties: Properties) : BoundingBlockItem(block, properties) {
    override fun updatePlacementContext(context: BlockPlaceContext): BlockPlaceContext? {
        val pv = context.player?.getViewVector(0f) ?: Vec3.ZERO
        val clickedPos = when (context.horizontalDirection.opposite) {
            Direction.NORTH -> if (pv.x < 0) context.clickedPos.offset(-1, 0, 0) else context.clickedPos
            Direction.SOUTH -> if (pv.x > 0) context.clickedPos.offset(1, 0, 0) else context.clickedPos
            Direction.WEST -> if (pv.z > 0) context.clickedPos.offset(0, 0, 1) else context.clickedPos
            Direction.EAST -> if (pv.z < 0) context.clickedPos.offset(0, 0, -1) else context.clickedPos
            else -> context.clickedPos
        }

        return BlockPlaceContext.at(context, clickedPos, context.player!!.direction)
    }
}