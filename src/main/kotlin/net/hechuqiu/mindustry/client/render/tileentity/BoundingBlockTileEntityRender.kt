package net.hechuqiu.mindustry.client.render.tileentity

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.hechuqiu.mindustry.common.registries.MindustryItems
import net.hechuqiu.mindustry.common.tile.TileEntityBoundingBlock
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class BoundingBlockTileEntityRender : BlockEntityRenderer<TileEntityBoundingBlock> {
    companion object {
        private val WIREFRAME_COLOR = floatArrayOf(1.0f, 0.5f, 0.0f, 1.0f) // RGBA: Orange
    }

    // https://forums.minecraftforge.net/topic/112121-1182-how-to-use-rendertypes-other-than-lines/
    override fun render(
        tile: TileEntityBoundingBlock,
        partialTick: Float,
        matrix: PoseStack,
        renderer: MultiBufferSource,
        light: Int,
        overlayLight: Int
    ) {
        RenderSystem.depthMask(true)

        matrix.pushPose()
        if (shouldRenderWireframe()) {
            renderWireframe(matrix, renderer, BlockPos.ZERO)
        }
        matrix.popPose()
    }

    private fun shouldRenderWireframe(): Boolean {
        return Minecraft.getInstance().player?.let { player ->
            player.handSlots.any { itemStack ->
                itemStack?.`is`(MindustryItems.BOUNDING_BLOCK) == true
            }
        } ?: false
    }

    private fun renderWireframe(
        matrix: PoseStack,
        bufferSource: MultiBufferSource,
        blockPos: BlockPos
    ) {
        val buffer = bufferSource.getBuffer(RenderType.lines())
        val box = AABB(blockPos).inflate(-0.25)

        renderFrame(
            buffer = buffer,
            matrix = matrix,
            box = box,
            r = WIREFRAME_COLOR[0],
            g = WIREFRAME_COLOR[1],
            b = WIREFRAME_COLOR[2],
            a = WIREFRAME_COLOR[3]
        )
    }

    private fun renderFrame(
        buffer: VertexConsumer,
        matrix: PoseStack,
        box: AABB,
        r: Float, g: Float, b: Float, a: Float
    ) {
        LevelRenderer.renderLineBox(matrix, buffer, box, r, g, b, a)
    }
}
