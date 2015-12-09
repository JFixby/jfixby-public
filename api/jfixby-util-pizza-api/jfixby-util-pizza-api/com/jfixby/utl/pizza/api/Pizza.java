package com.jfixby.utl.pizza.api;

import com.jfixby.cmns.api.ComponentInstaller;

public class Pizza {

	static private ComponentInstaller<PizzaComponent> componentInstaller = new ComponentInstaller<PizzaComponent>(
			"Pizza"); // P18 + Terrain + ISO = P18TerrainISO =~ Pizza

	public static final void installComponent(
			PizzaComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final PizzaComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final PizzaComponent component() {
		return componentInstaller.getComponent();
	}

}
