package com.jfixby.r3.ext.p18t.red.test;

import com.jfixby.cmns.desktop.DesktopAssembler;
import com.jfixby.examples.wdgs.WDGS_P18Terrain_Palette;
import com.jfixby.util.p18t.api.P18Landscape;
import com.jfixby.util.p18t.api.P18LandscapeBrush;
import com.jfixby.util.p18t.api.P18LandscapeFactory;
import com.jfixby.util.p18t.api.P18LandscapePointer;
import com.jfixby.util.p18t.api.P18LandscapeSpecs;
import com.jfixby.util.p18t.api.P18Terrain;
import com.jfixby.util.p18t.api.P18TerrainPalette;

public class P18TerrainLandscapeTest {

	public static void main(String[] args) {
		DesktopAssembler.setup();

		P18LandscapeFactory landscape_factory = P18Terrain.invoke().getP18LandscapeFactory();

		P18LandscapeSpecs land_specs = landscape_factory.newP18LandscapeSpecs();
		P18TerrainPalette palette = WDGS_P18Terrain_Palette.P18_TERRAIN_PALETTE;
		land_specs.setPalette(palette);
		P18Landscape landscape = landscape_factory.newP18Landscape(land_specs);

		landscape.print();

		P18LandscapeBrush brush = landscape.getBrush();

		P18LandscapePointer pointer = brush.pointAt(1d, 1d, 1d);
		// FixedInt2 positon = pointer.getBlockGridPosition();
		brush.applyPaint();

	}

}
