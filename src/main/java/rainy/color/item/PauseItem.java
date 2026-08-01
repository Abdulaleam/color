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

public class PauseItem extends Item {
    public PauseItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){
        if (!world.isClient){
            stopper(world,user);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context){
        World world = context.getWorld();
        if (!world.isClient) {
            stopper(world, context.getPlayer());
        }
        return ActionResult.SUCCESS;
    }
    private void stopper(World world, PlayerEntity user){
        if (!Color.gameActive){
            if (user != null) {
                user.sendMessage(Text.literal("The game isn't even Running bro, did you even start it").formatted(Formatting.BOLD,Formatting.LIGHT_PURPLE),true);
            }
            return;
        }
        Color.gameActive = false;
        Color.currentColor = null;
        world.getServer().getPlayerManager().broadcast(
                Text.literal("Don't Touch The Color Has Stopped").formatted(Formatting.BOLD, Formatting.RED),false
        );
    }
}
