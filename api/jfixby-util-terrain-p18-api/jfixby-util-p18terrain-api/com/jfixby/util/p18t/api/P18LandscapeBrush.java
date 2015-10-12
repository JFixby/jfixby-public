package com.jfixby.util.p18t.api;

import com.jfixby.cmns.api.math.FixedInt2;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public interface P18LandscapeBrush {

	void begin();

	void setFabric(Fabric fabric);

	void applyPaintAt(long grid_block_x, long grid_block_y);

	void applyPaintAt(FixedInt2 grid_block_position);

	P18LandscapeBrushApplicationResult end();

	P18LandscapePointer pointAt(double terrain_x, double terrain_y,
			double terrain_z);

}
