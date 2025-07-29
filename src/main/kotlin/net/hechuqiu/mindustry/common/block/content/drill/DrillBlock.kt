package net.hechuqiu.mindustry.common.block.content.drill

import net.hechuqiu.mindustry.common.block.content.MindustryEntityBlock
import net.hechuqiu.mindustry.common.block.interfaces.IMultiblock
import net.hechuqiu.mindustry.common.multiblock.MultiblockHandler
import net.hechuqiu.mindustry.common.tile.TileEntityDrillBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class DrillBlock(
    properties: Properties,
    val handler: MultiblockHandler
) :
    MindustryEntityBlock(properties), IMultiblock {

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.ENTITYBLOCK_ANIMATED
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        val size = handler.multiblockSize
        return Shapes.box(
            0.0, 0.0, 0.0,
            size.x.toDouble(), size.y.toDouble(), size.z.toDouble()
        )
    }

    override fun newBlockEntity(
        pos: BlockPos,
        state: BlockState
    ): BlockEntity? {
        return TileEntityDrillBlock(pos, state)
    }

    override fun getBoundingHandler(): MultiblockHandler = handler
}
