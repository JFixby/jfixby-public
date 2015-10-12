package com.jfixby.util.p18t.api;

public interface P18TerrainPaletteFactory {

	P18TerrainPaletteSpecs newP18TerrainPaletteSpecs();

	P18TerrainPalette newP18TerrainPalette(P18TerrainPaletteSpecs specs);

}
