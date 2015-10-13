package com.jfixby.util.terain.test.api.landscape;

import com.jfixby.util.terain.test.api.palette.TerrainPalette;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public interface Landscape<T> {

	void print();

	LandscapeOperationResult<T> newBlock(T keypoint, TerrainType terrain_type);

	TerrainPalette getPalette();

	TerrainBlock<T> getBlock(T keypoint);

}
