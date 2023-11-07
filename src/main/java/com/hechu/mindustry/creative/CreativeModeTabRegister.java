package com.hechu.mindustry.creative;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.world.item.ItemRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hechu.mindustry.MindustryConstants.MOD_ID;

public class CreativeModeTabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MindustryConstants.MOD_ID);
    public static final RegistryObject<CreativeModeTab> MINDUSTRY_CREATIVE_TAB = CREATIVE_MODE_TABS.register("mindustry_main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MOD_ID + ".mindustry"))
            .icon(() -> new ItemStack(ItemRegister.MECHANICAL_DRILL_ITEM.get()))
            .displayItems((featureFlags, output) -> {
                output.accept(ItemRegister.MECHANICAL_DRILL_ITEM.get());
                output.accept(ItemRegister.PNEUMATIC_DRILL_ITEM.get());
                output.accept(ItemRegister.HEALTH_TEST_ITEM.get());
                output.accept(ItemRegister.POWER_NODE_BLOCK_ITEM.get());
            }).build()
    );
    public static final RegistryObject<CreativeModeTab> MINDUSTRY_CREATIVE_TAB_MATERIALS = CREATIVE_MODE_TABS.register("mindustry_materials", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MOD_ID + ".materials"))
            .icon(() -> new ItemStack(ItemRegister.COPPER_ORE.get()))
            .displayItems((featureFlags, output) -> {
                output.accept(ItemRegister.COPPER_ORE.get());
                output.accept(ItemRegister.COAL_ORE.get());
                output.accept(ItemRegister.SCRAP_ORE.get());
                output.accept(ItemRegister.LEAD_ORE.get());
                output.accept(ItemRegister.THORIUM_ORE.get());
                output.accept(ItemRegister.TITANIUM_ORE.get());

                output.accept(ItemRegister.COPPER.get());
                output.accept(ItemRegister.COAL.get());
                output.accept(ItemRegister.SCRAP.get());
                output.accept(ItemRegister.LEAD.get());
                output.accept(ItemRegister.THORIUM.get());
                output.accept(ItemRegister.TITANIUM.get());

                output.accept(ItemRegister.GRAPHITE.get());
                output.accept(ItemRegister.META_GLASS.get());
                output.accept(ItemRegister.PHASE_FABRIC.get());
                output.accept(ItemRegister.PLASTANIUM.get());
                output.accept(ItemRegister.PYRATITE.get());
                output.accept(ItemRegister.SILICON.get());
                output.accept(ItemRegister.SURGE_ALLOY.get());
            }).build()
    );
}
