package rainy.color.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rainy.color.Color;

public class RainyItems {

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Color.MOD_ID, name), item);
    }

    public static void registerRainyItems() {}
}
