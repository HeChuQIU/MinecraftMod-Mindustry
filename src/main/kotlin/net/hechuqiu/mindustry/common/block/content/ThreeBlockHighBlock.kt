package net.hechuqiu.mindustry.common.block.content

import net.hechuqiu.mindustry.common.block.interfaces.IMultiblock
import net.hechuqiu.mindustry.common.multiblock.BoundingBlockHandler
import net.minecraft.core.BlockPos
import net.minecraft.core.BlockPos.MutableBlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class ThreeBlockHighBlock : MindustryBlock, IMultiblock {
    constructor(properties: Properties) : super(properties)

    override fun getBoundingHandler(): BoundingBlockHandler {
        return object : BoundingBlockHandler(){
            override fun <DATA> handle(
                level: Level,
                pos: BlockPos,
                state: BlockState,
                direction: Direction?,
                data: DATA,
                predicate: (Level, BlockPos, DATA) -> Boolean
            ): Boolean {
                val mutable = MutableBlockPos()
                for (i in 0..1) {
                    mutable.setWithOffset(pos, 0, i + 1, 0)
                    if (!predicate(level, mutable, data)) {
                        return false
                    }
                }
                return true
            }
        }
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shapes.box(0.0, 0.0, 0.0, 1.0, 3.0, 1.0)
    }

    override fun getVisualShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return Shapes.box(0.0, 0.0, 0.0, 1.0, 3.0, 1.0)
    }

    override fun getOcclusionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
        return Shapes.box(0.0, 0.0, 0.0, 1.0, 3.0, 1.0)
    }

    override fun getBlockSupportShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
        return Shapes.box(0.0, 0.0, 0.0, 1.0, 3.0, 1.0)
    }

    override fun getInteractionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape {
        return Shapes.box(0.0, 0.0, 0.0, 1.0, 3.0, 1.0)
    }

    override fun getCollisionShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return Shapes.box(0.0, 0.0, 0.0, 1.0, 3.0, 1.0)
    }
}