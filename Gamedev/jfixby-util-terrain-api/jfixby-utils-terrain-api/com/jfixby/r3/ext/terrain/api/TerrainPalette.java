package com.jfixby.r3.ext.terrain.api;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;

public interface TerrainPalette {

	public void print();

	public Collection<TerrainBlock> listBlocks(AssetID tile_id);
	public Collection<TerrainBlock> listAllBlocks();

	// public TerrainMeasurementConstant getMinZ();

	// public TerrainMeasurementConstant getMaxZ();

}
