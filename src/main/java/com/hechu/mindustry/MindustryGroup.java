package com.hechu.mindustry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.hechu.mindustry.Mindustry.MECHANICAL_DRILL_ITEM;

public class MindustryGroup extends CreativeModeTab {

    public MindustryGroup() {
        super(Mindustry.MODID);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(MECHANICAL_DRILL_ITEM.get());
    }
}
