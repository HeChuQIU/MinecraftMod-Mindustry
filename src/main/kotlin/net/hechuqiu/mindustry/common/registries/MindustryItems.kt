package net.hechuqiu.mindustry.common.registries

import net.hechuqiu.mindustry.Mindustry
import net.hechuqiu.mindustry.common.item.BoundingBlockItem
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

    val THREE_BLOCK_HIGH_BLOCK = REGISTRY.register("three_block_high") { registryName ->
        BoundingBlockItem(MindustryBlocks.THREE_BLOCK_HIGH_BLOCK, Item.Properties())
    }

    val TEST_BOUNDING_BLOCK0 = REGISTRY.register("test_bounding_block0") { registryName ->
        TestBoundingBlock0Item(MindustryBlocks.TEST_BOUNDING_BLOCK0, Item.Properties())
    }
}