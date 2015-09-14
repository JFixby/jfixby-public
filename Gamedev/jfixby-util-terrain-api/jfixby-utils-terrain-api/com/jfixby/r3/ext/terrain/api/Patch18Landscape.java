package com.jfixby.r3.ext.terrain.api;


public interface Patch18Landscape {

	TerrainPalette getPalette();

	void setTerrain(long cell_x_index, long cell_y_index,
			TerrainVariation variation);

	public TerrainMeasurementConstant getCellWidthX();

	public TerrainMeasurementConstant getCellWidthY();

//	public TerrainMeasurementConstant getAreaWidthX();

//	public TerrainMeasurementConstant getAreaWidthY();

	void print();

}
