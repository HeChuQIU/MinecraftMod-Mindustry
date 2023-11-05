package com.hechu.mindustry;

import com.hechu.mindustry.world.level.block.BlockRegister;
import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(), new EnglishLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ChineseLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new MindustrySimpleBlockModelProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustrySimpleBlockStateProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new MindustryItemModelProvider(packOutput, existingFileHelper));
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

            this.addBlock(BlockRegister.POWER_NODE, "Power Node");
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
            this.add("config.jade.plugin_mindustry.drill", "不知道干嘛的");
            this.add("mindustry.drill_progress", "%d%%已挖掘");
            this.add("mindustry.drill_speed", "挖掘速度 : %d/秒");
            this.add("config.jade.plugin_mindustry.health_block", "不知道干嘛的");
            this.add("mindustry.block_health", "生命值 : %d / %d");
            this.addBlock(BlockRegister.MECHANICAL_DRILL, "机械钻头");
            this.addBlock(BlockRegister.PNEUMATIC_DRILL, "气动钻头");
            this.addBlock(BlockRegister.HEALTH_TEST, "生命测试");
            this.addBlock(BlockRegister.TURRET, "炮台");

            this.addBlock(BlockRegister.POWER_NODE, "电力节点");
        }
    }

    public static class MindustryItemModelProvider extends ItemModelProvider {
        public MindustryItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
            super(packOutput, Mindustry.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            this.singleTexture("copper", new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Mindustry.MODID, "item/" + "copper"));
            this.withExistingParent(PowerNodeBlock.NAME, new ResourceLocation(Mindustry.MODID, "block/power_node"));
        }
    }

    public static class MindustrySimpleBlockModelProvider extends BlockModelProvider {
        public MindustrySimpleBlockModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
            super(packOutput, Mindustry.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            this.cubeAll(PowerNodeBlock.NAME, new ResourceLocation(Mindustry.MODID, "block/power_node"));
        }
    }

    public static class MindustrySimpleBlockStateProvider extends BlockStateProvider {
        public MindustrySimpleBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
            super(output, Mindustry.MODID, exFileHelper);
        }

        @Override
        protected void registerStatesAndModels() {
            this.simpleBlock(BlockRegister.POWER_NODE.get());
        }
    }
}
