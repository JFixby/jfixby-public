package com.jfixby.utl.pizza.api;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public interface PizzaBrush {

	void setFabric(Fabric fabric);

	PizzaBrushPointer pointAtCanvas(double canvas_x, double canvas_y);

	PizzaBrushPointer pointAtCanvas(FixedFloat2 canvas_xy);

	public void applyPaint();

	// void applyPaintAtCanvas(FixedFloat3 terrain_xyz);
	//
	// void applyPaintAtCanvas(double terrain_x, double terrain_y, double
	// terrain_z);

}
