package com.hechu.mindustry.world.item;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.world.item.drill.MechanicalDrill;
import com.hechu.mindustry.world.item.drill.PneumaticDrill;
import com.hechu.mindustry.world.item.materials.*;
import com.hechu.mindustry.world.item.multiblock.Kiln;
import com.hechu.mindustry.world.item.multiblock.TestTurretMultiblock;
import com.hechu.mindustry.world.item.ore.*;
import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlock;
import com.hechu.mindustry.world.level.block.multiblock.KilnBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hechu.mindustry.world.level.block.BlockRegister.*;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MindustryConstants.MOD_ID);
    public static final RegistryObject<Item> MECHANICAL_DRILL_ITEM = ITEMS.register(MechanicalDrill.NAME,
            () -> new MechanicalDrill(MECHANICAL_DRILL.get(), new Item.Properties()));
    public static final RegistryObject<Item> PNEUMATIC_DRILL_ITEM = ITEMS.register(PneumaticDrill.NAME,
            () -> new PneumaticDrill(PNEUMATIC_DRILL.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEALTH_TEST_ITEM = ITEMS.register(HealthTest.NAME,
            () -> new HealthTest(HEALTH_TEST.get(), new Item.Properties()));
    public static final RegistryObject<Item> TURRET_ITEM = ITEMS.register(Turret.NAME,
            () -> new Turret(TURRET.get(), new Item.Properties()));
    public static final RegistryObject<Item> TEST_TURRET_MULTIBLOCK_ITEM = ITEMS.register(TestTurretMultiblock.NAME,
            () -> new TestTurretMultiblock(TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> KILN_ITEM = ITEMS.register(KilnBlock.NAME,
            () -> new Kiln(KILN_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> POWER_NODE_BLOCK_ITEM = ITEMS.register(PowerNodeBlock.NAME,
            () -> new BlockItem(POWER_NODE.get(), new Item.Properties()));
    public static final RegistryObject<Item> COPPER_ORE = ITEMS.register(CopperOre.NAME,
            () -> new BlockItem(COPPER_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> COAL_ORE = ITEMS.register(CoalOre.NAME,
            () -> new BlockItem(COAL_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> LEAD_ORE = ITEMS.register(LeadOre.NAME,
            () -> new BlockItem(LEAD_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SCRAP_ORE = ITEMS.register(ScrapOre.NAME,
            () -> new BlockItem(SCRAP_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> THORIUM_ORE = ITEMS.register(ThoriumOre.NAME,
            () -> new BlockItem(THORIUM_ORE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_ORE = ITEMS.register(TitaniumOre.NAME,
            () -> new BlockItem(TITANIUM_ORE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> COPPER = ITEMS.register(Copper.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COAL = ITEMS.register(Coal.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD = ITEMS.register(Lead.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SCRAP = ITEMS.register(Scrap.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THORIUM = ITEMS.register(Thorium.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM = ITEMS.register(Titanium.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GRAPHITE = ITEMS.register(Graphite.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> META_GLASS = ITEMS.register(MetaGlass.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PHASE_FABRIC = ITEMS.register(PhaseFabric.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLASTANIUM = ITEMS.register(Plastanium.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PYRATITE = ITEMS.register(Pyratite.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILICON = ITEMS.register(Silicon.NAME,
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SURGE_ALLOY = ITEMS.register(SurgeAlloy.NAME,
            () -> new Item(new Item.Properties()));
}
