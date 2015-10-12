package com.jfixby.util.terain.test.api.palette;


public interface TerrainPaletteSpecs {

	void addTerrainBlock(TerrainType terrain_type);

	TerrainType getTerrainBlock(int i);

	int size();

	void addTerrainBlocks(java.util.Collection<TerrainType> javaList);

}
