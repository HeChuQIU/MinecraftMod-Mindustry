package com.hechu.mindustry.item;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.block.BlockRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {
    // Create a Deferred Register to hold Items which will all be registered under the "mindustry" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Mindustry.MODID);
    public static final RegistryObject<Item> MECHANICAL_DRILL_ITEM = ITEMS.register(com.hechu.mindustry.item.MechanicalDrill.NAME,
            () -> new MechanicalDrill(BlockRegister.MECHANICAL_DRILL.get(), new Item.Properties()));
    public static final RegistryObject<Item> PNEUMATIC_DRILL_ITEM = ITEMS.register(com.hechu.mindustry.item.PneumaticDrill.NAME,
            () -> new PneumaticDrill(BlockRegister.PNEUMATIC_DRILL.get(), new Item.Properties()));
}
