package com.jfixby.util.p18t.api;

import com.jfixby.r3.ext.api.patch18.grid.CellPosition;

public interface P18LandscapeListener {

	void onBlockRemove(P18TerrainLandscapeBlock<CellPosition> block);

	void onBlockAdd(P18TerrainLandscapeBlock<CellPosition> block);

}
