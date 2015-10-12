package com.jfixby.r3.ext.p18t.red.test;

import com.jfixby.cmns.api.math.Int2;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.examples.wdgs.WDGS_P18Terrain_Palette;
import com.jfixby.r3.ext.terrain.api.landscape.Landscape;
import com.jfixby.r3.ext.terrain.api.landscape.LandscapeFactory;
import com.jfixby.r3.ext.terrain.api.landscape.LandscapeSpecs;
import com.jfixby.r3.ext.terrain.api.palette.Terrain;
import com.jfixby.r3.ext.terrain.api.palette.TerrainPalette;

public class LandscapeTest {

	public static void main(String[] args) {
		Setup.setup();

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
