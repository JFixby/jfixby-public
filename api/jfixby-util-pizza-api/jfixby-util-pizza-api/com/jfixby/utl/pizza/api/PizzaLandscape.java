package com.jfixby.utl.pizza.api;

public interface PizzaLandscape {

	PizzaBrush getBrush();

	void setLandscapeListener(PizzaLandscapeListener listener);

	LandscapeActiveArea getActiveArea();

	void print();

}
