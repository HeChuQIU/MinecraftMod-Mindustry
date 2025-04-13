package net.hechuqiu.mindustry.common.item

import net.hechuqiu.mindustry.common.block.interfaces.IBoundingBlock
import net.minecraft.core.Direction
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class BoundingBlockItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    override fun canPlace(context: BlockPlaceContext, state: BlockState): Boolean {
        return (state.block as? IBoundingBlock)?.getBoundingHandler()?.handle(
            context.level, context.clickedPos, state, context.player?.direction ?: Direction.NORTH, null
        ) { level, blockPos, _ ->
            level.getBlockState(blockPos).canBeReplaced()
        } ?: super.canPlace(context, state)
    }
}