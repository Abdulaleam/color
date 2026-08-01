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
import rainy.color.item.RainyItems;

import java.text.Normalizer;
import java.util.*;

public class Color implements ModInitializer {
	public static final String MOD_ID = "color";
	public static final int INTERVAL_TICKS = 20 * 10;
	private static final String[] COLORED_SUFFIXES = {
			"_wool","_concrete", "_terracotta","_carpet","_stained_glass","_stained_glass_pane","_glazed_terracotta",
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
		  }),
		  Map.entry(DyeColor.MAGENTA, new Block[]{}),
		  Map.entry(DyeColor.LIGHT_BLUE, new Block[]{
				  Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE
		  }),
		  Map.entry(DyeColor.YELLOW, new Block[]{
				  Blocks.GOLD_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks. GOLD_ORE,
				  Blocks.HAY_BLOCK, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.BEE_NEST, Blocks.BEEHIVE
		  }),
		  Map.entry(DyeColor.LIME, new Block[]{
				  Blocks.SLIME_BLOCK
		  }),
		  Map.entry(DyeColor.PINK, new Block[]{
				  Blocks.CHERRY_LOG, Blocks.CHERRY_PLANKS, Blocks.CHERRY_PRESSURE_PLATE, Blocks.CHERRY_LEAVES
		  }),
		  Map.entry(DyeColor.GRAY, new Block[]{
				  Blocks.STONE, Blocks.COBBLESTONE, Blocks.ANDESITE, Blocks.GRAVEL, Blocks.DEEPSLATE
				  , Blocks.COBBLED_DEEPSLATE, Blocks.TUFF
		  }),
		  Map.entry(DyeColor.LIGHT_GRAY, new Block[]{
				  Blocks.IRON_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.IRON_ORE, Blocks.CALCITE, Blocks.DIORITE
				  , Blocks.POLISHED_ANDESITE, Blocks.SMOOTH_STONE
		  }),
		  Map.entry(DyeColor.CYAN, new Block[]{
				  Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE, Blocks.WARPED_NYLIUM,
				  Blocks.WARPED_BUTTON, Blocks.WARPED_PLANKS, Blocks.WARPED_STEM
				  }
				  ),
		  Map.entry(DyeColor.PURPLE, new Block[]{
				  Blocks.AMETHYST_BLOCK, Blocks.BUDDING_AMETHYST, Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR, Blocks.CRYING_OBSIDIAN

		  }),
		  Map.entry(DyeColor.BROWN, new Block[]{
				  Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.ROOTED_DIRT,Blocks.MUD, Blocks.SOUL_SOIL, Blocks.SOUL_SAND
				  , Blocks.OAK_LOG, Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.MANGROVE_LOG, Blocks.SPRUCE_LOG,
				  Blocks.MANGROVE_PLANKS
		  }),
		  Map.entry(DyeColor.GREEN, new Block[]{
				  Blocks.EMERALD_BLOCK, Blocks.EMERALD_ORE, Blocks.MOSS_BLOCK, Blocks.MOSS_CARPET, Blocks.CACTUS, Blocks.MELON,
				  Blocks.GRASS_BLOCK
		  }),
		  Map.entry(DyeColor.RED, new Block[]{
				  Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_ORE, Blocks.NETHER_BRICKS, Blocks.RED_BED, Blocks.RED_SAND,
				  Blocks.REDSTONE_WIRE, Blocks.CRIMSON_DOOR, Blocks.CRIMSON_PLANKS, Blocks.NETHER_WART_BLOCK,
				  Blocks.RED_MUSHROOM
		  }),
		  Map.entry(DyeColor.BLUE, new Block[]{
				  Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE
		  }),
		  Map.entry(DyeColor.BLACK, new Block[]{
				  Blocks.COAL_BLOCK, Blocks.COAL_ORE, Blocks.NETHERITE_BLOCK, Blocks.OBSIDIAN, Blocks.BASALT,
				  Blocks.POLISHED_ANDESITE, Blocks.POLISHED_BASALT, Blocks.BLACKSTONE, Blocks.ANCIENT_DEBRIS, Blocks.DARK_OAK_DOOR
				  , Blocks.DARK_OAK_PLANKS
		  })

  );

	private final Random random = new Random();
	public static boolean gameActive = false;
	public static int tickCounter = 0;
	public static DyeColor currentColor = null;


	@Override
	public void onInitialize() {
		RainyItems.registerRainyItems();
		RainyCommands.register();
		RainyTickHandler.register();


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
		Block[] extras = EXTRA_BLOCKS.get(color);
		if (extras != null) {
			Collections.addAll(blocks, extras);
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


