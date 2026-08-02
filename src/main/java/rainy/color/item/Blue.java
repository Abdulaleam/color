package rainy.color.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import rainy.color.Color;

public class Blue extends Item {
    public Blue(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            Blue(world);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            Blue(world);
        }
        return ActionResult.SUCCESS;
    }
    private void Blue(World world) {
        Color.gameActive = true;
        Color.tickCounter = 0;
        Color.currentColor = DyeColor.BLUE;
        Color.colorLocked = true;

        world.getServer().getPlayerManager().broadcast(
                Text.literal("\u26A0 BLUE ONLY!! DON'T TOUCH BLUE!!! \u26A0")
                        .formatted(Formatting.BOLD, Formatting.BLUE), false);

    }
}
