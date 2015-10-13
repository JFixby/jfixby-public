package com.jfixby.utl.pizza.api;

import com.jfixby.util.p18t.api.P18TerrainTypeVariation;

public interface PizzaTilesList {

	PizzaTileType findPizzaType(P18TerrainTypeVariation var);

	int size();

	PizzaTileType getElementAt(int index);

}
