package rainy.color;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Color implements ModInitializer {
	public static final String MOD_ID = "color";
	private static final int INTERVAL_TICKS = 20 * 90;
	private static final String[] COLORED_SUFFIXES = {
			"wool","_concrete", "_terracotta","_carpet","_stained_glass"};
	private final Random random = new Random();
	private boolean gameActive = false;
	private int tickCounter = 0;
	private DyeColor currentColor = null;


	@Override
	public void onInitialize() {

	}

}
