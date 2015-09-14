package com.jfixby.r3.ext.terrain.api;


public interface Patch18LandscapeSpecs {

	void setTerrainPalette(TerrainPalette terrain_palette);

	TerrainPalette getTerrainPalette();

	U_LandscapeListener getLandscapeListener();

	void setLandscapeListener(U_LandscapeListener listener);

	// void setDimentions(int x_width, int y_width);

	// public int getXWidth();

	// public int getYWidth();

	TerrainMeasurementConstant getCellXWidth();

	TerrainMeasurementConstant getCellYWidth();

	void setCellXWidth(TerrainMeasurement block_x_width);

	void setCellYWidth(TerrainMeasurement block_y_width);

	// void setGridIndexOffsetX(int grid_offset_x);
	//
	// void setGridIndexOffsetY(int grid_offset_y);
	//
	// int getGridIndexOffsetX();
	//
	// int getGridIndexOffsetY();

}
