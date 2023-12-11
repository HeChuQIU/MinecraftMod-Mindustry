package com.hechu.mindustry.datagen.provider;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.kiwi.MutilBlockModule;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.stream.Collectors;

public class MindustryMutilBlockLootSubProvider extends BlockLootSubProvider {

    private static final Set<Block> knownBlock = MutilBlockModule.getBlocks().stream().map(multiblockCoreBlock -> (Block) multiblockCoreBlock).collect(Collectors.toSet());
    protected MindustryMutilBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // TODO
        //  现在只有三种方块
        //  TEST_MULTIBLOCK_CORE
        //  TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK
        //  KILN_BLOCK
        //  在挖掘后会掉落,即这三种方块拥有战利品表
        //  扳手和炮台计划用扳手拆
        //  (其实是non-blocks需要开一个单独的战利品表提供器,狗命要紧,明天起来再写)
        MutilBlockModule.getBlocks().forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlock;
    }
}
