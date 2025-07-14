package net.hechuqiu.mindustry.common.registries

import net.hechuqiu.mindustry.Mindustry
import net.hechuqiu.mindustry.common.block.content.BoundingBlock
import net.hechuqiu.mindustry.common.block.content.MindustryBlock
import net.hechuqiu.mindustry.common.block.content.TestMultiblock0
import net.hechuqiu.mindustry.common.block.content.ThreeBlockHighBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredRegister

// THIS LINE IS REQUIRED FOR USING PROPERTY DELEGATES
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object MindustryBlocks {
    val REGISTRY = DeferredRegister.createBlocks(Mindustry.ID)

    // If you get an "overload resolution ambiguity" error, include the arrow at the start of the closure.
    val EXAMPLE_BLOCK by REGISTRY.register("example_block") { registryName ->
        MindustryBlock(
            BlockBehaviour.Properties.of()
            .lightLevel { 15 }
            .strength(3.0f))
    }

    val BOUNDING_BLOCK by REGISTRY.register("bounding_block") { registryName ->
        BoundingBlock()
    }

    val THREE_BLOCK_HIGH_BLOCK by REGISTRY.register("three_block_high") { registryName->
        ThreeBlockHighBlock(
            BlockBehaviour.Properties.of()
                .lightLevel { 15 }
                .strength(3.0f)
        )
    }

    val TEST_MULTIBLOCK0 by REGISTRY.register("test_multiblock0") { registryName ->
        TestMultiblock0(
            BlockBehaviour.Properties.of()
//                .lightLevel { 15 }
                .strength(3.0f)
        )
    }
}
