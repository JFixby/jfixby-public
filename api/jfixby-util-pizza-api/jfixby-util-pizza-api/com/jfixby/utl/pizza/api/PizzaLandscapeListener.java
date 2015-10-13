package com.jfixby.utl.pizza.api;

public interface PizzaLandscapeListener {

	void onBlockRemove(PizzaTile block);

	void onBlockAdd(PizzaTile block);

	void onBlockFocus(PizzaTile block);

}
