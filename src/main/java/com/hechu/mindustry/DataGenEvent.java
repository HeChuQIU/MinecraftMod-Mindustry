package com.hechu.mindustry;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvent {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        generator.addProvider(true, new ItemModelProvider(generator.getPackOutput(), Mindustry.MODID, event.getExistingFileHelper()) {
            @Override
            protected void registerModels() {
                this.singleTexture("copper", new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Mindustry.MODID, "item/" + "copper"));
            }
        });
    }
}
