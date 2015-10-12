package com.jfixby.util.terain.test.api.palette;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;

public interface TerrainPalette {

	public void print();

	public Collection<TerrainType> listBlocks(AssetID terrain_block_id);

	public Collection<TerrainType> listAllBlocks();

}
