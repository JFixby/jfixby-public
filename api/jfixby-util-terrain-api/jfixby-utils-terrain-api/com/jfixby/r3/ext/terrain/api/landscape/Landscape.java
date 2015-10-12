package com.jfixby.r3.ext.terrain.api.landscape;

import com.jfixby.r3.ext.terrain.api.palette.TerrainPalette;
import com.jfixby.r3.ext.terrain.api.palette.TerrainType;

public interface Landscape<T> {

	void print();

	TerrainBlock<T> newBlock(T keypoint, TerrainType terrain_type);

	TerrainPalette getPalette();

}
