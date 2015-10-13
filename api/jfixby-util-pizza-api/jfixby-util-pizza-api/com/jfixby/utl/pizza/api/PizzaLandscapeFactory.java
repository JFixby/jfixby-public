package com.jfixby.utl.pizza.api;

public interface PizzaLandscapeFactory {

	PizzaLandscapeSpecs newLandscapeSpecs();

	PizzaLandscape newPizzaLandscape(PizzaLandscapeSpecs landscape_specs);

}
