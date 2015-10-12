package com.jfixby.utl.pizza.api;

public interface PizzaLandscapeSpecs {

	void setActiveArea(float canvas_origin_x, float canvas_origin_y,
			float canvas_width, float canvas_height);

	void setPalette(PizzaPalette pizza_palette);

	PizzaPalette getPalette();

	public float getActiveAreaOriginX();

	public float getActiveAreaOriginY();

	public float getActiveAreaWidth();

	public float getActiveAreaHeight();

}
