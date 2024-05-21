package com.hechu.mindustry.distribution;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.world.level.block.entity.distribution.ConveyorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = MindustryConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Conveyor {
    private static final Set<ConveyorBlockEntity> tailConveyors = new HashSet<>();
    public static Set<ConveyorBlockEntity> getTailConveyors() {
        return tailConveyors;
    }

    public static final Set<ConveyorBlockEntity> computingConveyors = new HashSet<>();
    public static Set<ConveyorBlockEntity> getComputingConveyors() {
        return computingConveyors;
    }

    @SubscribeEvent
    public static void onConveyorEvent(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.getServer().getTickCount() % 60 == 0) {
            tailConveyors.stream().filter(ConveyorBlockEntity::isTail).forEach(ConveyorBlockEntity::moveItems);
        }
    }
}
