package com.jfixby.utl.pizza.api;

public interface PizzaLandscapeFactory {

	PizzaLandscapeSpecs newLandscapeSpecs();

	<Q> PizzaLandscape<Q> newPizzaLandscape(PizzaLandscapeSpecs landscape_specs);

}
