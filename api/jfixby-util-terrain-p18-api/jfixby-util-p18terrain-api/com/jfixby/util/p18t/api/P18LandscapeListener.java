package com.jfixby.util.p18t.api;

import com.jfixby.cmns.api.math.FixedInt2;

public interface P18LandscapeListener {

	void onBlockRemove(P18TerrainBlock<FixedInt2> block);

	void onBlockAdd(P18TerrainBlock<FixedInt2> block);

	void onBlockFocus(P18TerrainBlock<FixedInt2> block);

}
