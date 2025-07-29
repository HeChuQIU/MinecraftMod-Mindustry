package net.hechuqiu.mindustry.common.registries

import com.mojang.datafixers.DSL
import net.hechuqiu.mindustry.Mindustry
import net.hechuqiu.mindustry.common.tile.TileEntityBoundingBlock
import net.hechuqiu.mindustry.common.tile.TileEntityDrillBlock
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object MindustryTileEntity {
    val REGISTRY = DeferredRegister.create(
        Registries.BLOCK_ENTITY_TYPE, Mindustry.ID
    )

    val BOUNDING_BLOCK = REGISTRY.register(
        "bounding_block_entity",
        Supplier {
            BlockEntityType.Builder.of(
                ::TileEntityBoundingBlock,
                MindustryBlocks.BOUNDING_BLOCK
            ).build(DSL.remainderType())
        }
    )

    val DRILL_BLOCK = REGISTRY.register(
        "drill_block",
        Supplier {
            BlockEntityType.Builder.of(
                ::TileEntityDrillBlock,
                MindustryBlocks.MECHANICAL_BLOCK
            ).build(DSL.remainderType())
        }
    )
}