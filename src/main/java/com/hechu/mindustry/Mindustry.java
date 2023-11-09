package com.hechu.mindustry;

import com.hechu.mindustry.client.renderer.blockentity.MechanicalDrillBlockEntityRenderer;
import com.hechu.mindustry.client.renderer.blockentity.PneumaticDrillBlockEntityRenderer;
import com.hechu.mindustry.client.renderer.blockentity.PowerNodeRenderer;
import com.hechu.mindustry.client.renderer.blockentity.TurretRenderer;
import com.hechu.mindustry.config.CommonConfig;
import com.hechu.mindustry.config.ConfigHandler;
import com.hechu.mindustry.kiwi.BlockEntityModule;
import com.hechu.mindustry.kiwi.EntityModule;
import com.hechu.mindustry.utils.Utils;
import com.hechu.mindustry.world.entity.turrets.Duo;
import com.hechu.mindustry.world.entity.turrets.DuoRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import software.bernie.geckolib.GeckoLib;

@Mod(MindustryConstants.MOD_ID)
public class Mindustry {
    public Mindustry() {
        MindustryConstants.config_folder = FMLPaths.GAMEDIR.get().resolve("config/" + MindustryConstants.MOD_ID);
        Utils.checkFolder(MindustryConstants.config_folder);
        MindustryConstants.commonConfig = ConfigHandler.readConfig("common", CommonConfig.class);
//        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

//        modEventBus.addListener(this::registerTabs);
//
//        BlockRegister.BLOCKS.register(modEventBus);
//
//        ItemRegister.ITEMS.register(modEventBus);

//        EntityRegister.ENTITIES.register(modEventBus);

//        BlockEntityRegister.BLOCK_ENTITIES.register(modEventBus);

//        CreativeModeTabRegister.CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = MindustryConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {

            event.registerBlockEntityRenderer(BlockEntityModule.MECHANICAL_DRILL_BLOCK_ENTITY.get(), context -> new MechanicalDrillBlockEntityRenderer());
            event.registerBlockEntityRenderer(BlockEntityModule.PNEUMATIC_DRILL_BLOCK_ENTITY.get(), context -> new PneumaticDrillBlockEntityRenderer());
            event.registerBlockEntityRenderer(BlockEntityModule.TURRET_BLOCK_ENTITY.get(), TurretRenderer::new);
            event.registerBlockEntityRenderer(BlockEntityModule.POWER_NODE_BLOCK_ENTITY.get(), pContext -> new PowerNodeRenderer());

            event.registerEntityRenderer(EntityModule.DUO.get(), DuoRenderer::new);
        }

        @SubscribeEvent
        public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
            event.put(EntityModule.DUO.get(), Duo.createAttributes().build());
        }
    }
}
