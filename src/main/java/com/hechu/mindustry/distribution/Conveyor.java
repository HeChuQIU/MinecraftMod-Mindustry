package com.hechu.mindustry.distribution;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.world.level.block.entity.distribution.ConveyorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = MindustryConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Conveyor {
    public static final int CONVEYOR_EVENT_TICK = 40;

    private static final Set<ConveyorBlockEntity> tailConveyors = new HashSet<>();

    public static Set<ConveyorBlockEntity> getTailConveyors() {
        return tailConveyors;
    }

    public static final Set<ConveyorBlockEntity> computingConveyors = new HashSet<>();

    public static Set<ConveyorBlockEntity> getComputingConveyors() {
        return computingConveyors;
    }

    @SubscribeEvent
    public static void onConveyorBreak(BlockEvent.BreakEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();
        if (level.getBlockEntity(pos) instanceof ConveyorBlockEntity blockEntity) {
            tailConveyors.remove(blockEntity);
            computingConveyors.remove(blockEntity);
        }
    }

    @SubscribeEvent
    public static void onConveyorEvent(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.getServer().getTickCount() % CONVEYOR_EVENT_TICK == 0) {
            computingConveyors.clear();
            tailConveyors.stream().filter(conveyor -> !conveyor.isTail()).toList().forEach(tailConveyors::remove);
                tailConveyors.stream().filter(ConveyorBlockEntity::isTail).forEach(ConveyorBlockEntity::moveItems);
            computingConveyors.clear();
        }
    }
}
