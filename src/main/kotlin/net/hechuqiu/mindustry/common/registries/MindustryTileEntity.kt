package net.hechuqiu.mindustry.common.registries

import com.mojang.datafixers.DSL
import net.hechuqiu.mindustry.Mindustry
import net.hechuqiu.mindustry.common.tile.TileEntityBoundingBlock
import net.hechuqiu.mindustry.common.tile.TileEntityTestMultiblock0
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

    val TEST_MULTIBLOCK0 = REGISTRY.register(
        "test_multiblock0",
        Supplier {
            BlockEntityType.Builder.of(
                ::TileEntityTestMultiblock0,
                MindustryBlocks.TEST_MULTIBLOCK0
            ).build(DSL.remainderType())
        }
    )
}