package com.hechu.mindustry;

import com.hechu.mindustry.block.BlockEntityRegister;
import com.hechu.mindustry.block.BlockRegister;
import com.hechu.mindustry.block.MechanicalDrillBlockEntityRenderer;
import com.hechu.mindustry.block.PneumaticDrillBlockEntityRenderer;
import com.hechu.mindustry.entity.EntityRegister;
import com.hechu.mindustry.entity.turrets.Duo;
import com.hechu.mindustry.entity.turrets.DuoRenderer;
import com.hechu.mindustry.item.ItemRegister;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.example.registry.EntityRegistry;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Mindustry.MODID)
public class Mindustry {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "mindustry";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Mindustry() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerTabs);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BlockRegister.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ItemRegister.ITEMS.register(modEventBus);

        EntityRegister.ENTITIES.register(modEventBus);

        BlockEntityRegister.BLOCK_ENTITIES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    private void registerTabs(final CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(MODID, "mindustry"), builder -> builder
                .title(Component.translatable("itemGroup." + MODID + ".mindustry"))
                .icon(() -> new ItemStack(ItemRegister.MECHANICAL_DRILL_ITEM.get()))
                .displayItems((featureFlags, output) -> {
                    output.accept(ItemRegister.MECHANICAL_DRILL_ITEM.get());
                    output.accept(ItemRegister.PNEUMATIC_DRILL_ITEM.get());
                })
        );
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            LOGGER.info("HELLO from register renderers");
            //BLOCK ENTITIES
            event.registerBlockEntityRenderer(BlockEntityRegister.MECHANICAL_DRILL_BLOCK_ENTITY.get(), context -> new MechanicalDrillBlockEntityRenderer());
            event.registerBlockEntityRenderer(BlockEntityRegister.PNEUMATIC_DRILL_BLOCK_ENTITY.get(), context -> new PneumaticDrillBlockEntityRenderer());

            //ENTITIES
            event.registerEntityRenderer(EntityRegister.DUO.get(), DuoRenderer::new);
        }

        @SubscribeEvent
        public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
            event.put(EntityRegister.DUO.get(), Duo.createAttributes().build());
        }
    }
}
