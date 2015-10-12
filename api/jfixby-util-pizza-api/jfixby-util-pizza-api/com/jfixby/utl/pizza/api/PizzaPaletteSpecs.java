package com.jfixby.utl.pizza.api;

import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.p18t.api.P18TerrainPalette;

public interface PizzaPaletteSpecs {

	void setP18TerrainPalette(P18TerrainPalette p18TerrainPalette);

	void setIsoTransform(IsoTransform transform);

	P18TerrainPalette getP18TerrainPalette();

	IsoTransform getIsoTransform();

}
