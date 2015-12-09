package com.jfixby.util.p18t.api;

import com.jfixby.cmns.api.ComponentInstaller;

public class P18Terrain {

	static private ComponentInstaller<P18TerrainComponent> componentInstaller = new ComponentInstaller<P18TerrainComponent>(
			"P18Terrain");

	public static final void installComponent(
			P18TerrainComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final P18TerrainComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final P18TerrainComponent component() {
		return componentInstaller.getComponent();
	}

}
