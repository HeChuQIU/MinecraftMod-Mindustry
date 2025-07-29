package net.hechuqiu.mindustry.client.render.tileentity

import net.hechuqiu.mindustry.Mindustry
import net.hechuqiu.mindustry.common.tile.TileEntityDrillBlock
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer


class MechanicalBlockRenderer :
    GeoBlockRenderer<TileEntityDrillBlock> {
    constructor() : super(
        DefaultedBlockGeoModel(
            ResourceLocation.fromNamespaceAndPath(
                Mindustry.ID,
                "mechanical_drill"
            )
        )
    )
}