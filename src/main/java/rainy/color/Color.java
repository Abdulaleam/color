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
import rainy.color.item.RainyItemGroups;
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
					Blocks.BONE_BLOCK, Blocks.SEA_LANTERN, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS,
					Blocks.WHITE_TERRACOTTA, Blocks.WHITE_CONCRETE, Blocks.WHITE_WOOL,
					Blocks.DIORITE, Blocks.CALCITE, Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR,
					Blocks.SMOOTH_QUARTZ, Blocks.QUARTZ_BRICKS, Blocks.WHITE_GLAZED_TERRACOTTA,
					Blocks.WHITE_CONCRETE_POWDER, Blocks.WHITE_BED, Blocks.WHITE_CARPET,
					Blocks.WHITE_STAINED_GLASS, Blocks.WHITE_BANNER, Blocks.WHITE_CANDLE, Blocks.LILY_OF_THE_VALLEY,
					Blocks.OXEYE_DAISY, Blocks.AZURE_BLUET, Blocks.WHITE_TULIP, Blocks.CHISELED_TUFF_BRICKS,
					Blocks.DIORITE_STAIRS, Blocks.DIORITE_WALL, Blocks.POLISHED_DIORITE, Blocks.QUARTZ_STAIRS,
					Blocks.SMOOTH_QUARTZ_SLAB, Blocks.END_STONE_BRICKS, Blocks.SCAFFOLDING
			}),
			Map.entry(DyeColor.ORANGE, new Block[]{
					Blocks.COPPER_BLOCK, Blocks.RAW_COPPER_BLOCK, Blocks.CUT_COPPER, Blocks.PUMPKIN,
					Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MAGMA_BLOCK, Blocks.HONEY_BLOCK, Blocks.HONEYCOMB_BLOCK,
					Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS, Blocks.ACACIA_DOOR, Blocks.ACACIA_FENCE_GATE,
					Blocks.ACACIA_LEAVES, Blocks.ORANGE_TERRACOTTA, Blocks.ORANGE_CONCRETE, Blocks.ORANGE_WOOL,
					Blocks.RED_SAND, Blocks.NETHER_WART_BLOCK, Blocks.TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA,
					Blocks.ORANGE_CONCRETE_POWDER, Blocks.WEATHERED_COPPER, Blocks.EXPOSED_COPPER,
					Blocks.ORANGE_BED, Blocks.ORANGE_CARPET, Blocks.ORANGE_STAINED_GLASS, Blocks.ORANGE_BANNER,
					Blocks.ORANGE_CANDLE, Blocks.ORANGE_TULIP, Blocks.TORCHFLOWER, Blocks.CUT_COPPER_STAIRS,
					Blocks.COPPER_BULB, Blocks.COPPER_DOOR, Blocks.COPPER_GRATE, Blocks.COPPER_TRAPDOOR,
					Blocks.RED_SANDSTONE, Blocks.ACACIA_TRAPDOOR
			}),
			Map.entry(DyeColor.MAGENTA, new Block[]{
					Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR, Blocks.AMETHYST_BLOCK, Blocks.BUDDING_AMETHYST,
					Blocks.CRYING_OBSIDIAN, Blocks.MAGENTA_TERRACOTTA, Blocks.MAGENTA_CONCRETE, Blocks.MAGENTA_WOOL,
					Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT, Blocks.SHULKER_BOX, Blocks.MAGENTA_GLAZED_TERRACOTTA,
					Blocks.MAGENTA_CONCRETE_POWDER, Blocks.ALLIUM, Blocks.MAGENTA_BED, Blocks.MAGENTA_CARPET,
					Blocks.MAGENTA_STAINED_GLASS, Blocks.MAGENTA_BANNER, Blocks.MAGENTA_CANDLE, Blocks.PURPUR_STAIRS,
					Blocks.PURPLE_SHULKER_BOX, Blocks.AMETHYST_CLUSTER, Blocks.LARGE_AMETHYST_BUD
			}),
			Map.entry(DyeColor.LIGHT_BLUE, new Block[]{
					Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE,
					Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.LIGHT_BLUE_CONCRETE, Blocks.LIGHT_BLUE_WOOL,
					Blocks.PRISMARINE, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_CONCRETE_POWDER,
					Blocks.FROSTED_ICE, Blocks.SOUL_FIRE, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_BLUE_CARPET,
					Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_BLUE_BANNER, Blocks.LIGHT_BLUE_CANDLE,
					Blocks.BLUE_ORCHID, Blocks.SOUL_LANTERN, Blocks.SOUL_TORCH
			}),
			Map.entry(DyeColor.YELLOW, new Block[]{
					Blocks.GOLD_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks.GOLD_ORE,
					Blocks.HAY_BLOCK, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.BEE_NEST, Blocks.BEEHIVE,
					Blocks.YELLOW_TERRACOTTA, Blocks.YELLOW_CONCRETE, Blocks.YELLOW_WOOL,
					Blocks.SAND, Blocks.END_ROD, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.YELLOW_CONCRETE_POWDER,
					Blocks.OCHRE_FROGLIGHT, Blocks.SUNFLOWER, Blocks.END_STONE, Blocks.YELLOW_BED,
					Blocks.YELLOW_CARPET, Blocks.YELLOW_STAINED_GLASS, Blocks.YELLOW_BANNER, Blocks.YELLOW_CANDLE,
					Blocks.DANDELION, Blocks.BAMBOO_BLOCK, Blocks.BAMBOO, Blocks.NOTE_BLOCK,
					Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE
			}),
			Map.entry(DyeColor.LIME, new Block[]{
					Blocks.SLIME_BLOCK, Blocks.LIME_TERRACOTTA, Blocks.LIME_CONCRETE, Blocks.LIME_WOOL,
					Blocks.MELON, Blocks.SEA_PICKLE, Blocks.BIRCH_LEAVES, Blocks.LIME_GLAZED_TERRACOTTA,
					Blocks.LIME_CONCRETE_POWDER, Blocks.PEARLESCENT_FROGLIGHT, Blocks.LIME_BED, Blocks.LIME_CARPET,
					Blocks.LIME_STAINED_GLASS, Blocks.LIME_BANNER, Blocks.LIME_CANDLE, Blocks.MELON_STEM,
					Blocks.TARGET
			}),
			Map.entry(DyeColor.PINK, new Block[]{
					Blocks.CHERRY_LOG, Blocks.CHERRY_PLANKS, Blocks.CHERRY_PRESSURE_PLATE, Blocks.CHERRY_LEAVES,
					Blocks.PINK_TERRACOTTA, Blocks.PINK_CONCRETE, Blocks.PINK_WOOL,
					Blocks.BRAIN_CORAL_BLOCK, Blocks.BRAIN_CORAL, Blocks.MAGENTA_WOOL, Blocks.PINK_GLAZED_TERRACOTTA,
					Blocks.PINK_CONCRETE_POWDER, Blocks.PINK_PETALS, Blocks.PINK_BED, Blocks.PINK_CARPET,
					Blocks.PINK_STAINED_GLASS, Blocks.PINK_BANNER, Blocks.PINK_CANDLE, Blocks.CHERRY_SAPLING,
					Blocks.CHERRY_FENCE, Blocks.CHERRY_DOOR, Blocks.CHERRY_TRAPDOOR, Blocks.CHERRY_SLAB,
					Blocks.PINK_TULIP, Blocks.BRAIN_CORAL_FAN
			}),
			Map.entry(DyeColor.GRAY, new Block[]{
					Blocks.STONE, Blocks.COBBLESTONE, Blocks.ANDESITE, Blocks.GRAVEL, Blocks.DEEPSLATE,
					Blocks.COBBLED_DEEPSLATE, Blocks.TUFF, Blocks.POLISHED_ANDESITE,
					Blocks.GRAY_TERRACOTTA, Blocks.GRAY_CONCRETE, Blocks.GRAY_WOOL, Blocks.GRAY_GLAZED_TERRACOTTA,
					Blocks.GRAY_CONCRETE_POWDER, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES,
					Blocks.GRAY_BED, Blocks.GRAY_CARPET, Blocks.GRAY_STAINED_GLASS, Blocks.GRAY_BANNER,
					Blocks.GRAY_CANDLE, Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS,
					Blocks.ANDESITE_STAIRS, Blocks.ANDESITE_WALL, Blocks.CHISELED_DEEPSLATE, Blocks.REINFORCED_DEEPSLATE
			}),
			Map.entry(DyeColor.LIGHT_GRAY, new Block[]{
					Blocks.IRON_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.IRON_ORE, Blocks.CALCITE, Blocks.DIORITE,
					Blocks.POLISHED_ANDESITE, Blocks.SMOOTH_STONE, Blocks.STONE,
					Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_CONCRETE, Blocks.LIGHT_GRAY_WOOL,
					Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.POLISHED_DIORITE,
					Blocks.MUSHROOM_STEM, Blocks.LIGHT_GRAY_BED, Blocks.LIGHT_GRAY_CARPET, Blocks.LIGHT_GRAY_STAINED_GLASS,
					Blocks.LIGHT_GRAY_BANNER, Blocks.LIGHT_GRAY_CANDLE, Blocks.STRIPPED_BIRCH_LOG, Blocks.CLAY,
					Blocks.INFESTED_STONE, Blocks.TUFF_BRICKS, Blocks.CUT_COPPER_SLAB
			}),
			Map.entry(DyeColor.CYAN, new Block[]{
					Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE, Blocks.WARPED_NYLIUM,
					Blocks.WARPED_BUTTON, Blocks.WARPED_PLANKS, Blocks.WARPED_STEM,
					Blocks.DIAMOND_BLOCK, Blocks.CYAN_TERRACOTTA, Blocks.CYAN_CONCRETE, Blocks.CYAN_WOOL,
					Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.CYAN_CONCRETE_POWDER, Blocks.WARPED_WART_BLOCK,
					Blocks.WARPED_HYPHAE, Blocks.CYAN_BED, Blocks.CYAN_CARPET, Blocks.CYAN_STAINED_GLASS,
					Blocks.CYAN_BANNER, Blocks.CYAN_CANDLE, Blocks.WARPED_FENCE, Blocks.WARPED_DOOR,
					Blocks.WARPED_TRAPDOOR, Blocks.WARPED_FUNGUS, Blocks.TUBE_CORAL_FAN
			}),
			Map.entry(DyeColor.PURPLE, new Block[]{
					Blocks.AMETHYST_BLOCK, Blocks.BUDDING_AMETHYST, Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR, Blocks.CRYING_OBSIDIAN,
					Blocks.PURPLE_TERRACOTTA, Blocks.PURPLE_CONCRETE, Blocks.PURPLE_WOOL,
					Blocks.ENDER_CHEST, Blocks.MAGENTA_CONCRETE, Blocks.PURPLE_GLAZED_TERRACOTTA,
					Blocks.PURPLE_CONCRETE_POWDER, Blocks.MYCELIUM, Blocks.OBSIDIAN, Blocks.PURPLE_BED,
					Blocks.PURPLE_CARPET, Blocks.PURPLE_STAINED_GLASS, Blocks.PURPLE_BANNER, Blocks.PURPLE_CANDLE,
					Blocks.END_PORTAL_FRAME, Blocks.REPEATING_COMMAND_BLOCK, Blocks.ENCHANTING_TABLE
			}),
			Map.entry(DyeColor.BROWN, new Block[]{
					Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.ROOTED_DIRT, Blocks.MUD, Blocks.SOUL_SOIL, Blocks.SOUL_SAND,
					Blocks.OAK_LOG, Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.MANGROVE_LOG, Blocks.SPRUCE_LOG,
					Blocks.MANGROVE_PLANKS, Blocks.BROWN_TERRACOTTA, Blocks.BROWN_CONCRETE, Blocks.BROWN_WOOL,
					Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS,
					Blocks.DARK_OAK_LOG, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.BROWN_CONCRETE_POWDER,
					Blocks.PACKED_MUD, Blocks.MUD_BRICKS, Blocks.CHISELED_BOOKSHELF, Blocks.BOOKSHELF,
					Blocks.BAMBOO_PLANKS, Blocks.BROWN_BED, Blocks.BROWN_CARPET, Blocks.BROWN_STAINED_GLASS,
					Blocks.BROWN_BANNER, Blocks.BROWN_CANDLE, Blocks.FARMLAND, Blocks.FLOWER_POT, Blocks.BARREL,
					Blocks.CRAFTING_TABLE, Blocks.COMPOSTER, Blocks.CHEST, Blocks.OAK_FENCE, Blocks.OAK_STAIRS
			}),
			Map.entry(DyeColor.GREEN, new Block[]{
					Blocks.EMERALD_BLOCK, Blocks.EMERALD_ORE, Blocks.MOSS_BLOCK, Blocks.MOSS_CARPET, Blocks.CACTUS, Blocks.MELON,
					Blocks.GRASS_BLOCK, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.JUNGLE_LEAVES,
					Blocks.DARK_OAK_LEAVES, Blocks.MANGROVE_LEAVES, Blocks.AZALEA_LEAVES, Blocks.FLOWERING_AZALEA_LEAVES,
					Blocks.GREEN_TERRACOTTA, Blocks.GREEN_CONCRETE, Blocks.GREEN_WOOL, Blocks.SLIME_BLOCK,
					Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.GREEN_CONCRETE_POWDER, Blocks.VINE, Blocks.SEAGRASS,
					Blocks.LILY_PAD, Blocks.MELON_STEM, Blocks.GREEN_BED, Blocks.GREEN_CARPET, Blocks.GREEN_STAINED_GLASS,
					Blocks.GREEN_BANNER, Blocks.GREEN_CANDLE, Blocks.SUGAR_CANE,
					Blocks.KELP, Blocks.KELP_PLANT, Blocks.CHORUS_PLANT, Blocks.FERN, Blocks.LARGE_FERN,
					Blocks.TALL_GRASS, Blocks.SHORT_GRASS
			}),
			Map.entry(DyeColor.RED, new Block[]{
					Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_ORE, Blocks.NETHER_BRICKS, Blocks.RED_BED, Blocks.RED_SAND,
					Blocks.REDSTONE_WIRE, Blocks.CRIMSON_DOOR, Blocks.CRIMSON_PLANKS, Blocks.NETHER_WART_BLOCK,
					Blocks.RED_MUSHROOM, Blocks.RED_TERRACOTTA, Blocks.RED_CONCRETE, Blocks.RED_WOOL,
					Blocks.COPPER_BLOCK, Blocks.RED_GLAZED_TERRACOTTA, Blocks.RED_CONCRETE_POWDER,
					Blocks.CRIMSON_STEM, Blocks.CRIMSON_HYPHAE, Blocks.CRIMSON_NYLIUM, Blocks.NETHER_WART,
					Blocks.RED_MUSHROOM_BLOCK, Blocks.MANGROVE_ROOTS, Blocks.RED_CARPET, Blocks.RED_STAINED_GLASS,
					Blocks.RED_BANNER, Blocks.RED_CANDLE, Blocks.CRIMSON_FENCE, Blocks.CRIMSON_TRAPDOOR,
					Blocks.CRIMSON_FUNGUS, Blocks.FIRE_CORAL_BLOCK, Blocks.FIRE_CORAL, Blocks.RED_TULIP,
					Blocks.ROSE_BUSH, Blocks.POPPY, Blocks.NETHER_GOLD_ORE
			}),
			Map.entry(DyeColor.BLUE, new Block[]{
					Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.WATER,
					Blocks.BLUE_TERRACOTTA, Blocks.BLUE_CONCRETE, Blocks.BLUE_WOOL,
					Blocks.BLUE_ICE, Blocks.PRISMARINE_BRICKS, Blocks.BLUE_GLAZED_TERRACOTTA,
					Blocks.BLUE_CONCRETE_POWDER, Blocks.TUBE_CORAL_BLOCK, Blocks.TUBE_CORAL,
					Blocks.BLUE_BED, Blocks.BLUE_CARPET, Blocks.BLUE_STAINED_GLASS, Blocks.BLUE_BANNER,
					Blocks.BLUE_CANDLE, Blocks.CORNFLOWER, Blocks.CONDUIT, Blocks.BEACON
			}),
			Map.entry(DyeColor.BLACK, new Block[]{
					Blocks.COAL_BLOCK, Blocks.COAL_ORE, Blocks.NETHERITE_BLOCK, Blocks.OBSIDIAN, Blocks.BASALT,
					Blocks.POLISHED_ANDESITE, Blocks.POLISHED_BASALT, Blocks.BLACKSTONE, Blocks.ANCIENT_DEBRIS, Blocks.DARK_OAK_DOOR,
					Blocks.DARK_OAK_PLANKS, Blocks.BLACK_TERRACOTTA, Blocks.BLACK_CONCRETE, Blocks.BLACK_WOOL,
					Blocks.CRYING_OBSIDIAN, Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BLACK_CONCRETE_POWDER,
					Blocks.GILDED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICKS,
					Blocks.CHISELED_POLISHED_BLACKSTONE, Blocks.NETHERRACK, Blocks.WITHER_ROSE,
					Blocks.BLACK_BED, Blocks.BLACK_CARPET, Blocks.BLACK_STAINED_GLASS, Blocks.BLACK_BANNER,
					Blocks.BLACK_CANDLE, Blocks.DARK_OAK_LEAVES, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
					Blocks.END_PORTAL, Blocks.DRAGON_EGG, Blocks.RESPAWN_ANCHOR
			})
	);

	private final Random random = new Random();
	public static boolean gameActive = false;
	public static int tickCounter = 0;
	public static DyeColor currentColor = null;
    public static boolean colorLocked = false;

	@Override
	public void onInitialize() {
		RainyItemGroups.registerRainyItemGroups();
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


