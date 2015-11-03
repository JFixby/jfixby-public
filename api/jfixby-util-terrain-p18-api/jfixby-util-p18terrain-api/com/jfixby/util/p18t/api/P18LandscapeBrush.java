package com.jfixby.util.p18t.api;

import com.jfixby.cmns.api.floatn.FixedFloat3;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public interface P18LandscapeBrush {

	void setFabric(Fabric fabric);

	P18LandscapePointer pointAt(double terrain_x, double terrain_y,
			double terrain_z);

	P18LandscapePointer pointAt(FixedFloat3 terrain_xyz);

	void applyPaint();

}
