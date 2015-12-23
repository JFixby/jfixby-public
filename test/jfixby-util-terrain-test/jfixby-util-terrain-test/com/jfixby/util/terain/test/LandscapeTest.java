package com.jfixby.util.terain.test;

import com.jfixby.cmns.api.math.Int2;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.cmns.desktop.DesktopAssembler;
import com.jfixby.examples.wdgs.WDGS_P18Terrain_Palette;
import com.jfixby.util.terain.test.api.landscape.Landscape;
import com.jfixby.util.terain.test.api.landscape.LandscapeFactory;
import com.jfixby.util.terain.test.api.landscape.LandscapeSpecs;
import com.jfixby.util.terain.test.api.palette.Terrain;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;

public class LandscapeTest {

	public static void main(String[] args) {
		DesktopAssembler.setup();

		LandscapeFactory factory = Terrain.getLandscapeFactory();

		LandscapeSpecs landscape_specs = factory.newLandscapeSpecs();
		TerrainPalette palette = WDGS_P18Terrain_Palette.P18_TERRAIN_PALETTE
				.getTerrainPalette();
		landscape_specs.setPalette(palette);

		Landscape<Int2> landscape = factory.newLandscape(landscape_specs);

		landscape.newBlock(IntegerMath.newInt2(0, 0), palette.listAllBlocks()
				.getElementAt(0));

		landscape.print();

	}
}
