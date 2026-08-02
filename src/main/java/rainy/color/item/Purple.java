package rainy.color.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import rainy.color.Color;

public class Purple extends Item {
    public Purple(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            Purple(world);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            Purple(world);
        }
        return ActionResult.SUCCESS;
    }
    private void Purple(World world){
        Color.gameActive = true;
        Color.tickCounter = 0;
        Color.colorLocked = true;

        world.getServer().getPlayerManager().broadcast(
                Text.literal("\26A0 PURPLE ONLY!! DON'T TOUCH PURPLE!! \26A0")
                        .formatted(Formatting.BOLD, Formatting.DARK_PURPLE), false);

    }
}
