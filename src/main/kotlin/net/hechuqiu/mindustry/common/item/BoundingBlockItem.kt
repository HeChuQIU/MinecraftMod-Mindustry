package net.hechuqiu.mindustry.common.item

import net.hechuqiu.mindustry.common.block.interfaces.IMultiblock
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

open class BoundingBlockItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    override fun canPlace(context: BlockPlaceContext, state: BlockState): Boolean {
        return (state.block as? IMultiblock)?.getBoundingHandler()?.handle(
            context.level, context.clickedPos, state, context.player?.direction?.opposite, null
        ) { level, blockPos, _ ->
            level.getBlockState(blockPos).canBeReplaced()
        } ?: super.canPlace(context, state)
    }
}