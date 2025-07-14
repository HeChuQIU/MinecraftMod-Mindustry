package net.hechuqiu.mindustry.common.tile

import net.hechuqiu.mindustry.common.registries.MindustryTileEntity.TEST_MULTIBLOCK0
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.AnimationController.AnimationStateHandler
import software.bernie.geckolib.animation.AnimationState
import software.bernie.geckolib.animation.PlayState
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.GeckoLibUtil


class TileEntityTestMultiblock0 :
    MindustryBlockEntity, GeoBlockEntity {
    constructor(pos: BlockPos, blockState: BlockState) : super(TEST_MULTIBLOCK0.get(), pos, blockState)

    val DEPLOY_ANIM: RawAnimation? = RawAnimation.begin()
        .thenPlay("animation.mechanical-drill.start")
        .thenLoop("animation.mechanical-drill.run")

    private val cache: AnimatableInstanceCache? = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: ControllerRegistrar) {
        controllers.add(
            AnimationController<TileEntityTestMultiblock0>(
                this
            ) { state -> deployAnimController(state) }
        )
    }

    protected fun <E : TileEntityTestMultiblock0?> deployAnimController(state: AnimationState<E?>): PlayState {
        return state.setAndContinue(DEPLOY_ANIM)
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache? {
        return this.cache
    }
}