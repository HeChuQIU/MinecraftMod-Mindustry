package net.hechuqiu.mindustry.common.registries

import net.hechuqiu.mindustry.Mindustry
import net.hechuqiu.mindustry.common.item.MultiblockItem
import net.hechuqiu.mindustry.common.item.TestBoundingBlock0Item
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister

object MindustryItems {
    val REGISTRY = DeferredRegister.createItems(Mindustry.ID)

    val EXAMPLE_BLOCK = REGISTRY.register("example_block") { registryName ->
        BlockItem(MindustryBlocks.EXAMPLE_BLOCK, Item.Properties())
    }

    val BOUNDING_BLOCK = REGISTRY.register("bounding_block") { registryName ->
        BlockItem(MindustryBlocks.BOUNDING_BLOCK, Item.Properties())
    }

    val MECHANICAL_DRILL = REGISTRY.register("mechanical_drill") { registryName ->
        BlockItem(MindustryBlocks.MECHANICAL_BLOCK, Item.Properties())
    }
}