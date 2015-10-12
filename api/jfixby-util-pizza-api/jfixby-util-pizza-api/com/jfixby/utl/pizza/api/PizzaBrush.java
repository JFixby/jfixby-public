package com.jfixby.utl.pizza.api;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public interface PizzaBrush {

	void begin();

	void setFabric(Fabric fabric);

	void applyPaintAtCanvas(float canvas_x, float canvas_y);

	PizzaBrushApplicationResult end();

}
