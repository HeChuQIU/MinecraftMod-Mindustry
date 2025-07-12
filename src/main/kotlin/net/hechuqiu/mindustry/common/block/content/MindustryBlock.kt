package net.hechuqiu.mindustry.common.block.content

import net.hechuqiu.mindustry.common.block.interfaces.IMultiblock
import net.hechuqiu.mindustry.common.registries.MindustryBlocks
import net.hechuqiu.mindustry.common.tile.TileEntityBoundingBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties

open class MindustryBlock : Block {
    constructor(properties: Properties) : super(properties)

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, placer, stack)
        if (!level.isClientSide) {
            (this as? IMultiblock)?.let { mainBlock ->
                val handler = mainBlock.getBoundingHandler()
                handler.handle(level, pos, state, placer?.direction?.opposite, pos) { level, blockPos, mainPos ->
                    val boundingBlock = MindustryBlocks.BOUNDING_BLOCK
                    val boundingState = boundingBlock.defaultBlockState()
                    level.setBlock(blockPos, boundingState, UPDATE_ALL)
                    val tile = level.getBlockEntity(blockPos)
                    (tile as TileEntityBoundingBlock).setMainLocation(mainPos)
                    true
                }
            }
        }
    }

    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        movedByPiston: Boolean
    ) {
        super.onRemove(state, level, pos, newState, movedByPiston)
        if (!level.isClientSide) {
            (this as? IMultiblock)?.let { mainBlock ->
                val handler = mainBlock.getBoundingHandler()
                handler.handle(level, pos, state, state.getValue(BlockStateProperties.FACING), null) { level, blockPos, _ ->
                    level.removeBlock(blockPos, false)
                    true
                }
            }
        }
    }
}