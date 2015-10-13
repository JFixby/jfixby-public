package com.jfixby.utl.pizza.api;

public interface PizzaLandscape<Q> {

	PizzaBrush getBrush();

	void setLandscapeListener(PizzaLandscapeListener<Q> listener);

	LandscapeActiveArea getActiveArea();

	void print();

}
