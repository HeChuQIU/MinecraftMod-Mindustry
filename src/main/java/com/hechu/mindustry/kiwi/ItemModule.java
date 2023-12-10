package com.hechu.mindustry.kiwi;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.world.item.tools.Wrench;
import net.minecraft.world.item.Item;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hechu.mindustry.MindustryConstants.logger;

@KiwiModule(value = "item")
@KiwiModule.Category(value = MindustryConstants.MOD_ID + ":tab_materials")
public class ItemModule extends AbstractModule {
    @KiwiModule.Category(value = MindustryConstants.MOD_ID + ":tab_main")
    public static final KiwiGO<Item> WRENCH = go(() -> new Wrench(new Item.Properties()));

    public static final KiwiGO<Item> COPPER = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> COAL = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> LEAD = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> SCRAP = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> THORIUM = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> TITANIUM = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> GRAPHITE = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> META_GLASS = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> PHASE_FABRIC = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> PLASTANIUM = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> PYRATITE = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> SILICON = go(() -> new Item(new Item.Properties()));
    public static final KiwiGO<Item> SURGE_ALLOY = go(() -> new Item(new Item.Properties()));

    private static Set<Item> items = null;

    private static Set<String> registerName = null;

    public static Set<Item> getBlocks() {
        if (items == null) {
            items = Arrays.stream(ItemModule.class.getFields())
                    .filter(field -> {
                        int modifier = field.getModifiers();
                        return Modifier.isPublic(modifier) && Modifier.isFinal(modifier) && Modifier.isStatic(modifier);
                    })
                    .map(field -> {
                        try {
                            return field.get(null);
                        } catch (IllegalAccessException e) {
                            logger.error("Error to get field", e);
                            return null;
                        }
                    })
                    .filter(field -> field instanceof KiwiGO<?>)
                    .map(field -> ((KiwiGO<?>) field).get())
                    .filter(field -> field instanceof Item)
                    .map(field -> (Item) field)
                    .collect(Collectors.toSet());
        }
        return items;
    }

    public static Set<String> getRegisterName() {
        if (registerName == null) {
            registerName = Arrays.stream(ItemModule.class.getFields())
                    .filter(field -> {
                        int modifier = field.getModifiers();
                        return Modifier.isPublic(modifier) && Modifier.isFinal(modifier) && Modifier.isStatic(modifier);
                    })
                    .map(field -> {
                        KiwiModule.Name annotated = field.getAnnotation(KiwiModule.Name.class);
                        if (annotated == null){
                            return field.getName();
                        }
                        return annotated.value();
                    })
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
        }
        return registerName;
    }
}
