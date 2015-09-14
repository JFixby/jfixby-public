package com.jfixby.r3.ext.terrain.api;

public interface TerrainPaletteSpecs {

	void addTerrainBlock(TerrainBlock terrain_type);

	TerrainBlock getTerrainBlock(int i);

	int size();

	void addTerrainBlocks(java.util.Collection<TerrainBlock> javaList);

}
