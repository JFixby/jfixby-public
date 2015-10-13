package com.jfixby.utl.pizza.api;

public interface PizzaLandscapeListener<Q> {

	void onBlockRemove(PizzaBlock<Q> block);

	void onBlockAdd(PizzaBlock<Q> block);

	void onBlockFocus(PizzaBlock<Q> block);

}
