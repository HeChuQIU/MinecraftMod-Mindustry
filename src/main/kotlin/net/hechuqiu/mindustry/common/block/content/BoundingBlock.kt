package net.hechuqiu.mindustry.common.block.content

import net.hechuqiu.mindustry.common.tile.TileEntityBoundingBlock
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.PushReaction
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import java.util.function.BiConsumer

private typealias ShapeProxy = (BlockState, BlockGetter?, BlockPos?, CollisionContext?) -> VoxelShape?

class BoundingBlock : MindustryBlock(
    Properties.of()
//        .mapColor(BlockResourceInfo.STEEL.mapColor)
        .strength(3.5f, 4.8f)
        .requiresCorrectToolForDrops()
        .dynamicShape()
        .noOcclusion()
        .isViewBlocking({ _, _, _ -> false })
        .pushReaction(PushReaction.BLOCK)
), EntityBlock {

    fun getMainBlockPos(world: BlockGetter?, thisPos: BlockPos): BlockPos? {
        val bbte = world?.getBlockEntity(thisPos) as? TileEntityBoundingBlock ?: return null
        if (thisPos != bbte.getMainPos()) {
            return bbte.getMainPos()
        }
        return null;
    }

    override fun getRenderShape(state: BlockState) = RenderShape.INVISIBLE

    override fun getShape(state: BlockState, world: BlockGetter, pos: BlockPos, context: CollisionContext) =
        proxyShape(world, pos, context) { s, _, p, ctx -> s.getShape(world, p, ctx) }

    override fun getCollisionShape(state: BlockState, world: BlockGetter, pos: BlockPos, context: CollisionContext) =
        proxyShape(world, pos, context) { s, _, p, ctx -> s.getCollisionShape(world, p, ctx) }

    override fun getVisualShape(state: BlockState, world: BlockGetter, pos: BlockPos, context: CollisionContext) =
        proxyShape(world, pos, context) { s, _, p, ctx -> s.getVisualShape(world, p, ctx) }

    override fun getOcclusionShape(state: BlockState, world: BlockGetter, pos: BlockPos) =
        proxyShape(world, pos, null) { s, _, p, _ -> s.getOcclusionShape(world, p) }

    override fun getBlockSupportShape(state: BlockState, world: BlockGetter, pos: BlockPos) =
        proxyShape(world, pos, null) { s, _, p, _ -> s.getBlockSupportShape(world, p) }

    override fun getInteractionShape(state: BlockState, world: BlockGetter, pos: BlockPos) =
        proxyShape(world, pos, null) { s, _, p, _ -> s.getInteractionShape(world, p) }

    private fun proxyShape(
        world: BlockGetter,
        pos: BlockPos,
        context: CollisionContext?,
        proxy: ShapeProxy
    ): VoxelShape {
        val mainPos = getMainBlockPos(world, pos) ?: return Shapes.empty()
        val mainState = world.getBlockState(mainPos)
        val shape = proxy(mainState, world, mainPos, context)
        val offset = pos.subtract(mainPos)
        return shape?.move(-offset.x.toDouble(), -offset.y.toDouble(), -offset.z.toDouble()) ?: Shapes.empty()
    }

    override fun canBeReplaced(state: BlockState, fluid: Fluid) = false

    override fun useWithoutItem(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        hit: BlockHitResult
    ): InteractionResult {
        val mainPos = getMainBlockPos(world, pos) ?: return InteractionResult.FAIL
        return world.getBlockState(mainPos).useWithoutItem(world, player, hit.withPosition(mainPos))
    }

    override fun useItemOn(
        stack: ItemStack,
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult
    ): ItemInteractionResult {
        val mainPos = getMainBlockPos(world, pos) ?: return ItemInteractionResult.FAIL
        return world.getBlockState(mainPos).useItemOn(stack, world, player, hand, hit.withPosition(mainPos))
    }

    override fun onRemove(state: BlockState, world: Level, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        if (!state.`is`(newState.block)) {
            getMainBlockPos(world, pos)?.let { mainPos ->
                world.getBlockState(mainPos).takeUnless { it.isAir }?.let {
                    world.removeBlock(mainPos, false)
                }
            }
            super.onRemove(state, world, pos, newState, isMoving)
        }
    }

    override fun getCloneItemStack(
        state: BlockState,
        target: HitResult,
        world: LevelReader,
        pos: BlockPos,
        player: Player
    ): ItemStack {
        val mainPos = getMainBlockPos(world, pos) ?: return ItemStack.EMPTY
        return world.getBlockState(mainPos).block.getCloneItemStack(
            world.getBlockState(mainPos),
            target,
            world,
            mainPos,
            player
        )
    }

    override fun onDestroyedByPlayer(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        willHarvest: Boolean,
        fluidState: FluidState
    ): Boolean {
        if (willHarvest) return true
        getMainBlockPos(world, pos)?.let { mainPos ->
            world.getBlockState(mainPos).takeUnless { it.isAir }?.let { mainState ->
                mainState.onDestroyedByPlayer(world, mainPos, player, false, mainState.fluidState)
            }
        }
        return super.onDestroyedByPlayer(state, world, pos, player, false, fluidState)
    }

    override fun playerWillDestroy(level: Level, pos: BlockPos, state: BlockState, player: Player): BlockState {
        getMainBlockPos(level, pos)?.let { mainPos ->
            level.getBlockState(mainPos).takeUnless { it.isAir }?.let { mainState ->
                mainState.block.playerWillDestroy(level, mainPos, mainState, player)
                return state
            }
        }
        return super.playerWillDestroy(level, pos, state, player)
    }

    override fun onExplosionHit(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        explosion: Explosion,
        dropConsumer: BiConsumer<ItemStack?, BlockPos?>
    ) {
        getMainBlockPos(level, pos)?.let { mainPos ->
            level.getBlockState(mainPos).onExplosionHit(level, mainPos, explosion, dropConsumer)
        } ?: super.onExplosionHit(state, level, pos, explosion, dropConsumer)
    }

    override fun spawnAfterBreak(
        state: BlockState,
        level: ServerLevel,
        pos: BlockPos,
        stack: ItemStack,
        dropExperience: Boolean
    ) {
        getMainBlockPos(level, pos)?.let { mainPos ->
            level.getBlockState(mainPos).takeUnless { it.isAir }?.spawnAfterBreak(level, mainPos, stack, dropExperience)
        }
        super.spawnAfterBreak(state, level, pos, stack, dropExperience)
    }

    override fun playerDestroy(
        world: Level,
        player: Player,
        pos: BlockPos,
        state: BlockState,
        te: BlockEntity?,
        stack: ItemStack
    ) {
        getMainBlockPos(world, pos)?.let { mainPos ->
            val mainState = world.getBlockState(mainPos)
            mainState.block.playerDestroy(
                world,
                player,
                mainPos,
                mainState,
                world.getBlockEntity(pos) as? TileEntityBoundingBlock,
                stack
            )
        } ?: super.playerDestroy(world, player, pos, state, te, stack)
        world.removeBlock(pos, false)
    }

    override fun neighborChanged(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        neighborBlock: Block,
        neighborPos: BlockPos,
        isMoving: Boolean
    ) {
        if (!world.isClientSide) {
            (world.getBlockEntity(pos) as? TileEntityBoundingBlock)
                ?.onNeighborChange(neighborBlock, neighborPos)
//            WorldUtils.getTileEntity(TileEntityBoundingBlock::class.java, world, pos)
//                ?.onNeighborChange(neighborBlock, neighborPos)
        }
        getMainBlockPos(world, pos)?.let { mainPos ->
            world.getBlockState(mainPos).handleNeighborChanged(world, mainPos, neighborBlock, neighborPos, isMoving)
        }
    }

    override fun hasAnalogOutputSignal(blockState: BlockState) = true

    override fun getAnalogOutputSignal(blockState: BlockState, world: Level, pos: BlockPos): Int {
        if (!world.isClientSide) {
//            WorldUtils.getTileEntity(TileEntityBoundingBlock::class.java, world, pos)?.let {
//                return it.comparatorSignal
//            }
        }
        return 0
    }

    override fun getDestroyProgress(state: BlockState, player: Player, world: BlockGetter, pos: BlockPos): Float {
        val mainPos = getMainBlockPos(world, pos) ?: return super.getDestroyProgress(state, player, world, pos)
        return world.getBlockState(mainPos).getDestroyProgress(player, world, mainPos)
    }

    override fun getExplosionResistance(
        state: BlockState,
        world: BlockGetter,
        pos: BlockPos,
        explosion: Explosion
    ): Float {
        val mainPos = getMainBlockPos(world, pos) ?: return super.getExplosionResistance(state, world, pos, explosion)
        return world.getBlockState(mainPos).getExplosionResistance(world, mainPos, explosion)
    }

    override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity? {
        return TileEntityBoundingBlock(p0,p1)
    }

    override fun canBeReplaced(state: BlockState, useContext: BlockPlaceContext): Boolean {
        return false
    }
}
