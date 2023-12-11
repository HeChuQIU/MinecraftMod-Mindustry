package com.hechu.mindustry.datagen.provider;

import com.hechu.mindustry.kiwi.BlockModule;
import com.hechu.mindustry.kiwi.MutilBlockModule;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.stream.Collectors;

public class MindustryBlockLootSubProvider extends BlockLootSubProvider {
    private static final Set<Block> knownBlock = BlockModule.getBlocks();
    protected MindustryBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        BlockModule.getBlocks().forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlock;
    }
}
