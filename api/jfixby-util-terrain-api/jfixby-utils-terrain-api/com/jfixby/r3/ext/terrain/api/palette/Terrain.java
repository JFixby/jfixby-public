package com.jfixby.r3.ext.terrain.api.palette;

import com.jfixby.cmns.api.components.ComponentInstaller;
import com.jfixby.r3.ext.terrain.api.landscape.LandscapeFactory;

public class Terrain {
	static private ComponentInstaller<TerrainComponent> componentInstaller = new ComponentInstaller<TerrainComponent>(
			"Terrain");

	public static final void installComponent(
			TerrainComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final TerrainComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final TerrainComponent component() {
		return componentInstaller.getComponent();
	}

	public static TerrainPaletteFactory getPaletteFactory() {
		return invoke().getPaletteFactory();
	}

	public static LandscapeFactory getLandscapeFactory() {
		return invoke().getLandscapeFactory();
	}
}
