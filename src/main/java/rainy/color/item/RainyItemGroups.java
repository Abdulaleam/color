package rainy.color.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rainy.color.Color;

public class RainyItemGroups {

    public static final ItemGroup TOUCH_COLOR = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Color.MOD_ID, "touch_color"),
    FabricItemGroup.builder().icon(() -> new ItemStack(RainyItems.START))
            .displayName(Text.translatable("itemgroup.color.touch_color"))
            .entries((displayContext, entries) -> {
                entries.add(RainyItems.START);
                entries.add(RainyItems.STOP);
            })


            .build());

    public static void registerRainyItemGroups() {}
}
