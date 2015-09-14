package com.jfixby.r3.ext.terrain.api;

public interface Terrain {

	TerrainMeasurementConstant getXPosition();

	TerrainMeasurementConstant getYPosition();

	TerrainMeasurementConstant getZYPosition();

	TerrainXYZPosition getTerrainXYZPosition();

	TerrainVariation getTerrainVariation();

}
