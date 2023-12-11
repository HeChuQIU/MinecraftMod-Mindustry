package com.hechu.mindustry.datagen.provider;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.kiwi.BlockModule;
import com.hechu.mindustry.kiwi.MutilBlockModule;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MindustryLootTableProvider extends LootTableProvider {
    public MindustryLootTableProvider(PackOutput pOutput) {
        super(pOutput,
                Stream.concat(BlockModule.getRegisterName()
                                        .stream()
                                        .map(name -> new ResourceLocation(MindustryConstants.MOD_ID, name)),
                                MutilBlockModule.getRegisterName()
                                        .stream()
                                        .map(name -> new ResourceLocation(MindustryConstants.MOD_ID, name)))
                        .collect(Collectors.toSet()),
                List.of(new SubProviderEntry(MindustryBlockLootSubProvider::new, LootContextParamSets.BLOCK),
                        new SubProviderEntry(MindustryMutilBlockLootSubProvider::new, LootContextParamSets.BLOCK)));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {
        map.forEach((key, value) -> value.validate(validationcontext));
    }
}
