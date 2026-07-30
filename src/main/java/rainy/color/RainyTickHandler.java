package rainy.color;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.Set;

public class RainyTickHandler {
    public static void register(){
        ServerTickEvents.END_SERVER_TICK.register(RainyTickHandler::onTick);
    }
    private static void onTick(MinecraftServer server){
        if (!Color.gameActive){
            return;
        }
        Color.tickCounter++;
        if (Color.tickCounter >= Color.INTERVAL_TICKS){
            Color.tickCounter = 0;
            // add commands here
        }
        if (Color.currentColor != null) {
            checkPlayersForContact(server);
        }
    }
    private static void checkPlayersForContact(MinecraftServer server) {
        DyeColor currentColor = Color.currentColor;
        Set<Block> forbiddenBlocks = Color.blocksForColor(currentColor);
        if (forbiddenBlocks.isEmpty()) {
            return;
        }
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player.isSpectator() || !player.isAlive()) {
                continue;}
            if (isTouchingAny(player, forbiddenBlocks)) {
                player.kill();
                server.getPlayerManager().broadcast(
                        Text.literal(player.getName().getString() + " touched" + Color.prettyName(currentColor) + " and died")
                                .formatted(Formatting.RED),
                false);
            }
        }
    }
    private static boolean isTouchingAny(ServerPlayerEntity player, Set<Block>  forbiddenBlocks) {
        Box box = player.getBoundingBox().expand(0.05);
        BlockPos min = BlockPos.ofFloored(box.minX, box.minY, box.minZ);
        BlockPos max = BlockPos.ofFloored(box.maxX, box.maxY, box.maxZ);

        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    Block block = player.getWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (forbiddenBlocks.contains(block)) {
                        return true;
                    }
                }}}
                return false;
            }}
