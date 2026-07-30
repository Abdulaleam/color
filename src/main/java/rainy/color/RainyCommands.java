package rainy.color;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Random;

import static rainy.color.Color.gameActive;

public class RainyCommands {
    private static final Random RANDOM = new Random();

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            dispatcher.register(CommandManager.literal("start")
                    .executes(ctx -> {
                        MinecraftServer server = ctx.getSource().getServer();

                        if (Color.gameActive) {
                            ctx.getSource().sendFeedback(() ->
                                    Text.literal("The game is already running ;-; , did you even start it").formatted(Formatting.YELLOW), false);
                            return 0;
                        }

                        Color.gameActive = true;
                        Color.tickCounter = 0;
                        pickNewColor();

                        server.getPlayerManager().broadcast(
                                Text.literal("=== Don't Touch The Color HAS STARTED! ===")
                                        .formatted(Formatting.GOLD, Formatting.BOLD),
                                false);
                        announceColor(server);
                        return 1;
                    }));

            dispatcher.register(CommandManager.literal("stop")
                    .executes(ctx -> {
                        if (!Color.gameActive) {
                            ctx.getSource().sendFeedback(() ->
                                    Text.literal("The game isn't running gng , wake up!!").formatted(Formatting.YELLOW), false);
                            return 0;
                        }

                        Color.gameActive = false;
                        Color.currentColor = null;
                        ctx.getSource().getServer().getPlayerManager().broadcast(
                                Text.literal("Don't Touch the Color has stopped , aww dude :( ").formatted(Formatting.GRAY),
                                false);
                        return 1;
                    }));
        });
    }
    static void pickNewColor() {
        DyeColor[] colors = DyeColor.values();
        DyeColor next;
        do {
            next = colors[RANDOM.nextInt(colors.length)];
        } while (next == Color.currentColor && colors.length > 1);
        Color.currentColor = next;
    }
    static void announceColor(MinecraftServer server){
        DyeColor color = Color.currentColor;
        Formatting formatting = Color.formattingFor(color);
        Text msg = Text.literal(" \u26A0 DON'T TOUCH" + Color.prettyName(color).toUpperCase() + "! \u26A0")
                .formatted(Formatting.BOLD);
        server.getPlayerManager().broadcast(msg, false);
    }
}