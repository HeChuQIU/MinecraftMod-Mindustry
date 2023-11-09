package com.hechu.mindustry.kiwi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.KiwiTabBuilder;

import static com.hechu.mindustry.MindustryConstants.MOD_ID;

@KiwiModule(value = "tab")
public class TabModule extends AbstractModule {
    public static final KiwiGO<CreativeModeTab> TAB_MAIN = go(() ->
            new KiwiTabBuilder(new ResourceLocation(MOD_ID, "tab_main"))
                    .title(Component.translatable("itemGroup." + MOD_ID + ".mindustry"))
                    .icon(() -> new ItemStack(MutilBlockModule.MECHANICAL_DRILL.get())).build());
    public static final KiwiGO<CreativeModeTab> TAB_MATERIALS = go(() ->
            new KiwiTabBuilder(new ResourceLocation(MOD_ID, "tab_materials"))
                    .title(Component.translatable("itemGroup." + MOD_ID + ".materials"))
                    .icon(() -> new ItemStack(BlockModule.COPPER_ORE_BLOCK.get())).build());
}
