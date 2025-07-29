package net.hechuqiu.mindustry.common.tile

import net.hechuqiu.mindustry.common.registries.MindustryTileEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.ContainerHelper
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.common.Tags
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.*
import software.bernie.geckolib.util.GeckoLibUtil

class TileEntityDrillBlock(pos: BlockPos, blockState: BlockState) :
    MindustryBlockEntity(MindustryTileEntity.DRILL_BLOCK.get(), pos, blockState),
    WorldlyContainer,
    GeoBlockEntity {

    val DEPLOY_ANIM: RawAnimation? = RawAnimation.begin()
        .thenLoop("animation.mechanical_drill.run")

    private val cache: AnimatableInstanceCache? = GeckoLibUtil.createInstanceCache(this)

    var drillAngle = 0.0

    val drillSpeed = 0.23 / 20.0

    val drillPositions: List<BlockPos> = (0..1).flatMap { i ->
        (0..1).map { j ->
            BlockPos(i, -1, j)
        }
    }.toList()

    override fun tick(
        level: Level,
        pos: BlockPos,
        state: BlockState
    ) {

    }

    override fun clientTick(
        level: Level,
        pos: BlockPos,
        state: BlockState
    ) {
        drillAngle += 1.0
    }

    override fun serverTick(
        level: Level,
        pos: BlockPos,
        state: BlockState
    ) {
        drill()
    }

    var drillProgress: Double = 0.0

    fun drill() {
        val copperOreCount = drillPositions.map { pos ->
            level?.getBlockState(blockPos.offset(pos))?.`is`(Tags.Blocks.ORES_COPPER)
        }.count { it == true }
        drillProgress += copperOreCount * drillSpeed
        if (drillProgress >= 1.0) {
            var count = drillProgress.toInt()
            drillProgress %= 1.0
            placeItem(
                ItemStack(
                    Items.RAW_COPPER,
                    count
                )
            )
        }
    }

    protected fun <E : TileEntityDrillBlock?> deployAnimController(state: AnimationState<E?>): PlayState {
        return state.setAndContinue(DEPLOY_ANIM)
    }

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar?) {
        controllers?.add(
            AnimationController<TileEntityDrillBlock>(
                this
            ) { state -> deployAnimController(state) }
        )
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache? {
        return this.cache
    }

    val items: MutableList<ItemStack> = mutableListOf()

    override fun getSlotsForFace(side: Direction): IntArray {
        return IntArray(containerSize) { it }
    }

    override fun canPlaceItemThroughFace(
        index: Int,
        itemStack: ItemStack,
        direction: Direction?
    ): Boolean {
        return false
    }

    override fun canTakeItemThroughFace(
        index: Int,
        stack: ItemStack,
        direction: Direction
    ): Boolean {
        if (direction == Direction.DOWN || direction == Direction.UP) {
            return false
        }
        return true
    }

    override fun getContainerSize(): Int {
        return items.size
    }

    override fun isEmpty(): Boolean {
        return containerSize == 0
    }

    override fun getItem(slot: Int): ItemStack {
        return items[slot]
    }

    override fun removeItem(slot: Int, amount: Int): ItemStack {
        val stack = ContainerHelper.removeItem(items, slot, amount)
        if (stack.isEmpty) {
            items.removeAt(slot)
        }
        setChanged()
        return stack
    }

    override fun removeItemNoUpdate(slot: Int): ItemStack {
        val stack = ContainerHelper.takeItem(items, slot)
        if (stack.isEmpty) {
            items.removeAt(slot)
        }
        setChanged()
        return stack
    }

    override fun setItem(slot: Int, stack: ItemStack) {
        stack.limitSize(this.getMaxStackSize(stack))
        items[slot] = stack
        setChanged()
    }

    override fun stillValid(player: Player): Boolean {
        return true
    }

    override fun clearContent() {
        items.clear()
        setChanged()
    }

    fun placeItem(stackToPlace: ItemStack) {
        val existItemStack = items.firstOrNull { it.`is`(stackToPlace.item) }
        if (existItemStack != null) {
            existItemStack.grow(stackToPlace.count)
        } else {
            items.add(stackToPlace)
        }
    }
}