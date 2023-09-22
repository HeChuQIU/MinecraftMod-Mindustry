package com.hechu.mindustry;

import com.hechu.mindustry.world.item.ItemRegister;
import com.hechu.mindustry.world.level.block.BlockRegister;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvent {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        gen.addProvider(event.includeClient(), new EnglishLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ChineseLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new MindustryItemModelProvider(event));
    }

    // 英文语言文件
    public static class EnglishLanguageProvider extends LanguageProvider {
        public EnglishLanguageProvider(PackOutput packOutput) {
            // 前三个参数分别是 Data Generator 本身，模组 ID，以及语言代码
            // 语言代码对应语言文件的资源路径
            super(packOutput, Mindustry.MODID, "en_us");
        }

        @Override
        protected void addTranslations() {
            this.add("itemGroup.mindustry.mindustry", "Mindustry");
            this.addBlock(BlockRegister.MECHANICAL_DRILL, "mechanical drill");
            this.addBlock(BlockRegister.PNEUMATIC_DRILL, "pneumatic drill");
            this.addBlock(BlockRegister.HEALTH_TEST, "health test");
            this.addBlock(BlockRegister.TURRET, "turret");
        }
    }

    // 中文语言文件
    public static class ChineseLanguageProvider extends LanguageProvider {
        public ChineseLanguageProvider(PackOutput packOutput) {
            // 前三个参数分别是 Data Generator 本身，模组 ID，以及语言代码
            // 语言代码对应语言文件的资源路径
            super(packOutput, Mindustry.MODID, "zh_cn");
        }

        @Override
        protected void addTranslations() {
            this.add("itemGroup.mindustry.mindustry", "Mindustry");
            this.addBlock(BlockRegister.MECHANICAL_DRILL, "机械钻头");
            this.addBlock(BlockRegister.PNEUMATIC_DRILL, "气动钻头");
            this.addBlock(BlockRegister.HEALTH_TEST, "生命测试");
            this.addBlock(BlockRegister.TURRET, "炮台");
        }
    }

    public static class MindustryItemModelProvider extends ItemModelProvider {
        public MindustryItemModelProvider(GatherDataEvent event) {
            super(event.getGenerator().getPackOutput(), Mindustry.MODID, event.getExistingFileHelper());
        }

        @Override
        protected void registerModels() {
            this.singleTexture("copper", new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Mindustry.MODID, "item/" + "copper"));
        }
    }
}
