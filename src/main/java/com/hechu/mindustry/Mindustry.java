package com.hechu.mindustry;

import com.hechu.mindustry.client.renderer.blockentity.MechanicalDrillBlockEntityRenderer;
import com.hechu.mindustry.client.renderer.blockentity.PneumaticDrillBlockEntityRenderer;
import com.hechu.mindustry.client.renderer.blockentity.PowerNodeRenderer;
import com.hechu.mindustry.client.renderer.blockentity.TurretRenderer;
import com.hechu.mindustry.config.CommonConfig;
import com.hechu.mindustry.config.ConfigHandler;
import com.hechu.mindustry.creative.CreativeModeTabRegister;
import com.hechu.mindustry.utils.Utils;
import com.hechu.mindustry.world.entity.EntityRegister;
import com.hechu.mindustry.world.entity.turrets.Duo;
import com.hechu.mindustry.world.entity.turrets.DuoRenderer;
import com.hechu.mindustry.world.item.ItemRegister;
import com.hechu.mindustry.world.level.block.BlockRegister;
import com.hechu.mindustry.world.level.block.entity.BlockEntityRegister;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import software.bernie.geckolib.GeckoLib;

@Mod(MindustryConstants.MOD_ID)
public class Mindustry {
    public Mindustry() {
        MindustryConstants.config_folder = FMLPaths.GAMEDIR.get().resolve("config/" + MindustryConstants.MOD_ID);
        Utils.checkFolder(MindustryConstants.config_folder);
        MindustryConstants.commonConfig = ConfigHandler.readConfig("common", CommonConfig.class);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

        modEventBus.addListener(this::registerTabs);

        BlockRegister.BLOCKS.register(modEventBus);

        ItemRegister.ITEMS.register(modEventBus);

        EntityRegister.ENTITIES.register(modEventBus);

        BlockEntityRegister.BLOCK_ENTITIES.register(modEventBus);

        CreativeModeTabRegister.CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerTabs(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ItemRegister.MECHANICAL_DRILL_ITEM.get());
            event.accept(ItemRegister.PNEUMATIC_DRILL_ITEM.get());
        }
    }

    @Mod.EventBusSubscriber(modid = MindustryConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {

            event.registerBlockEntityRenderer(BlockEntityRegister.MECHANICAL_DRILL_BLOCK_ENTITY.get(), context -> new MechanicalDrillBlockEntityRenderer());
            event.registerBlockEntityRenderer(BlockEntityRegister.PNEUMATIC_DRILL_BLOCK_ENTITY.get(), context -> new PneumaticDrillBlockEntityRenderer());
            event.registerBlockEntityRenderer(BlockEntityRegister.TURRET_BLOCK_ENTITY.get(), TurretRenderer::new);
            event.registerBlockEntityRenderer(BlockEntityRegister.POWER_NODE_BLOCK_ENTITY.get(), pContext -> new PowerNodeRenderer());

            event.registerEntityRenderer(EntityRegister.DUO.get(), DuoRenderer::new);
        }

        @SubscribeEvent
        public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
            event.put(EntityRegister.DUO.get(), Duo.createAttributes().build());
        }
    }
}
