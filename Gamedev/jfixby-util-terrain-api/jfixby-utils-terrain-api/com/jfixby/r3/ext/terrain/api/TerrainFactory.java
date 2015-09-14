package com.jfixby.r3.ext.terrain.api;

public interface TerrainFactory {

	TerrainMeasurement newMeasurement(double d);

	TerrainMeasurement newMeasurement();

	TerrainBlockSpecs newTerrainBlockSpecs();

	TerrainBlock newTerrainBlock(TerrainBlockSpecs terrain_type_specs);

	TerrainPaletteSpecs newTerrainPaletteSpecs();

	TerrainPalette newTerrainPalette(TerrainPaletteSpecs palette_specs);

	Patch18LandscapeSpecs newLandscapeSpecs();

	Patch18Landscape newLandscape(Patch18LandscapeSpecs landscape_specs);

}
