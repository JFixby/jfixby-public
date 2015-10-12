package com.jfixby.r3.ext.terrain.api.palette;

import com.jfixby.cmns.api.gamedev.GameMeter;

public interface TerrainPaletteFactory {

	GameMeter newMeasurement(double d);

	GameMeter newMeasurement();

	TerrainTypeSpecs newTerrainBlockSpecs();

	TerrainType newTerrainBlock(TerrainTypeSpecs terrain_type_specs);

	TerrainPaletteSpecs newTerrainPaletteSpecs();

	TerrainPalette newTerrainPalette(TerrainPaletteSpecs palette_specs);

	
}
