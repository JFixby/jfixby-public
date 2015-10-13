package com.jfixby.utl.pizza.api;

import com.jfixby.cmns.api.collections.Collection;

public interface PizzaLandscape {

	PizzaBrush getBrush();

	void setLandscapeListener(PizzaLandscapeListener listener);

	LandscapeActiveArea getActiveArea();

	void print();

	Collection<PizzaTile> listAllBlocks();

}
