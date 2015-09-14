package com.jfixby.r3.ext.terrain.api;

import com.jfixby.cmns.api.assets.AssetID;

public interface TerrainBlock {

	public AssetID getName();

	public TerrainMeasurementConstant getHeight();

	public TerrainMeasurementConstant getAltitude();

	public TerrainMeasurementConstant getXWidth();

	public TerrainMeasurementConstant getYWidth();

}
