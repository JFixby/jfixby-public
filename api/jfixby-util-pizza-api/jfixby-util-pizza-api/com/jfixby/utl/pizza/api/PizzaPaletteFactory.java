package com.jfixby.utl.pizza.api;

public interface PizzaPaletteFactory {

	PizzaPaletteSpecs newPizzaPaletteSpecs();

	PizzaPalette newPizzaPalette(PizzaPaletteSpecs specs);

}
