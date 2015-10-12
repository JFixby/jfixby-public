package com.jfixby.util.p18t.api;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public interface P18LandscapeBrush {

	void begin();

	void setFabric(Fabric fabric);

	void applyPaintAt(double terrain_x, double terrain_y, double terrain_z);

	P18LandscapeBrushApplicationResult end();

}
