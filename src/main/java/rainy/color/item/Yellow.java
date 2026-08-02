package rainy.color.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import rainy.color.Color;

public class Yellow extends Item {
    public Yellow(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            Yellow(world);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            Yellow(world);
        }
        return ActionResult.SUCCESS;
    }
    private void Yellow(World world) {
        Color.gameActive = true;
        Color.tickCounter = 0;
        Color.currentColor = DyeColor.GRAY;
        Color.colorLocked = true;

        world.getServer().getPlayerManager().broadcast(
                Text.literal("\u26A0 YELLOW ONLY!! DON'T TOUCH YELLOW!! \u26A0")
                        .formatted(Formatting.BOLD,Formatting.YELLOW), false);

    }
}
