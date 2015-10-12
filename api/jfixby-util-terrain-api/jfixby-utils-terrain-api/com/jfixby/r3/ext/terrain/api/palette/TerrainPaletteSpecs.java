package com.jfixby.r3.ext.terrain.api.palette;


public interface TerrainPaletteSpecs {

	void addTerrainBlock(TerrainType terrain_type);

	TerrainType getTerrainBlock(int i);

	int size();

	void addTerrainBlocks(java.util.Collection<TerrainType> javaList);

}
