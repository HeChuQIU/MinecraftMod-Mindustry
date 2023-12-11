package com.hechu.mindustry.datagen;

import com.hechu.mindustry.datagen.provider.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvent {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        gen.addProvider(event.includeClient(), new MindustryBlockModelProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustryBlockStateProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustryBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustryItemModelProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeServer(), new MindustryRecipeProvider(packOutput));
        gen.addProvider(event.includeServer(), new MindustryLootTableProvider(packOutput));
    }
}
