package rainy.color.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import rainy.color.Color;

public class Green extends Item {

    public Green(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            Green(world);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            Green(world);
        }
        return ActionResult.SUCCESS;
    }
    private void Green(World world){
        Color.gameActive = true;
        Color.tickCounter = 0;
        Color.currentColor = DyeColor.GREEN;
        Color.colorLocked = true;

        world.getServer().getPlayerManager().broadcast(
                Text.literal("\u26A0 GREEN ONLY!! DON'T TOUCH GREEN!! \u26A0")
                        .formatted(Formatting.BOLD, Formatting.GREEN), false);

    }
}
