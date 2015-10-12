package com.jfixby.r3.ext.terrain.api.palette;

import com.jfixby.r3.ext.terrain.api.landscape.LandscapeFactory;

public interface TerrainComponent {

	TerrainPaletteFactory getPaletteFactory();

	LandscapeFactory getLandscapeFactory();

}
