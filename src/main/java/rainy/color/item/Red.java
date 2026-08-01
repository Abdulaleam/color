package rainy.color.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import rainy.color.Color;

public class Red extends Item {
    public Red(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            Red(world);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            Red(world);
        }
        return ActionResult.SUCCESS;
    }
    private void Red(World world) {
        Color.gameActive = true;
        Color.tickCounter = 0;
        Color.currentColor = DyeColor.RED;
        Color.colorLocked = true;

        world.getServer().getPlayerManager().broadcast(
                Text.literal("\u26A0 RED ONLY!! DON'T TOUCH RED!! \u26A0")
                        .formatted(Formatting.RED, Formatting.BOLD), false);
    }
}