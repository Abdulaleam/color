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
import rainy.color.RainyCommands;

public class GoItem extends Item {
    public GoItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){

        if (!world.isClient){
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context){
        World world = context.getWorld();
        if (!world.isClient){
            triggerStart(world, context.getPlayer());
        }
        return ActionResult.SUCCESS;
    }

    private void triggerStart(World world, PlayerEntity user){
        if (Color.gameActive){
            if (user != null){
                user.sendMessage(Text.literal("The Game is already running gang, wake up lmao").formatted(Formatting.BOLD,Formatting.YELLOW), true);
            }
            return;
        }
        Color.gameActive = true;
        Color.tickCounter = 0;
        RainyCommands.pickNewColor();
        world.getServer().getPlayerManager().broadcast(
                Text.literal("=== Don't Touch The Color Has Started , Stay Alive gang")
                        .formatted(Formatting.BOLD, Formatting.GOLD), false);
        RainyCommands.announceColor(world.getServer());

    }
}