package rainy.color.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rainy.color.Color;

public class RainyItems {
    public static final Item START = registerItem("start", new GoItem(new Item.Settings().maxCount(1)));
    public static final Item STOP = registerItem("stop", new PauseItem(new Item.Settings().maxCount(1)));
    public static final Item RED = registerItem("red", new Red(new Item.Settings().maxCount(1)));
    public static final Item GREEN = registerItem("green", new Green(new Item.Settings().maxCount(1)));
    public static final Item GRAY = registerItem("gray", new Gray(new Item.Settings().maxCount(1)));
    public static final Item BLUE = registerItem("blue", new Blue(new Item.Settings().maxCount(1)));
    public static final Item YELLOW = registerItem("yellow", new Yellow(new Item.Settings().maxCount(1)));
    public static final Item PURPLE = registerItem("purple", new Purple(new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Color.MOD_ID, name), item);
    }

    public static void registerRainyItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(START);
            entries.add(STOP);
        });
    }

}
