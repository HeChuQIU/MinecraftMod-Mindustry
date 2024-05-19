package com.hechu.mindustry.distribution;

import com.hechu.mindustry.MindustryConstants;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MindustryConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Conveyor {
    @SubscribeEvent
    public static void onConveyorEvent(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.getServer().getTickCount() % 20 == 0) {
//            event.getServer().getPlayerList().getPlayers().forEach(p -> p.displayClientMessage(Component.literal("Conveyor event fired!"), true));
//            event.getServer().sendSystemMessage(Component.literal("Conveyor event fired!"));
        }
    }

    @SubscribeEvent
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        event.getPlayer().displayClientMessage(Component.literal("Block broken!"), true);
    }

    @SubscribeEvent
    public static void onPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof net.minecraft.world.entity.player.Player player)
            player.displayClientMessage(Component.literal("Block placed!"), true);
    }
}
