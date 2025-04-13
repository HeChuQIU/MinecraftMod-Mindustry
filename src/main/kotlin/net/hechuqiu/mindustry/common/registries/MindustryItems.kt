package net.hechuqiu.mindustry.common.registries

import net.hechuqiu.mindustry.Mindustry
import net.hechuqiu.mindustry.common.item.BoundingBlockItem
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

    val THREE_BLOCK_HIGH_BLOCK = REGISTRY.register("three_block_high") { registryName ->
        BoundingBlockItem(MindustryBlocks.THREE_BLOCK_HIGH_BLOCK, Item.Properties())
    }
}