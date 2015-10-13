package com.jfixby.r3.api.ui.unit.pizza;

import com.jfixby.utl.pizza.api.PizzaLandscape;

public interface PizzaRendererSpecs {

	void setLandscape(PizzaLandscape<PizzaTile> pizza_scape);

	PizzaLandscape<PizzaTile> getLandscape();

}
