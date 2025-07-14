package net.hechuqiu.mindustry

import net.hechuqiu.mindustry.client.render.tileentity.BoundingBlockTileEntityRender
import net.hechuqiu.mindustry.client.render.tileentity.TileEntityTestMultiblock0Renderer
import net.hechuqiu.mindustry.common.registries.MindustryBlocks
import net.hechuqiu.mindustry.common.registries.MindustryItems
import net.hechuqiu.mindustry.common.registries.MindustryTileEntity
import net.hechuqiu.mindustry.common.tile.TileEntityBoundingBlock
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.runForDist


/**
 * Main mod class.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(Mindustry.ID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object Mindustry {
    const val ID = "mindustry"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        // Register the KDeferredRegister to the mod-specific event bus
        MindustryBlocks.REGISTRY.register(MOD_BUS)
        MindustryItems.REGISTRY.register(MOD_BUS)
        MindustryTileEntity.REGISTRY.register(MOD_BUS)

        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onClientSetup)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerSetup)
                "test"
            })

        println(obj)
    }

    @SubscribeEvent
    fun registerEntityRenderers(event: RegisterRenderers) {
        event.registerBlockEntityRenderer(
            MindustryTileEntity.BOUNDING_BLOCK.get()
        )
        { BoundingBlockTileEntityRender() }

        event.registerBlockEntityRenderer(
            MindustryTileEntity.TEST_MULTIBLOCK0.get()
        )
        { TileEntityTestMultiblock0Renderer() }

    }

    /**
     * This is used for initializing client specific
     * things such as renderers and keymaps
     * Fired on the mod specific event bus.
     */
    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")
    }

    /**
     * Fired on the global Forge bus.
     */
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent) {
        LOGGER.log(Level.INFO, "Hello! This is working!")
    }
}
