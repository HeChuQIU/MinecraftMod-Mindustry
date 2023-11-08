package com.hechu.mindustry;

import com.hechu.mindustry.client.renderer.blockentity.MechanicalDrillBlockEntityRenderer;
import com.hechu.mindustry.client.renderer.blockentity.PneumaticDrillBlockEntityRenderer;
import com.hechu.mindustry.client.renderer.blockentity.PowerNodeRenderer;
import com.hechu.mindustry.client.renderer.blockentity.TurretRenderer;
import com.hechu.mindustry.config.CommonConfig;
import com.hechu.mindustry.config.ConfigHandler;
import com.hechu.mindustry.utils.Utils;
import com.hechu.mindustry.world.entity.EntityRegister;
import com.hechu.mindustry.world.entity.turrets.Duo;
import com.hechu.mindustry.world.entity.turrets.DuoRenderer;
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

        EntityRegister.ENTITIES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = MindustryConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {

            event.registerBlockEntityRenderer(MindustryModule.MECHANICAL_DRILL_BLOCK_ENTITY.get(), context -> new MechanicalDrillBlockEntityRenderer());
            event.registerBlockEntityRenderer(MindustryModule.PNEUMATIC_DRILL_BLOCK_ENTITY.get(), context -> new PneumaticDrillBlockEntityRenderer());
            event.registerBlockEntityRenderer(MindustryModule.TURRET_BLOCK_ENTITY.get(), TurretRenderer::new);
            event.registerBlockEntityRenderer(MindustryModule.POWER_NODE_BLOCK_ENTITY.get(), pContext -> new PowerNodeRenderer());

            event.registerEntityRenderer(EntityRegister.DUO.get(), DuoRenderer::new);
        }

        @SubscribeEvent
        public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
            event.put(EntityRegister.DUO.get(), Duo.createAttributes().build());
        }
    }
}
