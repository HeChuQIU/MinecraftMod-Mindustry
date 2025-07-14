package net.hechuqiu.mindustry.client.render.tileentity

import net.hechuqiu.mindustry.Mindustry
import net.hechuqiu.mindustry.common.tile.TileEntityTestMultiblock0
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.GeckoLib
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.model.DefaultedEntityGeoModel
import software.bernie.geckolib.model.GeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer


class TileEntityTestMultiblock0Renderer :
    GeoBlockRenderer<TileEntityTestMultiblock0> {
    constructor() : super(
        DefaultedBlockGeoModel(
            ResourceLocation.fromNamespaceAndPath(
                Mindustry.ID,
                "mechanical-drill"
            )
        )
    )
}