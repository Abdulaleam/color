package rainy.color;

import net.fabricmc.api.ModInitializer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Color implements ModInitializer {
	public static final String MOD_ID = "color";
	private static final int INTERVAL_TICKS = 20 * 90;
	private static final String[] COLORED_SUFFIXES = {
			"_wool","_concrete", "_terracotta","_carpet","_stained_glass","_stained_glass_pane","glazed_terracotta",
	"_shulker_box","_bed"};

  private static final Map<DyeColor, Block[]> EXTRA_BLOCKS = Map.ofEntries(
		  Map.entry(DyeColor.WHITE, new Block[]{
				  Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW, Blocks.QUARTZ_BLOCK,
				  Blocks.BONE_BLOCK, Blocks.SEA_LANTERN, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS
		  }),
		  Map.entry(DyeColor.ORANGE, new Block[]{
				  Blocks.COPPER_BLOCK, Blocks.RAW_COPPER_BLOCK, Blocks.CUT_COPPER,Blocks.PUMPKIN,
				  Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MAGMA_BLOCK, Blocks.HONEY_BLOCK, Blocks.HONEYCOMB_BLOCK,
				  Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS , Blocks.ACACIA_DOOR , Blocks.ACACIA_FENCE_GATE
		  });
  )
	private final Random random = new Random();
	public static boolean gameActive = false;
	public static int tickCounter = 0;
	public static DyeColor currentColor = null;


	@Override
	public void onInitialize() {

	}
	public static Set<Block> blocksForColor(DyeColor color){
		Set<Block> blocks = new HashSet<>();
		String name = color.asString();
		for (String suffix : COLORED_SUFFIXES){
			Identifier id = Identifier.of("minecraft", name + suffix);
			Block block = Registries.BLOCK.get(id);
			if (block != Blocks.AIR){
				blocks.add(block);
			}
		}
		return blocks;
	}
	public static String prettyName(DyeColor color){
		return color.asString().replace('_',' ');

	}
	public static Formatting formattingFor(DyeColor color) {
		return switch (color) {
			case WHITE -> Formatting.WHITE;
			case ORANGE -> Formatting.GOLD;
			case MAGENTA -> Formatting.LIGHT_PURPLE;
			case LIGHT_BLUE -> Formatting.AQUA;
			case YELLOW -> Formatting.YELLOW;
			case LIME -> Formatting.GREEN;
			case PINK -> Formatting.LIGHT_PURPLE;
			case GRAY -> Formatting.DARK_GRAY;
			case CYAN -> Formatting.DARK_AQUA;
			case LIGHT_GRAY -> Formatting.GRAY;
			case BLUE -> Formatting.BLUE;
			case BLACK -> Formatting.BLACK;
			case RED -> Formatting.RED;
			case GREEN -> Formatting.DARK_GREEN;
			case BROWN -> Formatting.GOLD;
			case PURPLE -> Formatting.DARK_PURPLE;
		};
	}}


