package com.jfixby.utl.pizza.api;

import com.jfixby.cmns.api.collections.Collection;

public interface PizzaLandscape<Q> {

	PizzaBrush getBrush();

	void setLandscapeListener(PizzaLandscapeListener<Q> listener);

	LandscapeActiveArea getActiveArea();

	void print();

	Collection<PizzaBlock<Q>> listAllBlocks();

}
